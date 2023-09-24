package menu;

import user.Authenticator;
import user.UserRoles;
import utils.Divider;
import utils.InputValidator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Menu implements Serializable {
    List<MenuEvent> eventList;

    public Menu() {
        eventList = new ArrayList<>();
    }

//    public void addEvent(String name, Runnable action) {
//        eventList.add(new MenuEvent(name, action));
//    }

    public void addEvent(MenuEvent event) {
        eventList.add(event);
    }

    public void addEvent(MenuEvent event, UserRoles... AccessLevel) {
        // Get all roles' names
        List<String> roles = Stream.of(AccessLevel).map(UserRoles::getValue).toList();

        // Get current user's role
        String userRole = Authenticator.loggedUser.getClass().getName();

        // Check if current user's role is in the list of roles
        if (roles.contains(userRole)) {
            eventList.add(event);
        }
    }

    public void display() {

        System.out.printf("%d)%-1s: %s%n", -1, "", "Exit");
        System.out.printf("%d)%-2s: %s%n", 0, "", "Back");

        int index = 1;
        for (MenuEvent e : eventList) {

            int width = 3 - String.valueOf(index).length();

            System.out.printf("%d)%-" + (width > 0 ? width : 1) + "s: %s%n", index, "", e.getDisplayName());
            index++;
        }

        Divider.printDivider();
    }

    public void run() {
        Scanner input = new Scanner(System.in);
        int choice;
        
        while (true) {
            display();
            System.out.print("Enter choice: ");
            choice = InputValidator.validateInt(i -> i >= -1 && i <= eventList.size());
            if (choice == -1) {
                if (InputValidator.validateBoolean("Are you sure you want to exit?")) {
                    input.close();
                    System.exit(0);
                }

                Divider.printDivider();
                continue;

            } else if (choice == 0) {
                return;

            }

            eventList.get(choice - 1).run();
            Divider.printDivider();
        }
    }
}

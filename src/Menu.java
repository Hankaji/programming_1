import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    List<MenuEvent> eventList;

    public Menu() {
        eventList = new ArrayList<>();
    }

    public void addEvent(String name, Runnable action) {
        eventList.add(new MenuEvent(name, action));
    }

    public void addEvent(MenuEvent event) {
        eventList.add(event);
    }

    public void display() {

        System.out.printf("%d)%-1s: %s%n", -1, "", "Exit");
        System.out.printf("%d)%-2s: %s%n", 0, "", "Back");

        int index = 1;
        for (MenuEvent e : eventList) {

            int width = 3 - String.valueOf(index).length();

            System.out.printf("%d)%-" + (width > 0 ? width : 1) + "s: %s%n", index, "", e.getName());
            index++;
        }

        System.out.println("--------------------");
    }

    public void run() {
        Scanner input = new Scanner(System.in);
        int choice = 1;
        
        while (choice > 0 || choice <= eventList.size()) {
            display();
            System.out.print("Enter choice: ");
            choice = input.nextInt();
            if (choice == -1) {
                input.close();
                System.exit(0);

            } else if (choice == 0) {
                return;

            } else if (choice < 0 || choice > eventList.size()) {
                System.out.println("Invalid choice.");
                continue;
            }

            eventList.get(choice - 1).run();
        }

        input.close();
    }
}

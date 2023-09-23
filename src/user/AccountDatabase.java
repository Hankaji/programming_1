package user;

import utils.Divider;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AccountDatabase {
    private static Map<String, User> usersInstance;

    public static Map<String, User> getInstance() {
        if (usersInstance == null) {
            File file = new File("src/data/accounts.txt");
            if (file.exists()) {
                usersInstance = loadData();
            } else usersInstance = new HashMap<>();
        }
        return usersInstance;
    }

    public static void addUser(User user) {
        usersInstance.put(user.getName(), user);
    }

    public static User getUser(String username) {
        return usersInstance.get(username);
    }

    public static void removeUser(String username) {
        usersInstance.remove(username);
    }

    public static void displayAllUsers() {
        Divider.printDivider();
        System.out.println("All users:");
        for (Map.Entry<String, User> entry : usersInstance.entrySet()) {
            System.out.println(entry.getValue());
            System.out.println();
        }
        Divider.printDivider();
    }

    @SuppressWarnings("unchecked")
    private static Map<String, User> loadData() {
        String filePath = "src/data/accounts.txt";
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {

            // Access the data in the loaded Map
            //System.out.println("Accounts loaded successfully:");

            return (Map<String, User>) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
            return null;
        }
    }

//    private static Map<String, User> loadData() {
//        String filePath = "src/data/accounts.txt";
//        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
//
//            String line;
//            while ((line = in.readLine()) != null) {
//                String[] data = line.split(",");
//                String username = data[0];
//                String password = data[1];
//                String role = data[2];
//
//
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public static void saveToFile() throws IOException {
        String filename = "src/data/accounts.txt";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(usersInstance);
        }
    }
}

package user;

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

    private static Map<String, User> loadData() {
        String filePath = "src/data/accounts.txt";
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {

            // Access the data in the loaded Map
//            System.out.println("Accounts loaded successfully:");

            return (Map<String, User>) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveToFile() throws IOException {
        String filename = "src/data/accounts.txt";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(usersInstance);
        }
    }
}

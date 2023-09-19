package user;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Authenticator {
//    public static void main(String[] args) throws FileNotFoundException {;
//        System.out.println(fetchData(new File("src/data/accounts.txt")));
//        System.out.println(authenticate("John", "1234"));
//    }

    public static boolean authenticate(String username, String password) throws FileNotFoundException {
        Map<String, User> userMap = AccountDatabase.getInstance();
        return userMap.containsKey(username) && userMap.get(username).getPassword().equals(password);
    }
}

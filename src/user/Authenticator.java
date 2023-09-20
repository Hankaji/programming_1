package user;

import data.Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Authenticator {
    public static User loggedUser;

    public static boolean authenticate(String username, String password) {
        Map<String, User> userMap = Database.accountDatabase;
        if (userMap.containsKey(username) && userMap.get(username).getPassword().equals(password)) {
            loggedUser = userMap.get(username);
            return true;
        } else return false;
    }
}

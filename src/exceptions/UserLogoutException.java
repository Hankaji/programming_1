package exceptions;

public class UserLogoutException extends Exception {
    public UserLogoutException() {
        super("User has logged out!");
    }
}

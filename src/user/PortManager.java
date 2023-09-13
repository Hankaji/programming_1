package user;
import menu.Menu;

public class PortManager extends User {

//    private Port currentPort;

    public PortManager(String name, String password) {
        super(name, password);
        this.userMenu = new Menu();
    }
}

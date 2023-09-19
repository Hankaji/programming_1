package user;

import menu.Menu;

public class Admin extends User {
    public Admin(String name, String password) {
        super(name, password);
        this.userMenu = new Menu();
    }
}

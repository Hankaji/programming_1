package user;
import menu.Menu;

import java.io.Serializable;

public abstract class User implements Serializable {
    private final String name;
    private final String password;
    protected Menu userMenu;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Menu getUserMenu() {
        return userMenu;
    }

    @Override
    public String toString() {
        return "{name='" + name + '\'' +
                ", password='" + password + "'}";
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}

package user;
import menu.Menu;

abstract class User {
    private String name;
    private String password;
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
}

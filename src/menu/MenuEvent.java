package menu;

import exceptions.UserLogoutException;

import java.io.Serializable;

public class MenuEvent implements Serializable {
    private final String displayName;
    private final Runnable action;
    private Menu subMenu;

    public MenuEvent(String displayName, Runnable action) {
        this.displayName = displayName;
        this.action = action;
    }

    public MenuEvent(String displayName, Menu subMenu) {
        this.displayName = displayName;
        this.action = subMenu::run;
        this.subMenu = subMenu;
    }

    public boolean run(){
        if (subMenu != null) return subMenu.run();
        action.run();
        return true;
    }

    public void display() {
        System.out.println(displayName);
    }

    public String getDisplayName() {
        if (subMenu != null) {
            return displayName + " >";
        }
        return displayName;
    }

    public Menu getSubMenu() {
        return subMenu;
    }
}


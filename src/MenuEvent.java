public class MenuEvent {
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

    public void run() {
        action.run();
    }

    public void display() {
        System.out.println(displayName);
    }

    public String getName() {
        if (subMenu != null) {
            return displayName + " >";
        }
        return displayName;
    }
}

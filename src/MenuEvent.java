public class MenuEvent {
    private String displayName;
    private Runnable action;
    private Menu subMenu;

    public MenuEvent(String displayName, Runnable action) {
        this.displayName = displayName;
        this.action = action;
    }

    public MenuEvent(String displayName, Menu subMenu) {
        this.displayName = displayName;
        this.action = () -> subMenu.run();
        this.subMenu = subMenu;
    }

    public void run() {
        action.run();
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
}


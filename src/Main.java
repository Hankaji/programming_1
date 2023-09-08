public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();

        menu.addEvent("Add", () -> System.out.println("Add"));
        menu.addEvent("Subtract", () -> System.out.println("Subtract"));
        menu.addEvent("Multiply", () -> System.out.println("Multiply"));
        menu.addEvent("Divide", () -> System.out.println("Divide"));
        
        Menu subMenu = new Menu();
        subMenu.addEvent("Sub Add", () -> System.out.println("Sub Add"));
        subMenu.addEvent("Sub Subtract", () -> System.out.println("Sub Subtract"));
        subMenu.addEvent("Sub Multiply", () -> System.out.println("Sub Multiply"));
        subMenu.addEvent("Sub Divide", () -> System.out.println("Sub Divide"));

        menu.addEvent(new MenuEvent("Sub Menu", subMenu));

        // menu.display();
        menu.run();
    }
}

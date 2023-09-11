public class Main {
    public static void main(String[] args) {
        setUpMenu();
    }

    private static void setUpMenu() {
        Menu mainMenu = new Menu();

        // Creating sub menus
        Menu portsMenu = new Menu();
        Menu vehiclesMenu = new Menu();
        Menu containersMenu = new Menu();
        Menu managersMenu = new Menu();

        // Creating menu events where it opens sub menus
        MenuEvent ports = new MenuEvent("Ports", portsMenu);
        MenuEvent vehicles = new MenuEvent("Vehicles", vehiclesMenu);
        MenuEvent containers = new MenuEvent("Containers", containersMenu);
        MenuEvent managers = new MenuEvent("Managers", managersMenu);

        // Creating port events where it adds, removes and views ports
        MenuEvent addPort = new MenuEvent("Add Port", () -> {
            System.out.println("Add Port");
        });
        MenuEvent removePort = new MenuEvent("Remove Port", () -> {
            System.out.println("Remove Port");
        });
        MenuEvent viewPorts = new MenuEvent("View Ports", () -> {
            System.out.println("View Ports");
        });

        // Creating vehicle events where it adds, removes and views vehicles
        MenuEvent addVehicle = new MenuEvent("Add Vehicle", () -> {
            System.out.println("Add Vehicle");
        });
        MenuEvent removeVehicle = new MenuEvent("Remove Vehicle", () -> {
            System.out.println("Remove Vehicle");
        });
        MenuEvent viewVehicles = new MenuEvent("View Vehicles", () -> {
            System.out.println("View Vehicles");
        });

        // Creating container events where it adds, removes and views containers
        MenuEvent addContainer = new MenuEvent("Add Container", () -> {
            System.out.println("Add Container");
        });
        MenuEvent removeContainer = new MenuEvent("Remove Container", () -> {
            System.out.println("Remove Container");
        });
        MenuEvent viewContainers = new MenuEvent("View Containers", () -> {
            System.out.println("View Containers");
        });

        // Creating manager events where it adds, removes and views managers
        MenuEvent addManager = new MenuEvent("Add Manager", () -> {
            System.out.println("Add Manager");
        });
        MenuEvent removeManager = new MenuEvent("Remove Manager", () -> {
            System.out.println("Remove Manager");
        });
        MenuEvent viewManagers = new MenuEvent("View Managers", () -> {
            System.out.println("View Managers");
        });

        // Adding events to sub port menu
        portsMenu.addEvent(addPort);
        portsMenu.addEvent(removePort);
        portsMenu.addEvent(viewPorts);

        // Adding events to sub vehicle menu
        vehiclesMenu.addEvent(addVehicle);
        vehiclesMenu.addEvent(removeVehicle);
        vehiclesMenu.addEvent(viewVehicles);

        // Adding events to sub container menu
        containersMenu.addEvent(addContainer);
        containersMenu.addEvent(removeContainer);
        containersMenu.addEvent(viewContainers);

        // Adding events to sub manager menu
        managersMenu.addEvent(addManager);
        managersMenu.addEvent(removeManager);
        managersMenu.addEvent(viewManagers);

        // Adding all  (sub menus) to main menu
        mainMenu.addEvent(ports);
        mainMenu.addEvent(vehicles);
        mainMenu.addEvent(containers);
        mainMenu.addEvent(managers);

        // Create Menu Events
        MenuEvent load = new MenuEvent("Load", () -> {
            System.out.println("Load");
        });
        MenuEvent unload = new MenuEvent("Unload", () -> {
            System.out.println("Unload");
        });
        MenuEvent refuel = new MenuEvent("Refuel", () -> {
            System.out.println("Refuel");
        });
        MenuEvent fuelUsed = new MenuEvent("Fuel Used", () -> {
            System.out.println("Fuel Used");
        });
        MenuEvent containerWeight = new MenuEvent("Container Weight", () -> {
            System.out.println("Container Weight");
        });
        MenuEvent shipsInPort = new MenuEvent("Ships In Port", () -> {
            System.out.println("Ships In Port");
        });
        MenuEvent tripsInDay = new MenuEvent("Trips In Day", () -> {
            System.out.println("Trips In Day");
        });
        MenuEvent tripFromRange = new MenuEvent("Trip From Range", () -> {
            System.out.println("Trip From Range");
        });

        // Adding all (menu events) to main menu
        mainMenu.addEvent(load);
        mainMenu.addEvent(unload);
        mainMenu.addEvent(refuel);
        mainMenu.addEvent(fuelUsed);
        mainMenu.addEvent(containerWeight);
        mainMenu.addEvent(shipsInPort);
        mainMenu.addEvent(tripsInDay);
        mainMenu.addEvent(tripFromRange);

        // Running main menu
        mainMenu.run();
    }
}

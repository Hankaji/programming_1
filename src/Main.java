import menu.Menu;
import menu.MenuEvent;
import user.*;
import utils.Divider;
import utils.Holder;


import port.*;
import vehicle.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Menu adminMenu = new Menu();

    public static void main(String[] args) throws IOException {
//          Map<String, User> test = AccountDatabase.getInstance();

//          AccountDatabase.addUser(new Admin("admin", "admin"));
//          AccountDatabase.addUser(new PortManager("managerA", "managerA"));

//          System.out.println(test);

//          AccountDatabase.saveToFile();
        setUpMenu();
        User loggedUser = homePage();
        if (loggedUser instanceof Admin) {
            adminMenu.run();
        } else if (loggedUser instanceof PortManager) {
            return;
        }
    }

    private static User homePage() throws FileNotFoundException {
        System.out.println("COSC2081 GROUP ASSIGNMENT");
        System.out.println("CONTAINER PORT MANAGEMENT SYSTEM");
        System.out.println("Instructor: Mr. Minh Vu & Dr. Phong Ngo");
        System.out.println("Group: Group23");
        System.out.println("s3978081, Hoang Thai Phuc");
        System.out.println("s3977773, Nguyen Hoang Minh");
        System.out.println("s3977856, Hoang Nguyen Nhat Minh");
        System.out.println("s3979367, Tran Nguyen Anh Minh");

        Divider.printDivider();
        System.out.println("Please login to an account");
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("Username: ");
            String username = input.nextLine();

            System.out.print("Password: ");
            String password = input.nextLine();

            if (username.equals("-1") || password.equals("-1")) {
                return null;
            }

            if (Authenticator.authenticate(username, password)) {
                System.out.println("Login successful!");
                Divider.printDivider();
                System.out.println("Welcome back, " + username + "!");
                return AccountDatabase.getInstance().get(username);
            } else {
                System.out.println("Login failed!");
            }
        }
    }

    private static void setUpMenu() {

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
        MenuEvent addPort = new MenuEvent("Add port", () -> {
            System.out.println("Add port");
        });
        MenuEvent removePort = new MenuEvent("Remove port", () -> {
            System.out.println("Remove port");
        });
        MenuEvent viewPorts = new MenuEvent("View Ports", () -> {
            System.out.println("View Ports");
        });

        // Creating vehicle events where it adds, removes and views vehicles
        MenuEvent addVehicle = new MenuEvent("Add vehicle", () -> {
            System.out.println("Add vehicle");
        });
        MenuEvent removeVehicle = new MenuEvent("Remove vehicle", () -> {
            System.out.println("Remove vehicle");
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
        adminMenu.addEvent(ports);
        adminMenu.addEvent(vehicles);
        adminMenu.addEvent(containers);
        adminMenu.addEvent(managers);

        // Create menu.Menu Events
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
        MenuEvent shipsInPort = new MenuEvent("Ships In port", () -> {
            System.out.println("Ships In port");
        });
        MenuEvent tripsInDay = new MenuEvent("Trips In Day", () -> {
            System.out.println("Trips In Day");
        });
        MenuEvent tripFromRange = new MenuEvent("Trip From Range", () -> {
            System.out.println("Trip From Range");
        });

        // Adding all (menu events) to main menu
        adminMenu.addEvent(load);
        adminMenu.addEvent(unload);
        adminMenu.addEvent(refuel);
        adminMenu.addEvent(fuelUsed);
        adminMenu.addEvent(containerWeight);
        adminMenu.addEvent(shipsInPort);
        adminMenu.addEvent(tripsInDay);
        adminMenu.addEvent(tripFromRange);

        // Running main menu
//        adminMenu.run();
    }

    public static void TestPorts() {
        // Create Manchester port
        Port manchesterPort = new Port("p-1", "Manchester Port", 53.4808, 2.2426, 5000000.0, true);
        Port liverpoolPort = new Port("p-2", "Liverpool Port", 53.4084, 2.9916, 10000000.0, true);
        Port londonPort = new Port("p-3", "London Port", 51.5074, 0.1278, 7500000.0, true);
        Port newPort = new Port("p-4", "New Port", 51.5074, 0.1278, 7500000.0, true);

        // Create a port holder
//        Holder<Port> portHolder = new Holder<>();
//        portHolder.populateList("portsData.txt");
//        portHolder.addToList(manchesterPort);
//        portHolder.addToList(liverpoolPort);
//        portHolder.addToList(londonPort);
//        portHolder.addToList(newPort);
//        portHolder.getList().forEach(System.out::println);
//
//        portHolder.saveList("portsData.txt");

        // manchester ports containers
//        Container manchesterContainer1 = new Container("c-1", 1.0, CONTAINER_TYPE.DRY_STORAGE, manchesterPort, liverpoolPort);
//        Container manchesterContainer2 = new Container("c-2", 2.2, CONTAINER_TYPE.OPEN_TOP, manchesterPort, londonPort);
//        Container manchesterContainer3 = new Container("c-3", 3.1, CONTAINER_TYPE.OPEN_SIDE, manchesterPort, londonPort);

        // liverpool ports containers
//        Container liverpoolContainer1 = new Container("c-4", 4.0, CONTAINER_TYPE.REFRIGERATED, liverpoolPort, manchesterPort);
//        Container liverpoolContainer2 = new Container("c-5", 5.0, CONTAINER_TYPE.LIQUID, liverpoolPort, londonPort);
//        Container liverpoolContainer3 = new Container("c-6", 6.0, CONTAINER_TYPE.DRY_STORAGE, liverpoolPort, londonPort);

        // london ports containers
//        Container londonContainer1 = new Container("c-7", 1.4, CONTAINER_TYPE.OPEN_TOP, londonPort, manchesterPort);
//        Container londonContainer2 = new Container("c-8", 2.3, CONTAINER_TYPE.OPEN_SIDE, londonPort, liverpoolPort);
//        Container londonContainer3 = new Container("c-9", 0.8, CONTAINER_TYPE.REFRIGERATED, londonPort, liverpoolPort);

        // create trucks in liverpool port
        BasicTruck liverpoolTruck1 = new BasicTruck("tr-1", "Liverpool Truck 1", 100.0, 100.0, liverpoolPort, 100.0, new ArrayList<>());

        // view truck
//        System.out.println(liverpoolTruck1);
//        liverpoolTruck1.loadContainer(liverpoolContainer1);
//        liverpoolTruck1.loadContainer(liverpoolContainer2);
//        liverpoolTruck1.loadContainer(liverpoolContainer3);
//        System.out.println(liverpoolTruck1);

//        // view the ports
//        System.out.println(manchesterPort);
//        System.out.println(liverpoolPort);
//        System.out.println(londonPort);

        // view the manchester containers
//        System.out.println(manchesterContainer1);
//        System.out.println(manchesterContainer2);
//        System.out.println(manchesterContainer3);

        // view ports distance
//        System.out.println("Distance between Manchester and Liverpool: " + manchesterPort.getDistance(liverpoolPort) + " kilometers");
//        System.out.println("Distance between Manchester and London: " + manchesterPort.getDistance(londonPort) + " kilometers");
//        System.out.println("Distance between Liverpool and London: " + liverpoolPort.getDistance(londonPort) + " kilometers");

//        // view fuel consumption of London containers
//        System.out.println("Fuel consumption of London container 1: " + londonContainer1.getShipFuelConsumption() + " gallons");
//        System.out.println("Fuel consumption of London container 2: " + londonContainer2.getShipFuelConsumption() + " gallons");
//        System.out.println("Fuel consumption of London container 3: " + londonContainer3.getShipFuelConsumption() + " gallons");

        // Make a sample trip
//        Trip trip = new Trip("t-1",liverpoolTruck1,manchesterPort,liverpoolPort);
//        trip.setArrivalDate();
//        System.out.println(trip);
        Holder<Trip> tripHolder = new Holder<>();
        tripHolder.populateList("tripsData.txt");
        tripHolder.getList().forEach(System.out::println);
//        tripHolder.addToList(trip);
//        tripHolder.saveList("tripsData.txt");

    }
}

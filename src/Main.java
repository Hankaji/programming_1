import data.Database;
import menu.Menu;
import menu.MenuEvent;
import user.*;
import utils.Divider;

import java.util.Objects;
import java.util.Scanner;


import port.*;
import vehicle.*;

import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static final Menu adminMenu = new Menu();
    private static final Menu portManagerMenu = new Menu();

//    private static final Holder<Port> portHolder = new Holder<>();
//    private static final Holder<Container> containerHolder = new Holder<>();
//    private static final Holder<Vehicle> vehicleHolder = new Holder<>();
//    private static final Holder<PortManager> portManagerHolder = new Holder<>();
//    private static final Holder<Trip> tripHolder = new Holder<>();

    public static void main(String[] args) throws IOException {
        AccountDatabase.getInstance();
        setUpMenu();

//        AccountDatabase.addUser(new Admin("admin", "admin"));

        AccountDatabase.displayAllUsers();

        // Authorization
        User loggedUser = displayHomePage();
        if (loggedUser instanceof Admin) {
            adminMenu.run();
        } else if (loggedUser instanceof PortManager) {
            portManagerMenu.run();
        }
    }

    private static User displayHomePage() throws FileNotFoundException {
        System.out.println("COSC2081 GROUP ASSIGNMENT");
        System.out.println("CONTAINER PORT MANAGEMENT SYSTEM");
        System.out.println("Instructor: Mr. Minh Vu & Dr. Phong Ngo");
        System.out.println("Group: Group23");
        System.out.println("s3978081, Hoang Thai Phuc");
        System.out.println("s3977773, Nguyen Hoang Minh");
        System.out.println("s3977856, Hoang Nguyen Nhat Minh");
        System.out.println("s3979367, Tran Nguyen Anh Minh");

        // Login
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

        // populate Holders
//        Database.portHolder.populateList("portsData.txt");
//        Database.containerHolder.populateList("containersData.txt");
//        Database.vehicleHolder.populateList("vehiclesData.txt");
//        Database.portManagerHolder.populateList("portManagersData.txt");
//        Database.tripHolder.populateList("tripsData.txt");

        Database.portHolder.printList();

        // View vehicles
//        Database.vehicleHolder.getMap().forEach(System.out::println);

        // Creating a shutdown hook to save all holders
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                // Save all holders
                System.out.println();
                Database.portHolder.saveList("portsData.txt");
                Database.containerHolder.saveList("containersData.txt");
                Database.vehicleHolder.saveList("vehiclesData.txt");
                Database.portManagerHolder.saveList("portManagersData.txt");
                Database.tripHolder.saveList("tripsData.txt");
                try {
                    AccountDatabase.saveToFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        // Creating sub menus
        Menu portsMenu = new Menu();
        Menu vehiclesMenu = new Menu();
        Menu containersMenu = new Menu();
        Menu managersMenu = new Menu();
        Menu addVehicleMenu = new Menu();
        Menu trucksMenu = new Menu();

        // Creating menu events where it opens sub menus
        MenuEvent ports = new MenuEvent("Ports", portsMenu);
        MenuEvent vehicles = new MenuEvent("Vehicles", vehiclesMenu);
        MenuEvent containers = new MenuEvent("Containers", containersMenu);
        MenuEvent managers = new MenuEvent("Managers", managersMenu);
        MenuEvent addVehicleEvent = new MenuEvent("Add Vehicle", addVehicleMenu);

        // Create menu events for Vehicles
        MenuEvent addShipEvent = new MenuEvent("Ship", () -> addVehicle("Ship"));

        MenuEvent addBasicTruck = new MenuEvent("Basic Truck", () -> addVehicle("Basic Truck"));
        MenuEvent addReeferTruck = new MenuEvent("Reefer Truck", () -> addVehicle("Reefer Truck"));
        MenuEvent addTankerTruck = new MenuEvent("Tanker Truck", () -> addVehicle("Tanker Truck"));

        trucksMenu.addEvent(addBasicTruck);
        trucksMenu.addEvent(addReeferTruck);
        trucksMenu.addEvent(addTankerTruck);

        MenuEvent addTrucks = new MenuEvent("Truck", trucksMenu);

        addVehicleMenu.addEvent(addShipEvent);
        addVehicleMenu.addEvent(addTrucks);

        // Creating port events where it adds, removes and views ports
        MenuEvent addPortEvent = new MenuEvent("Add port", Main::addPort);
        MenuEvent removePort = new MenuEvent("Remove port", () -> {
            Scanner input = new Scanner(System.in);
            System.out.println("Please enter the port ID (p-*): ");
            String portID = input.nextLine();
            Database.portHolder.removeFromMap(portID);
        } );

        MenuEvent viewPorts = new MenuEvent("View Ports", Database.portHolder::printList);

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
            Scanner input = new Scanner(System.in);
            System.out.println("Please enter the container ID (c-*): ");
            String containerID = input.nextLine();
            Database.containerHolder.removeFromMap(containerID);
        });
        MenuEvent viewContainers = new MenuEvent("View Containers", Database.containerHolder::printList);

        // Creating manager events where it adds, removes and views managers
        MenuEvent addManager = new MenuEvent("Add Manager", Main::addManager);

        MenuEvent removeManager = new MenuEvent("Remove Manager", () -> {
            System.out.println("Remove Manager");
        });
        MenuEvent viewManagers = new MenuEvent("View Managers", AccountDatabase::displayAllUsers);


        // Adding events to sub port menu
        portsMenu.addEvent(addPortEvent);
        portsMenu.addEvent(removePort);
        portsMenu.addEvent(viewPorts);

        // Adding events to sub vehicle menu
        vehiclesMenu.addEvent(addVehicleEvent);
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

        // Adding all  (sub menus) to admin menu and port manager menu
        // Admin menu
        adminMenu.addEvent(ports);
        adminMenu.addEvent(vehicles);
        adminMenu.addEvent(containers);
        adminMenu.addEvent(managers);

        // Port manager menu
        portManagerMenu.addEvent(ports);

        // Create menu.Menu Events
        MenuEvent load = new MenuEvent("Load", () -> {
            String ans = "yes";
            while (ans.equals("yes")){

            }
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

        // Adding all (menu events) to admin menu and port manager menu
        // Admin menu
        adminMenu.addEvent(load);
        adminMenu.addEvent(unload);
        adminMenu.addEvent(refuel);
        adminMenu.addEvent(fuelUsed);
        adminMenu.addEvent(containerWeight);
        adminMenu.addEvent(shipsInPort);
        adminMenu.addEvent(tripsInDay);
        adminMenu.addEvent(tripFromRange);

        // Port manager menu
        portManagerMenu.addEvent(load);
        portManagerMenu.addEvent(unload);
        portManagerMenu.addEvent(refuel);
        portManagerMenu.addEvent(fuelUsed);
        portManagerMenu.addEvent(containerWeight);
        portManagerMenu.addEvent(shipsInPort);
        portManagerMenu.addEvent(tripsInDay);
        portManagerMenu.addEvent(tripFromRange);
    }

    private static void addManager() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the manager's username: ");
        String managerUsername = input.nextLine();
        System.out.println("Please enter the manager's password: ");
        String managerPassword = input.nextLine();
        Port port;
        while (true) {

            System.out.print("Please enter the port manager's current port ID (p-*): ");
            String vehiclePortID = input.nextLine();

            port = Database.portHolder.getMap().getOrDefault(vehiclePortID, null);
            if (port == null) {
                System.out.println("Port not found. Try Again!");
                continue;
            }
            break;
        }
        PortManager portManager = new PortManager(managerUsername, managerPassword, port);
        AccountDatabase.addUser(portManager);

    }


    private static void addPort() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the port ID (p-*): ");
        String portID = input.nextLine();
        System.out.println("Please enter the port's name: ");
        String portName = input.nextLine();
        System.out.println("Please enter the port's latitude: ");
        Double portLatitude = input.nextDouble();
        System.out.println("Please enter the port's longitude: ");
        Double portLongitude = input.nextDouble();
        System.out.println("Please enter the port's fuel capacity: ");
        Double portFuelCapacity = input.nextDouble();
        System.out.println("Please enter the port's landing ability (True/ False): ");
        Boolean portLandingAbility = input.nextBoolean();
        Port port = new Port(portID, portName, portLatitude, portLongitude, portFuelCapacity, portLandingAbility);
        Database.portHolder.addItem(portID, port);
    }
     private static void addVehicle(String vehicleType) {
        Scanner input = new Scanner(System.in);

        System.out.printf("Please enter the %s's name: ", vehicleType);
        String vehicleName = input.nextLine();
        System.out.printf("Please enter the %s's ID " + (Objects.equals(vehicleType, "Ship") ? "(sh-*)" : (Objects.equals(vehicleType, "Basic Truck") || Objects.equals(vehicleType, "Reefer Truck") || Objects.equals(vehicleType, "Tanker Truck")) ? "(tr-*)" : null) + ": ", vehicleType);
        String vehicleID = input.nextLine();
        System.out.printf("Please enter the %s's current fuel: ", vehicleType);
        Double vehicleCurrentFuel = input.nextDouble();
        System.out.printf("Please enter the %s's max fuel: ", vehicleType);
        Double vehicleMaxFuel = input.nextDouble();
        System.out.printf("Please enter the %s's carrying capacity: ", vehicleType);
        Double vehicleCarryingCapacity = input.nextDouble();
        Port vehiclePort;
        input.nextLine();
        while (true) {

            System.out.printf("Please enter the %s's current port ID (p-*): ", vehicleType);
            String vehiclePortID = input.nextLine();

            vehiclePort = Database.portHolder.getMap().getOrDefault(vehiclePortID, null);
            if (vehiclePort == null) {
                System.out.println("Port not found. Try Again!");
                continue;
            }
            break;
        }


        if (Objects.equals(vehicleType, "Ship")) {
            Ship ship = new Ship(vehicleID, vehicleName, vehicleCurrentFuel, vehicleMaxFuel, vehiclePort, vehicleCarryingCapacity, new ArrayList<>());
            Database.vehicleHolder.addItem(vehicleID, ship);
        } else if (Objects.equals(vehicleType, "Basic Truck")) {
            BasicTruck basicTruck = new BasicTruck(vehicleID, vehicleName, vehicleCurrentFuel, vehicleMaxFuel, vehiclePort, vehicleCarryingCapacity, new ArrayList<>());
            Database.vehicleHolder.addItem(vehicleID, basicTruck);
        } else if (Objects.equals(vehicleType, "Reefer Truck")) {
            ReeferTruck reeferTruck = new ReeferTruck(vehicleID, vehicleName, vehicleCurrentFuel, vehicleMaxFuel, vehiclePort, vehicleCarryingCapacity, new ArrayList<>());
            Database.vehicleHolder.addItem(vehicleID, reeferTruck);
        } else if (Objects.equals(vehicleType, "Tanker Truck")) {
            TankerTruck tankerTruck = new TankerTruck(vehicleID, vehicleName, vehicleCurrentFuel, vehicleMaxFuel, vehiclePort, vehicleCarryingCapacity, new ArrayList<>());
            Database.vehicleHolder.addItem(vehicleID, tankerTruck);
        }
        input.close();
    }



    

// TESTING DATA, NO RELEVANT CODE BELOW HERE

//    public static void TestPorts() {
//        // Create Manchester port
//        Port manchesterPort = new Port("p-1", "Manchester Port", 53.4808, 2.2426, 5000000.0, true);
//        Port liverpoolPort = new Port("p-2", "Liverpool Port", 53.4084, 2.9916, 10000000.0, true);
//        Port londonPort = new Port("p-3", "London Port", 51.5074, 0.1278, 7500000.0, true);
//        Port newPort = new Port("p-4", "New Port", 51.5074, 0.1278, 7500000.0, true);
//
//        // Create a port holder
//        Holder<Port> portHolder = new Holder<>();
//        portHolder.populateList("portsData.txt");
//        portHolder.addToList(manchesterPort);
//        portHolder.addToList(liverpoolPort);
//        portHolder.addToList(londonPort);
//        portHolder.addToList(newPort);
//        portHolder.getList().forEach(System.out::println);
//
//        portHolder.saveList("portsData.txt");
//
//        // manchester ports containers
//        Container manchesterContainer1 = new Container("c-1", 1.0, CONTAINER_TYPE.DRY_STORAGE, manchesterPort, liverpoolPort);
//        Container manchesterContainer2 = new Container("c-2", 2.2, CONTAINER_TYPE.OPEN_TOP, manchesterPort, londonPort);
//        Container manchesterContainer3 = new Container("c-3", 3.1, CONTAINER_TYPE.OPEN_SIDE, manchesterPort, londonPort);
//
//        // liverpool ports containers
//        Container liverpoolContainer1 = new Container("c-4", 4.0, CONTAINER_TYPE.REFRIGERATED, liverpoolPort, manchesterPort);
//        Container liverpoolContainer2 = new Container("c-5", 5.0, CONTAINER_TYPE.LIQUID, liverpoolPort, londonPort);
//        Container liverpoolContainer3 = new Container("c-6", 6.0, CONTAINER_TYPE.DRY_STORAGE, liverpoolPort, londonPort);
//
//        // london ports containers
//        Container londonContainer1 = new Container("c-7", 1.4, CONTAINER_TYPE.OPEN_TOP, londonPort, manchesterPort);
//        Container londonContainer2 = new Container("c-8", 2.3, CONTAINER_TYPE.OPEN_SIDE, londonPort, liverpoolPort);
//        Container londonContainer3 = new Container("c-9", 0.8, CONTAINER_TYPE.REFRIGERATED, londonPort, liverpoolPort);
//
//        // create trucks in liverpool port
//        BasicTruck liverpoolTruck1 = new BasicTruck("tr-1", "Liverpool Truck 1", 100.0, 100.0, liverpoolPort, 100.0, new ArrayList<>());
//
//        // view truck
//        System.out.println(liverpoolTruck1);
//        liverpoolTruck1.loadContainer(liverpoolContainer1);
//        liverpoolTruck1.loadContainer(liverpoolContainer2);
//        liverpoolTruck1.loadContainer(liverpoolContainer3);
//        System.out.println(liverpoolTruck1);
//
//        // view the ports
//        System.out.println(manchesterPort);
//        System.out.println(liverpoolPort);
//        System.out.println(londonPort);
//
//        // view the manchester containers
//        System.out.println(manchesterContainer1);
//        System.out.println(manchesterContainer2);
//        System.out.println(manchesterContainer3);
//
//        // view ports distance
//        System.out.println("Distance between Manchester and Liverpool: " + manchesterPort.getDistance(liverpoolPort) + " kilometers");
//        System.out.println("Distance between Manchester and London: " + manchesterPort.getDistance(londonPort) + " kilometers");
//        System.out.println("Distance between Liverpool and London: " + liverpoolPort.getDistance(londonPort) + " kilometers");
//
//        // view fuel consumption of London containers
//        System.out.println("Fuel consumption of London container 1: " + londonContainer1.getShipFuelConsumption() + " gallons");
//        System.out.println("Fuel consumption of London container 2: " + londonContainer2.getShipFuelConsumption() + " gallons");
//        System.out.println("Fuel consumption of London container 3: " + londonContainer3.getShipFuelConsumption() + " gallons");
//
//        // Make a sample trip
//        Trip trip = new Trip("t-1",liverpoolTruck1,manchesterPort,liverpoolPort);
//        trip.setArrivalDate();
//        System.out.println(trip);
//        Holder<Trip> tripHolder = new Holder<>();
//        tripHolder.populateList("tripsData.txt");
//        tripHolder.getList().forEach(System.out::println);
//        tripHolder.addToList(trip);
//        tripHolder.saveList("tripsData.txt");
//
//    }
}

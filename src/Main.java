import data.Database;
import menu.Menu;
import menu.MenuEvent;
import user.*;
import utils.Divider;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


import java.util.*;

import port.*;
import utils.InputValidator;
import vehicle.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static final Menu adminMenu = new Menu();
    private static final Menu portManagerMenu = new Menu();
    private static User loggedUser = null;

    public static void main(String[] args) throws IOException {
        AccountDatabase.getInstance();
        setUpMenu();

//        AccountDatabase.addUser(new Admin("admin", "admin"));

        AccountDatabase.displayAllUsers();

        // Authorization
        loggedUser = displayHomePage();
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
            String username = input.nextLine().trim();

            System.out.print("Password: ");
            String password = input.nextLine().trim();

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
        // Creating a shutdown hook to save all holders
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Save all holders
            System.out.println();
            Database.portHolder.saveList("portsData.txt");
            Database.containerHolder.saveList("containersData.txt");
            Database.vehicleHolder.saveList("vehiclesData.txt");
            Database.tripHolder.saveList("tripsData.txt");
            try {


                AccountDatabase.saveToFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));

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
        MenuEvent addShipEvent = new MenuEvent("Ship", () -> addVehicle(Ship.class));

        MenuEvent addBasicTruck = new MenuEvent("Basic Truck", () -> addVehicle(BasicTruck.class));
        MenuEvent addReeferTruck = new MenuEvent("Reefer Truck", () -> addVehicle(ReeferTruck.class));
        MenuEvent addTankerTruck = new MenuEvent("Tanker Truck", () -> addVehicle(TankerTruck.class));

        trucksMenu.addEvent(addBasicTruck);
        trucksMenu.addEvent(addReeferTruck);
        trucksMenu.addEvent(addTankerTruck);

        MenuEvent addTrucks = new MenuEvent("Truck", trucksMenu);

        addVehicleMenu.addEvent(addShipEvent);
        addVehicleMenu.addEvent(addTrucks);

        // Creating port events where it adds, removes and views ports
        MenuEvent addPortEvent = new MenuEvent("Add port", Main::addPort);
        MenuEvent removePort = new MenuEvent("Remove port", Main::removePort);

        MenuEvent viewPorts = new MenuEvent("View Ports", Database.portHolder::printList);

        MenuEvent removeVehicle = new MenuEvent("Remove vehicle", Main::removeVehicle);
        MenuEvent viewVehicles = new MenuEvent("View Vehicles", Database.vehicleHolder::printList);

        // Creating container events where it adds, removes and views containers
        MenuEvent addContainer = new MenuEvent("Add Container", Main::addContainer);
        MenuEvent removeContainer = new MenuEvent("Remove Container", Main::removeContainer);
        MenuEvent viewContainers = new MenuEvent("View Containers", Database.containerHolder::printList);

        // Creating manager events where it adds, removes and views managers
        MenuEvent addManager = new MenuEvent("Add Manager", Main::addManager);

        MenuEvent removeManager = new MenuEvent("Remove Manager", Main::removeManager);
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
        portManagerMenu.addEvent(containers);

        // Create menu.Menu Events
        MenuEvent checkin = new MenuEvent("Checkin Vehicles", () -> {
            // Access vehicle database to get vehicles that are in the current port
            // Access container database to get containers that are in the current port
            Port port = checkUserPort();
            // Access trip database to get trips that do not have arrival date with arrival port as current port
            List<Trip> arrivingTrips = Database.tripHolder.getMap().values().stream().filter(trip -> trip.getStatus() == TRIP_STATUS.ARRIVING && trip.getEndPort().getName().equals(port.getName())).toList();
            // for each List, change status to ARRIVED, and set arrival date, set vehicle's current port to the port
            for (Trip trip : arrivingTrips) {
                trip.setArrivalDate();
                Vehicle vehicle = trip.getVehicleUsed();
                vehicle.setCurrentPort(port);
                // remove all containers from vehicle and add them to the port
                
//                List<Container> containersToUpdate = vehicle.getContainers();
//                vehicle.setContainers(new ArrayList<>());
//                for (Container container : containersToUpdate) {
//                    container.setCurrentPort(port);
//                }
                trip.setStatus(TRIP_STATUS.ARRIVED);
                // log out vehicles arriving
                System.out.println(trip.getVehicleUsed().getName() + " arrived at " + port.getName());

            }



        });
        MenuEvent load = new MenuEvent("Load", Main::loadContainer);
        MenuEvent unload = new MenuEvent("Unload", () -> System.out.println("Unload"));
        MenuEvent refuel = new MenuEvent("Refuel", () -> System.out.println("Refuel"));
        MenuEvent fuelUsed = new MenuEvent("Fuel Used", () -> System.out.println("Fuel Used"));
        MenuEvent containerWeight = new MenuEvent("Container Weight", Main::getTotalContainersWeight);
        MenuEvent shipsInPort = new MenuEvent("Ships In port", Main::getTotalShipsInPort);
        MenuEvent tripsInDay = new MenuEvent("Trips In Day", Database.tripHolder::printList);
        MenuEvent tripFromRange = new MenuEvent("Trip From Range", () -> System.out.println("Trip From Range"));

        // Adding all (menu events) to admin menu and port manager menu
        // Admin menu
        for (MenuEvent menuEvent : Arrays.asList(checkin,
                load,
                unload,
                refuel,
                fuelUsed,
                containerWeight,
                shipsInPort,
                tripsInDay,
                tripFromRange)) {
            adminMenu.addEvent(menuEvent);
        }

        // Port manager menu
//        portManagerMenu.addEvent(checkin);
        for (MenuEvent menuEvent : Arrays.asList(checkin,
                load,
                unload,
                refuel,
                fuelUsed,
                containerWeight,
                shipsInPort,
                tripsInDay,
                tripFromRange)) {
            portManagerMenu.addEvent(menuEvent);
        }
    }

    private static Port checkUserPort() {
        // Check if user is a port manager, if so, return the port they are managing
        if (loggedUser instanceof PortManager) {
            System.out.println("Current port: " + ((PortManager) loggedUser).getCurrentPort().getName());
            return ((PortManager) loggedUser).getCurrentPort();
        }

        // Else, ask the user to enter the port ID
        String portID = InputValidator.validateString(value -> Database.portHolder.getMap().containsKey(value),
                "Please enter the port ID (p-*): ",
                "Port doesn't exist, please try again.");
        System.out.println("Current port: " + Database.portHolder.getMap().get(portID).getName());
        return Database.portHolder.getMap().get(portID);
    }

    private static void getTotalContainersWeight() {
        // Get the port
        Port port = checkUserPort();

        // calculate the total weight
        Double totalWeight = 0.0;
        for (Container container : Database.containerHolder.getMap().values()) {
            if (container.getCurrentPort().getName().equals(port.getName())) {
                totalWeight += container.getWeight();
            }
        }

        System.out.println("Total weight of containers in " + port.getName() + " is " + totalWeight + " tons");
    }

    private static void getTotalShipsInPort() {
        // Get the port
        Port port = checkUserPort();

        // Get and count the ships
        int totalShips = 0;
        List<Ship> ships = new ArrayList<>();
        for (Vehicle vehicle : Database.vehicleHolder.getMap().values()) {
            if (vehicle instanceof Ship && vehicle.getCurrentPort().getName().equals(port.getName())) {
                totalShips++;
                ships.add((Ship) vehicle);
            }
        }

        // Display information
        System.out.println("Total ships in " + port.getName() + " is " + totalShips);

        // Print the ships
        ships.forEach(System.out::println);
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

    private static void removeManager() {
        System.out.println("Please enter the manager's username: ");
        String managerUsername = InputValidator.validateString(value -> AccountDatabase.getUser(value) instanceof PortManager);
        AccountDatabase.removeUser(managerUsername);
        System.out.println("Manager removed successfully!");
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
     private static void addVehicle(Class<? extends Vehicle> vehicleTypeCls) {
        String vehicleType;
        String vehicleIDFormat = "(tr-*)";
         switch (vehicleTypeCls.getName()) {
             case "vehicle.Ship" -> {
                 vehicleType = "Ship";
                 vehicleIDFormat = "(sh-*)";
             }
             case "vehicle.BasicTruck" -> vehicleType = "Basic Truck";
             case "vehicle.ReeferTruck" -> vehicleType = "Reefer Truck";
             case "vehicle.TankerTruck" -> vehicleType = "Tanker Truck";
             default -> throw new IllegalStateException("Unexpected value: " + vehicleTypeCls.getName());
         }

        System.out.printf("Please enter the %s's name: ", vehicleType);
        String vehicleName = InputValidator.validateString();
        System.out.printf("Please enter the %s's ID " + vehicleIDFormat + ": ", vehicleType);
        String vehicleID = InputValidator.validateString();
        System.out.printf("Please enter the %s's current fuel: ", vehicleType);
        Double vehicleCurrentFuel = InputValidator.validateDouble();
        System.out.printf("Please enter the %s's max fuel: ", vehicleType);
        Double vehicleMaxFuel = InputValidator.validateDouble();
        System.out.printf("Please enter the %s's carrying capacity: ", vehicleType);
        Double vehicleCarryingCapacity = InputValidator.validateDouble();
        String vehiclePortID = InputValidator.validateString(value -> Database.portHolder.getMap().containsKey(value),
                String.format("Please enter the %s's current port ID (p-*): ", vehicleType),
                "Port not found. Try Again!");
         Port vehiclePort = Database.portHolder.getMap().get(vehiclePortID);

        Vehicle vehicle = null;
        if (vehicleTypeCls == Ship.class) {
            vehicle = new Ship(vehicleName, vehicleID, vehicleCurrentFuel, vehicleMaxFuel, vehiclePort, vehicleCarryingCapacity, new ArrayList<>());
        } else if (vehicleTypeCls == BasicTruck.class) {
            vehicle = new BasicTruck(vehicleName, vehicleID, vehicleCurrentFuel, vehicleMaxFuel, vehiclePort, vehicleCarryingCapacity, new ArrayList<>());
        } else if (vehicleTypeCls == ReeferTruck.class) {
            vehicle = new ReeferTruck(vehicleName, vehicleID, vehicleCurrentFuel, vehicleMaxFuel, vehiclePort, vehicleCarryingCapacity, new ArrayList<>());
        } else if (vehicleTypeCls == TankerTruck.class) {
            vehicle = new TankerTruck(vehicleName, vehicleID, vehicleCurrentFuel, vehicleMaxFuel, vehiclePort, vehicleCarryingCapacity, new ArrayList<>());
        }

        Database.vehicleHolder.addItem(vehicleID, vehicle);
    }

    private static void removeVehicle() {
        System.out.print("Please enter the vehicle ID (sh-* / tr-*): ");
        String vehicleID = InputValidator.validateString(value -> Database.vehicleHolder.getMap().containsKey(value));
        Database.vehicleHolder.getMap().remove(vehicleID);
        System.out.println("Vehicle removed successfully!");
    }
    private static void addContainer() {
        Scanner input = new Scanner(System.in);

        String containerID = InputValidator.validateString(value -> !Database.containerHolder.getMap().containsKey(value),
                "Please enter the container ID (c-*): ",
                "Container already exists, please try again.");

        double containerWeight = InputValidator.validateDouble(value -> value > 0,
                "Please enter the container weight: ",
                "weight cant be negative or zero, please try again.");

        int idx = 1;
        for (CONTAINER_TYPE type : CONTAINER_TYPE.values()) {
            System.out.println(idx + ") " + type);
            idx++;
        }
        int containerType = InputValidator.validateInt(value -> value <= 5 && value >= 1,
                "Please choose the container type: ");

        String startPortID = InputValidator.validateString(value -> Database.portHolder.getMap().containsKey(value),
                "Please enter the start port id: ",
                "Port doesn't exist, please try again.");
        Port startPort = Database.portHolder.getMap().get(startPortID);

        String currentPortID = InputValidator.validateString(value -> Database.portHolder.getMap().containsKey(value),
                "Please enter the current port id: ",
                "Port doesn't exist, please try again.");
        Port currentPort = Database.portHolder.getMap().get(currentPortID);

        String destinationPortID = InputValidator.validateString(value -> Database.portHolder.getMap().containsKey(value),
                "Please enter the destination port id: ",
                "Port doesn't exist, please try again.");
        Port destinationPort = Database.portHolder.getMap().get(destinationPortID);

        Container container = new Container(containerID, containerWeight, CONTAINER_TYPE.values()[containerType - 1], startPort, currentPort, destinationPort);
        Database.containerHolder.addItem(containerID, container);
    }

    private static void removePort() {
        System.out.println("Please enter the port ID (p-*): ");
        String portID = InputValidator.validateString(value -> Database.portHolder.getMap().containsKey(value));
        Database.portHolder.removeFromMap(portID);
        System.out.println("Port removed successfully!");
    }

    private static void removeContainer() {
        System.out.println("Please enter the container ID (c-*): ");
        String containerID = InputValidator.validateString(value -> Database.containerHolder.getMap().containsKey(value));
        Database.containerHolder.removeFromMap(containerID);
        System.out.println("Container removed successfully!");
    }

    private static void loadContainer() {
        Port port = checkUserPort();
//        if (Database.vehicleHolder.getMap().values().toArray().length != 0 &&
//                Database.vehicleHolder.getMap().values().stream().anyMatch(vehicle -> vehicle.getCurrentPort().getName().equals(port.getName()))) {
//            System.out.println("No vehicles in " + port.getName());
//            return;
//        }
        // Check if there is any vehicle in the current port
        if (Database.vehicleHolder.getMap().values().stream().noneMatch(vehicle -> vehicle.getCurrentPort().getName().equals(port.getName()))) {
            System.out.println("No vehicles in " + port.getName());
            return;
        }
//
//        if (Database.containerHolder.getMap().values().stream().anyMatch(container -> container.getCurrentPort().getName().equals(port.getName()))) {
//            System.out.println("No containers in " + port.getName());
//            return;
//        }

//         display all containers in the port
        System.out.println("Containers in " + port.getName() + ":");
        for (Container container : Database.containerHolder.getMap().values()) {
            if (container.getStartPort().getName().equals(port.getName())) {
                System.out.println(container);
            }
        }
        System.out.println();
        // display all vehicles in the port
        System.out.println("Vehicles in " + port.getName() + ":");
        for (Vehicle vehicle : Database.vehicleHolder.getMap().values()) {
            if (vehicle.getCurrentPort().getName().equals(port.getName())) {
                System.out.println(vehicle);
            }
        }
        Scanner input = new Scanner(System.in);

        System.out.println("Please enter the vehicle ID (sh-* / tr-*): ");
        String vehicleID = InputValidator.validateString(value -> Database.vehicleHolder.getMap().containsKey(value));

        Vehicle vehicle = Database.vehicleHolder.getMap().get(vehicleID);
        while (true) {
            System.out.println("Please enter the container ID (c-*): ");
            String containerID = InputValidator.validateString(value -> Database.containerHolder.getMap().containsKey(value));
            Container container = Database.containerHolder.getMap().get(containerID);
            // check if containerID has same destination as first container in vehicle
            try {
                if (vehicle.getContainers().get(0).getDestinationPort().getName().equals(container.getDestinationPort().getName())) {
                    System.out.println("Container " + containerID + " loaded successfully!");
                    vehicle.loadContainer(container);
                } else {
                    System.out.println("Container " + containerID + " has different destination port than the first container in the vehicle!");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Container " + containerID + " loaded successfully!");
                vehicle.loadContainer(container);
            }
            System.out.println("Do you want to load another container? (Y/N)");
            String choice = InputValidator.validateString(value -> value.equalsIgnoreCase("Y") || value.equalsIgnoreCase("N"));
            if (choice.equalsIgnoreCase("N")) {
                break;
            }
        }

        // move the vehicle to the destination port
        vehicle.moveToPort(vehicle.getContainers().get(0).getDestinationPort());
//
//        // arrive at the destination port

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

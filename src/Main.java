import data.Database;
import exceptions.UserLogoutException;
import menu.Menu;
import menu.MenuEvent;
import user.*;
import utils.Divider;

import java.util.List;
import java.util.Scanner;


import java.util.*;

import port.*;
import utils.IDGenerator;
import utils.InputValidator;
import vehicle.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static Menu appMenu;
    private static User loggedUser = null;

    public static void main(String[] args) {
        // Setting up
        AccountDatabase.getInstance();
        deleteTripsAfter7Days();
        createShutdownHook();

        // Run the program
        // While loop is used here because when user logout it returns false, which is inverted and becomes true, so the loop continues
        do {
            loggedUser = displayHomePage();
            appMenu = new Menu();
            setUpMenu();
        } while(!appMenu.run());
    }

    private static void deleteTripsAfter7Days() {
        List<Trip> tripsToDelete = Database.tripHolder.getMap().values().stream().toList();
        tripsToDelete.forEach(Trip::checkTripExistingTime);
    }

    private static User displayHomePage() {
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
        // Creating sub menus
        Menu portsMenu = new Menu();
        Menu vehiclesMenu = new Menu();
        Menu containersMenu = new Menu();
        Menu managersMenu = new Menu();
        Menu addVehicleMenu = new Menu();
        Menu trucksMenuSubMenu = new Menu();

        // Creating menu events where it opens sub menus
        MenuEvent portsSubMenu = new MenuEvent("Ports", portsMenu);
        MenuEvent vehiclesSubMenu = new MenuEvent("Vehicles", vehiclesMenu);
        MenuEvent containersSubMenu = new MenuEvent("Containers", containersMenu);
        MenuEvent managersSubMenu = new MenuEvent("Managers", managersMenu);
        MenuEvent addVehicleSubMenu = new MenuEvent("Add Vehicle", addVehicleMenu);
        MenuEvent addTrucksSubMenu = new MenuEvent("Truck", trucksMenuSubMenu);

        // Create menu events for Vehicles
        MenuEvent addShipEvent = new MenuEvent("Ship", () -> addVehicle(Ship.class));
        MenuEvent addBasicTruckEvent = new MenuEvent("Basic Truck", () -> addVehicle(BasicTruck.class));
        MenuEvent addReeferTruckEvent = new MenuEvent("Reefer Truck", () -> addVehicle(ReeferTruck.class));
        MenuEvent addTankerTruckEvent = new MenuEvent("Tanker Truck", () -> addVehicle(TankerTruck.class));
        MenuEvent removeVehicleEvent = new MenuEvent("Remove vehicle", Main::removeVehicle);
        MenuEvent viewVehiclesEvent = new MenuEvent("View Vehicles", Database.vehicleHolder::printList);
        MenuEvent editVehiclesEvent = new MenuEvent("Edit Vehicles", Main::editVehicle);

        // SubMenu for trucks
        trucksMenuSubMenu.addEvent(addBasicTruckEvent);
        trucksMenuSubMenu.addEvent(addReeferTruckEvent);
        trucksMenuSubMenu.addEvent(addTankerTruckEvent);


        addVehicleMenu.addEvent(addShipEvent);
        addVehicleMenu.addEvent(addTrucksSubMenu);

        // Creating port events where it adds, removes and views ports
        MenuEvent addPortEvent = new MenuEvent("Add port", Main::addPort);
        MenuEvent removePortEvent = new MenuEvent("Remove port", Main::removePort);
        MenuEvent viewPortsEvent = new MenuEvent("View Ports", Database.portHolder::printList);
        MenuEvent editPortsEvent = new MenuEvent("Edit Ports", Main::editPort);

        // Creating container events where it adds, removes and views containers
        MenuEvent addContainerEvent = new MenuEvent("Add Container", Main::addContainer);
        MenuEvent removeContainerEvent = new MenuEvent("Remove Container", Main::removeContainer);
        MenuEvent viewContainersEvent = new MenuEvent("View Containers", Database.containerHolder::printList);
        MenuEvent editContainersEvent = new MenuEvent("edit Containers", Main::editContainer);

        // Creating manager events where it adds, removes and views managers
        MenuEvent addManagerEvent = new MenuEvent("Add Manager", Main::addManager);
        MenuEvent removeManagerEvent = new MenuEvent("Remove Manager", Main::removeManager);
        MenuEvent viewManagersEvent = new MenuEvent("View Managers", AccountDatabase::displayAllUsers);
        MenuEvent editManagersEvent = new MenuEvent("Edit Managers", Main::editManager);
        MenuEvent editProfileEvent = new MenuEvent("Edit Profile", Main::editProfile);

        // Adding events to sub port menu
        portsMenu.addEvent(addPortEvent, UserRoles.ADMIN);
        portsMenu.addEvent(removePortEvent, UserRoles.ADMIN);
        portsMenu.addEvent(viewPortsEvent);
        portsMenu.addEvent(editPortsEvent, UserRoles.ADMIN);

        // Adding events to sub vehicle menu
        vehiclesMenu.addEvent(addVehicleSubMenu, UserRoles.ADMIN);
        vehiclesMenu.addEvent(removeVehicleEvent, UserRoles.ADMIN);
        vehiclesMenu.addEvent(viewVehiclesEvent);
        vehiclesMenu.addEvent(editVehiclesEvent, UserRoles.ADMIN);

        // Adding events to sub container menu
        containersMenu.addEvent(addContainerEvent);
        containersMenu.addEvent(removeContainerEvent);
        containersMenu.addEvent(viewContainersEvent);
        containersMenu.addEvent(editContainersEvent, UserRoles.ADMIN);


        // Adding events to sub manager menu
        managersMenu.addEvent(addManagerEvent, UserRoles.ADMIN);
        managersMenu.addEvent(removeManagerEvent, UserRoles.ADMIN);
        managersMenu.addEvent(viewManagersEvent);
        managersMenu.addEvent(editManagersEvent, UserRoles.ADMIN);
        managersMenu.addEvent(editProfileEvent, UserRoles.PORT_MANAGER);

        // Adding all  (sub menus) to admin menu and port manager menu
        // Admin menu
        appMenu.addEvent(portsSubMenu);
        appMenu.addEvent(vehiclesSubMenu);
        appMenu.addEvent(containersSubMenu);
        appMenu.addEvent(managersSubMenu);

        // Create menu.Menu Events
        MenuEvent checkinEvent = new MenuEvent("Checkin Vehicles", Main::checkinVehicle);
        MenuEvent loadEvent = new MenuEvent("Load", Main::loadContainer);
        MenuEvent unloadEvent = new MenuEvent("Unload", Main::unloadContainer);
        MenuEvent refuelEvent = new MenuEvent("Refuel", Main::refuelVehicle);
        MenuEvent fuelUsedEvent = new MenuEvent("Fuel Used", Main::fuelUsed);
        MenuEvent containerWeightEvent = new MenuEvent("Container Weight", Main::getTotalContainersWeight);
        MenuEvent shipsInPortEvent = new MenuEvent("Ships In port", Main::getTotalShipsInPort);
        MenuEvent tripsInDayEvent = new MenuEvent("Trips In Day", Database.tripHolder::printList);
        MenuEvent tripFromRangeEvent = new MenuEvent("Trip From Range", () -> System.out.println("Trip From Range"));

        // Adding all (menu events) to admin menu and port manager menu
        // Adding events to menu
        for (MenuEvent menuEvent : Arrays.asList(loadEvent,
                unloadEvent,
                refuelEvent,
                fuelUsedEvent,
                containerWeightEvent,
                shipsInPortEvent,
                tripsInDayEvent,
                tripFromRangeEvent)) {
            appMenu.addEvent(menuEvent);
        }
    }

    private static void createShutdownHook() {
        // Creating a shutdown hook to save all holders
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Save all holders
            System.out.println();
            Database.portHolder.saveList("portsData.txt");
            Database.containerHolder.saveList("containersData.txt");
            Database.vehicleHolder.saveList("vehiclesData.txt");
            Database.tripHolder.saveList("tripsData.txt");
            Database.refuelHolder.saveList("refuelsData.txt");
            try {


                AccountDatabase.saveToFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        // End of shutdown hook
    }

    private static void fuelUsed() {
        // get port
        Port port = checkUserPort();

        System.out.println("Refuels in " + port.getName() + ":");
        Double totalFuel = 0.0;
        // get refuels in port
        for (Refuel refuel : Database.refuelHolder.getMap().values()) {
            if (refuel.getPort().getName().equals(port.getName())) {
                System.out.println(refuel);
                totalFuel += refuel.getFuelAmount();
            }
        }

        System.out.println("Total fuel used in " + port.getName() + " is " + totalFuel + " gallons");

        // get refuels in today
        System.out.println("Refuels today in " + port.getName() + ":");
        Double totalFuelToday = 0.0;
        for (Refuel refuel : Database.refuelHolder.getMap().values()) {
            if (refuel.getPort().getName().equals(port.getName()) && refuel.getRefuelDate().getDay() == new Date().getDay()) {
                System.out.println(refuel);
                totalFuelToday += refuel.getFuelAmount();
            }
        }

        System.out.println("Total fuel used today in " + port.getName() + " is " + totalFuelToday + " gallons");


    }

    private static void refuelVehicle() {
        // get port
        Port port = checkUserPort();
        // display list of vehicles in port
        System.out.println("Vehicles in " + port.getName() + ":");
        for (Vehicle vehicle : Database.vehicleHolder.getMap().values()) {
            if (vehicle.getCurrentPort().getName().equals(port.getName())) {
                System.out.println(vehicle);
            }
        }
        if (Database.vehicleHolder.getMap().values().stream().noneMatch(vehicle -> vehicle.getCurrentPort().getName().equals(port.getName()))) {
            System.out.println("No vehicles in " + port.getName());
            return;
        }

        // get vehicle to refuel using input validator
        String vehicleID = InputValidator.validateString(value -> Database.vehicleHolder.getMap().containsKey(value),
                "Please enter the vehicle ID (sh-* / tr-*): ",
                "Vehicle doesn't exist, please try again.");
        // refuel vehicle
        Vehicle vehicle = Database.vehicleHolder.getMap().get(vehicleID);
        vehicle.refuel();

    }

    private static void unloadContainer() {
        // get current port
        Port port = checkUserPort();

        // display all vehicles in port that have containers
        System.out.println("Vehicles in " + port.getName() + " that have containers:");
        for (Vehicle vehicle : Database.vehicleHolder.getMap().values()) {
            if (vehicle.getCurrentPort().getName().equals(port.getName()) && !vehicle.getContainers().isEmpty()) {
                System.out.println(vehicle);
            }
        }

        // get vehicle to unload using input validator
        String vehicleID = InputValidator.validateString(value -> Database.vehicleHolder.getMap().containsKey(value),
                "Please enter the vehicle ID (sh-* / tr-*): ",
                "Vehicle doesn't exist, please try again.");
        // unload all containers in the vehicle
        Vehicle vehicle = Database.vehicleHolder.getMap().get(vehicleID);
        List<Container> containersToUnload = vehicle.getContainers();
        vehicle.setContainers(new ArrayList<>());
        for (Container container : containersToUnload) {
            container.setCurrentPort(port);
        }
        System.out.println("Containers unloaded successfully!");

    }

    private static void checkinVehicle() {
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

            List<Container> containersToUpdate = vehicle.getContainers();
            vehicle.setContainers(new ArrayList<>());
            for (Container container : containersToUpdate) {
                container.setCurrentPort(port);
            }
            trip.setStatus(TRIP_STATUS.ARRIVED);
            // log out vehicles arriving
            System.out.println(trip.getVehicleUsed().getName() + " arrived at " + port.getName() + " and ready to unload containers");
        }
    }


    private static Port checkUserPort() {
        // Check if user is a port manager, if so, return the port they are managing
        if (loggedUser instanceof PortManager) {
            System.out.println("Current port: " + ((PortManager) loggedUser).getCurrentPort().getName());
            return ((PortManager) loggedUser).getCurrentPort();
        }

        // print all ports in the database
        System.out.println("List of ports to choose from: ");
        Database.portHolder.printList();
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
        String managerUsername = InputValidator.validateString(value -> !AccountDatabase.getInstance().containsKey(value),
                "Please enter the manager's username: ",
                "Username already exists, please try again.");
        String managerPassword = InputValidator.validateString("Please enter the manager's password: ");

        // get all ports that does not have a port manager by filtering out ports that have a port manager by using account database
        System.out.println("List of available ports to choose from: ");
        System.out.println(Database.portHolder.getMap().values().stream().filter(port -> Database.accountDatabase.values().stream().noneMatch(user -> user instanceof PortManager && ((PortManager) user).getCurrentPort().getName().equals(port.getName()))).toList());

        String vehiclePortID = InputValidator.validateString(v -> Database.portHolder.getMap().containsKey(v),
                "Please enter the port manager's current port ID (p-*): ",
                "Port not found. Try Again!");
        Port port = Database.portHolder.getMap().get(vehiclePortID);

        PortManager portManager = new PortManager(managerUsername, managerPassword, port);
        AccountDatabase.addUser(portManager);
    }

    private static void removeManager() {
        System.out.println("List of managers to choose from: ");
        AccountDatabase.displayAllUsers();

        System.out.println("\nPlease enter the manager's username: ");
        String managerUsername = InputValidator.validateString(value -> AccountDatabase.getUser(value) instanceof PortManager);
        AccountDatabase.removeUser(managerUsername);
        System.out.println("Manager removed successfully!");
    }

    private static void editProfile() {
        String managerPassword = InputValidator.validateString("Please enter your new password: ");
        String vehiclePortID = InputValidator.validateString(v -> Database.portHolder.getMap().containsKey(v),
                "Please enter your new working port ID (p-*): ",
                "Port not found. Try Again!");
        Port port = Database.portHolder.getMap().get(vehiclePortID);

        User portManager = Database.accountDatabase.get(loggedUser.getName());
        portManager.setPassword(managerPassword);
        ((PortManager) portManager).setCurrentPort(port);

        System.out.println("Your information has changed successfully!");
    }

    private static void editManager() {
        String managerName = InputValidator.validateString(Database.accountDatabase::containsKey,
                "Please enter a manager username: ",
                "Manager doesn't exist, please try again.");
        String vehiclePortID = InputValidator.validateString(v -> Database.portHolder.getMap().containsKey(v),
                "Please enter your new working port ID (p-*): ",
                "Port not found. Try Again!");
        Port port = Database.portHolder.getMap().get(vehiclePortID);

        User portManager = Database.accountDatabase.get(managerName);
        ((PortManager) portManager).setCurrentPort(port);

        System.out.println("Your information has changed successfully!");

        if (InputValidator.validateBoolean("Do you want to continue?")) editManager();
    }

    private static void addPort() {
        String portID = IDGenerator.getNextAvailableID("p", Database.portHolder.getMap().keySet().stream().toList());
        String portName = InputValidator.validateString("Please enter the port's name: ");
        Double portLatitude = InputValidator.validateDouble("Please enter the port's latitude: ");
        Double portLongitude = InputValidator.validateDouble("Please enter the port's longitude: ");
        Double portFuelCapacity = InputValidator.validateDouble("Please enter the port's storage capacity: ");
        Boolean portLandingAbility = InputValidator.validateBoolean("Please enter the port's landing ability");
        Port port = new Port(portID, portName, portLatitude, portLongitude, portFuelCapacity, portLandingAbility);
        Database.portHolder.addItem(portID, port);
    }

    private static void addVehicle(Class<? extends Vehicle> vehicleTypeCls) {
        String vehicleType;
        String vehicleIDFormat = "(tr-*)";
        List<String> vehicleIdList = Database.vehicleHolder.getMap().keySet().stream().toList();
        List<String> vehicleIdFilteredList;
        vehicleIdFilteredList = vehicleIdList.stream().filter(id -> id.startsWith("tr")).toList();

        switch (vehicleTypeCls.getName()) {
            case "vehicle.Ship" -> {
                vehicleType = "Ship";
                vehicleIDFormat = "(sh-*)";
                vehicleIdFilteredList = vehicleIdList.stream().filter(id -> id.startsWith("sh")).toList();
            }
            case "vehicle.BasicTruck" -> vehicleType = "Basic Truck";
            case "vehicle.ReeferTruck" -> vehicleType = "Reefer Truck";
            case "vehicle.TankerTruck" -> vehicleType = "Tanker Truck";
            default -> throw new IllegalStateException("Unexpected value: " + vehicleTypeCls.getName());
        }

        String vehicleName = InputValidator.validateString(String.format("Please enter the %s's name: ", vehicleType));

        String vehicleID = IDGenerator.getNextAvailableID(vehicleIDFormat.substring(1, 3), vehicleIdFilteredList);

        Double vehicleCurrentFuel = InputValidator.validateDouble(String.format("Please enter the %s's current fuel: ", vehicleType));

        Double vehicleMaxFuel = InputValidator.validateDouble(String.format("Please enter the %s's max fuel: ", vehicleType));

        Double vehicleCarryingCapacity = InputValidator.validateDouble(String.format("Please enter the %s's carrying capacity: ", vehicleType));

        // print out all ports in the database
        System.out.println("List of ports to choose from: ");
        Database.portHolder.printList();

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
        System.out.println("\nVehicle added successfully!");
        System.out.println(vehicle + "\n");

        Database.vehicleHolder.addItem(vehicleID, vehicle);

        // continue adding?
        if (InputValidator.validateBoolean("Do you want to continue?")) addVehicle(vehicleTypeCls);

    }

    private static void removeVehicle() {
        System.out.print("Please enter the vehicle ID (sh-* / tr-*): ");
        String vehicleID = InputValidator.validateString(value -> Database.vehicleHolder.getMap().containsKey(value));
        Database.vehicleHolder.getMap().remove(vehicleID);
        System.out.println("Vehicle removed successfully!");
    }

    private static void editVehicle() {
        // User input
        String vehicleID = InputValidator.validateString(v -> Database.vehicleHolder.getMap().containsKey(v),
                "Please enter the vehicle ID (sh-* / tr-*): ",
                "Vehicle doesn't exists, please try again.");
        String vehicleName = InputValidator.validateString("Please enter the vehicle's name: ");
        String newVehicleID = InputValidator.validateString(v -> !Database.vehicleHolder.getMap().containsKey(v),
                "Please enter the vehicle's ID (sh-* / tr-*): ",
                "Vehicle already exists, please try again.");
        Double vehicleCurrentFuel = InputValidator.validateDouble("Please enter the vehicle's current fuel: ");
        Double vehicleMaxFuel = InputValidator.validateDouble("Please enter the vehicle's max fuel: ");
        Double vehicleCarryingCapacity = InputValidator.validateDouble("Please enter the vehicle's carrying capacity: ");
        String vehiclePortID = InputValidator.validateString(value -> Database.portHolder.getMap().containsKey(value),
                "Please enter the vehicle's current port ID (p-*): ",
                "Port not found. Try Again!");
        Port currentPort = Database.portHolder.getMap().get(vehiclePortID);

        // Get the vehicle
        Vehicle vehicle = Database.vehicleHolder.getMap().get(vehicleID);

        // Edit the vehicle
        vehicle.setName(vehicleName);
        vehicle.setID(newVehicleID);
        vehicle.setCurrentFuel(vehicleCurrentFuel);
        vehicle.setMaxFuel(vehicleMaxFuel);
        vehicle.setCarryingCapacity(vehicleCarryingCapacity);
        vehicle.setCurrentPort(currentPort);

        System.out.println("Vehicle edited successfully!");

        if (InputValidator.validateBoolean("Do you want to continue?")) editVehicle();
    }

    @SuppressWarnings("unchecked")
    private static void addContainer() {
        Port port = checkUserPort();
        String containerID = IDGenerator.getNextAvailableID("c", Database.containerHolder.getMap().keySet().stream().toList());

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

        String destinationPortID = InputValidator.validateString(value -> Database.portHolder.getMap().containsKey(value),
                "Please enter the destination port id: ",
                "Port doesn't exist, please try again.");
        Port destinationPort = Database.portHolder.getMap().get(destinationPortID);

        Container container = new Container(containerID, containerWeight, CONTAINER_TYPE.values()[containerType - 1], port, port, destinationPort);
        System.out.println("Container added successfully!");
        System.out.println(container);
        Database.containerHolder.addItem(containerID, container);
    }

    private static void removePort() {
        System.out.println("List of ports to choose from: ");
        Database.portHolder.printList();

        System.out.println("Please enter the port ID (p-*): ");
        String portID = InputValidator.validateString(value -> Database.portHolder.getMap().containsKey(value));
        Database.portHolder.removeFromMap(portID);
        System.out.println("Port removed successfully!");
    }

    private static void removeContainer() {
        Port port = checkUserPort();

        // print out all containers in the port
        if (Database.containerHolder.getMap().values().stream().noneMatch(container -> container.getCurrentPort().getName().equals(port.getName()))) {
            System.out.println("No containers in " + port.getName());
            return;
        }

        System.out.println("List of containers to choose from: ");
        for (Container container : Database.containerHolder.getMap().values()) {
            if (container.getStartPort().getName().equals(port.getName())) {
                System.out.println(container);
            }
        }

        System.out.println("\nPlease enter the container ID (c-*): ");
        // containerId must be in the port
        String containerID = InputValidator.validateString(value -> Database.containerHolder.getMap().containsKey(value) && Database.containerHolder.getMap().get(value).getCurrentPort().getName().equals(port.getName()));
        Database.containerHolder.removeFromMap(containerID);
        System.out.println("Container removed successfully!");
    }

    private static void editContainer() {
        String containerID = InputValidator.validateString(v -> Database.containerHolder.getMap().containsKey(v),
                "Please enter the container ID (c-*): ",
                "Container doesn't exists, please try again.");
        String newContainerID = InputValidator.validateString(v -> !Database.containerHolder.getMap().containsKey(v),
                "Please enter the container ID (c-*): ",
                "Container already exists, please try again.");
        double containerWeight = InputValidator.validateDouble(value -> value > 0,
                "Please enter the container weight: ",
                "weight cant be negative or zero, please try again.");

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

        Container container = Database.containerHolder.getMap().get(containerID);
        container.setID(newContainerID);
        container.setWeight(containerWeight);
        container.setStartPort(startPort);
        container.setCurrentPort(currentPort);
        container.setDestinationPort(destinationPort);

        System.out.println("Container edited successfully!");

        if (InputValidator.validateBoolean("Do you want to continue?")) editContainer();
    }

    private static void loadContainer() {
        Port port = checkUserPort();

        // Check if there is any vehicle in the current port
        if (Database.vehicleHolder.getMap().values().stream().noneMatch(vehicle -> vehicle.getCurrentPort().getName().equals(port.getName()))) {
            System.out.println("No vehicles in " + port.getName());
            return;
        }

        displayPortItems(port);
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


    }

    private static void displayPortItems(Port port) {
        // display all containers in the port
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
    }

    private static void editPort() {
        // User input
        String portID = InputValidator.validateString(v -> Database.portHolder.getMap().containsKey(v),
                "Please enter the port ID (p-*): ",
                "Port ID doesn't exist, please try again.");
        String newPortID = InputValidator.validateString(v -> !Database.portHolder.getMap().containsKey(v),
                "Please enter the new port ID (p-*): ",
                "Port ID already exist, please try again.");
        String portName = InputValidator.validateString("Please enter the port's name");
        Double portLatitude = InputValidator.validateDouble("Please enter the port's latitude");
        Double portLongitude = InputValidator.validateDouble("Please enter the port's longitude");
        Double portStorageCapacity = InputValidator.validateDouble("Please enter the port's storage capacity");
        boolean portLandingAbility = InputValidator.validateBoolean("Please enter the port's landing ability");

        // Edit port
        Port port = Database.portHolder.getMap().get(portID);
        port.setID(newPortID);
        port.setName(portName);
        port.setLocation(new Location(portLatitude, portLongitude));
        port.setStorageCapacity(portStorageCapacity);
        port.setLanding(portLandingAbility);

        System.out.println("Port edited successfully!");

        if (InputValidator.validateBoolean("Do you want to continue?")) editPort();
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

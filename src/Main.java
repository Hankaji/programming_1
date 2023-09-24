import data.Database;
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
        MenuEvent viewAllContainersEvent = new MenuEvent("View All Containers", Database.containerHolder::printList);
        MenuEvent viewContainersEvent = new MenuEvent("View Containers", Main::viewContainers);
        MenuEvent editContainersEvent = new MenuEvent("Edit Containers", Main::editContainer);

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
        containersMenu.addEvent(viewAllContainersEvent, UserRoles.ADMIN);
        containersMenu.addEvent(editContainersEvent, UserRoles.ADMIN);


        // Adding events to sub manager menu
        managersMenu.addEvent(addManagerEvent, UserRoles.ADMIN);
        managersMenu.addEvent(removeManagerEvent, UserRoles.ADMIN);
        managersMenu.addEvent(viewManagersEvent);
        managersMenu.addEvent(editManagersEvent, UserRoles.ADMIN);
        managersMenu.addEvent(editProfileEvent, UserRoles.PORT_MANAGER);

        // Adding events to main menu

        // Create menu.Menu Events
        MenuEvent checkinEvent = new MenuEvent("Checkin Vehicles", Main::checkinVehicle);
        MenuEvent loadEvent = new MenuEvent("Load", Main::loadContainer);
        MenuEvent unloadEvent = new MenuEvent("Unload", Main::unloadContainer);
        MenuEvent refuelEvent = new MenuEvent("Refuel", Main::refuelVehicle);
        MenuEvent fuelUsedEvent = new MenuEvent("Fuel Used", Main::fuelUsed);
        MenuEvent containerWeightEvent = new MenuEvent("Container Weight", Main::getTotalContainersWeight);
        MenuEvent shipsInPortEvent = new MenuEvent("Ships In port", Main::getTotalShipsInPort);
        MenuEvent viewAllTripsEvent = new MenuEvent("View All Trips", Database.tripHolder::printList);
        MenuEvent tripsInDayEvent = new MenuEvent("Trips In Day", Main::tripsInDay);
        MenuEvent tripFromRangeEvent = new MenuEvent("Trip From Range", Main::tripsFromRange);

        appMenu.addEvent(checkinEvent);
        appMenu.addEvent(portsSubMenu);
        appMenu.addEvent(vehiclesSubMenu);
        appMenu.addEvent(containersSubMenu);
        appMenu.addEvent(managersSubMenu);

        appMenu.addEvent(loadEvent);
        appMenu.addEvent(unloadEvent);
        appMenu.addEvent(refuelEvent);
        appMenu.addEvent(fuelUsedEvent);
        appMenu.addEvent(containerWeightEvent);
        appMenu.addEvent(shipsInPortEvent);
        appMenu.addEvent(viewAllTripsEvent, UserRoles.ADMIN);
        appMenu.addEvent(tripsInDayEvent);
        appMenu.addEvent(tripFromRangeEvent);
    }

    private static void viewContainers() {
        // get port
        Port port = checkUserPort();
        if (port == null) {
            System.out.println("No port found.");
            return;
        }
        // get containers in port
        List<Container> containers = new ArrayList<>();
        // count each types of containers
        int dryCount = 0;
        int openTopCount = 0;
        int openSideCount = 0;
        int reeferCount = 0;
        int liquidCount = 0;

        for (Container container : Database.containerHolder.getMap().values()) {
            if (container.getCurrentPort().getName().equals(port.getName())) {
                switch (container.getType()) {
                    case DRY_STORAGE -> dryCount++;
                    case OPEN_TOP -> openTopCount++;
                    case OPEN_SIDE -> openSideCount++;
                    case REFRIGERATED -> reeferCount++;
                    case LIQUID -> liquidCount++;
                }
                containers.add(container);
            }
        }
        // print containers
        if (!containers.isEmpty()) {
            // print total number of containers
            System.out.println("Total number of containers in " + port.getName() + ": " + containers.size());
            // print number of each types of containers and their list
            System.out.println("\nNumber of dry storage containers: " + dryCount);
            System.out.println("List of dry storage containers:");
            for (Container container : containers) {
                if (container.getType() == CONTAINER_TYPE.DRY_STORAGE) {
                    System.out.println(container);
                }
            }
            System.out.println("\nNumber of open top containers: " + openTopCount);
            System.out.println("List of open top containers:");
            for (Container container : containers) {
                if (container.getType() == CONTAINER_TYPE.OPEN_TOP) {
                    System.out.println(container);
                }
            }
            System.out.println("\nNumber of open side containers: " + openSideCount);
            System.out.println("List of open side containers:");
            for (Container container : containers) {
                if (container.getType() == CONTAINER_TYPE.OPEN_SIDE) {
                    System.out.println(container);
                }
            }
            System.out.println("\nNumber of refrigerated containers: " + reeferCount);
            System.out.println("List of refrigerated containers:");
            for (Container container : containers) {
                if (container.getType() == CONTAINER_TYPE.REFRIGERATED) {
                    System.out.println(container);
                }
            }
            System.out.println("\nNumber of liquid containers: " + liquidCount);
            System.out.println("List of liquid containers:");
            for (Container container : containers) {
                if (container.getType() == CONTAINER_TYPE.LIQUID) {
                    System.out.println(container);
                }
            }



        } else {
            System.out.println("No containers found.");
        }
    }

    private static void tripsFromRange() {
        // get port
        Port port = checkUserPort();
        if (port == null) {
            System.out.println("No port found.");
            return;
        }
        // get start date
        Date startDate = InputValidator.validateDate("Please enter the start date (dd/mm/yyyy): ");
        if (startDate == null) return;
        // get end date
        Date endDate = InputValidator.validateDate("Please enter the end date (dd/mm/yyyy): ");
        if (endDate == null) return;
        // format dates
        String[] startDateArr = startDate.toString().split(" ");
        String startDateStr = startDateArr[2] + " " + startDateArr[1] + " " + startDateArr[5];
        String[] endDateArr = endDate.toString().split(" ");
        String endDateStr = endDateArr[2] + " " + endDateArr[1] + " " + endDateArr[5];
        System.out.println("Trips to/from " + port.getName() + " from " + startDateStr + " to " + endDateStr + ":");
        // get trips in port on date range

        // get trips in port using filter by checking if the start port/end port is this port
        List<Trip> trips = Database.tripHolder.getMap().values().stream().filter(trip -> trip.getStartPort().getName().equals(port.getName()) || trip.getEndPort().getName().equals(port.getName())).toList();

        // get trips in port using for to check if the departure date/arrival date is in the date range
        List<Trip> tripsInRange = new ArrayList<>();
//        check if this port is endport or startport
        if (trips.stream().anyMatch(trip -> trip.getEndPort().getName().equals(port.getName()))) {
            // if it is endport, check if the departure day is in the date range
            for (Trip trip : trips) {
                if (trip.getEndPort().getName().equals(port.getName())) {
                    if (trip.getArrivalDate().getDay() - startDate.getDay() >= 0 && trip.getArrivalDate().getDay() - endDate.getDay() <= 0) {
                        tripsInRange.add(trip);
                    }
                }
            }
        } else {
            // if it is startport, check if the arrival date is in the date range
            for (Trip trip : trips) {
                if (trip.getStartPort().getName().equals(port.getName())) {
                    try {
                        if (trip.getDepartureDate().getDay() - startDate.getDay() >= 0 && trip.getDepartureDate().getDay() - endDate.getDay() <= 0) {
                            tripsInRange.add(trip);
                        }
                    } catch (NullPointerException e) {
                        // catching null pointer exception when arrival date is null because it hasn't arrived yet
                        if (trip.getDepartureDate().compareTo(startDate) >= 0 && trip.getDepartureDate().compareTo(endDate) <= 0) {
                            tripsInRange.add(trip);
                        }
                    }
                }
            }
        }

//        for (Trip trip : trips) {
//            try {
//                if (((trip.getDepartureDate().compareTo(startDate) >= 0 && trip.getDepartureDate().compareTo(endDate) <= 0)) || ((trip.getArrivalDate().compareTo(startDate) >= 0 && trip.getArrivalDate().compareTo(endDate) <= 0))) {
//                    tripsInRange.add(trip);
//                }
//            } catch (NullPointerException e) {
//                // catching null pointer exception when arrival date is null because it hasn't arrived yet
//                if (trip.getDepartureDate().compareTo(startDate) >= 0 && trip.getDepartureDate().compareTo(endDate) <= 0 ) {
//                    tripsInRange.add(trip);
//                }
//            }
//        }

//        try {
//            // filter by checking if the departure date/arrival date is in the date range
//            trips = trips.stream().filter(trip -> trip.getDepartureDate().compareTo(startDate) >= 0 && trip.getDepartureDate().compareTo(endDate) <= 0 || trip.getArrivalDate().compareTo(startDate) >= 0 && trip.getArrivalDate().compareTo(endDate) <= 0).toList();
//        } catch (NullPointerException e) {
//            // catching null pointer exception when arrival date is null because it hasn't arrived yet
//            trips = trips.stream().filter(trip -> trip.getDepartureDate().compareTo(startDate) >= 0 && trip.getDepartureDate().compareTo(endDate) <= 0).toList();
//        }
        // print trips
        if (!tripsInRange.isEmpty()) {
            tripsInRange.forEach(System.out::println);
        } else {
            System.out.println("No trips found.");
        }



    }

    @SuppressWarnings("deprecation")
    private static void tripsInDay() {
        // get port
        Port port = checkUserPort();
        if (port == null) {
            System.out.println("No port found.");
            return;
        }
        // get date
        Date date = InputValidator.validateDate("Please enter the date (dd/mm/yyyy): ");
        if (date == null) return;
        // format date
        String[] dateArr = date.toString().split(" ");
        String dateStr = dateArr[2] + " " + dateArr[1] + " " + dateArr[5];
        System.out.println("Trips to/from " + port.getName() + " on " + dateStr + ":");
        // get trips in port on date
        for (Trip trip : Database.tripHolder.getMap().values()) {
            // get trips that have start port/end port as this port and departure date/ arrival date as this date
            try {
                if (((trip.getStartPort().getName().equals(port.getName())) || (trip.getEndPort().getName().equals(port.getName()))) && (trip.getDepartureDate().getDay() == date.getDay() || trip.getArrivalDate().getDay() == date.getDay())) {
                    System.out.println(trip);
                }
            } catch (NullPointerException e) {
                // catching null pointer exception when arrival date is null because it hasn't arrived yet
                if (((trip.getStartPort().getName().equals(port.getName())) || (trip.getEndPort().getName().equals(port.getName()))) && (trip.getDepartureDate().getDay() == date.getDay())) {
                    System.out.println(trip);
                }
            }
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
        if (port == null) {
            System.out.println("No port found.");
            return;
        }

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
            //noinspection deprecation
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
        if (port == null) {
            System.out.println("No port found.");
            return;
        }
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
        if (port == null) {
            System.out.println("No port found.");
            return;
        }


        // display all vehicles in port that have containers
        System.out.println("Vehicles in " + port.getName() + " that have containers:");
        try {
            for (Vehicle vehicle : Database.vehicleHolder.getMap().values()) {
                if (vehicle.getCurrentPort().getName().equals(port.getName()) && !vehicle.getContainers().isEmpty()) {
                    System.out.println(vehicle);
                }
            }
        } catch (Exception e) {
            System.out.println("No vehicles in " + port.getName() + " that have containers");
            return;
        }

        // get vehicle to unload using input validator
        String vehicleID = InputValidator.validateString(value -> Database.vehicleHolder.getMap().containsKey(value),
                "Please enter the vehicle ID (sh-* / tr-*): ",
                "Vehicle doesn't exist, please try again.");
        // unload all containers in the vehicle
        Vehicle vehicle = Database.vehicleHolder.getMap().get(vehicleID);
        // get new list of containers in the vehicle to avoid concurrent modification exception
        List<Container> containers = new ArrayList<>(vehicle.getContainers());
        // check if the containers' weight + current storage in port exceeds the port's storage capacity
        Double totalWeight = 0.0;
        for (Container container : containers) {
            totalWeight += container.getWeight();
        }
        if (totalWeight + port.getCurrentStorage() > port.getStorageCapacity()) {
            System.out.println("Cannot unload containers because the total weight of containers in " + port.getName() + " exceeds the port's storage capacity");
            System.out.println("Current storage: " + port.getCurrentStorage() + " tons");
            System.out.println("Storage capacity: " + port.getStorageCapacity() + " tons");

            return;
        }

        for (Container container : containers) {
            // unload container
            vehicle.unloadContainer(container);
            // set container's current port to the current port
            container.setCurrentPort(port);
        }
        System.out.println("Containers unloaded successfully!");

    }

    private static void checkinVehicle() {
        // Access vehicle database to get vehicles that are in the current port
        // Access container database to get containers that are in the current port
        Port port = checkUserPort();
        if (port == null) {
            System.out.println("No port found.");
            return;
        }
        // Access trip database to get trips that do not have arrival date with arrival port as current port
        List<Trip> arrivingTrips = Database.tripHolder.getMap().values().stream().filter(trip -> trip.getStatus() == TRIP_STATUS.ARRIVING && trip.getEndPort().getName().equals(port.getName())).toList();
        // for each List, change status to ARRIVED, and set arrival date, set vehicle's current port to the port
        for (Trip trip : arrivingTrips) {
            trip.setArrivalDate();
            Database.vehicleHolder.getMap().get(trip.getVehicleUsed().getID()).setCurrentPort(port);

            for (Container container : trip.getVehicleUsed().getContainers()) {
                Database.containerHolder.getMap().get(container.getID()).setCurrentPort(port);
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
        if (portID.equals("-1")) return null;
        System.out.println("Current port: " + Database.portHolder.getMap().get(portID).getName());
        return Database.portHolder.getMap().get(portID);
    }

    private static void getTotalContainersWeight() {
        // Get the port
        Port port = checkUserPort();
        if (port == null) {
            System.out.println("No port found.");
            return;
        }

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
        if (port == null) {
            System.out.println("No port found.");
            return;
        }

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
        if (managerUsername.equals("-1")) return;
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
        // print all managers in the database
        System.out.println("List of managers to choose from: ");
        AccountDatabase.displayAllUsers();

        String managerName = InputValidator.validateString(Database.accountDatabase::containsKey,
                "Please enter a manager username: ",
                "Manager doesn't exist, please try again.");

        // print all ports in the database
        System.out.println("List of ports to choose from: ");
        Database.portHolder.printList();

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
        Port port = checkUserPort();
        if (port == null) {
            System.out.println("No port found.");
            return;
        }

        System.out.println("List of vehicles to choose from: ");
        for (Vehicle vehicle : Database.vehicleHolder.getMap().values()) {
            if (vehicle.getCurrentPort().getName().equals(port.getName())) {
                System.out.println(vehicle);
            }
        }

        System.out.print("Please enter the vehicle ID (sh-* / tr-*): ");
        String vehicleID = InputValidator.validateString(value -> Database.vehicleHolder.getMap().containsKey(value) && Database.vehicleHolder.getMap().get(value).getCurrentPort().getName().equals(port.getName()));
        if (vehicleID.equals("-1")) return;
        Database.vehicleHolder.getMap().remove(vehicleID);
        System.out.println("Vehicle removed successfully!");
    }

    private static void editVehicle() {
        // print all vehicles in the database
        System.out.println("List of vehicles to choose from: ");
        Database.vehicleHolder.printList();

        String vehicleID = InputValidator.validateString(v -> Database.vehicleHolder.getMap().containsKey(v),
                "Please enter the vehicle ID (sh-* / tr-*): ",
                "Vehicle doesn't exists, please try again.");
        String vehicleName = InputValidator.validateString("Please enter the vehicle's name: ");
        Double vehicleCurrentFuel = InputValidator.validateDouble("Please enter the vehicle's current fuel: ");
        Double vehicleMaxFuel = InputValidator.validateDouble("Please enter the vehicle's max fuel: ");
        Double vehicleCarryingCapacity = InputValidator.validateDouble("Please enter the vehicle's carrying capacity: ");

        // print all ports in the database
        System.out.println("List of ports to choose from: ");
        Database.portHolder.printList();

        String vehiclePortID = InputValidator.validateString(value -> Database.portHolder.getMap().containsKey(value),
                "Please enter the vehicle's current port ID (p-*): ",
                "Port not found. Try Again!");
        Port currentPort = Database.portHolder.getMap().get(vehiclePortID);

        // Get the vehicle
        Vehicle vehicle = Database.vehicleHolder.getMap().get(vehicleID);

        // Edit the vehicle
        vehicle.setName(vehicleName);
        vehicle.setCurrentFuel(vehicleCurrentFuel);
        vehicle.setMaxFuel(vehicleMaxFuel);
        vehicle.setCarryingCapacity(vehicleCarryingCapacity);
        vehicle.setCurrentPort(currentPort);

        System.out.println("Vehicle edited successfully!");

        if (InputValidator.validateBoolean("Do you want to continue?")) editVehicle();
    }


    private static void addContainer() {
        Port port = checkUserPort();
        if (port == null) {
            System.out.println("No port found.");
            return;
        }
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
        if (portID.equals("-1")) return;
        Database.portHolder.removeFromMap(portID);
        System.out.println("Port removed successfully!");
    }

    private static void removeContainer() {
        Port port = checkUserPort();
        if (port == null) {
            System.out.println("No port found.");
            return;
        }

        // print out all containers in the port
        if (Database.containerHolder.getMap().values().stream().noneMatch(container -> container.getCurrentPort().getName().equals(port.getName()))) {
            System.out.println("No containers in " + port.getName());
            return;
        }

        System.out.println("List of containers to choose from: ");
        for (Container container : Database.containerHolder.getMap().values()) {
            if (container.getCurrentPort().getName().equals(port.getName())) {
                System.out.println(container);
            }
        }

        // get container to remove using input validator
        // container must have current port as this port

        String containerID = InputValidator.validateString(value -> Database.containerHolder.getMap().containsKey(value) && Database.containerHolder.getMap().get(value).getCurrentPort().getName().equals(port.getName()),
                "Please enter the container ID (c-*): ",
                "Container doesn't exist, please try again.");
        if (containerID.equals("-1")) return;
        Database.containerHolder.removeFromMap(containerID);
        System.out.println("Container removed successfully!");
    }

    private static void editContainer() {
        Port port = checkUserPort();
        if (port == null) {
            System.out.println("No port found.");
            return;
        }

        // print all containers in the database
        System.out.println("List of containers to choose from: ");
        Database.containerHolder.printList();

        String containerID = InputValidator.validateString(v -> Database.containerHolder.getMap().containsKey(v),
                "Please enter the container ID (c-*): ",
                "Container doesn't exists, please try again.");

        double containerWeight = InputValidator.validateDouble(value -> value > 0,
                "Please enter the container weight: ",
                "weight cant be negative or zero, please try again.");

        // print all ports in the database
        System.out.println("List of ports to choose from: ");
        Database.portHolder.printList();

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
        container.setWeight(containerWeight);
        container.setStartPort(startPort);
        container.setCurrentPort(currentPort);
        container.setDestinationPort(destinationPort);

        System.out.println("Container edited successfully!");

        if (InputValidator.validateBoolean("Do you want to continue?")) editContainer();
    }

    private static void loadContainer() {
        Port port = checkUserPort();
        if (port == null) {
            System.out.println("No port found.");
            return;
        }

        // display all containers in the port not loaded in any vehicle
        try {
            System.out.println("Containers in " + port.getName() + ":");
            for (Container container : Database.containerHolder.getMap().values()) {
                if (container.getCurrentPort().getName().equals(port.getName()) && !container.getLoaded()) {
                    System.out.println(container);
                }
            }
        } catch (Exception e) {
            System.out.println("No containers in " + port.getName());
            return;
        }
            System.out.println();
        try {
            // display all vehicles in the port
            System.out.println("Vehicles in " + port.getName() + ":");
            for (Vehicle vehicle : Database.vehicleHolder.getMap().values()) {
                if (vehicle.getCurrentPort().getName().equals(port.getName())) {
                    System.out.println(vehicle);
                }
            }
        } catch (Exception e) {
            System.out.println("No vehicles in " + port.getName());
            return;
        }

        System.out.print("Please enter the vehicle ID (sh-* / tr-*): ");
        String vehicleID = InputValidator.validateString(value -> Database.vehicleHolder.getMap().containsKey(value) && Database.vehicleHolder.getMap().get(value).getCurrentPort().getName().equals(port.getName()));

        Vehicle vehicle = Database.vehicleHolder.getMap().get(vehicleID);
        while (true) {
            // print all containers in the port that is not loaded in any vehicle
            System.out.println("List of containers to choose from: ");
            for (Container container : Database.containerHolder.getMap().values()) {
                if (container.getCurrentPort().getName().equals(port.getName()) && !container.getLoaded()) {
                    System.out.println(container);
                }
            }


            System.out.print("Please enter the container ID (c-*): ");
            String containerID = InputValidator.validateString(value -> Database.containerHolder.getMap().containsKey(value) && Database.containerHolder.getMap().get(value).getCurrentPort().getName().equals(port.getName()) && !Database.containerHolder.getMap().get(value).getLoaded());
            Container container = Database.containerHolder.getMap().get(containerID);
            // check if containerID has same destination as first container in vehicle

            // check landing of port
            if (!port.getLanding()) {
                System.out.println("Port " + port.getName() + " does not have landing ability!");
                continue;
            }

            try {
                // check if container weight + all other containers on vehicle is less than vehicle carrying capacity
                if (vehicle.getContainers().stream().mapToDouble(Container::getWeight).sum() + container.getWeight() > vehicle.getCarryingCapacity()) {
                    System.out.println("Container " + containerID + " is too heavy for the vehicle!");
                    continue;
                }

                if (vehicle.getContainers().get(0).getDestinationPort().getName().equals(container.getDestinationPort().getName())) {
                    if (vehicle instanceof Truck) {
                        vehicle.loadContainer(container);
                    } else {
                        vehicle.loadContainer(container);
                    }
                } else {
                    System.out.println("Container " + containerID + " has different destination port than the first container in the vehicle!");
                }
            } catch (IndexOutOfBoundsException e) {
                if (vehicle instanceof Truck) {
                    vehicle.loadContainer(container);
                } else {
                    vehicle.loadContainer(container);
                }
            }
            if(!InputValidator.validateBoolean("Do you want to load another container? (Y/N)")) break;
        }
        // move the vehicle to the destination port
        try {
            vehicle.moveToPort(vehicle.getContainers().get(0).getDestinationPort());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No containers loaded in the vehicle!");
        }
    }

    private static void editPort() {
        // print all ports in the database
        System.out.println("List of ports to choose from: ");
        Database.portHolder.printList();

        // User input
        String portID = InputValidator.validateString(v -> Database.portHolder.getMap().containsKey(v),
                "Please enter the port ID (p-*): ",
                "Port ID doesn't exist, please try again.");
        String portName = InputValidator.validateString("Please enter the port's name");
        Double portLatitude = InputValidator.validateDouble("Please enter the port's latitude");
        Double portLongitude = InputValidator.validateDouble("Please enter the port's longitude");
        Double portStorageCapacity = InputValidator.validateDouble("Please enter the port's storage capacity");
        boolean portLandingAbility = InputValidator.validateBoolean("Please enter the port's landing ability");

        // Edit port
        Port port = Database.portHolder.getMap().get(portID);
        port.setName(portName);
        port.setLocation(new Location(portLatitude, portLongitude));
        port.setStorageCapacity(portStorageCapacity);
        port.setLanding(portLandingAbility);

        System.out.println("Port edited successfully!");

        if (InputValidator.validateBoolean("Do you want to continue?")) editPort();
    }
}

import menu.Menu;
import menu.MenuEvent;
import user.PortManager;
import utils.Holder;

import java.util.Optional;
import java.util.Scanner;


import port.*;
import vehicle.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) {




        setUpMenu();
//        PortManager john = new PortManager("John", "1234");
//        PortManager slave = new PortManager("Slave", "1234");
//        PortManager reggin = new PortManager("Reggin", "1234");
//
//        Holder<PortManager> portHolder = new Holder<>();
//        portHolder.addToList(john);
//        portHolder.addToList(slave);
//        portHolder.addToList(reggin);
//
//        System.out.println(portHolder.getList());
//        portHolder.saveList("portManagerList.txt");
//        TestPorts();
    }

    private static void setUpMenu() {
        // Creating Holders
        Holder<Port> PortHolder = new Holder<>();
        Holder<Container> ContainerHolder = new Holder<>();
        Holder<Vehicle> VehicleHolder = new Holder<>();
        Holder<PortManager> PortManagerHolder = new Holder<>();
        Holder<Trip> TripHolder = new Holder<>();

        // populate Holders
        PortHolder.populateList("portsData.txt");
        ContainerHolder.populateList("containersData.txt");
        VehicleHolder.populateList("vehiclesData.txt");
        PortManagerHolder.populateList("portManagersData.txt");
        TripHolder.populateList("tripsData.txt");


        // View vehicles
        VehicleHolder.getList().forEach(System.out::println);

        // Creating a shutdown hook to save all holders
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                // Save all holders
                System.out.println("");
                PortHolder.saveList("portsData.txt");
                ContainerHolder.saveList("containersData.txt");
                VehicleHolder.saveList("vehiclesData.txt");
                PortManagerHolder.saveList("portManagersData.txt");
                TripHolder.saveList("tripsData.txt");


            }
        });

        // Creating a scanner object
        Scanner input = new Scanner(System.in);

        Menu mainMenu = new Menu();

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
        MenuEvent addVehicle = new MenuEvent("Add Vehicle", addVehicleMenu);



        // Create menu events for Vehicles
        MenuEvent addShip = new MenuEvent("Ship", () -> {
            System.out.println("Please enter the ship's name: ");
            String vehicleName = input.nextLine();
            System.out.println("Please enter the ship's ID: ");
            String vehicleID = input.nextLine();
            System.out.println("Please enter the ship's current fuel: ");
            Double vehicleCurrentFuel = input.nextDouble();
            System.out.println("Please enter the ship's max fuel: ");
            Double vehicleMaxFuel = input.nextDouble();
            System.out.println("Please enter the ship's carrying capacity: ");
            Double vehicleCarryingCapacity = input.nextDouble();
            System.out.println("Please enter the ship's port ID (p-*): ");
            String vehiclePortID = input.nextLine();
            Port vehiclePort = null;
            for (Port port : PortHolder.getList()) {
                if (port.getID().equals(vehiclePortID)) {
                    vehiclePort = port;
                }
            }
            Ship ship = new Ship(vehicleID, vehicleName, vehicleCurrentFuel, vehicleMaxFuel, vehiclePort, vehicleCarryingCapacity, new ArrayList<>());
        });

        MenuEvent addBasicTruck = new MenuEvent("Basic Truck", () -> {
            System.out.println("Please enter the basic truck's name: ");
            String vehicleName = input.nextLine();
            System.out.println("Please enter the basic truck's ID: ");
            String vehicleID = input.nextLine();
            System.out.println("Please enter the basic truck's current fuel: ");
            Double vehicleCurrentFuel = input.nextDouble();
            System.out.println("Please enter the basic truck's max fuel: ");
            Double vehicleMaxFuel = input.nextDouble();
            System.out.println("Please enter the basic truck's carrying capacity: ");
            Double vehicleCarryingCapacity = input.nextDouble();
            Port vehiclePort = null;
            input.nextLine();
            while (true) {
                // get port id
                System.out.println("Please enter the basic truck's port id (p-*): ");
                String vehiclePortID = input.nextLine();
                // get port
                for (Port port : PortHolder.getList()) {
                    if (port.getID().equals(vehiclePortID)) {
                        vehiclePort = port;
                        // exit loop
                        break;
                    }
                }
                if (vehiclePort == null) {
                    System.out.println("Port not found. Try Again!");
                    continue;
                }
                break;
            }
            BasicTruck basicTruck = new BasicTruck(vehicleID, vehicleName, vehicleCurrentFuel, vehicleMaxFuel, vehiclePort, vehicleCarryingCapacity, new ArrayList<>());
            VehicleHolder.addToList(basicTruck);
        });
        MenuEvent addReeferTruck = new MenuEvent("Reefer Truck", () -> {
            System.out.println("Please enter the reefer truck's name: ");
            String vehicleName = input.nextLine();
            System.out.println("Please enter the reefer truck's ID: ");
            String vehicleID = input.nextLine();
            System.out.println("Please enter the reefer truck's current fuel: ");
            Double vehicleCurrentFuel = input.nextDouble();
            System.out.println("Please enter the reefer truck's max fuel: ");
            Double vehicleMaxFuel = input.nextDouble();
            System.out.println("Please enter the reefer truck's carrying capacity: ");
            Double vehicleCarryingCapacity = input.nextDouble();
            Port vehiclePort = null;
            input.nextLine();
            while (true) {
                // get port id
                System.out.println("Please enter the basic truck's port id (p-*): ");
                String vehiclePortID = input.nextLine();
                // get port
                for (Port port : PortHolder.getList()) {
                    if (port.getID().equals(vehiclePortID)) {
                        vehiclePort = port;
                        // exit loop
                        break;
                    }
                }
                if (vehiclePort == null) {
                    System.out.println("Port not found. Try Again!");
                    continue;
                }
                break;
            }
            ReeferTruck reeferTruck = new ReeferTruck(vehicleID, vehicleName, vehicleCurrentFuel, vehicleMaxFuel, vehiclePort, vehicleCarryingCapacity, new ArrayList<>());
            VehicleHolder.addToList(reeferTruck);
        });
        MenuEvent addTankerTruck = new MenuEvent("Tanker Truck", () -> {
            System.out.println("Please enter the tanker truck's name: ");
            String vehicleName = input.nextLine();
            System.out.println("Please enter the tanker truck's ID: ");
            String vehicleID = input.nextLine();
            System.out.println("Please enter the tanker truck's current fuel: ");
            Double vehicleCurrentFuel = input.nextDouble();
            System.out.println("Please enter the tanker truck's max fuel: ");
            Double vehicleMaxFuel = input.nextDouble();
            System.out.println("Please enter the tanker truck's carrying capacity: ");
            Double vehicleCarryingCapacity = input.nextDouble();
            Port vehiclePort = null;
            input.nextLine();
            while (true) {
                // get port id
                System.out.println("Please enter the basic truck's port id (p-*): ");
                String vehiclePortID = input.nextLine();
                // get port
                for (Port port : PortHolder.getList()) {
                    if (port.getID().equals(vehiclePortID)) {
                        vehiclePort = port;
                        // exit loop
                        break;
                    }
                }
                if (vehiclePort == null) {
                    System.out.println("Port not found. Try Again!");
                    continue;
                }
                break;
            }
            TankerTruck tankerTruck = new TankerTruck(vehicleID, vehicleName, vehicleCurrentFuel, vehicleMaxFuel, vehiclePort, vehicleCarryingCapacity, new ArrayList<>());
            VehicleHolder.addToList(tankerTruck);
        });

        trucksMenu.addEvent(addBasicTruck);
        trucksMenu.addEvent(addReeferTruck);
        trucksMenu.addEvent(addTankerTruck);
        MenuEvent addTrucks = new MenuEvent("Truck", trucksMenu);
        addVehicleMenu.addEvent(addShip);
        addVehicleMenu.addEvent(addTrucks);

        // Creating port events where it adds, removes and views ports
        MenuEvent addPort = new MenuEvent("Add port", () -> {
            System.out.println("Please enter the port ID (p-*): ");
            String portID = input.nextLine();
            System.out.println("Please enter the port name: ");
            String portName = input.nextLine();
            System.out.println("Please enter the port latitude: ");
            Double portLatitude = input.nextDouble();
            System.out.println("Please enter the port longitude: ");
            Double portLongitude = input.nextDouble();
            System.out.println("Please enter the port fuel capacity: ");
            Double portFuelCapacity = input.nextDouble();
            System.out.println("Please enter the port landing ability (True/ False): ");
            Boolean portLandingAbility = input.nextBoolean();
            Port port = new Port(portID, portName, portLatitude, portLongitude, portFuelCapacity, portLandingAbility);
            PortHolder.addToList(port);
        });
        MenuEvent removePort = new MenuEvent("Remove port", () -> {
            System.out.println("Remove port");
        });
        MenuEvent viewPorts = new MenuEvent("View Ports", () -> {
            System.out.println("View Ports");
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
        mainMenu.addEvent(ports);
        mainMenu.addEvent(vehicles);
        mainMenu.addEvent(containers);
        mainMenu.addEvent(managers);

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

//    public static void TestPorts() {
//        // Create Manchester port
//        Port manchesterPort = new Port("p-1", "Manchester Port", 53.4808, 2.2426, 5000000.0, true);
//        Port liverpoolPort = new Port("p-2", "Liverpool Port", 53.4084, 2.9916, 10000000.0, true);
//        Port londonPort = new Port("p-3", "London Port", 51.5074, 0.1278, 7500000.0, true);
//        Port newPort = new Port("p-4", "New Port", 51.5074, 0.1278, 7500000.0, true);
//
//        // Create a port holder
////        Holder<Port> portHolder = new Holder<>();
////        portHolder.populateList("portsData.txt");
////        portHolder.addToList(manchesterPort);
////        portHolder.addToList(liverpoolPort);
////        portHolder.addToList(londonPort);
////        portHolder.addToList(newPort);
////        portHolder.getList().forEach(System.out::println);
////
////        portHolder.saveList("portsData.txt");
//
//        // manchester ports containers
////        Container manchesterContainer1 = new Container("c-1", 1.0, CONTAINER_TYPE.DRY_STORAGE, manchesterPort, liverpoolPort);
////        Container manchesterContainer2 = new Container("c-2", 2.2, CONTAINER_TYPE.OPEN_TOP, manchesterPort, londonPort);
////        Container manchesterContainer3 = new Container("c-3", 3.1, CONTAINER_TYPE.OPEN_SIDE, manchesterPort, londonPort);
//
//        // liverpool ports containers
////        Container liverpoolContainer1 = new Container("c-4", 4.0, CONTAINER_TYPE.REFRIGERATED, liverpoolPort, manchesterPort);
////        Container liverpoolContainer2 = new Container("c-5", 5.0, CONTAINER_TYPE.LIQUID, liverpoolPort, londonPort);
////        Container liverpoolContainer3 = new Container("c-6", 6.0, CONTAINER_TYPE.DRY_STORAGE, liverpoolPort, londonPort);
//
//        // london ports containers
////        Container londonContainer1 = new Container("c-7", 1.4, CONTAINER_TYPE.OPEN_TOP, londonPort, manchesterPort);
////        Container londonContainer2 = new Container("c-8", 2.3, CONTAINER_TYPE.OPEN_SIDE, londonPort, liverpoolPort);
////        Container londonContainer3 = new Container("c-9", 0.8, CONTAINER_TYPE.REFRIGERATED, londonPort, liverpoolPort);
//
//        // create trucks in liverpool port
//        BasicTruck liverpoolTruck1 = new BasicTruck("tr-1", "Liverpool Truck 1", 100.0, 100.0, liverpoolPort, 100.0, new ArrayList<>());
//
//        // view truck
////        System.out.println(liverpoolTruck1);
////        liverpoolTruck1.loadContainer(liverpoolContainer1);
////        liverpoolTruck1.loadContainer(liverpoolContainer2);
////        liverpoolTruck1.loadContainer(liverpoolContainer3);
////        System.out.println(liverpoolTruck1);
//
////        // view the ports
////        System.out.println(manchesterPort);
////        System.out.println(liverpoolPort);
////        System.out.println(londonPort);
//
//        // view the manchester containers
////        System.out.println(manchesterContainer1);
////        System.out.println(manchesterContainer2);
////        System.out.println(manchesterContainer3);
//
//        // view ports distance
////        System.out.println("Distance between Manchester and Liverpool: " + manchesterPort.getDistance(liverpoolPort) + " kilometers");
////        System.out.println("Distance between Manchester and London: " + manchesterPort.getDistance(londonPort) + " kilometers");
////        System.out.println("Distance between Liverpool and London: " + liverpoolPort.getDistance(londonPort) + " kilometers");
//
////        // view fuel consumption of London containers
////        System.out.println("Fuel consumption of London container 1: " + londonContainer1.getShipFuelConsumption() + " gallons");
////        System.out.println("Fuel consumption of London container 2: " + londonContainer2.getShipFuelConsumption() + " gallons");
////        System.out.println("Fuel consumption of London container 3: " + londonContainer3.getShipFuelConsumption() + " gallons");
//
//        // Make a sample trip
////        Trip trip = new Trip("t-1",liverpoolTruck1,manchesterPort,liverpoolPort);
////        trip.setArrivalDate();
////        System.out.println(trip);
//        Holder<Trip> tripHolder = new Holder<>();
//        tripHolder.populateList("tripsData.txt");
//        tripHolder.getList().forEach(System.out::println);
////        tripHolder.addToList(trip);
////        tripHolder.saveList("tripsData.txt");
//
//    }
}

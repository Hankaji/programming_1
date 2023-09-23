import port.*;
import user.AccountDatabase;
import user.Admin;
import user.PortManager;
import user.User;
import utils.Holder;
import vehicle.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataPopulation {
    private static final Holder<Port> portHolder = new Holder<>();
    private static final Holder<Container> containerHolder = new Holder<>();
    private static final Holder<Vehicle> vehicleHolder = new Holder<>();
    private static final Holder<Trip> tripHolder = new Holder<>();
    public static void main(String[] args) throws IOException {
        // Delete the all the data files and repopulate it with the following data
        String accountFilePath = "src/data/accounts.txt";
        String containerFilerPath = "src/data/containersData.txt";
        String portFilePath = "src/data/portsData.txt";
        String tripFilePath = "src/data/tripsData.txt";
        String vehicleFilePath = "src/data/vehiclesData.txt";

        for (String filePath : Arrays.asList(
                accountFilePath,
                containerFilerPath,
                portFilePath,
                tripFilePath,
                vehicleFilePath)) {
            File myObj = new File(filePath);
            if (myObj.delete()) {
                System.out.println("Deleted the file: " + myObj.getName());
            } else {
                System.out.println("Failed to delete the file.");
            }
        }

        // Ports
        populatePortHolder();
        portHolder.saveList("portsData.txt");

        // Containers
        populateContainerHolder();
        containerHolder.saveList("containersData.txt");

        // Users
        populateUsers();
        AccountDatabase.saveToFile();

        // Vehicles
        populateVehicleData();
        vehicleHolder.saveList("vehiclesData.txt");

        // Trips
        populateTripData();
        tripHolder.saveList("tripsData.txt");
    }

    private static void populatePortHolder() {
        Port manchesterPort = new Port("p-1", "Manchester Port", 53.4808, 2.2426, 1000.0, true);
        Port londonPort = new Port("p-2", "London Port", 51.5074, 0.1278, 1000.0, true);
        Port liverpoolPort = new Port("p-3", "Liverpool Port", 53.4084, 2.9916, 1000.0, true);
        Port greenwichPort = new Port("p-4", "Greenwich Port", 51.4826, 0.0077, 1000.0, true);
        Port doverPort = new Port("p-5", "Dover Port", 51.1279, 1.3134, 1000.0, true);
        Port southamptonPort = new Port("p-6", "Southampton Port", 50.9097, 1.4044, 1000.0, true);


        // Create a port holder
        portHolder.addItem(manchesterPort.getID(), manchesterPort);
        portHolder.addItem(londonPort.getID(), londonPort);
        portHolder.addItem(liverpoolPort.getID(), liverpoolPort);
        portHolder.addItem(greenwichPort.getID(), greenwichPort);
        portHolder.addItem(doverPort.getID(), doverPort);
        portHolder.addItem(southamptonPort.getID(), southamptonPort);
    }

    private static void populateContainerHolder() {
        Container container1 = new Container("c-1", 1.0, CONTAINER_TYPE.DRY_STORAGE, portHolder.getMap().get("p-1"), portHolder.getMap().get("p-1"), portHolder.getMap().get("p-2"));
        Container container2 = new Container("c-2", 1.0, CONTAINER_TYPE.DRY_STORAGE, portHolder.getMap().get("p-4"), portHolder.getMap().get("p-2"), portHolder.getMap().get("p-3"));
        Container container3 = new Container("c-3", 1.0, CONTAINER_TYPE.DRY_STORAGE, portHolder.getMap().get("p-1"), portHolder.getMap().get("p-3"), portHolder.getMap().get("p-4"));
        Container container4 = new Container("c-4", 1.0, CONTAINER_TYPE.DRY_STORAGE, portHolder.getMap().get("p-3"), portHolder.getMap().get("p-4"), portHolder.getMap().get("p-5"));
        Container container5 = new Container("c-5", 1.0, CONTAINER_TYPE.DRY_STORAGE, portHolder.getMap().get("p-6"), portHolder.getMap().get("p-5"), portHolder.getMap().get("p-6"));
        Container container6 = new Container("c-6", 1.0, CONTAINER_TYPE.DRY_STORAGE, portHolder.getMap().get("p-3"), portHolder.getMap().get("p-6"), portHolder.getMap().get("p-1"));
        Container container7 = new Container("c-7", 1.0, CONTAINER_TYPE.DRY_STORAGE, portHolder.getMap().get("p-5"), portHolder.getMap().get("p-1"), portHolder.getMap().get("p-2"));
        Container container8 = new Container("c-8", 1.0, CONTAINER_TYPE.LIQUID, portHolder.getMap().get("p-1"), portHolder.getMap().get("p-2"), portHolder.getMap().get("p-3"));
        Container container9 = new Container("c-9", 1.0, CONTAINER_TYPE.LIQUID, portHolder.getMap().get("p-5"), portHolder.getMap().get("p-3"), portHolder.getMap().get("p-4"));
        Container container10 = new Container("c-10", 1.0, CONTAINER_TYPE.LIQUID, portHolder.getMap().get("p-1"), portHolder.getMap().get("p-4"), portHolder.getMap().get("p-5"));
        Container container11 = new Container("c-11", 1.0, CONTAINER_TYPE.LIQUID, portHolder.getMap().get("p-2"), portHolder.getMap().get("p-5"), portHolder.getMap().get("p-6"));
        Container container12 = new Container("c-12", 1.0, CONTAINER_TYPE.LIQUID, portHolder.getMap().get("p-4"), portHolder.getMap().get("p-6"), portHolder.getMap().get("p-1"));
        Container container13 = new Container("c-13", 1.0, CONTAINER_TYPE.LIQUID, portHolder.getMap().get("p-1"), portHolder.getMap().get("p-1"), portHolder.getMap().get("p-2"));
        Container container14 = new Container("c-14", 1.0, CONTAINER_TYPE.LIQUID, portHolder.getMap().get("p-1"), portHolder.getMap().get("p-2"), portHolder.getMap().get("p-3"));
        Container container15 = new Container("c-15", 1.0, CONTAINER_TYPE.OPEN_SIDE, portHolder.getMap().get("p-3"), portHolder.getMap().get("p-3"), portHolder.getMap().get("p-4"));
        Container container16 = new Container("c-16", 1.0, CONTAINER_TYPE.OPEN_SIDE, portHolder.getMap().get("p-1"), portHolder.getMap().get("p-4"), portHolder.getMap().get("p-5"));
        Container container17 = new Container("c-17", 1.0, CONTAINER_TYPE.OPEN_SIDE, portHolder.getMap().get("p-5"), portHolder.getMap().get("p-5"), portHolder.getMap().get("p-6"));
        Container container18 = new Container("c-18", 1.0, CONTAINER_TYPE.OPEN_SIDE, portHolder.getMap().get("p-4"), portHolder.getMap().get("p-6"), portHolder.getMap().get("p-1"));
        Container container19 = new Container("c-19", 1.0, CONTAINER_TYPE.OPEN_SIDE, portHolder.getMap().get("p-6"), portHolder.getMap().get("p-1"), portHolder.getMap().get("p-2"));
        Container container20 = new Container("c-20", 1.0, CONTAINER_TYPE.OPEN_SIDE, portHolder.getMap().get("p-3"), portHolder.getMap().get("p-2"), portHolder.getMap().get("p-3"));
        Container container21 = new Container("c-21", 1.0, CONTAINER_TYPE.OPEN_SIDE, portHolder.getMap().get("p-1"), portHolder.getMap().get("p-3"), portHolder.getMap().get("p-4"));
        Container container22 = new Container("c-22", 1.0, CONTAINER_TYPE.OPEN_TOP, portHolder.getMap().get("p-1"), portHolder.getMap().get("p-4"), portHolder.getMap().get("p-5"));
        Container container23 = new Container("c-23", 1.0, CONTAINER_TYPE.OPEN_TOP, portHolder.getMap().get("p-2"), portHolder.getMap().get("p-5"), portHolder.getMap().get("p-6"));
        Container container24 = new Container("c-24", 1.0, CONTAINER_TYPE.OPEN_TOP, portHolder.getMap().get("p-5"), portHolder.getMap().get("p-6"), portHolder.getMap().get("p-1"));
        Container container25 = new Container("c-25", 1.0, CONTAINER_TYPE.OPEN_TOP, portHolder.getMap().get("p-6"), portHolder.getMap().get("p-1"), portHolder.getMap().get("p-2"));
        Container container26 = new Container("c-26", 1.0, CONTAINER_TYPE.OPEN_TOP, portHolder.getMap().get("p-1"), portHolder.getMap().get("p-2"), portHolder.getMap().get("p-3"));
        Container container27 = new Container("c-27", 1.0, CONTAINER_TYPE.OPEN_TOP, portHolder.getMap().get("p-3"), portHolder.getMap().get("p-3"), portHolder.getMap().get("p-4"));
        Container container28 = new Container("c-28", 1.0, CONTAINER_TYPE.OPEN_TOP, portHolder.getMap().get("p-1"), portHolder.getMap().get("p-4"), portHolder.getMap().get("p-5"));
        Container container29 = new Container("c-29", 1.0, CONTAINER_TYPE.REFRIGERATED, portHolder.getMap().get("p-2"), portHolder.getMap().get("p-5"), portHolder.getMap().get("p-6"));
        Container container30 = new Container("c-30", 1.0, CONTAINER_TYPE.REFRIGERATED, portHolder.getMap().get("p-3"), portHolder.getMap().get("p-6"), portHolder.getMap().get("p-1"));
        Container container31 = new Container("c-31", 1.0, CONTAINER_TYPE.REFRIGERATED, portHolder.getMap().get("p-4"), portHolder.getMap().get("p-1"), portHolder.getMap().get("p-2"));
        Container container32 = new Container("c-32", 1.0, CONTAINER_TYPE.REFRIGERATED, portHolder.getMap().get("p-6"), portHolder.getMap().get("p-2"), portHolder.getMap().get("p-3"));
        Container container33 = new Container("c-33", 1.0, CONTAINER_TYPE.REFRIGERATED, portHolder.getMap().get("p-3"), portHolder.getMap().get("p-3"), portHolder.getMap().get("p-4"));
        Container container34 = new Container("c-34", 1.0, CONTAINER_TYPE.REFRIGERATED, portHolder.getMap().get("p-2"), portHolder.getMap().get("p-4"), portHolder.getMap().get("p-5"));
        Container container35 = new Container("c-35", 1.0, CONTAINER_TYPE.REFRIGERATED, portHolder.getMap().get("p-1"), portHolder.getMap().get("p-5"), portHolder.getMap().get("p-6"));
        Container container36 = new Container("c-36", 1.0, CONTAINER_TYPE.REFRIGERATED, portHolder.getMap().get("p-3"), portHolder.getMap().get("p-6"), portHolder.getMap().get("p-1"));


        // Create a port holder
        containerHolder.addItem(container1.getID(), container1);
        containerHolder.addItem(container2.getID(), container2);
        containerHolder.addItem(container3.getID(), container3);
        containerHolder.addItem(container4.getID(), container4);
        containerHolder.addItem(container5.getID(), container5);
        containerHolder.addItem(container6.getID(), container6);
        containerHolder.addItem(container7.getID(), container7);
        containerHolder.addItem(container8.getID(), container8);
        containerHolder.addItem(container9.getID(), container9);
        containerHolder.addItem(container10.getID(), container10);
        containerHolder.addItem(container11.getID(), container11);
        containerHolder.addItem(container12.getID(), container12);
        containerHolder.addItem(container13.getID(), container13);
        containerHolder.addItem(container14.getID(), container14);
        containerHolder.addItem(container15.getID(), container15);
        containerHolder.addItem(container16.getID(), container16);
        containerHolder.addItem(container17.getID(), container17);
        containerHolder.addItem(container18.getID(), container18);
        containerHolder.addItem(container19.getID(), container19);
        containerHolder.addItem(container20.getID(), container20);
        containerHolder.addItem(container21.getID(), container21);
        containerHolder.addItem(container22.getID(), container22);
        containerHolder.addItem(container23.getID(), container23);
        containerHolder.addItem(container24.getID(), container24);
        containerHolder.addItem(container25.getID(), container25);
        containerHolder.addItem(container26.getID(), container26);
        containerHolder.addItem(container27.getID(), container27);
        containerHolder.addItem(container28.getID(), container28);
        containerHolder.addItem(container29.getID(), container29);
        containerHolder.addItem(container30.getID(), container30);
        containerHolder.addItem(container31.getID(), container31);
        containerHolder.addItem(container32.getID(), container32);
        containerHolder.addItem(container33.getID(), container33);
        containerHolder.addItem(container34.getID(), container34);
        containerHolder.addItem(container35.getID(), container35);
        containerHolder.addItem(container36.getID(), container36);
    }

    private static void populateUsers() {
        AccountDatabase.getInstance();

        PortManager portManager1 = new PortManager("minh", "minh", portHolder.getMap().get("p-1"));
        PortManager portManager2 = new PortManager("james", "james", portHolder.getMap().get("p-2"));
        PortManager portManager3 = new PortManager("josh", "josh", portHolder.getMap().get("p-3"));
        PortManager portManager4 = new PortManager("joe", "joe", portHolder.getMap().get("p-4"));
        PortManager portManager5 = new PortManager("jake", "jake", portHolder.getMap().get("p-5"));
        PortManager portManager6 = new PortManager("joshua", "joshua", portHolder.getMap().get("p-6"));

        AccountDatabase.addUser(new Admin("admin", "admin"));
        AccountDatabase.addUser(portManager1);
        AccountDatabase.addUser(portManager2);
        AccountDatabase.addUser(portManager3);
        AccountDatabase.addUser(portManager4);
        AccountDatabase.addUser(portManager5);
        AccountDatabase.addUser(portManager6);
    }

    private static void populateVehicleData() {
        // Create 12 Ships: String name, String id: sh-*, double currentFuel, double maxFuel, Port currentPort, double carryingCapacity in tons, List<Container> containers
        Vehicle ship1 = new Ship("EA ship", "sh-1", 1000.0, 1000.0, portHolder.getMap().get("p-1"), 14.0, new ArrayList<>());
        Vehicle ship2 = new Ship("Steam ship", "sh-2", 800.0, 1100.0, portHolder.getMap().get("p-2"), 12.0, new ArrayList<>());
        Vehicle ship3 = new Ship("Sail ship", "sh-3", 900.0, 1200.0, portHolder.getMap().get("p-3"), 13.5, new ArrayList<>());
        Vehicle ship4 = new Ship("Cargo ship", "sh-4", 800.0, 1000.0, portHolder.getMap().get("p-4"), 14.0, new ArrayList<>());
        Vehicle ship5 = new Ship("Container ship", "sh-5", 870.0, 1200.0, portHolder.getMap().get("p-5"), 14.8, new ArrayList<>());
        Vehicle ship6 = new Ship("Tanker ship", "sh-6", 640.0, 1000.0, portHolder.getMap().get("p-6"), 13.2, new ArrayList<>());
        Vehicle ship7 = new Ship("Bulk carrier ship", "sh-7", 850.0, 1000.0, portHolder.getMap().get("p-1"), 3.3, new ArrayList<>());
        Vehicle ship8 = new Ship("Ferry ship", "sh-8", 1000.0, 1200.0, portHolder.getMap().get("p-2"), 14.0, new ArrayList<>());
        Vehicle ship9 = new Ship("Cruise ship", "sh-9", 1000.0, 1000.0, portHolder.getMap().get("p-3"), 17.3, new ArrayList<>());
        Vehicle ship10 = new Ship("Fishing ship", "sh-10", 800.0, 1200.0, portHolder.getMap().get("p-4"), 9.6, new ArrayList<>());
        Vehicle ship11 = new Ship("Research ship", "sh-11", 700.0, 1000.0, portHolder.getMap().get("p-5"), 10.4, new ArrayList<>());
        Vehicle ship12 = new Ship("Tug ship", "sh-12", 800.0, 1000.0, portHolder.getMap().get("p-6"), 15.8, new ArrayList<>());

        // Create 6 trucks in each port, (2 basic trucks, 2 reefer trucks, 2 tanker trucks) (String name, String id tr-*, double currentFuel, double maxFuel, Port currentPort, double carryingCapacity in tons, List<Container> containers)
        Vehicle truck1 = new BasicTruck("Basic Truck 1", "tr-1", 1000.0, 1000.0, portHolder.getMap().get("p-1"), 14.0, new ArrayList<>());
        Vehicle truck2 = new BasicTruck("Basic Truck 2", "tr-2", 800.0, 1100.0, portHolder.getMap().get("p-1"), 12.0, new ArrayList<>());
        Vehicle truck3 = new ReeferTruck("Reefer Truck 3", "tr-3", 900.0, 1200.0, portHolder.getMap().get("p-1"), 13.5, new ArrayList<>());
        Vehicle truck4 = new ReeferTruck("Reefer Truck 4", "tr-4", 800.0, 1000.0, portHolder.getMap().get("p-1"), 14.0, new ArrayList<>());
        Vehicle truck5 = new TankerTruck("Tanker Truck 5", "tr-5", 870.0, 1200.0, portHolder.getMap().get("p-1"), 14.8, new ArrayList<>());
        Vehicle truck6 = new TankerTruck("Tanker Truck 6", "tr-6", 640.0, 1000.0, portHolder.getMap().get("p-1"), 13.2, new ArrayList<>());
        Vehicle truck420 = new BasicTruck("Basic Truck 420", "tr-420", 100000000.0, 1000000000000.0, portHolder.getMap().get("p-1"), 14.0, new ArrayList<>());

        // port 2
        Vehicle truck7 = new BasicTruck("Basic Truck 7", "tr-7", 1000.0, 1000.0, portHolder.getMap().get("p-2"), 14.0, new ArrayList<>());
        Vehicle truck8 = new BasicTruck("Basic Truck 8", "tr-8", 800.0, 1100.0, portHolder.getMap().get("p-2"), 12.0, new ArrayList<>());
        Vehicle truck9 = new ReeferTruck("Reefer Truck 9", "tr-9", 900.0, 1200.0, portHolder.getMap().get("p-2"), 13.5, new ArrayList<>());
        Vehicle truck10 = new ReeferTruck("Reefer Truck 10", "tr-10", 800.0, 1000.0, portHolder.getMap().get("p-2"), 14.0, new ArrayList<>());
        Vehicle truck11 = new TankerTruck("Tanker Truck 11", "tr-11", 870.0, 1200.0, portHolder.getMap().get("p-2"), 14.8, new ArrayList<>());
        Vehicle truck12 = new TankerTruck("Tanker Truck 12", "tr-12", 640.0, 1000.0, portHolder.getMap().get("p-2"), 13.2, new ArrayList<>());

        // port 3
        // Create 6 trucks in each port, (2 basic trucks, 2 reefer trucks, 2 tanker trucks) (String name, String id tr-*, double currentFuel 500 - 1500, double maxFuel 1000 - 1500, Port currentPort, double carryingCapacity in tons, List<Container> containers)
        Vehicle truck13 = new BasicTruck("Basic truck 13", "tr-13", 780.0, 1000.0, portHolder.getMap().get("p-3"), 14.0, new ArrayList<>());
        Vehicle truck14 = new BasicTruck("Basic truck 14", "tr-14", 980.0, 1500.0, portHolder.getMap().get("p-3"), 11.5, new ArrayList<>());
        Vehicle truck15 = new ReeferTruck("Basic truck 15", "tr-15", 1080.0, 1200.0, portHolder.getMap().get("p-3"), 7.3, new ArrayList<>());
        Vehicle truck16 = new ReeferTruck("Basic truck 16", "tr-16", 1200.0, 1100.0, portHolder.getMap().get("p-3"), 8.0, new ArrayList<>());
        Vehicle truck17 = new TankerTruck("Basic truck 17", "tr-17", 980.0, 1400.0, portHolder.getMap().get("p-3"), 10.1, new ArrayList<>());
        Vehicle truck18 = new TankerTruck("Basic truck 18", "tr-18", 1080.0, 1000.0, portHolder.getMap().get("p-3"), 12.0, new ArrayList<>());

        // Port 4
        Vehicle truck19 = new BasicTruck("Reefer truck 19", "tr-19", 780.0, 1000.0, portHolder.getMap().get("p-4"), 14.0, new ArrayList<>());
        Vehicle truck20 = new BasicTruck("Reefer truck 20", "tr-20", 980.0, 1500.0, portHolder.getMap().get("p-4"), 11.5, new ArrayList<>());
        Vehicle truck21 = new ReeferTruck("Reefer truck 21", "tr-21", 1080.0, 1200.0, portHolder.getMap().get("p-4"), 7.3, new ArrayList<>());
        Vehicle truck22 = new ReeferTruck("Reefer truck 22", "tr-22", 1200.0, 1100.0, portHolder.getMap().get("p-4"), 8.0, new ArrayList<>());
        Vehicle truck23 = new TankerTruck("Reefer truck 23", "tr-23", 980.0, 1400.0, portHolder.getMap().get("p-4"), 10.1, new ArrayList<>());
        Vehicle truck24 = new TankerTruck("Reefer truck 24", "tr-24", 1080.0, 1000.0, portHolder.getMap().get("p-4"), 12.0, new ArrayList<>());

        // Port 5
        Vehicle truck25 = new BasicTruck("Basic truck 25", "tr-25", 780.0, 1000.0, portHolder.getMap().get("p-3"), 14.0, new ArrayList<>());
        Vehicle truck26 = new BasicTruck("Basic truck 26", "tr-26", 980.0, 1500.0, portHolder.getMap().get("p-3"), 11.5, new ArrayList<>());
        Vehicle truck27 = new BasicTruck("Basic truck 27", "tr-27", 1080.0, 1200.0, portHolder.getMap().get("p-3"), 7.3, new ArrayList<>());
        Vehicle truck28 = new BasicTruck("Basic truck 28", "tr-28", 1200.0, 1100.0, portHolder.getMap().get("p-3"), 8.0, new ArrayList<>());
        Vehicle truck29 = new BasicTruck("Basic truck 29", "tr-29", 980.0, 1400.0, portHolder.getMap().get("p-3"), 10.1, new ArrayList<>());
        Vehicle truck30 = new BasicTruck("Basic truck 30", "tr-30", 1080.0, 1000.0, portHolder.getMap().get("p-3"), 12.0, new ArrayList<>());

        // Port 6
        Vehicle truck31 = new BasicTruck("Basic truck 31", "tr-31", 780.0, 1000.0, portHolder.getMap().get("p-3"), 14.0, new ArrayList<>());
        Vehicle truck32 = new BasicTruck("Basic truck 32", "tr-32", 980.0, 1500.0, portHolder.getMap().get("p-3"), 11.5, new ArrayList<>());
        Vehicle truck33 = new ReeferTruck("Basic truck 33", "tr-33", 1080.0, 1200.0, portHolder.getMap().get("p-3"), 7.3, new ArrayList<>());
        Vehicle truck34 = new ReeferTruck("Basic truck 34", "tr-34", 1200.0, 1100.0, portHolder.getMap().get("p-3"), 8.0, new ArrayList<>());
        Vehicle truck35 = new TankerTruck("Basic truck 35", "tr-35", 980.0, 1400.0, portHolder.getMap().get("p-3"), 10.1, new ArrayList<>());
        Vehicle truck36 = new TankerTruck("Basic truck 36", "tr-36", 1080.0, 1000.0, portHolder.getMap().get("p-3"), 12.0, new ArrayList<>());


        // add all vehicles to vehicleHolder
        vehicleHolder.addItem(ship1.getID(), ship1);
        vehicleHolder.addItem(ship2.getID(), ship2);
        vehicleHolder.addItem(ship3.getID(), ship3);
        vehicleHolder.addItem(ship4.getID(), ship4);
        vehicleHolder.addItem(ship5.getID(), ship5);
        vehicleHolder.addItem(ship6.getID(), ship6);
        vehicleHolder.addItem(ship7.getID(), ship7);
        vehicleHolder.addItem(ship8.getID(), ship8);
        vehicleHolder.addItem(ship9.getID(), ship9);
        vehicleHolder.addItem(ship10.getID(), ship10);
        vehicleHolder.addItem(ship11.getID(), ship11);
        vehicleHolder.addItem(ship12.getID(), ship12);
        vehicleHolder.addItem(truck1.getID(), truck1);
        vehicleHolder.addItem(truck2.getID(), truck2);
        vehicleHolder.addItem(truck3.getID(), truck3);
        vehicleHolder.addItem(truck4.getID(), truck4);
        vehicleHolder.addItem(truck5.getID(), truck5);
        vehicleHolder.addItem(truck6.getID(), truck6);
        vehicleHolder.addItem(truck7.getID(), truck7);
        vehicleHolder.addItem(truck8.getID(), truck8);
        vehicleHolder.addItem(truck9.getID(), truck9);
        vehicleHolder.addItem(truck10.getID(), truck10);
        vehicleHolder.addItem(truck11.getID(), truck11);
        vehicleHolder.addItem(truck12.getID(), truck12);
        vehicleHolder.addItem(truck13.getID(), truck13);
        vehicleHolder.addItem(truck14.getID(), truck14);
        vehicleHolder.addItem(truck15.getID(), truck15);
        vehicleHolder.addItem(truck16.getID(), truck16);
        vehicleHolder.addItem(truck17.getID(), truck17);
        vehicleHolder.addItem(truck18.getID(), truck18);
        vehicleHolder.addItem(truck19.getID(), truck19);
        vehicleHolder.addItem(truck20.getID(), truck20);
        vehicleHolder.addItem(truck21.getID(), truck21);
        vehicleHolder.addItem(truck22.getID(), truck22);
        vehicleHolder.addItem(truck23.getID(), truck23);
        vehicleHolder.addItem(truck24.getID(), truck24);
        vehicleHolder.addItem(truck25.getID(), truck25);
        vehicleHolder.addItem(truck26.getID(), truck26);
        vehicleHolder.addItem(truck27.getID(), truck27);
        vehicleHolder.addItem(truck28.getID(), truck28);
        vehicleHolder.addItem(truck29.getID(), truck29);
        vehicleHolder.addItem(truck30.getID(), truck30);
        vehicleHolder.addItem(truck31.getID(), truck31);
        vehicleHolder.addItem(truck32.getID(), truck32);
        vehicleHolder.addItem(truck33.getID(), truck33);
        vehicleHolder.addItem(truck34.getID(), truck34);
        vehicleHolder.addItem(truck35.getID(), truck35);
        vehicleHolder.addItem(truck36.getID(), truck36);
        vehicleHolder.addItem(truck420.getID(), truck420);
    }

    private static void populateTripData() {
        // Crete 20 trips
        Trip trip1 = new Trip("t-1", vehicleHolder.getMap().get("sh-1"), generateRandomDates(1, 3), generateRandomDates(4, 6), portHolder.getMap().get("p-1"), portHolder.getMap().get("p-2"));
        Trip trip2 = new Trip("t-2", vehicleHolder.getMap().get("tr-12"), generateRandomDates(1, 3), generateRandomDates(4, 6), portHolder.getMap().get("p-2"), portHolder.getMap().get("p-3"));
        Trip trip3 = new Trip("t-3", vehicleHolder.getMap().get("sh-3"), generateRandomDates(1, 3), generateRandomDates(4, 6), portHolder.getMap().get("p-3"), portHolder.getMap().get("p-4"));
        Trip trip4 = new Trip("t-4", vehicleHolder.getMap().get("sh-4"), generateRandomDates(1, 3), generateRandomDates(4, 6), portHolder.getMap().get("p-4"), portHolder.getMap().get("p-5"));
        Trip trip5 = new Trip("t-5", vehicleHolder.getMap().get("tr-25"), generateRandomDates(1, 3), generateRandomDates(4, 6), portHolder.getMap().get("p-5"), portHolder.getMap().get("p-6"));
        Trip trip6 = new Trip("t-6", vehicleHolder.getMap().get("sh-6"), generateRandomDates(1, 3), generateRandomDates(4, 6), portHolder.getMap().get("p-6"), portHolder.getMap().get("p-1"));
        Trip trip7 = new Trip("t-7", vehicleHolder.getMap().get("tr-17"), generateRandomDates(1, 3), generateRandomDates(4, 6), portHolder.getMap().get("p-1"), portHolder.getMap().get("p-2"));
        Trip trip8 = new Trip("t-8", vehicleHolder.getMap().get("sh-8"), generateRandomDates(1, 3), generateRandomDates(4, 6), portHolder.getMap().get("p-2"), portHolder.getMap().get("p-3"));
        Trip trip9 = new Trip("t-9", vehicleHolder.getMap().get("sh-9"), generateRandomDates(1, 3), generateRandomDates(4, 6), portHolder.getMap().get("p-3"), portHolder.getMap().get("p-4"));
        Trip trip10 = new Trip("t-10", vehicleHolder.getMap().get("tr-10"), generateRandomDates(1, 3), generateRandomDates(4, 6), portHolder.getMap().get("p-4"), portHolder.getMap().get("p-5"));
        Trip trip11 = new Trip("t-11", vehicleHolder.getMap().get("tr-19"), generateRandomDates(1, 3), generateRandomDates(4, 6), portHolder.getMap().get("p-5"), portHolder.getMap().get("p-6"));
        Trip trip12 = new Trip("t-12", vehicleHolder.getMap().get("sh-12"), generateRandomDates(1, 3), generateRandomDates(4, 6), portHolder.getMap().get("p-6"), portHolder.getMap().get("p-1"));
        Trip trip13 = new Trip("t-13", vehicleHolder.getMap().get("tr-27"), generateRandomDates(1, 3), generateRandomDates(4, 6), portHolder.getMap().get("p-1"), portHolder.getMap().get("p-2"));
        Trip trip14 = new Trip("t-14", vehicleHolder.getMap().get("sh-2"), generateRandomDates(1, 3), generateRandomDates(4, 6), portHolder.getMap().get("p-2"), portHolder.getMap().get("p-3"));
        Trip trip15 = new Trip("t-15", vehicleHolder.getMap().get("tr-33"), generateRandomDates(1, 3), generateRandomDates(4, 6), portHolder.getMap().get("p-3"), portHolder.getMap().get("p-4"));
        Trip trip16 = new Trip("t-16", vehicleHolder.getMap().get("sh-4"), generateRandomDates(1, 3), generateRandomDates(4, 6), portHolder.getMap().get("p-4"), portHolder.getMap().get("p-5"));
        Trip trip17 = new Trip("t-17", vehicleHolder.getMap().get("sh-5"), generateRandomDates(1, 3), generateRandomDates(4, 6), portHolder.getMap().get("p-5"), portHolder.getMap().get("p-6"));
        Trip trip18 = new Trip("t-18", vehicleHolder.getMap().get("tr-16"), generateRandomDates(1, 3), generateRandomDates(4, 6), portHolder.getMap().get("p-6"), portHolder.getMap().get("p-1"));
        Trip trip19 = new Trip("t-19", vehicleHolder.getMap().get("tr-30"), generateRandomDates(1, 3), generateRandomDates(4, 6), portHolder.getMap().get("p-1"), portHolder.getMap().get("p-2"));
        Trip trip20 = new Trip("t-20", vehicleHolder.getMap().get("sh-8"), generateRandomDates(1, 3), generateRandomDates(4, 6), portHolder.getMap().get("p-2"), portHolder.getMap().get("p-3"));
        Trip trip21 = new Trip("t-21", vehicleHolder.getMap().get("sh-11"), new Date(), new Date(), portHolder.getMap().get("p-2"), portHolder.getMap().get("p-3"));


        // set all trips to ARRIVED
        trip1.setStatus(TRIP_STATUS.ARRIVED);
        trip2.setStatus(TRIP_STATUS.ARRIVED);
        trip3.setStatus(TRIP_STATUS.ARRIVED);
        trip4.setStatus(TRIP_STATUS.ARRIVED);
        trip5.setStatus(TRIP_STATUS.ARRIVED);
        trip6.setStatus(TRIP_STATUS.ARRIVED);
        trip7.setStatus(TRIP_STATUS.ARRIVED);
        trip8.setStatus(TRIP_STATUS.ARRIVED);
        trip9.setStatus(TRIP_STATUS.ARRIVED);
        trip10.setStatus(TRIP_STATUS.ARRIVED);
        trip11.setStatus(TRIP_STATUS.ARRIVED);
        trip12.setStatus(TRIP_STATUS.ARRIVED);
        trip13.setStatus(TRIP_STATUS.ARRIVED);
        trip14.setStatus(TRIP_STATUS.ARRIVED);
        trip15.setStatus(TRIP_STATUS.ARRIVED);
        trip16.setStatus(TRIP_STATUS.ARRIVED);
        trip17.setStatus(TRIP_STATUS.ARRIVED);
        trip18.setStatus(TRIP_STATUS.ARRIVED);
        trip19.setStatus(TRIP_STATUS.ARRIVED);
        trip20.setStatus(TRIP_STATUS.ARRIVED);
        trip21.setStatus(TRIP_STATUS.ARRIVED);


        // Add all trips to tripHolder
        tripHolder.addItem(trip1.getID(), trip1);
        tripHolder.addItem(trip2.getID(), trip2);
        tripHolder.addItem(trip3.getID(), trip3);
        tripHolder.addItem(trip4.getID(), trip4);
        tripHolder.addItem(trip5.getID(), trip5);
        tripHolder.addItem(trip6.getID(), trip6);
        tripHolder.addItem(trip7.getID(), trip7);
        tripHolder.addItem(trip8.getID(), trip8);
        tripHolder.addItem(trip9.getID(), trip9);
        tripHolder.addItem(trip10.getID(), trip10);
        tripHolder.addItem(trip11.getID(), trip11);
        tripHolder.addItem(trip12.getID(), trip12);
        tripHolder.addItem(trip13.getID(), trip13);
        tripHolder.addItem(trip14.getID(), trip14);
        tripHolder.addItem(trip15.getID(), trip15);
        tripHolder.addItem(trip16.getID(), trip16);
        tripHolder.addItem(trip17.getID(), trip17);
        tripHolder.addItem(trip18.getID(), trip18);
        tripHolder.addItem(trip19.getID(), trip19);
        tripHolder.addItem(trip20.getID(), trip20);
        tripHolder.addItem(trip21.getID(), trip21);
    }

    public static Date generateRandomDates(int minDays, int maxDays) {
        Random random = new Random();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        int randomDaysToAdd = random.nextInt(maxDays - minDays + 1) + minDays;
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, randomDaysToAdd);

        return calendar.getTime();
    }
}

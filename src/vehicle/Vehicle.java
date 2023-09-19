package vehicle;

import data.Database;
import port.*;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

public abstract class Vehicle implements Serializable {
    // private attributes of the class
    // String name
    // String ID
    // Double currentFuel
    // Double maxFuel
    // Double carryingCapacity
    // List<String> containers

    // Attributes
    private String ID;
    private String name;
    private Double currentFuel;
    private Double maxFuel;
    private Double carryingCapacity;

    private Port currentPort;
    private List<Container> containers;

    protected Vehicle(String name, String ID, Double currentFuel, Double maxFuel, Port port ,Double carryingCapacity, List<Container> containers) {
        this.name = name;
        this.ID = ID;
        this.currentFuel = currentFuel;
        this.maxFuel = maxFuel;
        this.currentPort = port;
        this.carryingCapacity = carryingCapacity;
        this.containers = containers;
    }

    public Vehicle() {
        // create default attributes
        this.name = "default";
        this.ID = "default";
        this.currentFuel = 0.0;
        this.maxFuel = 0.0;
        this.carryingCapacity = 0.0;
        this.containers = null;
        this.currentPort = null;
    }

    public void loadContainer(Container container) {
        containers.add(container);
        // print the vehicle name and the container ID
        System.out.println("Loaded container " + container.getID() + " onto " + this.name);
    }


    public void unloadContainer(Container container) {
        containers.remove(container);
        System.out.println("Unloaded container " + container.getID() + " from " + this.name);
    }

    public Double refuelVehicleAndReturnAmount() {
        Double fuelNeeded = maxFuel - currentFuel;
        currentFuel = maxFuel;
        return fuelNeeded;
    }

    public void deleteVehicle() {}

    public Boolean checkFuel() {
        return currentFuel > 0;
    }
    @Override
    public String toString() {
        return "vehicle{" +
                "name: '" + name + '\'' +
                ", ID: '" + ID + '\'' +
                ", currentFuel: " + currentFuel +
                ", maxFuel: " + maxFuel +
                ", carryingCapacity: " + carryingCapacity +
                ", containers: " + containers +
                ", currentPort: " + ((currentPort != null) ? currentPort.getName() : "sail away")   +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public Double getCurrentFuel() {
        return currentFuel;
    }

    public Double getMaxFuel() {
        return maxFuel;
    }

    public Double getCarryingCapacity() {
        return carryingCapacity;
    }

    public Port getCurrentPort() {
        return currentPort;
    }

    public List<Container> getContainers() {
        return containers;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void setCurrentFuel(Double currentFuel) {
        this.currentFuel = currentFuel;
    }

    public void setMaxFuel(Double maxFuel) {
        this.maxFuel = maxFuel;
    }

    public void setCarryingCapacity(Double carryingCapacity) {
        this.carryingCapacity = carryingCapacity;
    }

    public void setCurrentPort(Port currentPort) {
        this.currentPort = currentPort;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }

    public void moveToPort(Port destinationPort) {
        // check if the vehicle has enough fuel to move to the destination port
        double fuelNeeded = 0.0;
        for (Container container : containers) {
            // fuelNeeded depends on the type of vehicle
            fuelNeeded += (this instanceof Ship) ? container.getShipFuelConsumption() * currentPort.getDistance(destinationPort) : container.getTruckFuelConsumption() * currentPort.getDistance(destinationPort);
        }
        if (currentFuel >= fuelNeeded) {
            // if it does, then move the vehicle to the destination port
            System.out.println(this.name + " moved from " + currentPort.getName() + " to " + destinationPort.getName());
            currentFuel -= fuelNeeded;
        } else {
            // if it doesn't, then print an error message
            System.out.println("Not enough fuel to move " + this.name + " to " + destinationPort.getName());
            return;
        }

        // create a trip
        // get trip id by checking trip database
        int tripID = Database.tripHolder.getMap().values().toArray().length;
        String tripIDString =  "t-" + tripID;
        Trip trip = new Trip(tripIDString, this, currentPort, destinationPort);
        currentPort = null;

//        Trip trip = new Trip(tripIDString, this, currentPort, destinationPort);
        Database.tripHolder.addItem(tripIDString, trip);
    }

    // create an arrive method

}


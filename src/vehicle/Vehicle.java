package vehicle;

import port.*;

import java.io.Serializable;
import java.util.List;

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

    protected Vehicle(String ID, String name, Double currentFuel, Double maxFuel, Port port ,Double carryingCapacity, List<Container> containers) {
        this.name = name;
        this.ID = ID;
        this.currentFuel = currentFuel;
        this.maxFuel = maxFuel;
        this.currentPort = port;
        this.carryingCapacity = carryingCapacity;
        this.containers = containers;
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
}

package vehicle;

import data.Database;
import port.*;
import utils.IDGenerator;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
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
        container.setLoaded(true);
        // print the vehicle name and the container ID
        System.out.println("Loaded container " + container.getID() + " onto " + this.name);

    }


    public void unloadContainer(Container container) {
        containers.remove(container);
        container.setLoaded(false);
        System.out.println("Unloaded container " + container.getID() + " from " + this.name + " at " + currentPort.getName());
    }

    public Double refuelVehicleAndReturnAmount() {
        Double fuelNeeded = maxFuel - currentFuel;
        currentFuel = maxFuel;
        return fuelNeeded;
    }

    public void deleteVehicle() {}

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
        double fuelConsumption = 0.0;
        // get weight of each type of container
        Double dryStorageWeight = 0.0;
        Double openTopWeight = 0.0;
        Double openSideWeight = 0.0;
        Double refrigeratedWeight = 0.0;
        Double liquidWeight = 0.0;

        // get FuelConsumption of each type of container by finding the first container of that type and getting its fuel consumption
        Double dryStorageFuelConsumption = 0.0;
        Double openTopFuelConsumption = 0.0;
        Double openSideFuelConsumption = 0.0;
        Double refrigeratedFuelConsumption = 0.0;
        Double liquidFuelConsumption = 0.0;
        // find the first container of each type
        for (Container container : containers) {
            if (container.getType() == CONTAINER_TYPE.DRY_STORAGE) {
                // check vehicle type
                if (this instanceof Ship) {
                    dryStorageFuelConsumption = container.getShipFuelConsumption();
                } else {
                    dryStorageFuelConsumption = container.getTruckFuelConsumption();
                }
                break;
            }
        }
        for (Container container : containers) {
            if (container.getType() == CONTAINER_TYPE.OPEN_TOP) {
                if (this instanceof Ship) {
                    openTopFuelConsumption = container.getShipFuelConsumption();
                } else {
                    openTopFuelConsumption = container.getTruckFuelConsumption();
                }
                break;
            }
        }
        for (Container container : containers) {
            if (container.getType() == CONTAINER_TYPE.OPEN_SIDE) {
                if (this instanceof Ship) {
                    openSideFuelConsumption = container.getShipFuelConsumption();
                } else {
                    openSideFuelConsumption = container.getTruckFuelConsumption();
                }
                break;
            }
        }
        for (Container container : containers) {
            if (container.getType() == CONTAINER_TYPE.REFRIGERATED) {
                if (this instanceof Ship) {
                    refrigeratedFuelConsumption = container.getShipFuelConsumption();
                } else {
                    refrigeratedFuelConsumption = container.getTruckFuelConsumption();
                }
                break;
            }
        }
        for (Container container : containers) {
            if (container.getType() == CONTAINER_TYPE.LIQUID) {
                if (this instanceof Ship) {
                    liquidFuelConsumption = container.getShipFuelConsumption();
                } else {
                    liquidFuelConsumption = container.getTruckFuelConsumption();
                }
                break;
            }
        }
        // use loop to get weight of each type of container
        for (Container container : containers) {
            // check destination port's landing
            if (!container.getDestinationPort().getLanding()) {
                System.out.println("Cannot move " + this.name + " to " + destinationPort.getName() + " because it does not have landing capabilities");
                System.out.println("Please unload containers and load them on a ship");
                return;
            }
            switch (container.getType()) {
                case DRY_STORAGE -> dryStorageWeight += container.getWeight();
                case OPEN_TOP -> openTopWeight += container.getWeight();
                case OPEN_SIDE -> openSideWeight += container.getWeight();
                case REFRIGERATED -> refrigeratedWeight += container.getWeight();
                case LIQUID -> liquidWeight += container.getWeight();
            }
        }


        // calculate fuel needed
        fuelNeeded = this.getCurrentPort().getDistance(destinationPort) * (dryStorageWeight * dryStorageFuelConsumption + openTopWeight * openTopFuelConsumption + openSideWeight * openSideFuelConsumption + refrigeratedWeight * refrigeratedFuelConsumption + liquidWeight * liquidFuelConsumption);

        System.out.println("Current fuel: " + currentFuel + " gallons");
        System.out.println("Fuel needed: " + fuelNeeded + " gallons");

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
        // get trip id by using idgenerator
        String tripID = IDGenerator.getNextAvailableID("t", Database.tripHolder.getMap().keySet().stream().toList());

        Trip trip = new Trip(tripID, this, currentPort, destinationPort);
        currentPort = null;

        Database.tripHolder.addItem(tripID, trip);
    }

    public void refuel() {
        // check if the vehicle is at a port

        // check if the vehicle has enough fuel
        if (Objects.equals(currentFuel, maxFuel)) {
            System.out.println("Cannot refuel " + this.name + " because it is already full");
            return;
        }
        // refuel the vehicle

        Double fuelNeeded = maxFuel - currentFuel;

        currentFuel = maxFuel;

        // create a refuel
        // get refuel id by checking refuel database
        int refuelID = Database.refuelHolder.getMap().values().toArray().length;
        String refuelIDString =  "r-" + refuelID;
        Refuel refuel = new Refuel(refuelIDString, fuelNeeded, currentPort, this);
        Database.refuelHolder.addItem(refuelIDString, refuel);

        System.out.println(this.name + " refueled at " + currentPort.getName() + " for " + fuelNeeded + " gallons");
    }


}


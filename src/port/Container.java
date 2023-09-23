package port;

import java.io.Serializable;

public class Container implements Serializable {
    private String ID;
    private Double weight;
    private final CONTAINER_TYPE CONTAINERType;
    private Double shipFuelConsumption;
    private Double truckFuelConsumption;
    private Port startPort;
    private Port currentPort;
    private Port destinationPort;

    private Boolean isLoaded;


    public Boolean getLoaded() {
        return isLoaded;
    }

    public void setLoaded(Boolean loaded) {
        isLoaded = loaded;
    }

    public Container(String ID, Double weight, CONTAINER_TYPE CONTAINERType, Port startPort, Port currentPort, Port destinationPort) {
        this.ID = ID;
        this.weight = weight;
        this.CONTAINERType = CONTAINERType;
        this.startPort = startPort;
        this.currentPort = currentPort;
        this.destinationPort = destinationPort;
        this.isLoaded = false;

        switch (this.CONTAINERType) {
            case DRY_STORAGE -> {
                this.shipFuelConsumption = 3.5;
                this.truckFuelConsumption = 4.6;
            }
            case OPEN_TOP -> {
                this.shipFuelConsumption = 2.8;
                this.truckFuelConsumption = 3.2;
            }
            case OPEN_SIDE -> {
                this.shipFuelConsumption = 2.7;
                this.truckFuelConsumption = 3.2;
            }
            case REFRIGERATED -> {
                this.shipFuelConsumption = 4.5;
                this.truckFuelConsumption = 5.4;
            }
            case LIQUID -> {
                this.shipFuelConsumption = 4.8;
                this.truckFuelConsumption = 5.3;
            }
        }
    }

    @Override
    public String toString() {
        return "Container{" +
                "ID: " + ID +
                ", weight: " + weight + " tons" +
                ", type: " + CONTAINERType +
                ", isLoaded: " + isLoaded +
                ", shipFuelConsumption: " + shipFuelConsumption + " gallons/km" +
                ", truckFuelConsumption :" + truckFuelConsumption + " gallons/km" +
                ", startPort: " + startPort.getName() +
                ", currentPort: " + currentPort.getName() +
                ", destinationPort: " + destinationPort.getName() +
                '}';
    }

    public CONTAINER_TYPE getType() {
        return CONTAINERType;
    }

    public Double getShipFuelConsumption() {
        return  shipFuelConsumption;
    }

    public Double getTruckFuelConsumption() {
        return  truckFuelConsumption;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Port getStartPort() {
        return startPort;
    }

    public void setStartPort(Port startPort) {
        this.startPort = startPort;
    }

    public Port getCurrentPort() {
        return currentPort;
    }

    public void setCurrentPort(Port currentPort) {
        this.currentPort = currentPort;
    }

    public Port getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(Port destinationPort) {
        this.destinationPort = destinationPort;
    }
}

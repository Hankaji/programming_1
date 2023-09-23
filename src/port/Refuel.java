package port;

import vehicle.*;

import java.io.Serializable;
import java.util.Date;

public class Refuel implements Serializable {
    private String ID;
    private Double fuelAmount;
    private Port port;
    private Vehicle vehicle;

    private Date refuelDate;

    public Refuel(String ID, Double fuelAmount, Port port, Vehicle vehicle) {
        this.ID = ID;
        this.fuelAmount = fuelAmount;
        this.port = port;
        this.vehicle = vehicle;
        this.refuelDate = new Date();
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Double getFuelAmount() {
        return fuelAmount;
    }

    public void setFuelAmount(Double fuelAmount) {
        this.fuelAmount = fuelAmount;
    }

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Date getRefuelDate() {
        return refuelDate;
    }

    public void setRefuelDate(Date refuelDate) {
        this.refuelDate = refuelDate;
    }

    public static String displayDate(Date date) {
        String[] dateArr = date.toString().split(" ");
        return dateArr[3] + " " + dateArr[2] + " " + dateArr[1] + " " + dateArr[5];
    }

    @Override
    public String toString() {
        return "Refuel{" +
                "ID: '" + ID + '\'' +
                ", fuelAmount: " + fuelAmount + " gallons" +
                ", port: " + port.getName() +
                ", vehicle: " + vehicle.getName() +
                ", refuelDate: " + displayDate(refuelDate) +
                '}';
    }
}

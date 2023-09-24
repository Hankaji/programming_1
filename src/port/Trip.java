package port;

import data.Database;
import vehicle.*;

import java.io.Serializable;
import java.util.Date;

public class Trip implements Serializable {
    private String ID;
    private Vehicle vehicleUsed;
    private Port startPort;
    private Port endPort;
    private Date departureDate;
    private Date arrivalDate;
    private TRIP_STATUS status;

    public Trip(String ID, Vehicle vehicleUsed, Port startPort, Port endPort) {
        this.ID = ID;
        this.vehicleUsed = vehicleUsed;
        this.startPort = startPort;
        this.endPort = endPort;
        this.departureDate = new Date();
        this.status = TRIP_STATUS.ARRIVING;
    }

    // This constructor is used only for data population purpose
    public Trip(String ID, Vehicle vehicleUsed, Date departureDate, Date arrivalDate, Port startPort, Port endPort) {
        this.ID = ID;
        this.vehicleUsed = vehicleUsed;
        this.startPort = startPort;
        this.endPort = endPort;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.status = TRIP_STATUS.ARRIVING;
    }

    // Display date, month and year from a date object
    public static String displayDate(Date date) {
        try {
            String[] dateArr = date.toString().split(" ");
            return dateArr[3] + " " + dateArr[2] + " " + dateArr[1] + " " + dateArr[5];
        } catch (NullPointerException e) {
            return "Not arrived yet";
        }
    }

    public void checkTripExistingTime() {
        Date currentDate = new Date();
        // Get different between current date and departure date
        long diff = 0;
        try {
            diff = currentDate.getTime() - arrivalDate.getTime();

        } catch (NullPointerException e) {
            return;
        }
        // Get different in seconds
        long diffSeconds = diff / (1000 * 60 * 60 * 24);

        // If different is more than 7 days, delete trip
        if (diffSeconds >= 7) {
            Database.tripHolder.getMap().remove(ID);
        }
    }

    public void setArrivalDate() {
        this.arrivalDate = new Date();
    }

    public String getID() {
        return ID;
    }

    public Vehicle getVehicleUsed() {
        return vehicleUsed;
    }

    public Port getStartPort() {
        return startPort;
    }

    public Port getEndPort() {
        return endPort;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public TRIP_STATUS getStatus() {
        return status;
    }

    public void setStatus(TRIP_STATUS status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "ID: '" + ID + '\'' +
                ", vehicleUsed: " + vehicleUsed.getName() +
                ", startPort: " + startPort.getName() +
                ", endPort: " + endPort.getName() +
                ", departureDate: " + displayDate(departureDate) +
                ", arrivalDate: " + displayDate(arrivalDate) +
                ", status: " + status +
                '}';
    }
}

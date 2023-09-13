package port;
public class Port {
    private String ID;
    private String name;
    private Location location;
    private Double storageCapacity;
    private Boolean landing;

    public Port(String ID, String name, Double latitude, Double longitude ,Double storageCapacity, Boolean landing) {
        this.ID = ID;
        this.name = name;
        this.location = new Location(latitude, longitude);
        this.storageCapacity = storageCapacity;
        this.landing = landing;
    }

    // get distance between another port
    public Double getDistance(Port port) {
        Double lat1 = this.location.getLatitude();
        Double lon1 = this.location.getLongitude();
        Double lat2 = port.getLocation().getLatitude();
        Double lon2 = port.getLocation().getLongitude();

        Double R = 6371e3; // metres
        double radLat1 = Math.toRadians(lat1);
        double radLat2 = Math.toRadians(lat2);
        double deltaLats = Math.toRadians(lat2-lat1);
        double deltaLongs = Math.toRadians(lon2-lon1);

        double a = Math.sin(deltaLats/2) * Math.sin(deltaLats/2) +
                Math.cos(radLat1) * Math.cos(radLat2) *
                        Math.sin(deltaLongs/2) * Math.sin(deltaLongs/2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        // Convert to kilometers and round to 2 decimal places and return the value
        return Math.round((R * c) / 1000.0) * 100.0 / 100.0;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "port{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", " + location.toString() +
                ", storageCapacity=" + storageCapacity +
                ", landing=" + landing +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getStorageCapacity() {
        return storageCapacity;
    }

    public void setStorageCapacity(Double storageCapacity) {
        this.storageCapacity = storageCapacity;
    }

    public Boolean getLanding() {
        return landing;
    }

    public void setLanding(Boolean landing) {
        this.landing = landing;
    }

}

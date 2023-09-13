package port;

public class Location {
    private Double latitude;    // -90 to 90
    private Double longitude;   // -180 to 180

    public Location(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Location: (" +
                latitude +
                ", " + longitude +
                ')';
    }
}

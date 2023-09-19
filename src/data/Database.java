package data;

import port.Container;
import port.Port;
import port.Trip;
import user.AccountDatabase;
import user.PortManager;
import user.User;
import utils.Holder;
import vehicle.Vehicle;

import java.util.Map;

public class Database {
    public static final Holder<Port> portHolder = Holder.<Port>fetchList("portsData.txt");
    public static final Holder<Container> containerHolder = Holder.<Container>fetchList("containersData.txt");
    public static final Holder<Vehicle> vehicleHolder = Holder.<Vehicle>fetchList("vehiclesData.txt");
    public static final Holder<PortManager> portManagerHolder = Holder.<PortManager>fetchList("portManagersData.txt");
    public static final Holder<Trip> tripHolder = Holder.<Trip>fetchList("tripsData.txt");

    public  static final Map<String, User> accountDatabase = AccountDatabase.getInstance();
}

package vehicle;

import port.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReeferTruck extends Truck {
    protected ReeferTruck(String ID, String name, Double currentFuel, Double maxFuel, Port port, Double carryingCapacity, List<Container> containers) {
        super(ID, name, currentFuel, maxFuel, port, carryingCapacity, containers, new ArrayList<>(Arrays.asList(CONTAINER_TYPE.DRY_STORAGE, CONTAINER_TYPE.OPEN_TOP, CONTAINER_TYPE.OPEN_SIDE)));
    }
}

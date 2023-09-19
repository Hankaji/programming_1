package vehicle;

import port.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ReeferTruck extends Truck {
    public ReeferTruck(String name, String ID, Double currentFuel, Double maxFuel, Port port, Double carryingCapacity, List<Container> containers) {
        super(name, ID, currentFuel, maxFuel, port, carryingCapacity, containers, new ArrayList<>(Arrays.asList(CONTAINER_TYPE.DRY_STORAGE, CONTAINER_TYPE.OPEN_TOP, CONTAINER_TYPE.OPEN_SIDE)));
    }
}

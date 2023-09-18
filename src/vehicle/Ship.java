package vehicle;

import port.*;

import java.util.List;
import java.util.Optional;

public class Ship extends Vehicle {

    public Ship(String name, String id, Double currentFuel, Double maxFuel, Port port, Double carryingCapacity, List<Container> containers) {
        super(name, id, currentFuel, maxFuel, port, carryingCapacity, containers);
    }
}

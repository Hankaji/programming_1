package vehicle;

import port.*;

import java.util.List;

public class Ship extends Vehicle {

    protected Ship(String name, String id, Double currentFuel, Double maxFuel, Port port, Double carryingCapacity, List<Container> containers) {
        super(name, id, currentFuel, maxFuel, port, carryingCapacity, containers);
    }
}

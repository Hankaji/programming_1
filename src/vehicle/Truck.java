package vehicle;

import port.*;
import java.util.List;


public abstract class Truck extends Vehicle {
    protected List<CONTAINER_TYPE> carriableContainers;
    protected Truck(String name, String ID, Double currentFuel, Double maxFuel, Port port, Double carryingCapacity, List<Container> containers, List<CONTAINER_TYPE> carriableContainers) {
        super(name, ID, currentFuel, maxFuel, port, carryingCapacity, containers);
        this.carriableContainers = carriableContainers;
    }

    @Override
    public void loadContainer(Container container) {
        if (carriableContainers.contains(container.getType())) {
            super.loadContainer(container);
        } else {
            System.out.println("Cannot load container " + container.getID() + " onto " + this.getName() + ", wrong container type!");
        }
    }

    public List<CONTAINER_TYPE> getCarriableContainers() {
        return carriableContainers;
    }
}

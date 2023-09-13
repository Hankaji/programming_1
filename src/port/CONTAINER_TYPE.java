package port;

import java.io.Serializable;

// Enumerate for the types of containers
public enum CONTAINER_TYPE implements Serializable {
    DRY_STORAGE,
    OPEN_TOP,
    OPEN_SIDE,
    REFRIGERATED,
    LIQUID
}

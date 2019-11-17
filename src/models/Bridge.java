package models;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import util.PortConstants;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Bridge {
    private final List<Ship> shipsOnBridge = new CopyOnWriteArrayList<>();
    private final Logger logger = (Logger) LoggerFactory.getLogger("bridge");
    private static Bridge bridge = null;

    public synchronized boolean tryAddShip(Ship ship) {
        logger.debug("Try to add ship");
        if (shipsOnBridge.size() < PortConstants.BRIDGE_THROUGHPUT) {
            logger.debug("Add new ship!");
            shipsOnBridge.add(ship);
            return true;
        }
        logger.debug("Fault");

        return false;
    }

    public synchronized int getAmountOfShips() {
        return shipsOnBridge.size();
    }

    public synchronized boolean isFull() {
        return shipsOnBridge.size() == 5;
    }

    public synchronized void removeShipFromBridge(Ship ship) {
        shipsOnBridge.remove(ship);
        logger.debug("Remove ship from bridge");
    }

    public static Bridge getBridge() {
        if (bridge == null) {
            bridge = new Bridge();
        }
        return bridge;
    }

    public Ship returnShipWithTypeOfCargo(TypeOfCargo cargo) {
        for (Ship ship : shipsOnBridge) {
            if (ship.getTypeOfCargo().equals(cargo)) {
                return ship;
            }
        }

        return null;
    }

    private Bridge() {}
}

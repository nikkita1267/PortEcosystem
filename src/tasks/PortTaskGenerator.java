package tasks;

import ch.qos.logback.classic.Logger;
import models.Bridge;
import models.Port;
import org.slf4j.LoggerFactory;
import models.Ship;

import java.util.concurrent.Callable;

public class PortTaskGenerator implements PortEcosystemTaskGenerator<Port, Ship, Boolean> {
    private final Logger logger = (Logger) LoggerFactory.getLogger("port-task-generator");

    public Callable<Boolean> generateTask(Port port, Ship ship) {
        return () -> {
            if (port.isAvailable()) {
                logger.debug("Task begin: remove ship from bridge");
                Bridge.getBridge().removeShipFromBridge(ship);
                port.acceptShip(ship);
                port.ship();
                return true;
            }

            return false;
        };
    }
}

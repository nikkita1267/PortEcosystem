package util;

import ch.qos.logback.classic.Logger;
import models.*;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

public class ShipGenerator extends Thread {
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    private final Logger logger = (Logger) LoggerFactory.getLogger("ship-generator");

    public Ship generateNewShip() {
        try {
            Thread.sleep(PortConstants.SHIP_GENERATING_SPEED);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TypeOfCargo typeOfCargo = TypeOfCargo.getCargoByIndex(random.nextInt(3));
        ShipCapacity capacity = ShipCapacity.getCapacityByIndex(random.nextInt(3));

        logger.debug("Generate new ship with cargo " + typeOfCargo + " and capacity of " + capacity);
        return new Ship(typeOfCargo, capacity);
    }
}

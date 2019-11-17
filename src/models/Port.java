package models;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import util.PortConstants;

import java.util.concurrent.atomic.AtomicInteger;

public class Port {
    private final TypeOfCargo acceptableTypeOfCargo;
    private final double shippingSpeed;
    private volatile Ship threatedShip;
    private volatile AtomicInteger amountOfCargo = new AtomicInteger(0);
    private final Logger logger;

    public Port(TypeOfCargo acceptableTypeOfCargo, double shippingSpeed, Ship threatedShip) {
        this.acceptableTypeOfCargo = acceptableTypeOfCargo;
        this.shippingSpeed = shippingSpeed;
        this.threatedShip = threatedShip;
        logger = (Logger) LoggerFactory.getLogger("port-with-" + acceptableTypeOfCargo);
    }

    public Port(TypeOfCargo acceptableTypeOfCargo, double shippingSpeed) {
        this(acceptableTypeOfCargo, shippingSpeed, null);
    }

    public boolean acceptShip(Ship ship) {
        logger.debug("Try to accept ship");
        if (!isAvailable()) {
            return false;
        }
        logger.debug("Accepted");
        threatedShip = ship;
        return true;
    }

    public boolean isAvailable() {
        return threatedShip == null;
    }

    public synchronized void ship() {
        try {
            logger.debug("Start shipping");
            amountOfCargo.addAndGet(threatedShip.getCapacity());
            Thread.sleep((long) (shippingSpeed * PortConstants.SHIPPING_SPEED_PER_ONE_CARGO * threatedShip.getCapacity()));
            // Free port for new ship
            logger.debug("End shipping");
            threatedShip = null;
        } catch (Exception e) {
            logger.error("Exception in ship " + e.getMessage());
        }
    }

    public AtomicInteger getAmountOfCargo() {
        return amountOfCargo;
    }

    public void decreaseAmountOfCargo() {
        logger.debug("Steel one cargo");
        amountOfCargo.decrementAndGet();
    }

    public TypeOfCargo getAcceptableTypeOfCargo() {
        return acceptableTypeOfCargo;
    }
}

package models;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import util.PortConstants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Barrel {
    private final Logger logger = (Logger) LoggerFactory.getLogger("barrel-logger");
    private final Map<TypeOfCargo, Integer> content = new ConcurrentHashMap<>();

    private static Barrel barrel = new Barrel();

    public static Barrel getBarrel() {
        return barrel;
    }

    private void addSomeCargo(TypeOfCargo cargo, int amount) {
        content.merge(cargo, amount, Integer::sum);
        logger.debug("Add " + amount + " elements of cargo " + cargo);
    }

    public synchronized void increaseAmountOfCargo(TypeOfCargo cargo) {
        addSomeCargo(cargo, 1);
    }

    public synchronized int getAmountOfCargo(TypeOfCargo cargo) {
        return content.get(cargo);
    }

    public synchronized boolean isAllIngridientsReady() {
        logger.debug("Bread: " + content.get(TypeOfCargo.BREAD) + " "
                   + "Sausages: " + content.get(TypeOfCargo.SAUSAGES) + " "
                   + "Mayonnaise: " + content.get(TypeOfCargo.MAYONNAISE));
        if (content.get(TypeOfCargo.BREAD) >= PortConstants.AMOUNT_OF_INGRIDIENTS &&
                content.get(TypeOfCargo.SAUSAGES) >= PortConstants.AMOUNT_OF_INGRIDIENTS &&
                content.get(TypeOfCargo.MAYONNAISE) >= PortConstants.AMOUNT_OF_INGRIDIENTS) {
            logger.debug("All ingridients are ready");
        } else {
            logger.debug("Not all ingridients are ready");
        }
        return (content.get(TypeOfCargo.BREAD) >= PortConstants.AMOUNT_OF_INGRIDIENTS &&
                content.get(TypeOfCargo.SAUSAGES) >= PortConstants.AMOUNT_OF_INGRIDIENTS &&
                content.get(TypeOfCargo.MAYONNAISE) >= PortConstants.AMOUNT_OF_INGRIDIENTS);
    }

    public void performOneEat() {
        logger.debug("One rogue eats");
        for (Map.Entry<TypeOfCargo, Integer> ingridients : content.entrySet()) {
            addSomeCargo(ingridients.getKey(), -1);
        }
    }

    private Barrel() {
        content.put(TypeOfCargo.BREAD, 0);
        content.put(TypeOfCargo.SAUSAGES, 0);
        content.put(TypeOfCargo.MAYONNAISE, 0);
    }
}

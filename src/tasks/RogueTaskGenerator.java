package tasks;

import ch.qos.logback.classic.Logger;
import models.*;
import org.slf4j.LoggerFactory;
import util.PortConstants;
import util.Ports;
import util.RogueManager;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class RogueTaskGenerator implements PortEcosystemTaskGenerator<Rogue, Port, Void> {
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    private final RogueManager rogueManager;
    private final Logger logger = (Logger) LoggerFactory.getLogger("rogue-task-generator");

    public RogueTaskGenerator(List<Rogue> allRogues) {
        this.rogueManager = new RogueManager(allRogues);
    }

    @Override
    public Callable<Void> generateTask(Rogue rogue, Port port) {
        return () -> {
            try {
                if (Barrel.getBarrel().isAllIngridientsReady()) {
                    logger.debug("All ingridients are ready. Task is change to eat");
                    Thread.sleep(PortConstants.ROGUE_EAT_TIME);
                    rogueManager.notifyAllAboutEating();
                    rogueManager.performEat();
                }
                if (!rogue.isEating() && !Barrel.getBarrel().isAllIngridientsReady()) {
                    logger.debug("Not all ingridients are ready. Start steeling task");
                    Thread.sleep(PortConstants.ROGUE_STEEL_SPEED * 10);
                    Port targetPort = Ports.getPortByTypeOfCargo(TypeOfCargo.getCargoByIndex(random.nextInt(3)));

                    if (targetPort.getAmountOfCargo().longValue() > 0) {
                        targetPort.decreaseAmountOfCargo();
                        Barrel.getBarrel().increaseAmountOfCargo(targetPort.getAcceptableTypeOfCargo());
                    } else {
                        return null;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        };
    }
}

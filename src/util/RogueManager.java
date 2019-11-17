package util;

import ch.qos.logback.classic.Logger;
import models.Barrel;
import models.Rogue;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class RogueManager {
    private CyclicBarrier barrierStartEating = new CyclicBarrier(8);
    private CyclicBarrier endEatingBarrier = new CyclicBarrier(8);
    private final List<Rogue> rogues;
    private final Barrel barrel = Barrel.getBarrel();
    private final Logger logger = (Logger) LoggerFactory.getLogger("rogue-manager");

    public RogueManager(List<Rogue> rogues) {
        this.rogues = rogues;
    }

    public synchronized void notifyAllAboutEating() {
        logger.debug("Notify all rogues about eating");
        for (Rogue rogue : rogues) {
            rogue.startEating();
        }
    }

    private void endEating() {
        logger.debug("Wait for all rogues to end eating");
        try {
            endEatingBarrier.await();
        } catch (Exception ignored) {}
        logger.debug("Notify rogues about ending of eating");
        for (Rogue rogue : rogues) {
            rogue.stopEating();
        }
    }

    public void performEat() {
        logger.debug("Wait for all rogues to start eating");
        try {
            barrierStartEating.await();
        } catch (Exception ignored) {}
        logger.debug("Start eating");
        synchronized (barrel) {
            barrel.performOneEat();
        }
        endEating();
    }
}

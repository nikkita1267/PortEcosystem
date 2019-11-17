import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import models.*;
import org.slf4j.LoggerFactory;
import tasks.PortEcosystemTaskGenerator;
import tasks.PortTaskGenerator;
import tasks.RogueTaskGenerator;
import util.PortConstants;
import util.Ports;
import util.ShipGenerator;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class Environment {
    private static boolean isEnvironmentAlive = true;
    private final Map<RogueRole, List<Rogue>> rogues = new HashMap<>();
    private final List<Rogue> allRogues;
    private final Logger logger = (Logger) LoggerFactory.getLogger("main-environment");

    public Environment() {
        addAllRogues();
        allRogues = getAllRogues();
    }

    public void startEnvironment() {
        logger.debug("Start environment");
        addAllRogues();
        final Bridge bridge = Bridge.getBridge();
        final ShipGenerator shipGenerator = new ShipGenerator();
        final ExecutorService portExecutor = Executors.newFixedThreadPool(PortConstants.AMOUNT_OF_PORTS);
        final ExecutorService rogueExecutor = Executors.newFixedThreadPool(PortConstants.AMOUNT_OF_ROGUES);
        final PortEcosystemTaskGenerator<Port, Ship, Boolean> portTaskGenerator = new PortTaskGenerator();
        final PortEcosystemTaskGenerator<Rogue, Port, Void> rogueTaskGenerator = new RogueTaskGenerator(allRogues);

        Thread shipGenerating = new Thread(() -> {
            while (isEnvironmentAlive) {
                bridge.tryAddShip(shipGenerator.generateNewShip());
            }
        }, "ship-generating-thread");
        shipGenerating.setDaemon(true);
        shipGenerating.start();

        while (isEnvironmentAlive) {
            List<Port> availablePorts = Ports.getAllAvailablePorts();

            for (Port port : availablePorts) {
                Ship ship = bridge.returnShipWithTypeOfCargo(port.getAcceptableTypeOfCargo());
                if (ship != null) {
                    portExecutor.submit(portTaskGenerator.generateTask(port, ship));
                }
            }

            randomlyChangeRolesOfRogues();

            for (int i = 0; i < PortConstants.AMOUNT_OF_ROGUES - PortConstants.AMOUNT_OF_COOKERS; ++i) {
                TypeOfCargo cargo = TypeOfCargo.getCargoByIndex(i % PortConstants.AMOUNT_OF_TYPES_OF_CARGO);
                rogueExecutor.submit(rogueTaskGenerator.generateTask(
                        rogues.get(RogueRole.STEALER).get(i), Ports.getPortByTypeOfCargo(cargo)
                ));
            }
        }
    }

    public void stopEnvironment() {
        isEnvironmentAlive = false;
    }

    public static void main(String[] args) {
        Environment env = new Environment();
        env.startEnvironment();
    }

    private void randomlyChangeRolesOfRogues() {
        int counterOfCookers = 0;
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        final List<Rogue> allRogues = getAllRogues();
        final List<Rogue> newCookers = new ArrayList<>();
        final List<Rogue> newStealers = new ArrayList<>();
        for (Rogue rogue : allRogues) {
            RogueRole newRole = RogueRole.STEALER;
            if (counterOfCookers < PortConstants.AMOUNT_OF_COOKERS) {
                int index = random.nextInt(2);
                newRole = index == 0 ? RogueRole.STEALER : RogueRole.COOK;
                if (newRole == RogueRole.COOK) {
                    counterOfCookers++;
                }
            }
            rogue.setRole(newRole);
            if (newRole == RogueRole.COOK) {
                newCookers.add(rogue);
            } else {
                newStealers.add(rogue);
            }
        }

        rogues.replace(RogueRole.STEALER, newStealers);
        rogues.replace(RogueRole.COOK, newCookers);
    }

    private void addAllRogues() {
        List<Rogue> cookers = new ArrayList<>();
        for (int i = 0; i < PortConstants.AMOUNT_OF_COOKERS; ++i) {
            cookers.add(new Rogue(RogueRole.COOK, null));
        }

        rogues.put(RogueRole.COOK, cookers);
        List<Rogue> stealers = new ArrayList<>();
        for (int i = 0; i < PortConstants.AMOUNT_OF_ROGUES - PortConstants.AMOUNT_OF_COOKERS; ++i) {
            stealers.add(new Rogue(RogueRole.COOK, null));
        }
        rogues.put(RogueRole.STEALER, stealers);
    }

    private List<Rogue> getAllRogues() {
        List<Rogue> allRogues = new ArrayList<>();
        for (List<Rogue> roguesByRole : rogues.values()) {
            allRogues.addAll(roguesByRole);
        }

        return allRogues;
    }
}

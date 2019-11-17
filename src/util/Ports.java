package util;

import models.Port;
import models.TypeOfCargo;

import java.util.*;
import java.util.stream.Collectors;

public class Ports {
    private Ports() {}

    private static final Map<TypeOfCargo, Port> AVAILABLE_PORTS = new HashMap<>();

    static {
        AVAILABLE_PORTS.put(TypeOfCargo.BREAD, new Port(TypeOfCargo.BREAD,
                PortConstants.SHIPPING_SPEED_PER_ONE_CARGO));
        AVAILABLE_PORTS.put(TypeOfCargo.MAYONNAISE, new Port(TypeOfCargo.MAYONNAISE,
                PortConstants.SHIPPING_SPEED_PER_ONE_CARGO));
        AVAILABLE_PORTS.put(TypeOfCargo.SAUSAGES, new Port(TypeOfCargo.SAUSAGES,
                PortConstants.SHIPPING_SPEED_PER_ONE_CARGO));
    }

    public static Port getPortByTypeOfCargo(TypeOfCargo cargo) {
        return AVAILABLE_PORTS.get(cargo);
    }

    /**
     * @return {null} if there aren't any free port
     */
    public static Port getAvailablePort() {
        for (Port port : AVAILABLE_PORTS.values()) {
            if (port.isAvailable()) {
                return port;
            }
        }

        // Not reachable
        return null;
    }

    public static List<Port> getAllAvailablePorts() {
        List<Port> result = new ArrayList<>();
        for (Port port : AVAILABLE_PORTS.values()) {
            if (port.isAvailable()) {
                result.add(port);
            }
        }

        return result;
    }

    public static List<Port> getAllPorts() {
        return AVAILABLE_PORTS.values().parallelStream().collect(Collectors.toList());
    }
}

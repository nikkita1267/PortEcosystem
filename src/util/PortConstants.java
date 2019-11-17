package util;


public final class PortConstants {
    public static final int ROGUE_EAT_TIME = 100;
    public static final int BRIDGE_THROUGHPUT = 5;
    public static final int SHIPPING_SPEED_PER_ONE_CARGO = 5;
    public static final int ROGUE_STEEL_SPEED = 3;
    public static final int AMOUNT_OF_INGRIDIENTS = 8;
    public static final int AMOUNT_OF_PORTS = 3;
    public static final int AMOUNT_OF_ROGUES = 8;
    public static final int AMOUNT_OF_COOKERS = 2;
    public static final int SHIP_GENERATING_SPEED = 10;
    public static final int AMOUNT_OF_TYPES_OF_CARGO = 3;

    private PortConstants() {
        throw new ExceptionInInitializerError("You can't instansiate this class!");
    }
}

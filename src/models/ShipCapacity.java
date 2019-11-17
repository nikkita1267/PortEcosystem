package models;

public enum ShipCapacity {
    SMALL_CAPACITY(10),
    MIDDLE_CAPACITY(20),
    LARGE_CAPACITY(30);

    private int capacity;
    ShipCapacity(int capacity) {
        this.capacity = capacity;
    }

    public static ShipCapacity getCapacityByIndex(int index) {
        switch (index) {
            case 0:
                return SMALL_CAPACITY;
            case 1:
                return MIDDLE_CAPACITY;
            default:
                return LARGE_CAPACITY;
        }
    }

    public int getCapacity() {
        return capacity;
    }
}

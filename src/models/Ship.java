package models;

public class Ship {
    private TypeOfCargo typeOfCargo;
    private ShipCapacity capacity;

    public Ship(TypeOfCargo typeOfCargo, ShipCapacity capacity) {
        this.typeOfCargo = typeOfCargo;
        this.capacity = capacity;
    }

    public TypeOfCargo getTypeOfCargo() {
        return typeOfCargo;
    }

    public void setTypeOfCargo(TypeOfCargo typeOfCargo) {
        this.typeOfCargo = typeOfCargo;
    }

    public int getCapacity() {
        return capacity.getCapacity();
    }

    public void setCapacity(ShipCapacity capacity) {
        this.capacity = capacity;
    }
}

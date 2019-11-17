package models;

public enum TypeOfCargo {
    BREAD,
    MAYONNAISE,
    SAUSAGES;

    public static TypeOfCargo getCargoByIndex(int index) {
        switch (index) {
            case 0:
                return BREAD;
            case 1:
                return MAYONNAISE;
            default:
                return SAUSAGES;
        }
    }
}

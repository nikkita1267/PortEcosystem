package models;

public class Rogue {
    private RogueRole role;
    private TypeOfCargo currentlyPortableType;
    private volatile boolean isEating = false;

    public Rogue(RogueRole role, TypeOfCargo currentlyPortableType) {
        this.role = role;
        this.currentlyPortableType = currentlyPortableType;
    }

    public RogueRole getRole() {
        return role;
    }

    public void setRole(RogueRole role) {
        this.role = role;
    }

    public TypeOfCargo getCurrentlyPortableType() {
        return currentlyPortableType;
    }

    public void setCurrentlyPortableType(TypeOfCargo currentlyPortableType) {
        this.currentlyPortableType = currentlyPortableType;
    }

    public void startEating() {
        isEating = true;
    }

    public void stopEating() {
        isEating = false;
    }

    public boolean isEating() {
        return isEating;
    }
}

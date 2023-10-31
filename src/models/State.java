package.models;

public class State {
    private int prosperityLevel;
    private int food;
    private int materials;
    private int energy;
    private double moneySpent;
    private String previousAction;

    public State(int prosperityLevel, int food, int materials, int energy, double moneySpent, String previousAction) {
        this.prosperityLevel = prosperityLevel;
        this.food = food;
        this.materials = materials;
        this.energy = energy;
        this.moneySpent = moneySpent;
        this.previousAction = previousAction;
    }

    public int getProsperityLevel() {
        return prosperityLevel;
    }

    public int getFood() {
        return food;
    }

    public int getMaterials() {
        return materials;
    }

    public int getEnergy() {
        return energy;
    }

    public double getMoneySpent() {
        return moneySpent;
    }

    public String getPreviousAction() {
        return previousAction;
    }

    public State getState(){
        return this;
    }

}

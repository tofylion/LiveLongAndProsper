package models;

public class State {
    private int prosperityLevel;
    private int food;
    private int materials;
    private int energy;
    private double moneySpent;

    public State(int prosperityLevel, int food, int materials, int energy, double moneySpent) {
        this.prosperityLevel = prosperityLevel;
        this.food = food;
        this.materials = materials;
        this.energy = energy;
        this.moneySpent = moneySpent;
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

    public State getState(){
        return this;
    }

}

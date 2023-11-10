package code.models;

import code.constants.Constants;
import code.enums.Actions;

public class State {
    private int prosperityLevel;
    private int food;
    private int materials;
    private int energy;
    private double moneySpent;
    private int deliveryPending;
    private Actions deliveryType;

    public State(int prosperityLevel, int food, int materials, int energy, double moneySpent, int deliveryPending,
            Actions deliveryType) {
        this.prosperityLevel = prosperityLevel;
        this.food = food;
        this.materials = materials;
        this.energy = energy;
        this.moneySpent = moneySpent;
        this.deliveryPending = deliveryPending;
        this.deliveryType = deliveryType;
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

    public State getState() {
        return this;
    }

    public int getDeliveryPending() {
        return deliveryPending;
    }

    public Actions getDeliveryType() {
        return deliveryType;
    }

    public boolean deliveryArrived() {
        return !isDeliveryPending() && deliveryType != null;
    }

    public boolean isDeliveryPending() {
        return this.deliveryPending > 0;
    }

    public boolean stateBlocked() {
        return food <= 0 || energy <= 0 || materials <= 0 || moneySpent >= Constants.budget;
    }

    public State useResources(int foodPrice, int materialsPrice, int energyPrice) {
        return new State(prosperityLevel, food - 1, materials - 1, energy - 1, moneySpent + foodPrice + energyPrice + materialsPrice, deliveryPending - 1,
                deliveryType);
    }

    public State useResources(int cost, int food, int materials, int energy, int foodPrice, int materialsPrice,
            int energyPrice, int prosperityIncrease) {
        return new State(Math.min(prosperityLevel + prosperityIncrease, Constants.prosperityGoal), this.food - food, this.materials - materials, this.energy - energy,
                moneySpent + cost + (food*foodPrice) + (materials*materialsPrice) + (energy*energyPrice), deliveryPending - 1, deliveryType);
    }

    public State addResources(int food, int materials, int energy) {
        return new State(prosperityLevel,
                Math.min(this.food + food, Constants.resourceLimit),
                Math.min(this.materials + materials, Constants.resourceLimit),
                Math.min(this.energy + energy, Constants.resourceLimit),
                moneySpent,
                deliveryPending,
                null);
    }

    public State requestResource(int deliveryTime, Actions deliveryType, int foodPrice, int materialsPrice, int energyPrice) {
        State newState = new State(prosperityLevel, food, materials, energy, moneySpent, deliveryTime, deliveryType);
        return newState.useResources(foodPrice, materialsPrice, energyPrice);
    }

    public State copy() {
        return new State(prosperityLevel, food, materials, energy, moneySpent, deliveryPending, deliveryType);
    }

    public String toString() {
        return String.format("State{\n  prosperity=%d,\n  food=%d,\n  materials=%d,\n  energy=%d,\n  money_spent=%.2f\n}", prosperityLevel,
                food, materials, energy, moneySpent);
    }

}

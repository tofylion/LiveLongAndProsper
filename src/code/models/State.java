package code.models;

import code.constants.Constants;
import code.enums.Actions;

public class State {
    private int prosperityLevel;
    private int food;
    private int materials;
    private int energy;
    private int moneySpent;
    private int deliveryPending;
    private Actions deliveryType;

    public State(int prosperityLevel, int food, int materials, int energy, int moneySpent, int deliveryPending,
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

    public int getMoneySpent() {
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
        return new State(prosperityLevel, food - 1, materials - 1, energy - 1,
                moneySpent + foodPrice + energyPrice + materialsPrice, Math.max(deliveryPending - 1, 0),
                deliveryType);
    }

    public State useResources(int cost, int food, int materials, int energy, int foodPrice, int materialsPrice,
            int energyPrice, int prosperityIncrease) {
        return new State(prosperityLevel + prosperityIncrease, this.food - food,
                this.materials - materials, this.energy - energy,
                moneySpent + cost + (food * foodPrice) + (materials * materialsPrice) + (energy * energyPrice),
                Math.max(deliveryPending - 1, 0), deliveryType);
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

    public State requestResource(int deliveryTime, Actions deliveryType, int foodPrice, int materialsPrice,
            int energyPrice) {
        State newState = new State(prosperityLevel, food, materials, energy, moneySpent, deliveryTime, deliveryType);
        return newState.useResources(foodPrice, materialsPrice, energyPrice);
    }

    public State copy() {
        return new State(prosperityLevel, food, materials, energy, moneySpent, deliveryPending, deliveryType);
    }

    public String toString() {
        return "State{\nprosperity=" + prosperityLevel + ",food=" + food + ",materials=" + materials + ",energy=" + energy + ",money_spent=" + moneySpent + "\n}";
    }

    public String toFullString() {
        return new StringBuilder()
        .append(prosperityLevel)
        .append(food)
        .append(materials)
        .append(energy)
        .append(moneySpent)
        .append(deliveryType)
        .append(deliveryPending)
        .toString();
    }

    public boolean hasFullFood() {
        return food >= Constants.resourceLimit;
    }
    public boolean hasFullMaterials() {
        return materials >= Constants.resourceLimit;
    }
    public boolean hasFullEnergy() {
        return energy >= Constants.resourceLimit;
    }


}

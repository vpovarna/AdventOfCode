package io.aoc._2022.day11;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

public class Observation {
    private final int currentMonkeyNumber;
    private Deque<Integer> items;
    private final Operation operation;
    private final int divisionNumber;
    private final int trueMonkeyNumber;
    private final int falseMonkeyNumber;
    private int numberOfInspectedItems = 0;

    public Observation(int currentMonkeyNumber, Deque<Integer> items, Operation operation, int divisionNumber, int trueMonkeyNumber, int falseMonkeyNumber) {
        this.currentMonkeyNumber = currentMonkeyNumber;
        this.items = items;
        this.operation = operation;
        this.divisionNumber = divisionNumber;
        this.trueMonkeyNumber = trueMonkeyNumber;
        this.falseMonkeyNumber = falseMonkeyNumber;
    }

    public int getCurrentMonkeyNumber() {
        return currentMonkeyNumber;
    }

    public Queue<Integer> getItems() {
        return items;
    }

    public Operation getOperation() {
        return operation;
    }

    public int getDivisionNumber() {
        return divisionNumber;
    }

    public int getTrueMonkeyNumber() {
        return trueMonkeyNumber;
    }

    public int getFalseMonkeyNumber() {
        return falseMonkeyNumber;
    }

    public int getNumberOfInspectedItems() {
        return numberOfInspectedItems;
    }

    public void increaseNumberOfInspectedItem() {
        numberOfInspectedItems +=1;
    }
    public void updateItems(int itemNumber) {
        items.add(itemNumber);
    }

    public void removeItem() {
        items.removeFirst();
    }

    @Override
    public String toString() {
        return "Observation{" +
                "currentMonkeyNumber=" + currentMonkeyNumber +
                ", items=" + items +
                ", divisionNumber=" + divisionNumber +
                ", trueMonkeyNumber=" + trueMonkeyNumber +
                ", falseMonkeyNumber=" + falseMonkeyNumber +
                ", operation=" + operation +
                '}';
    }
}


package io.aoc._2022.day11;

import java.util.Deque;
import java.util.Queue;

public class Observation {
    private final int currentMonkeyNumber;
    private final Deque<Long> items;
    private final Operation operation;
    private final long divisionNumber;
    private final int trueMonkeyNumber;
    private final int falseMonkeyNumber;
    private long numberOfInspectedItems = 0;

    public Observation(int currentMonkeyNumber, Deque<Long> items, Operation operation, int divisionNumber, int trueMonkeyNumber, int falseMonkeyNumber) {
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

    public Queue<Long> getItems() {
        return items;
    }

    public Operation getOperation() {
        return operation;
    }

    public long getDivisionNumber() {
        return divisionNumber;
    }

    public int getTrueMonkeyNumber() {
        return trueMonkeyNumber;
    }

    public int getFalseMonkeyNumber() {
        return falseMonkeyNumber;
    }

    public long getNumberOfInspectedItems() {
        return numberOfInspectedItems;
    }

    public void increaseNumberOfInspectedItem() {
        numberOfInspectedItems += 1;
    }

    public void updateItems(long itemNumber) {
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


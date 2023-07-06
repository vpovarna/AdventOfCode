package io.aoc._2022.day05;

class Instruction {
    private final int numberOfItems;
    private final int sourceStack;
    private final int destinationStack;

    public Instruction(int numberOfItems, int sourceStack, int destinationStack) {
        this.numberOfItems = numberOfItems;
        this.sourceStack = sourceStack;
        this.destinationStack = destinationStack;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public int getSourceStack() {
        return sourceStack;
    }

    public int getDestinationStack() {
        return destinationStack;
    }

    @Override
    public String toString() {
        return "Instruction[" +
                "numberOfItems=" + numberOfItems +
                ", sourceStack=" + sourceStack +
                ", destinationStack=" + destinationStack +
                ']';
    }
}

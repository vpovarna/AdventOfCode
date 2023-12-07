package io.aoc._2023.day07;

public enum Type {
    FIVE_OF_A_KIND(5),
    FOUR_OF_A_KIND(4),
    FULL_HOUSE(3.5),
    THREE_OF_A_KIND(3),
    TWO_PAIR(2.5),
    ONE_PAIR(2),
    HIGH_CARD(1);
    public final double value;

    Type(double value) {
        this.value = value;
    }
}

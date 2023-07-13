package io.aoc._2022.day11;

public class Operation {
    private final char operator;
    private final String worryLevelCoefficient;
    public Operation(char operator, String worryLevelCoefficient) {
        this.operator = operator;
        this.worryLevelCoefficient = worryLevelCoefficient.trim();
    }

    public long getNewWorryLevel(long oldWorryLevel) {
        long oldWorryLevelValue = ("old".equals(worryLevelCoefficient)) ? oldWorryLevel : Integer.parseInt(worryLevelCoefficient);

        return switch (operator) {
            case '+' -> oldWorryLevel + oldWorryLevelValue;
            case '*' -> oldWorryLevel * oldWorryLevelValue;
            default -> throw new IllegalStateException("Unexpected value: " + operator);
        };
    }
}

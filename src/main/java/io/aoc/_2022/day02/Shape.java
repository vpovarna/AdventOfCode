package io.aoc._2022.day02;

public enum Shape {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    private final int score;

    Shape(int score) {
        this.score = score;
    }

    static Shape of(char symbol) {
        Shape shape;
        switch (symbol) {
            case 'A':
            case 'X':
                shape = ROCK;
                break;
            case 'B':
            case 'Y':
                shape = PAPER;
                break;
            case 'C':
            case 'Z':
                shape = SCISSORS;
                break;
            default:
                throw new IllegalArgumentException("Unknown symbol");
        }
        return shape;
    }

    public int getScore() {
        return score;
    }
}

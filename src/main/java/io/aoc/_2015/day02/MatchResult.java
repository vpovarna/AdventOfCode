package io.aoc._2015.day02;

enum MatchResult {
    LOOSE(0),
    DRAW(3),
    WIN(6);

    private final int score;

    MatchResult(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    static MatchResult of(char symbol) {
        MatchResult matchResult;
        switch (symbol) {
            case 'X':
                matchResult = LOOSE;
                break;
            case 'Y':
                matchResult = DRAW;
                break;
            case 'Z':
                matchResult = WIN;
                break;
            default:
                throw new IllegalArgumentException("Unknown symbol");
        }
        return matchResult;
    }
}

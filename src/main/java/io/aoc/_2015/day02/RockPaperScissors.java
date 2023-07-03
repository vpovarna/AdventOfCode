package io.aoc._2015.day02;

public class RockPaperScissors {

    public MatchResult playRound(Shape myShape, Shape oponentShape) {
        if (myShape == oponentShape) {
            return MatchResult.DRAW;
        } else if (isWin(myShape, oponentShape)) {
            return MatchResult.WIN;
        } else {
            return MatchResult.LOOSE;
        }
    }

    private boolean isWin(Shape myShape, Shape oponentShape) {
        return myShape == Shape.ROCK && oponentShape == Shape.SCISSORS ||
                myShape == Shape.SCISSORS && oponentShape == Shape.PAPER ||
                myShape == Shape.PAPER && oponentShape == Shape.ROCK;
    }
}

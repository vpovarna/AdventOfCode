package io.aoc._2015.day02;

import io.aoc._2015.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);
    private final RockPaperScissors rockPaperScissors = new RockPaperScissors();

    public int part1(String input) {
        return input.lines()
                .filter(line -> line.length() == 3)
                .mapToInt(this::playPart1Game)
                .sum();
    }

    public int part2(String input) {
        return input.lines()
                .filter(line -> line.length() == 3)
                .mapToInt(this:: playPart2Game)
                .sum();
    }

    private int playPart1Game(String line) {
        final Shape openentShape = Shape.of(line.charAt(0));
        final Shape myShape = Shape.of(line.charAt(2));
        final MatchResult matchResult = rockPaperScissors.playRound(myShape, openentShape);
        return matchResult.getScore() + myShape.getScore();
    }

    private int playPart2Game(String line) {
        Shape opponentShape = Shape.of(line.charAt(0));
        MatchResult requiredMatchResult = MatchResult.of(line.charAt(2));

        for (Shape myShape : Shape.values()) {
            MatchResult matchResult = rockPaperScissors.playRound(myShape, opponentShape);
            if (matchResult == requiredMatchResult) {
                int shapeScore = myShape.getScore();
                int requiredMatchResultScore = matchResult.getScore();
                return shapeScore + requiredMatchResultScore;
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        final Problem problem = new Problem();
        final String inputFile = Utils.readInputFileAsString(2, "input.txt");
        logger.info("Aoc2022, Day2 Problem, Part1: {}", problem.part1(inputFile));
        logger.info("Aoc2022, Day2 Problem, Part1: {}", problem.part2(inputFile));
    }
}

package io.aoc._2020.day12;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class Problem {
    public static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 12, "input.txt");
        logger.info("Aoc2020, Day10 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day10 Problem, Part2: {}", problem.part2(input));
    }

    private int part1(String input) {
        List<Instruction> instructions = parseInput(input);
        var position = new Position(0, 0, 'E');
        instructions.forEach(position::move);
        return position.manhattanDistance();
    }

    private int part2(String input) {
        return -1;
    }

    private List<Instruction> parseInput(String input) {
        var lines = input.split(Constants.EOL);
        return Arrays.stream(lines)
                .map(String::trim)
                .map(line -> new Instruction(line.charAt(0), Integer.parseInt(line.substring(1))))
                .toList();
    }

}

record Instruction(char orientation, int steps) {
}

class Position {
    private final List<Character> cardinalDirections = List.of('E', 'S', 'W', 'N');
    private int x;
    private int y;
    private char currentOrientation;

    public Position(int x, int y, char currentOrientation) {
        this.x = x;
        this.y = y;
        this.currentOrientation = currentOrientation;
    }

    public void move(Instruction instruction) {
        switch (instruction.orientation()) {
            case 'F' -> {
                switch (currentOrientation) {
                    case 'N' -> y += instruction.steps();
                    case 'S' -> y -= instruction.steps();
                    case 'E' -> x += instruction.steps();
                    case 'W' -> x -= instruction.steps();
                }
            }
            case 'N' -> y += instruction.steps();
            case 'S' -> y -= instruction.steps();
            case 'E' -> x += instruction.steps();
            case 'W' -> x -= instruction.steps();
            case 'R' -> {
                var index = (cardinalDirections.indexOf(currentOrientation) + (instruction.steps() / 90)) % cardinalDirections.size();
                currentOrientation = cardinalDirections.get(index);
            }
            case 'L' -> {
                var tmpIndex = cardinalDirections.indexOf(currentOrientation) - (instruction.steps() / 90);
                var index = (tmpIndex < 0) ? cardinalDirections.size() + tmpIndex : tmpIndex;
                currentOrientation = cardinalDirections.get(index);
            }
            default -> throw new IllegalArgumentException("Invalid instruction");
        }

    }

    public int manhattanDistance() {
        return Math.abs(x) + Math.abs(y);
    }

    @Override
    public String toString() {
        return "Position[" +
                "x=" + x +
                ", y=" + y +
                ", currentOrientation=" + currentOrientation +
                ']';
    }
}

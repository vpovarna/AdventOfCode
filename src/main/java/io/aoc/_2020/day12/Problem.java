package io.aoc._2020.day12;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class Problem {
    public static final Logger logger = LoggerFactory.getLogger(Problem.class);

    private static final int NORTH = 0;
    private static final int EAST = 90;
    private static final int SOUTH = 180;
    private static final int WEST = 270;


    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 12, "input.txt");
        logger.info("Aoc2020, Day12 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day12 Problem, Part2: {}", problem.part2(input));
    }

    private int part1(String input) {
        var instructions = parseInput(input);
        var shipPosition = new Position(new Coordinate(0, 0), 90);

        for (var instruction : instructions) {
            var value = instruction.steps();
            var currentCoordinate = shipPosition.coordinate;
            switch (instruction.orientation()) {
                case 'N' -> currentCoordinate.y -= value;
                case 'S' -> currentCoordinate.y += value;
                case 'E' -> currentCoordinate.x += value;
                case 'W' -> currentCoordinate.x -= value;
                case 'F' -> {
                    switch (shipPosition.direction) {
                        case NORTH -> currentCoordinate.y -= value;
                        case SOUTH -> currentCoordinate.y += value;
                        case EAST -> currentCoordinate.x += value;
                        case WEST -> currentCoordinate.x -= value;
                    }
                }
                case 'R' -> shipPosition.direction = changeDirectionPart1(value, shipPosition.direction);
                case 'L' -> shipPosition.direction = changeDirectionPart1(-value, shipPosition.direction);
            }
        }

        return Math.abs(shipPosition.coordinate.x) + Math.abs(shipPosition.coordinate.y);

    }

    private int changeDirectionPart1(int value, int direction) {
        direction += value;
        if (direction < 0) {
            direction += 360;
        }
        if (direction >= 360) {
            direction -= 360;
        }
        return direction;
    }

    private int part2(String input) {
        var instructions = parseInput(input);

        var shipCoordinate = new Coordinate(0, 0);
        var viewPointCoordinate = new Coordinate(10, -1);

        for (var instruction : instructions) {
            var value = instruction.steps();
            switch (instruction.orientation()) {
                case 'N' -> viewPointCoordinate.y -= value;
                case 'S' -> viewPointCoordinate.y += value;
                case 'E' -> viewPointCoordinate.x += value;
                case 'W' -> viewPointCoordinate.x -= value;
                case 'F' -> {
                    shipCoordinate.x += viewPointCoordinate.x * value;
                    shipCoordinate.y += viewPointCoordinate.y * value;
                }
                case 'L' -> viewPointCoordinate = changeDirectionPart2(-value, viewPointCoordinate);
                case 'R' -> viewPointCoordinate = changeDirectionPart2(value, viewPointCoordinate);
            }
        }

        return Math.abs(shipCoordinate.x) + Math.abs(shipCoordinate.y);
    }

    private static Coordinate changeDirectionPart2(int value, Coordinate viewPointCoordinate) {
        if (value < 0) {
            value += 360;
        }

        double radians = Math.toRadians(value);

        float newx = (float) (viewPointCoordinate.x * Math.cos(radians) - viewPointCoordinate.y * Math.sin(radians));
        float newy = (float) (viewPointCoordinate.x * Math.sin(radians) + viewPointCoordinate.y * Math.cos(radians));

        return new Coordinate((int) newx, (int) newy);
    }

    private List<Instruction> parseInput(String input) {
        var lines = input.split(Constants.EOL);
        return Arrays.stream(lines)
                .map(String::trim)
                .map(line -> new Instruction(line.charAt(0), Integer.parseInt(line.substring(1))))
                .toList();
    }

}

class Coordinate {
    int x;
    int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

record Instruction(char orientation, int steps) {
}

class Position {
    Coordinate coordinate;
    int direction;

    public Position(Coordinate coordinate, int direction) {
        this.coordinate = coordinate;
        this.direction = direction;
    }
}
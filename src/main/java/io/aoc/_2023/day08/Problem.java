package io.aoc._2023.day08;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Pattern;

public class Problem {

    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var inputFile = Utils.readInputFileAsString(2023, 8);
        var problem = new Problem();

        logger.info("Aoc2023, Day8 Problem, Part1: {}", problem.part1(inputFile));
        logger.info("Aoc2023, Day8 Problem, Part2: {}", problem.part2(inputFile));
    }

    private int part1(String input) {
        var labelMaps = parseInput(input);
        var instructions = labelMaps.instructions();
        var directions = labelMaps.directions();

        return getIndex("AAA", directions, instructions, instructions.size());
    }

    private long part2(String input) {
        LabelMaps labelMaps = parseInput(input);
        String[] startingNodes = extractStartingNodes(labelMaps.directions().keySet());

        var instructions = labelMaps.instructions();
        var directions = labelMaps.directions();

        return Arrays.stream(startingNodes)
                .mapToLong(direction -> getIndex(direction, directions, instructions, instructions.size()))
                .reduce(1L, this::lcm);
    }

    private static int getIndex(String currentDirection, Map<String, Direction> directions, List<String> instructions, int instructionsSize) {
        var index = 0;

        while (!Pattern.matches("..Z", currentDirection)) {
            final Direction currentDirections = directions.get(currentDirection);
            final String currentInstruction = instructions.get(index % instructionsSize);

            if (currentInstruction.equals("L")) {
                currentDirection = currentDirections.left();
            } else {
                currentDirection = currentDirections.right();
            }
            index += 1;
        }
        return index;
    }

    private long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

    long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    private LabelMaps parseInput(String input) {
        String[] lines = input.split(Constants.EMPTY_LINE);
        var instructions = Arrays.asList(lines[0].split(""));
        var directions = new HashMap<String, Direction>();

        // Replace this with regular expressions.
        for (var line : lines[1].split(Constants.EOL)) {
            String[] parts = line.trim().split(" = ");
            String from = parts[0];

            String possibleDirections = parts[1];
            possibleDirections = possibleDirections.replace("(", "");
            possibleDirections = possibleDirections.replace(")", "");
            String[] directionParts = possibleDirections.split(", ");
            directions.put(from, new Direction(directionParts[0], directionParts[1]));
        }

        return new LabelMaps(instructions, directions);
    }

    private String[] extractStartingNodes(Set<String> allDirections) {
        return allDirections.stream()
                .filter(s -> s.endsWith("A"))
                .toArray(String[]::new);
    }
}

record LabelMaps(List<String> instructions, Map<String, Direction> directions) {
}

record Direction(String left, String right) {
}

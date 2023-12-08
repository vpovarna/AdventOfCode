package io.aoc._2023.day08;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

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

        var instructionsSize = instructions.length;

        var direction = "AAA";
        var index = 0;

        while (!direction.equals("ZZZ")) {
            final Direction currentDirections = directions.get(direction);
            final String currentInstruction = instructions[index % instructionsSize];

            if (currentInstruction.equals("L")) {
                direction = currentDirections.left();
            } else {
                direction = currentDirections.right();
            }
            index += 1;
        }
        return index;
    }

    private int part2(String input) {
        return 1;
    }

    private LabelMaps parseInput(String input) {
        String[] lines = input.split(Constants.EMPTY_LINE);

        var instructions = lines[0].split("");

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


}

record LabelMaps(String[] instructions, Map<String, Direction> directions) {
}

record Direction(String left, String right) {
}

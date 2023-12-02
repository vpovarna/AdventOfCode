package io.aoc._2023.day02;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);
    private final Map<String, Integer> cubes = Map.of(
            "red", 12,
            "green", 13,
            "blue", 14);

    public static void main(String[] args) {
        var inputFile = Utils.readInputFileAsString(2023, 2);
        var problem = new Problem();

        logger.info("Aoc2023, Day2 Problem, Part1: {}", problem.part1(inputFile));
        logger.info("Aoc2023, Day2 Problem, Part2: {}", problem.part2(inputFile));
    }

    private int part1(String input) {
        return Arrays.stream(input.split(Constants.EOL))
                .map(line -> new Game(getGameNumber(line), isAllMatch(line)))
                .filter(Game::allMatch)
                .mapToInt(Game::gameNumber)
                .sum();

    }

    private int part2(String input) {
        return Arrays.stream(input.split(Constants.EOL))
                .map(this::getCubes)
                .map(this::cubesSetPower)
                .mapToInt(x -> x)
                .sum();
    }

    private int cubesSetPower(List<Cube> cubes) {
        return cubes.stream()
                .map(Cube::nrOfBoxes)
                .reduce((a, b) -> a * b)
                .orElse(1);
    }

    private boolean isAllMatch(String line) {
        return cubes.keySet()
                .stream()
                .allMatch(cubeColor -> isGameValid(line, cubeColor));
    }

    private List<Cube> getCubes(String line) {
        return cubes.keySet()
                .stream()
                .map(cubeColor -> getCube(line, cubeColor))
                .toList();
    }

    private Cube getCube(String line, String color) {
        var pattern = Pattern.compile(String.format("(\\d+) %s", color));
        var matchers = pattern.matcher(line);
        var nrOfBoxes = Integer.MIN_VALUE;

        while (matchers.find()) {
            var nrOfCubes = matchers.group().split(Constants.EMPTY_STRING)[0];
            nrOfBoxes = Math.max(nrOfBoxes, Integer.parseInt(nrOfCubes));
        }
        return new Cube(color, nrOfBoxes);
    }

    private boolean isGameValid(String line, String color) {
        var pattern = Pattern.compile(String.format("(\\d+) %s", color));
        var matchers = pattern.matcher(line);

        while (matchers.find()) {
            var nrOfCubes = matchers.group().split(Constants.EMPTY_STRING)[0];
            if (Integer.parseInt(nrOfCubes) > cubes.get(color)) {
                return false;
            }
        }
        return true;
    }

    private int getGameNumber(String line) {
        var pattern = Pattern.compile("Game \\d+");
        var matcher = pattern.matcher(line);
        int gameNr = -1;
        while (matcher.find()) {
            var match = matcher.group();
            gameNr = Integer.parseInt(match.split(Constants.EMPTY_STRING)[1]);
        }
        return gameNr;
    }

}

record Game(int gameNumber, boolean allMatch) {
}

record Cube(String color, int nrOfBoxes) {
}

package io.aoc._2022.day11;

import io.aoc._2022.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);
    public static final String SPACE = " ";
    public static final String COMMA = ",";
    public static final String DOTS = ":";
    public static final String LINE_BREAK = "\n";

    public int part1(String input) {
        final var observations = parseInput(input);
        for (int i = 0; i < 20; i++) {
            calculateObservations(observations);
        }

        final var numberOfSortedItems = observations.stream()
                .map(Observation::getNumberOfInspectedItems)
                .sorted(Comparator.reverseOrder())
                .toList();

        return (numberOfSortedItems.get(0) * numberOfSortedItems.get(1));
    }

    private void calculateObservations(List<Observation> observations) {
        for (Observation observation : observations) {
            var items = observation.getItems();
            for (int item : items) {
                var newWorryLevel = observation.getOperation().getNewWorryLevel(item);
                var monkeyBaredWorryLevel = newWorryLevel / 3;
                if (monkeyBaredWorryLevel % observation.getDivisionNumber() == 0) {
                    updateObservation(observations, observation.getCurrentMonkeyNumber(), observation.getTrueMonkeyNumber(), monkeyBaredWorryLevel);
                } else {
                    updateObservation(observations, observation.getCurrentMonkeyNumber(), observation.getFalseMonkeyNumber(), monkeyBaredWorryLevel);
                }
                observation.increaseNumberOfInspectedItem();
            }
        }
    }

    private void updateObservation(List<Observation> observations, int currentMonkey, int targetMonkeyNumber, int monkeyBaredWorryLevel) {
        observations.get(currentMonkey).removeItem();
        observations.get(targetMonkeyNumber).updateItems(monkeyBaredWorryLevel);
    }

    private List<Observation> parseInput(String input) {
        return Arrays.stream(input.split("\r\n\r\n"))
                .map(this::generateObservation)
                .toList();
    }

    private Observation generateObservation(String observation) {
        final String[] observationNodes = observation.split(LINE_BREAK);
        var monkeyNumber = Integer.parseInt(observationNodes[0].split(SPACE)[1].split(DOTS)[0]);

        var parts = observationNodes[1].split(DOTS)[1].split(COMMA);
        var items = Arrays.stream(parts)
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();

        String[] tokens = observationNodes[2].split(SPACE);
        var worryOperation = tokens[tokens.length - 2];
        var worryLevelCoefficient = tokens[tokens.length - 1];

        var divisionNumber = getLastElementAsInt(observationNodes[3]);
        var trueMonkeyNumber = getLastElementAsInt(observationNodes[4]);
        var falseMonkeyNumber = getLastElementAsInt(observationNodes[5]);

        return new Observation(monkeyNumber,
                new ArrayDeque<>(items),
                new Operation(worryOperation.charAt(0), worryLevelCoefficient),
                divisionNumber,
                trueMonkeyNumber,
                falseMonkeyNumber);
    }

    private static int getLastElementAsInt(String observationNodes) {
        final String[] tokens = observationNodes.split(SPACE);
        return Integer.parseInt(tokens[tokens.length - 1].trim());
    }

    public static void main(String[] args) {
        var input = Utils.readInputFileAsString(11, "input.txt");
        var problem = new Problem();
        logger.info("Aoc2022, Day11 Problem, Part1: {}", problem.part1(input));
//        logger.info("Aoc2022, Day10 Problem, Part2:\n{}", problem.part2(input));
    }
}

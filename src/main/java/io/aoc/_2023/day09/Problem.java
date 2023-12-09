package io.aoc._2023.day09;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var inputFile = Utils.readInputFileAsString(2023, 9);

        var problem = new Problem();
        logger.info("Aoc2023, Day8 Problem, Part1: {}", problem.part1(inputFile));
        logger.info("Aoc2023, Day8 Problem, Part2: {}", problem.part2(inputFile));
    }

    private int part1(String inputFile) {
        return Arrays.stream(inputFile.split(Constants.EOL))
                .mapToInt(line -> extrapolate(parseLine(line)))
                .sum();
    }

    private int part2(String inputFile) {
        return Arrays.stream(inputFile.split(Constants.EOL))
                .mapToInt(line -> extrapolate(parseLine(line).reversed()))
                .sum();
    }

    private int extrapolate(List<Integer> numbers) {
        List<List<Integer>> layers = new ArrayList<>();
        layers.add(numbers);

        var lastArray = layers.getLast();
        while (!areAllNumbersZero(lastArray)) {
            layers.add(getDiffs(lastArray));
            lastArray = layers.getLast();
        }

        lastArray.add(0);
        layers.set(layers.size() - 1, lastArray);

        for (var i = layers.size() - 2; i >= 0; i--) {
            var currentLine = layers.get(i);
            var nextLine = layers.get(i + 1);
            int e = currentLine.getLast() + nextLine.getLast();
            currentLine.addLast(e);
            layers.set(i, currentLine);
        }
        return layers.getFirst().getLast();
    }

    private boolean areAllNumbersZero(List<Integer> numbers) {
        return numbers.stream().allMatch(x -> x == 0);
    }

    private List<Integer> parseLine(String line) {
        return Arrays.stream(line.split(Constants.EMPTY_STRING))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private List<Integer> getDiffs(List<Integer> numbers) {
        var arr = new ArrayList<Integer>();
        for (var i = 1; i < numbers.size(); i++) {
            arr.add(numbers.get(i) - numbers.get(i - 1));
        }
        return arr;
    }

}

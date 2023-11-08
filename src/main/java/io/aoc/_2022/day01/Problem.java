package io.aoc._2022.day01;

import io.aoc._2022.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public int part1(String input) {
        return getCaloriesSums(input).max().orElse(0);
    }

    public int part2(String input) {
        return getCaloriesSums(input)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .mapToInt(Integer::valueOf)
                .sum();
    }

    private IntStream getCaloriesSums(String input) {
        final String[] blocks = input.split("\r\n\r\n");
        return Arrays.stream(blocks)
                .mapToInt(this::sum);
    }

    private int sum(String input) {
        return input.lines()
                .mapToInt(Integer::parseInt)
                .sum();
    }

    public static void main(String[] args) {
        final String inputFile = Utils.readInputFileAsString(2022, 1, "input.txt");
        final Problem problem = new Problem();

        logger.info("Aoc2022, Day1 Problem, Part1: {}", problem.part1(inputFile));
        logger.info("Aoc2022, Day1 Problem, Part2: {}", problem.part2(inputFile));
    }

}

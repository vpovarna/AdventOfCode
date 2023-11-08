package io.aoc._2020.day01;

import io.aoc._2022.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 1, "input.txt");
        logger.info("Aoc2020, Day1 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day1 Problem, Part2: {}", problem.part2(input));
    }

    private int part1(String input) {
        return -1;
    }

    private int part2(String input) {
        return -1;
    }
}

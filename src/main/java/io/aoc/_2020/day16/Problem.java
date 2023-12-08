package io.aoc._2020.day16;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Problem {
    private final static Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 16, "input.txt");
        logger.info("Aoc2020, Day16 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day16 Problem, Part2: {}", problem.part2(input));
    }

    private int part1(String input) {
        parseInput(input);
        return 0;
    }


    private int part2(String input) {
        return 0;
    }

    private void parseInput(String input) {
        var parts = input.split(Constants.EMPTY_LINE);
        System.out.println(parts[0]);
        System.out.println(parts[1]);
        System.out.println(parts[2]);

    }

}


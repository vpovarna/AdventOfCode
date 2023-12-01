package io.aoc._2023.day01;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var inputFile = Utils.readInputFileAsString(2023, 1);
        var problem = new Problem();

        logger.info("Aoc2023, Day1 Problem, Part1: {}", problem.part1(inputFile));
        logger.info("Aoc2023, Day1 Problem, Part2: {}", problem.part2(inputFile));
    }

    private int part1(String input) {
        return Arrays.stream(input.split(Constants.EOL))
                .map(this::getNumber)
                .mapToInt(x -> x)
                .sum();
    }

    private int part2(String input) {
        return -1;
    }

    private int getNumber(String line) {
        var numbers = IntStream.range(0, line.length())
                .mapToObj(line::charAt)
                .filter(Character::isDigit)
                .toArray();

        String result;
        if (numbers.length == 1) {
            result = String.format("%s%s", numbers[0], numbers[0]);
        } else {
            result = String.format("%s%s", numbers[0], numbers[numbers.length - 1]);
        }

        return Integer.parseInt(result);
    }

}

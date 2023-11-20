package io.aoc._2020.day09;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 9, "input.txt");
        logger.info("Aoc2020, Day9 Problem, Part1: {}", problem.part1(input, 27));
        logger.info("Aoc2020, Day9 Problem, Part2: {}", problem.part2(input));
    }

    public long part1(String input, int preamble) {
        var values = parseInput(input);

        for (var i = preamble; i < values.length; i++) {
            var arr = Arrays.copyOfRange(values, i - preamble, i);
            var target = values[i];
            if (!twoSum(arr, target)) {
                return target;
            }
        }

        return -1;
    }

    public int part2(String input) {
        return 1;
    }

    private long[] parseInput(String input) {
        return Arrays.stream(input.split(Constants.EOL))
                .map(String::trim)
                .mapToLong(Long::parseLong)
                .toArray();
    }

    private boolean twoSum(long[] arr, long target) {
        var seen = new HashSet<Long>();
        for (var n : arr) {
            if (seen.contains(target - n)) {
                return true;
            }
            seen.add(n);
        }
        return false;
    }
}

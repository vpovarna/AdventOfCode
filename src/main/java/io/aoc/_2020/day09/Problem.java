package io.aoc._2020.day09;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 9, "input.txt");
        logger.info("Aoc2020, Day9 Problem, Part1: {}", problem.part1(input, 27));
        logger.info("Aoc2020, Day9 Problem, Part2: {}", problem.part2(input, 27));
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

    public long part2(String input, int preamble) {
        var target = part1(input, preamble);
        var values = parseInput(input);

        var consecutiveNumbersSoFar = getConsecutiveNumbers(target, values);
        if (consecutiveNumbersSoFar.size() < 2) {
            throw new RuntimeException("Unable to find the consecutive numbers.");
        }
        var minValue = Long.MAX_VALUE;
        var maxValue = Long.MIN_VALUE;

        for (var n : consecutiveNumbersSoFar) {
            minValue = Math.min(minValue, n);
            maxValue = Math.max(maxValue, n);
        }

        return maxValue + minValue;
    }

    private List<Long> getConsecutiveNumbers(long target, long[] values) {
        for (var i = 0; i < values.length; i++) {
            var consecutiveNumbersSoFar = new ArrayList<Long>();
            if (values[i] >= target) {
                continue;
            }

            consecutiveNumbersSoFar.add(values[i]);
            for (var j = i + 1; j < values.length - 1; j++) {

                var sum = consecutiveNumbersSoFar.stream().mapToLong(x -> x).sum();
                long partialSum = sum + values[j];
                if (partialSum > target) {
                    // exit
                    break;
                }

                if (partialSum < target) {
                    consecutiveNumbersSoFar.add(values[j]);
                } else {
                    consecutiveNumbersSoFar.add(values[j]);
                    return consecutiveNumbersSoFar;
                }

            }
        }
        return new ArrayList<>();
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

package io.aoc._2020.day01;

import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;

import static io.aoc.utils.Constants.EOL;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 1, "input.txt");
        logger.info("Aoc2020, Day1 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day1 Problem, Part2: {}", problem.part2(input));
    }

    public int part1(String input) {

        var numbers = parseInput(input);
        var target = 2020;

        var solutions = solve(numbers, target);
        if (solutions.length == 2) {
            return solutions[0] * solutions[1];
        }

        return -1;
    }

    public int part2(String input) {
        var numbers = parseInput(input);
        Arrays.sort(numbers);
        var target = 2020;

        for (var i = numbers.length -1; i >= 0; i--) {
            var newTarget = target - numbers[i];
            int[] targetArray = Arrays.copyOfRange(numbers, 0, i + 1);
            int[] response = solve(targetArray, newTarget);
            if (response.length == 2) {
                return response[0] * response[1] * numbers[i];
            }
        }

        return -1;
    }

    private static int[] solve(int[] numbers, int target) {
        var visited = new HashSet<Integer>();

        for (int n : numbers) {
            int newTarget = target - n;
            if (visited.contains(newTarget)) {
                return new int[] {n, newTarget};
            }
            visited.add(n);
        }
        return new int[]{};
    }

    private static int[] parseInput(String input) {
        return Arrays.stream(input.split(EOL))
                .map(String::trim)
                .map(Integer::parseInt)
                .mapToInt(x -> x)
                .toArray();
    }
}

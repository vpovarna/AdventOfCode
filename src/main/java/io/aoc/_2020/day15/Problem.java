package io.aoc._2020.day15;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);
    public static final int DEFAULT_VALUE = 0;
    public static final int FIRST_INDEX = 0;
    public static final int LAST_INDEX = 1;
    public static final int MAX_NR_OF_INDEXES = 2;


    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 15);
        logger.info("Aoc2020, Day15 Problem, Part1: {}", problem.run(input, 2020));
        logger.info("Aoc2020, Day15 Problem, Part2: {}", problem.run(input, 30000000));
    }

    private int run(String input, int nrOfIterations) {
        int[] numbers = parseInput(input);
        final Map<Integer, List<Integer>> storage = new HashMap<>();

        // initial mapping
        int n = numbers.length;
        for (var i = 0; i < n; i++) {
            storage.put(numbers[i], List.of(i + 1));
        }

        var lastNumber = numbers[n - 1];
        final List<Integer> emptyList = new ArrayList<>();

        for (var i = n + 1; i <= nrOfIterations; i++) {
            var currentList = storage.getOrDefault(lastNumber, emptyList);
            if (currentList.size() < MAX_NR_OF_INDEXES) {
                var list = storage.getOrDefault(DEFAULT_VALUE, emptyList);
                var tmpList = (list.isEmpty()) ? List.of(i) : List.of(i, list.get(FIRST_INDEX));
                storage.put(DEFAULT_VALUE, tmpList);
                lastNumber = DEFAULT_VALUE;
            } else {
                lastNumber = currentList.get(FIRST_INDEX) - currentList.get(LAST_INDEX);
                List<Integer> list = storage.getOrDefault(lastNumber, emptyList);
                var tmpList = (list.isEmpty()) ? List.of(i) : List.of(i, list.get(FIRST_INDEX));
                storage.put(lastNumber, tmpList);
            }
        }
        return lastNumber;
    }

    private int[] parseInput(String input) {
        return Arrays.stream(input.split(Constants.COMMA))
                .map(String::trim)
                .map(String::strip)
                .mapToInt(Integer::parseInt)
                .toArray();

    }
}

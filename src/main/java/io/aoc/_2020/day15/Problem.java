package io.aoc._2020.day15;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Problem {

    private final static Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 15);
        logger.info("Aoc2020, Day10 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day10 Problem, Part2: {}", problem.part2(input));
    }

    private int part1(String input) {
        int[] numbers = parseInput(input);

        Map<Integer, Queue<Integer>> storage = new HashMap<>();

        // initial mapping
        int n = numbers.length;
        for (var i = 0; i < n; i++) {
            Queue<Integer> queue = new ArrayBlockingQueue<>(2);
            queue.add(i + 1);
            storage.put(numbers[i], queue);
        }

        var lastNumber = numbers[n -1];

        for (var i = n; i < n + 3; i++) {
            Queue<Integer> defaultQueue = new ArrayBlockingQueue<>(1);
            if (storage.getOrDefault(lastNumber, defaultQueue).size() < 2) {
                lastNumber = 0;
                defaultQueue.add(i);
                if (storage.containsKey(lastNumber)) {
                    var queue = storage.get(lastNumber);
                    queue.add(i);
                    storage.put(lastNumber, queue);
                } else {
                    storage.put(lastNumber, defaultQueue);
                }
            } else {
                var queue = storage.get(lastNumber);
                int i1 = queue.poll();
                int i2 = queue.peek();
                lastNumber = Math.abs(i2 - i1);
                queue.add(i);
            }
            System.out.println(storage);
        }
        return 0;
    }

    private int part2(String input) {
        return 0;
    }

    private int[] parseInput(String input) {
        return Arrays.stream(input.split(Constants.COMMA))
                .map(String::trim)
                .map(String::strip)
                .mapToInt(Integer::parseInt)
                .toArray();

    }
}

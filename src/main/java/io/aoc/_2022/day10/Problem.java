package io.aoc._2022.day10;

import io.aoc._2022.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    private int part1(String input) {
        var lines = input.lines().toList();
        var duringCycle = calculateDuringCycleValues(lines);

        var sum = 0;
        for (int i = 20; i <= 220; i += 40) {
            sum += i * duringCycle.get(i);
        }
        return sum;
    }

    private StringJoiner part2(String input) {
        var lines = input.lines().toList();
        var cycleValues = calculateDuringCycleValues(lines);

        var cycle = 1;
        var rowJoiner = new StringJoiner("\n");
        for (var i = 0; i < 6; ++i) {
            var lineBuilder = new StringBuilder();
            for (var j = 0; j < 40; ++j, ++cycle) {
                lineBuilder.append(Math.abs(cycleValues.get(cycle) - j) <= 1 ? "#" : " ");
            }
            rowJoiner.add(lineBuilder.toString());
        }
        return rowJoiner;
    }

    private Map<Integer, Integer> calculateDuringCycleValues(List<String> lines) {
        var duringCycle = new HashMap<Integer, Integer>();
        var x = 1;
        var cycle = 1;

        for (var line : lines) {
            if ("noop".equals(line)) {
                duringCycle.put(cycle++, x);
            } else {
                duringCycle.put(cycle++, x);
                duringCycle.put(cycle++, x);
                x += Integer.parseInt(line.split(" ")[1]);
            }
        }

        return duringCycle;
    }

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2022, 10, "input.txt");
        logger.info("Aoc2022, Day10 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2022, Day10 Problem, Part2:\n{}", problem.part2(input));
    }

}

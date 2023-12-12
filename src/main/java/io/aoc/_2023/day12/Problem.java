package io.aoc._2023.day12;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var inputFile = Utils.readInputFileAsString(2023, 12);

        var problem = new Problem();
        logger.info("Aoc2023, Day12 Problem, Part1: {}", problem.part1(inputFile));
        logger.info("Aoc2023, Day12 Problem, Part2: {}", problem.part2(inputFile));
    }

    private int part1(String input) {
        var conditionRecords = parseInput(input);
        return conditionRecords.stream()
                .map(this::getAllPossibleSolutions)
                .mapToInt(x -> x)
                .sum();
    }

    private int part2(String input) {
        return -1;
    }

    private List<ConditionRecords> parseInput(String input) {
        return Arrays.stream(input.split(Constants.EOL))
                .map(line -> line.split(Constants.EMPTY_STRING))
                .map(parts -> new ConditionRecords(
                        parts[0],
                        Arrays.stream(parts[1].split(Constants.COMMA))
                                .mapToInt(Integer::parseInt)
                                .toArray()
                ))
                .toList();
    }

    private Integer getAllPossibleSolutions(ConditionRecords conditionRecords) {
        var count = 0;

        var stack = new ArrayDeque<String>();
        stack.add(conditionRecords.spring());

        while (!stack.isEmpty()) {
            var currentLine = stack.pop();
            var chars = currentLine.toCharArray();
            for (var i = 0; i < chars.length; i++) {
                if (chars[i] == '?') {
                    chars[i] = '#';
                    String s1 = new String(chars);
                    if (!s1.contains("?") && Arrays.equals(calculateDistribution(s1), conditionRecords.rules())) {
                        count += 1;
                    }
                    stack.add(s1);

                    chars[i] = '.';
                    String s2 = new String(chars);
                    if (!s2.contains("?") && Arrays.equals(calculateDistribution(s2), conditionRecords.rules())) {
                        count += 1;
                    }
                    stack.add(s2);
                    break;
                }
            }

        }

        return count;
    }

    private int[] calculateDistribution(String spring) {
        var list = new ArrayList<Integer>();
        var chars = spring.toCharArray();
        for (var i = 0; i < chars.length; i++) {
            if (chars[i] == '#') {
                var j = i + 1;
                while (j < chars.length && chars[j] == '#') {
                    j = j + 1;
                }
                list.add(j - i);
                i = j;
            }
        }

        return list.stream().mapToInt(x -> x).toArray();
    }


}

record ConditionRecords(String spring, int[] rules) {

    @Override
    public String toString() {
        return "[" + spring + "," + Arrays.toString(rules) + "]";
    }
}

package io.aoc._2023.day22;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2023, 22, "input.txt");
        logger.info("Aoc2020, Day20 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day20 Problem, Part2: {}", problem.part2(input));
    }

    private int part1(String input) {
        List<List<Integer>> bricks = parseInput(input);
        for (var i = 0; i < bricks.size(); i++) {
            var maxZ = 1;
            var brick = bricks.get(i);
            for (var check : bricks.subList(0, i)) {
                if (overlaps(check, brick)) {
                    maxZ = Math.max(maxZ, check.get(5) + 1);
                }
            }
            var prevBrickValue = brick.get(5);
            brick.set(5, prevBrickValue - (brick.get(2) - maxZ));
            brick.set(2, maxZ);
        }

        // we need to sort the bricks since they can get out of order
        var sortedBricks = sortBricks(bricks);

        // two dictionaries from index to a set
        var keySupportValuesDict = new HashMap<Integer, Set<Integer>>();

        // reversed dictionary
        var valueSupportKeyDict = new HashMap<Integer, Set<Integer>>();

        for (var j = 0; j < sortedBricks.size(); j++) {
            var upperBrick = sortedBricks.get(j);
            var sublist = sortedBricks.subList(0, j);
            for (var i = 0; i < sublist.size(); i++) {
                var lowerBrick = sublist.get(i);

                if (overlaps(upperBrick, lowerBrick) && (upperBrick.get(2) == lowerBrick.get(5) + 1)) {
                    updateDictionary(keySupportValuesDict, i, j);
                    updateDictionary(valueSupportKeyDict, j, i);
                }
            }
        }

        return getTotal(sortedBricks, keySupportValuesDict, valueSupportKeyDict);
    }

    private void updateDictionary(HashMap<Integer, Set<Integer>> keySupportValuesDict, int i, int j) {
        var set = keySupportValuesDict.getOrDefault(i, new HashSet<>());
        set.add(j);
        keySupportValuesDict.put(i, set);
    }

    // Functional approach?
    private static int getTotal(List<List<Integer>> sortedBricks, HashMap<Integer, Set<Integer>> keySupportValuesDict, HashMap<Integer, Set<Integer>> valueSupportKeyDict) {
        var total = 0;
        for (var i = 0; i < sortedBricks.size(); i++) {
            var bricksConnections = keySupportValuesDict.getOrDefault(i, new HashSet<>());
            var allMatched = bricksConnections.stream()
                    .allMatch(j -> valueSupportKeyDict.getOrDefault(j, new HashSet<>()).size() >= 2);
            if (allMatched) {
                total += 1;
            }
        }
        return total;
    }

    private int part2(String input) {
        return 0;
    }

    private boolean overlaps(List<Integer> a, List<Integer> b) {
        return Math.max(a.get(0), b.get(0)) <= Math.min(a.get(3), b.get(3)) && Math.max(a.get(1), b.get(1)) <= Math.min(a.get(4), b.get(4));
    }

    // Replace the array with a mutable class?
    private List<List<Integer>> parseInput(String input) {
        var lines = input.split(Constants.EOL);

        var arr = new ArrayList<List<Integer>>();
        for (var line : lines) {
            var list = Arrays.stream(line.replace("~", ",").split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            arr.add(list);
        }

        return sortBricks(arr);
    }

    private List<List<Integer>> sortBricks(List<List<Integer>> arr) {
        return arr.stream()
                .sorted(Comparator.comparing(l -> l.get(2)))
                .collect(Collectors.toList());
    }

}

package io.aoc._2023.day12;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    private static final HashMap<State, Long> MEMORY = new HashMap<>();

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

    private long part2(String input) {
        var conditionRecords = parseInput(input);
        var ans = 0L;
        for (var conditionRecord : conditionRecords) {
            var spring = conditionRecord.spring();
            var newSpringsLine = spring + ('?' + spring).repeat(4);
            var initRecords = Arrays.stream(conditionRecord.rules())
                    .boxed()
                    .toList();
            var newNumbers = new LinkedList<>(initRecords);
            for (var i = 0; i < 4; i++) {
                newNumbers.addAll(initRecords);
            }

            ans += combinationsCount(newSpringsLine, newNumbers, false);
        }
        return ans;
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

    public long combinationsCount(String line, List<Integer> numbers, boolean inGroup) {
        var state = new State(line, numbers, inGroup);
        var cachedValue = cacheRead(state);

        if (cachedValue != null) {
            return cachedValue;
        }

        if (line.isEmpty()) {
            return numbers.isEmpty() || (numbers.size() == 1 && numbers.getFirst() == 0) ? 1 : 0;
        }

        var head = line.charAt(0);
        var remainingLine = line.substring(1);

        switch (head) {
            case '.' -> {
                // The "." can be part of the normal string chars or a replacement from "?". We need to differentiate these two cases.
                if (inGroup) {
                    // Nothing to do.
                    if (numbers.getFirst() != 0) {
                        return 0;
                    }
                    // Cache the result and move fw.
                    return cache(state, combinationsCount(remainingLine, numbers.subList(1, numbers.size()), false));
                }
                return cache(state, combinationsCount(remainingLine, numbers, false));
            }
            case '#' -> {
                if (!numbers.isEmpty() && numbers.getFirst() > 0) {
                    var reducedNumbers = new LinkedList<>(numbers);
                    reducedNumbers.set(0, numbers.getFirst() - 1);
                    return cache(state, combinationsCount(remainingLine, reducedNumbers, true));
                }
            }
            case '?' -> {
                return cache(
                        state,
                        combinationsCount('.' + remainingLine, numbers, inGroup)
                                + combinationsCount('#' + remainingLine, numbers, inGroup)
                );
            }
            default -> throw new IllegalArgumentException("Illegal character");
        }

        return 0;
    }

    private long cache(State state, long result) {
        MEMORY.put(state, result);
        return result;
    }

    private Long cacheRead(State state) {
        return MEMORY.getOrDefault(state, null);
    }

    // Brute Force approach. Works for part1 since the max size of possibilities is 2^20 ~= 1_000_000
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
                    String s1 = String.valueOf(chars);
                    count += getCount(s1, conditionRecords.rules());
                    stack.add(s1);

                    chars[i] = '.';
                    String s2 = String.valueOf(chars);
                    count += getCount(s2, conditionRecords.rules());
                    stack.add(s2);
                    break;
                }
            }

        }

        return count;
    }

    private int getCount(String s, int[] rules) {
        return (!s.contains("?") && Arrays.equals(calculateDistribution(s), rules)) ? 1 : 0;
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
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "[" + spring + "," + Arrays.toString(rules) + "]";
    }
}

record State(String line, List<Integer> damaged, boolean inGroup) {
}

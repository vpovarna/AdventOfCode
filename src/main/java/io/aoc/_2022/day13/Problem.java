package io.aoc._2022.day13;

import io.aoc._2022.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);
    public int part1(String input) {
        var pairs = readPairs(input);
        return IntStream.range(0, pairs.size())
                .filter(i -> compare(pairs.get(i).get(0), pairs.get(i).get(1), 0) <= 0)
                .map(i -> i + 1).sum();
    }

    public int part2(String input) {
        var pairs = readPairs(input);
        var dividers = List.of(List.of(2), List.of(6));
        var packets = pairs.stream().flatMap(p -> Stream.of(p.get(0), p.get(1))).toList();
        var sorted = Stream.concat(packets.stream(), dividers.stream())
                .sorted((a,b) -> compare(a, b, 0)).toList();
        return IntStream.range(0, sorted.size())
                .filter(p -> dividers.contains(sorted.get(p)))
                .map(i -> i + 1).reduce(1, (a,b) -> a * b);
    }

    private int compare(List<?> left, List<?> right, int i) {
        if (i >= left.size() && i >= right.size()) {
            return 0;
        } else if (i >= left.size()) {
            return -1;
        } else if (i >= right.size()) {
            return 1;
        }

        var li = left.get(i);
        var ri = right.get(i);
        if (li instanceof Integer && ri instanceof Integer) {
            return li.equals(ri) ? compare(left, right, i+1) : (int)li -(int)ri;
        }

        List<?> ll = li instanceof List ? (List<?>)(li) : List.of(li);
        List<?> rl = ri instanceof List ? (List<?>)(ri) : List.of(ri);
        var res = compare(ll, rl, 0);
        return res == 0 ? compare(left, right, i+1) : res;
    }

    private List<List<List<?>>> readPairs(String input) {
        return Arrays.stream(input.split("\n\n"))
                .map(pair -> pair.split("\n"))
                .map(pair -> List.of(parse(pair[0]), parse(pair[1])))
                .toList();
    }

    private List<?> parse (String input) {
        LinkedList<List<Object>> stack = new LinkedList<>();
        List<Object> latest = new LinkedList<>();

        StringBuffer buffer = new StringBuffer();

        Runnable add = () -> {
            if (!buffer.isEmpty()) {
                stack.getFirst().add(Integer.parseInt(buffer.toString()));
                buffer.setLength(0);
            }
        };

        for (int i = 0; i< input.length(); i++) {
            switch (input.charAt(i)) {
                case '[' -> stack.push(new LinkedList<>());
                case ']' -> {
                    add.run();
                    latest =stack.pop();
                    if (!stack.isEmpty()) {
                        stack.getFirst().add(latest);
                    }
                }
                case ',' -> add.run();
                default -> buffer.append(input.charAt(i));
            }
        }
        return latest;
    }

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(13, "input.txt");
        logger.info("Aoc2022, Day13 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2022, Day13 Problem, Part2: {}", problem.part2(input));
    }
}

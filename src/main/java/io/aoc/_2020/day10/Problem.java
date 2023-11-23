package io.aoc._2020.day10;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Problem {

    public static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 10, "input.txt");
        logger.info("Aoc2020, Day10 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day10 Problem, Part2: {}", problem.part2(input));
    }

    private long part1(String input) {
        var numbers = parseInput(input);
        var windows = new ArrayList<Tuple<Integer, Integer>>();

        for (var i = 1; i < numbers.size(); i++) {
            windows.add(new Tuple<>(numbers.get(i), numbers.get(i - 1)));
        }

        return count(windows, 1) * count(windows, 3);
    }

    private long part2(String input) {
        var numbers = parseInput(input);

        var a = 1L;
        var b = 0L;
        var c = 0L;

        int count = numbers.size();
        for (var i = count - 2; i >= 0; i--) {
            var s = (i + 1 < count && numbers.get(i + 1) - numbers.get(i) <= 3 ? a : 0) +
                    (i + 2 < count && numbers.get((i + 2)) - numbers.get(i) <= 3 ? b : 0) +
                    (i + 3 < count && numbers.get((i + 3)) - numbers.get(i) <= 3 ? c : 0);
            c = b;
            b = a;
            a = s;
        }
        return a;
    }

    private List<Integer> parseInput(String input) {
        var numbers = Arrays.stream(input.split(Constants.EOL))
                .map(String::trim)
                .map(Integer::parseInt)
                .sorted()
                .toList();

        var result = new ArrayList<Integer>();
        result.add(0);
        result.addAll(numbers);
        result.add(numbers.get(numbers.size() - 1) + 3);
        return result;
    }

    private long count(ArrayList<Tuple<Integer, Integer>> windows, int target) {
        return windows.stream()
                .filter(t -> t.getItem1() - t.getItem2() == target)
                .count();
    }

}

class Tuple<T1, T2> {
    private final T1 item1;
    private final T2 item2;

    public Tuple(T1 item1, T2 item2) {
        this.item1 = item1;
        this.item2 = item2;
    }

    public T1 getItem1() {
        return item1;
    }

    public T2 getItem2() {
        return item2;
    }

    @Override
    public String toString() {
        return "(" + item1 +
                " , " + item2 +
                ')';
    }
}

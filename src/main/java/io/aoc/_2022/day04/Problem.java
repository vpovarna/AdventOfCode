package io.aoc._2022.day04;

import io.aoc._2022.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.aoc._2022.day04.Interval.checkIfTwoIntervalsFullyOverlaps;
import static io.aoc._2022.day04.Interval.checkIfTwoIntervalsOverlaps;

public class Problem {
    private final static Logger logger = LoggerFactory.getLogger(Problem.class);
    public static final String COMA = ",";
    public static final String DASH = "-";

    public int part1(String input) {
        return (int) inputParser(input)
                .filter(intervals -> checkIfTwoIntervalsFullyOverlaps(intervals[0], intervals[1]))
                .count();
    }

    public int part2(String input) {
        return (int) inputParser(input)
                .filter(intervals -> checkIfTwoIntervalsOverlaps(intervals[0], intervals[1]))
                .count();
    }

    private Stream<Interval[]> inputParser(String input) {
        return input.lines()
                .map(this::getIntervals)
                .filter(intervals -> intervals.length == 2);
    }

    private Interval[] getIntervals(String s) {
        final String[] strings = s.split(COMA);
        final int expectedLength = 2;

        if (strings.length != expectedLength) {
            throw new IllegalArgumentException("Input line doesn't match the format");
        }

        Interval[] intervals = new Interval[expectedLength];
        for (int i = 0; i < intervals.length; i++) {
            final List<Integer> coordinates = Arrays.stream(strings[i].split(DASH))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            final Interval interval = new Interval(coordinates.get(0), coordinates.get(1));
            intervals[i] = interval;
        }

        return intervals;
    }

    public static void main(String[] args) {
        final Problem problem = new Problem();
        final String inputFile = Utils.readInputFileAsString(4, "input.txt");
        logger.info("Aoc2022, Day4 Problem, Part1: {}", problem.part1(inputFile));
        logger.info("Aoc2022, Day4 Problem, Part2: {}", problem.part2(inputFile));
    }

}

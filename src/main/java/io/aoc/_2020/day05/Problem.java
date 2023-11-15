package io.aoc._2020.day05;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.assertj.core.util.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toSet;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 5, "input.txt");
        logger.info("Aoc2020, Day5 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day5 Problem, Part2: {}", problem.part2(input));
    }

    public int part1(String input) {
        return existingSeatsIdStream(input)
                .max(Comparator.naturalOrder())
                .orElse(-1);
    }

    public int part2(String input) {
        var minimumSeatId = existingSeatsIdStream(input)
                .min(Comparator.naturalOrder())
                .orElse(-1);
        var maximumSeatId = existingSeatsIdStream(input)
                .max(Comparator.naturalOrder())
                .orElse(-1);

        var existingSeatIds = existingSeatsIdStream(input).collect(toSet());

        return IntStream.range(minimumSeatId, maximumSeatId)
                .boxed()
                .filter(not(existingSeatIds::contains))
                .findAny()
                .orElseThrow();
    }

    private Stream<Integer> existingSeatsIdStream(String input) {
        return Arrays.stream(input.split(Constants.EOL))
                .map(this::getSeatNumber);
    }

    @VisibleForTesting
    protected int getSeatNumber(String input) {
        var rowDirection = input.substring(0, input.length() - 3);
        var columnDirection = input.substring(input.length() - 3);

        var row = findPosition(rowDirection, 0, 127, 'F');
        var col = findPosition(columnDirection, 0, 7, 'L');

        return row * 8 + col;
    }

    @VisibleForTesting
    protected int findPosition(String start, int s, int e, char lowerHalfSymbol) {
        for (var c : start.toCharArray()) {
            if (c == lowerHalfSymbol) {
                e = (s + e) / 2;
            } else {
                s = ((s + e) / 2) + 1;
            }
        }
        return Math.min(s, e);
    }

}

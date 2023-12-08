package io.aoc._2023.day06;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var inputFile = Utils.readInputFileAsString(2023, 6);
        var problem = new Problem();

        logger.info("Aoc2023, Day6 Problem, Part1: {}", problem.part1(inputFile));
        logger.info("Aoc2023, Day6 Problem, Part2: {}", problem.part2(inputFile));
    }

    private long part1(String input) {
        var stats = parseInput(input);
        return calculateWays(stats.times(), stats.records());

    }

    private long part2(String input) {
        var stats = parseInput(input);
        return getRecords(getNumber(stats.times()), getNumber(stats.records()));
    }

    private long getNumber(List<Long> values) {
        var stringValues = values.stream()
                .map(String::valueOf)
                .toList();
        return Long.parseLong(String.join("", stringValues));
    }

    private long calculateWays(List<Long> times, List<Long> records) {
        return IntStream.range(0, times.size())
                .mapToLong(index -> getRecords(times.get(index), records.get(index)))
                .filter(nr -> nr > 0)
                .reduce((x, y) -> x * y)
                .orElse(1L);
    }

    private long getRecords(long time, long existingRecord) {
        return LongStream.range(0, time)
                .map(i -> (time - i) * i)
                .filter(result -> result > existingRecord)
                .count();
    }

    private Stats parseInput(String input) {
        String[] lines = input.split(Constants.EOL);
        if (lines.length !=2) {
            throw new IllegalArgumentException("Invalid input");
        }
        return new Stats(extractNumbers(lines[0]), extractNumbers(lines[1]));
    }

    private List<Long> extractNumbers(String line) {
        List<Long> list = new ArrayList<>();
        Pattern digitRegex = Pattern.compile("\\d+");
        Matcher matched = digitRegex.matcher(line);

        while (matched.find()) {
            list.add(Long.parseLong(matched.group()));
        }

        return list;
    }
}

record Stats(List<Long> times, List<Long> records){}

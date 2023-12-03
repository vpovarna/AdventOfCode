package io.aoc._2023.day03;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var inputFile = Utils.readInputFileAsString(2023, 3);
        var problem = new Problem();

        logger.info("Aoc2023, Day3 Problem, Part1: {}", problem.part1(inputFile));
        logger.info("Aoc2023, Day3 Problem, Part2: {}", problem.part2(inputFile));
    }

    private int part1(String input) {
        var rows = input.split(Constants.EOL);
        Pattern digitsPatterns = Pattern.compile("\\d+");
        Pattern symbolPattern = Pattern.compile("[^.0-9]");

        List<Part> numbers = extract(rows, digitsPatterns);
        List<Part> gears = extract(rows, symbolPattern);

        return gears.parallelStream()
                .mapToInt(gear -> numbers.stream()
                        .filter(number -> nextTo(gear, number))
                        .mapToInt(Part::toInt)
                        .sum())
                .sum();
    }

    private int part2(String input) {
        var rows = input.split(Constants.EOL);
        Pattern digitsPatterns = Pattern.compile("\\d+");
        Pattern symbolPattern = Pattern.compile("[^.0-9]");

        List<Part> numbers = extract(rows, digitsPatterns);
        List<Part> gears = extract(rows, symbolPattern);

        return gears.parallelStream()
                .map(gear -> numbers.stream()
                        .filter(number -> nextTo(gear, number))
                        .mapToInt(Part::toInt)
                        .toArray())
                .filter(neighbours -> neighbours.length == 2)
                .mapToInt(neighbours -> neighbours[0] * neighbours[neighbours.length - 1])
                .sum();
    }

    private List<Part> extract(String[] rows, Pattern pattern) {
        var arr = new ArrayList<Part>();
        for (var i = 0; i < rows.length; i++) {
            var currentRow = rows[i];
            Matcher matcher = pattern.matcher(currentRow);
            while (matcher.find()) {
                var match = matcher.group();
                arr.add(new Part(match, i, matcher.start()));
            }
        }

        return arr;
    }

    private boolean nextTo(Part p1, Part p2) {
        return Math.abs(p2.row() - p1.row()) <= 1 &&
                p1.col() <= p2.col() + p2.text().length() &&
                p2.col() <= p1.col() + p1.text().length();
    }

}

record Part(String text, int row, int col) {
    public int toInt() {
        return Integer.parseInt(text);
    }
}

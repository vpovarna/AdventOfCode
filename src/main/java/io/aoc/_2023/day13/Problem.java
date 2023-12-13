package io.aoc._2023.day13;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.ToIntFunction;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var inputFile = Utils.readInputFileAsString(2023, 13);

        var problem = new Problem();
        logger.info("Aoc2023, Day12 Problem, Part1: {}", problem.part1(inputFile));
        logger.info("Aoc2023, Day12 Problem, Part2: {}", problem.part2(inputFile));
    }

    private int part1(String input) {
        var lineMultiplicationFactor = 100;
        var grids = parseInput(input);

        ToIntFunction<Grid> count = grid -> lineMultiplicationFactor * getReflectionLineIndex(grid) + getReflectionLineIndex(grid.transpose());

        return grids.stream()
                .mapToInt(count)
                .sum();
    }

    private int part2(String inputFile) {
        return 0;
    }

    private List<Grid> parseInput(String input) {
        return Arrays.stream(input.split(Constants.EMPTY_LINE))
                .map(this::getGrid)
                .toList();
    }

    private Grid getGrid(String rowGrid) {
        var lines = Arrays.stream(rowGrid.split(Constants.EOL)).toList();
        return new Grid(lines);
    }

    private boolean hasReflection(List<String> lines, int index) {
        var n = lines.size();
        var m = lines.get(0).length();

        for (var j = 0; j < m; j++) {
            for (var i = 0; i < n; i++) {
                var k = index * 2 + 1 - i;
                if (k < 0 || k >= n) {
                    continue;
                }

                if (lines.get(i).charAt(j) != lines.get(k).charAt(j)) {
                    return false;
                }
            }
        }

        return true;
    }

    private int getReflectionLineIndex(Grid grid) {
        var gridLines = grid.lines();

        int size = gridLines.size();
        for (var i = 0; i < size - 1; i++) {
            if (hasReflection(gridLines, i)) {
                return i + 1;
            }
        }

        return 0;
    }
}

record Grid(List<String> lines) {
    public Grid transpose() {
        var acc = new ArrayList<String>();
        var m = lines.size();
        var n = lines.get(0).length();

        for (var i = 0; i < n; i++) {
            var charArr = new char[m];
            for (var j = 0; j < m; j++) {
                charArr[j] = lines.get(j).charAt(i);
            }
            acc.add(new String(charArr));
        }

        return new Grid(acc);
    }
}

package io.aoc._2023.day13;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);
    private static final int MULTIPLICATION_FACTOR = 100;


    public static void main(String[] args) {
        var inputFile = Utils.readInputFileAsString(2023, 13);

        var problem = new Problem();
        logger.info("Aoc2023, Day12 Problem, Part1: {}", problem.part1(inputFile));
        logger.info("Aoc2023, Day12 Problem, Part2: {}", problem.part2(inputFile));
    }

    private int part1(String input) {
        var grids = parseInput(input);

        ToIntFunction<Grid> count = grid -> MULTIPLICATION_FACTOR * (getReflectionLineIndex(grid, -1) + 1) +
                getReflectionLineIndex(grid.transpose(), -1) + 1;

        return grids.stream()
                .mapToInt(count)
                .sum();
    }

    private int part2(String input) {
        var grids = parseInput(input);
        return grids.stream()
                .mapToInt(this::mutateAndGetReflection)
                .sum();
    }

    private List<Grid> parseInput(String input) {
        return Arrays.stream(input.split(Constants.EMPTY_LINE))
                .map(this::getGrid)
                .toList();
    }

    private Grid getGrid(String rowGrid) {
        // Grid lines should be mutable.
        var lines = Arrays
                .stream(rowGrid.split(Constants.EOL))
                .collect(Collectors.toList());
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

    private int getReflectionLineIndex(Grid grid, int avoidIndex) {
        var gridLines = grid.lines();

        int size = gridLines.size();
        for (var i = 0; i < size - 1; i++) {
            if (i != avoidIndex && hasReflection(gridLines, i)) {
                return i;
            }
        }

        return -1;
    }

    private int mutateAndGetReflection(Grid grid) {
        var gridLines = grid.lines();

        var origHorizontalLineNr = getReflectionLineIndex(grid, -1);
        var origVerticalLineNr = getReflectionLineIndex(grid.transpose(), -1);

        for (var i = 0; i < gridLines.size(); i++) {
            var currentLine = gridLines.get(i).toCharArray();
            for (var j = 0; j < currentLine.length; j++) {
                // Clone the currentLine to a tmp array.
                var tmpArray = currentLine.clone();
                tmpArray[j] = (gridLines.get(i).charAt(j) == '#') ? '.' : '#';

                // Update grid with the new string
                gridLines.set(i, String.valueOf(tmpArray));

                // Get the new reflection line index
                var newGrid = new Grid(gridLines);
                var newHorizontalLineNr = getReflectionLineIndex(newGrid, origHorizontalLineNr);
                var newVerticalLineNr = getReflectionLineIndex(newGrid.transpose(), origVerticalLineNr);

                // Check if the new indexes are different from the default values or the original values.
                if ((newHorizontalLineNr != -1 || newVerticalLineNr != -1) &&
                        (newHorizontalLineNr != origHorizontalLineNr || newVerticalLineNr != origVerticalLineNr)) {
                    return (newHorizontalLineNr != -1) ?
                            (newHorizontalLineNr + 1) * MULTIPLICATION_FACTOR :
                            newVerticalLineNr + 1;
                }

                // Reset updated line
                gridLines.set(i, String.valueOf(currentLine));
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

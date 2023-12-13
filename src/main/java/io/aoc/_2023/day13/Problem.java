package io.aoc._2023.day13;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
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
//        var result = 0;
//        for (var grid : grids) {
//            int verticalNumberOfReflections = getReflectionSize(grid.transpose());
//            if (verticalNumberOfReflections > 0) {
//                result += verticalNumberOfReflections;
//                continue;
//            }
//
//            result += lineMultiplicationFactor * getReflectionSize(grid);
//        }
        //        return result;


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

    private boolean hasReflection(List<String> lines) {
        if (lines.size() < 2) {
            return false;
        }

        var left = 0;
        var right = lines.size() - 1;

        while (left < right) {
            if (!lines.get(left).equals(lines.get(right))) {
                return false;
            }
            left += 1;
            right -= 1;
        }

        return true;
    }

    private int getReflectionLineIndex(Grid grid) {
        var gridLines = grid.lines();

        int size = gridLines.size();
        for (var i = 0; i < size; i++) {
            var subGridLines = gridLines.subList(i, size);
            if (hasReflection(subGridLines)) {
                return subGridLines.size() / 2 + 1;
            }
        }

        return 0;
    }

    private int summary(Grid grid) {
        var gridLines = grid.lines();
        var n = gridLines.size();
        var m = gridLines.get(0).length();

        var horiz = -1;
        for (var i = 0; i < n; i++) {
            var subGridLines = gridLines.subList(i, n);
            if (hasReflection(subGridLines)) {
                horiz = i;
                break;
            }
        }

        var vert = -1;
        var transposedGridLines = grid.transpose().lines();
        for (var j = 0; j < m; j++) {
            var subGridLines = transposedGridLines.subList(j, m);
            if (hasReflection(subGridLines)) {
                vert = j - 1;
                break;
            }
        }

        var ans = 0;
        if (vert != -1) {
            ans += vert + 1;
        }

        if (horiz != -1) {
            ans += 100 * (horiz + 1);
        }
        return ans;
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

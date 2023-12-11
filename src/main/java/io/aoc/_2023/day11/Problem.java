package io.aoc._2023.day11;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var inputFile = Utils.readInputFileAsString(2023, 11);

        var problem = new Problem();
        logger.info("Aoc2023, Day11 Problem, Part1: {}", problem.solve(inputFile, 2));
        logger.info("Aoc2023, Day11 Problem, Part2: {}", problem.solve(inputFile, 1_000_000));
    }

    private long solve(String input, int factor) {
        var grid = getGrid(input);
        var galaxyCoordinate = getGalaxyCoordinate(grid);
        var n = galaxyCoordinate.size();

        long ans = 0L;
        for (var i = 0 ; i< n; i++) {
            for (var j = i; j < n; j++) {
                long d = calculateDistance(galaxyCoordinate.get(i), galaxyCoordinate.get(j), factor, grid);
                ans += d;
            }
        }

        return ans;
    }

    private char[][] getGrid(String input) {
        var lines = input.split(Constants.EOL);
        var n = lines.length;
        var m = lines[0].length() - 1;

        char[][] grid = new char[n][m];

        for (var i = 0; i < n; i++) {
            var line = lines[i];
            for (var j = 0; j < m; j++) {
                grid[i][j] = line.charAt(j);
            }
        }

        return grid;
    }

    // Manhattan distance
    private long calculateDistance(Coordinate coordinate1, Coordinate coordinate2, int factor, char[][] grid) {
        var i1 = Math.min(coordinate1.x(), coordinate2.x());
        var i2 = Math.max(coordinate1.x(), coordinate2.x());

        var j1 = Math.min(coordinate1.y(), coordinate2.y());
        var j2 = Math.max(coordinate1.y(), coordinate2.y());

        long ans = 0L;
        for (var i = i1; i < i2; i++) {
            ans +=1;
            if (isEmptyRow(i, grid)) {
                ans += factor - 1;
            }
        }
        for (var j = j1; j < j2; j++) {
            ans +=1;
            if (isEmptyCol(j, grid)) {
                ans += factor - 1;
            }
        }

        return ans;
    }

    private boolean isEmptyCol(int index, char[][] grid) {
        for (char[] chars : grid) {
            if (chars[index] == '#') {
                return false;
            }
        }
        return true;
    }

    private boolean isEmptyRow(int index, char[][] grid) {
        for (var i = 0; i< grid[0].length; i++) {
            if (grid[index][i] == '#'){
                return false;
            }
        }
        return true;
    }

    private List<Coordinate> getGalaxyCoordinate(char[][] grid) {
        var list = new ArrayList<Coordinate>();
        for (var i = 0; i < grid.length; i++) {
            for (var j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '#') {
                    list.add(new Coordinate(i, j));
                }
            }
        }
        return list;
    }
}

record Coordinate(int x, int y) {
}
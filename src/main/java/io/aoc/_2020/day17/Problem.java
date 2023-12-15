package io.aoc._2020.day17;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Problem {

    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 17, "input.txt");
        logger.info("Aoc2020, Day17 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day17 Problem, Part2: {}", problem.part2(input));
    }

    private int part1(String input) {
        var positions = new HashSet<Position3D>();
        char[][] grid = getGrid(input);

        // Create a HashSet to store all the 3 dim grid coordinates
        for (var i = 0; i < grid.length; i++) {
            for (var j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '#') {
                    positions.add(new Position3D(i, j, 0));
                }
            }
        }

        for (var i = 0; i < 6; i++) {
            var newPositions = new HashSet<Position3D>();
            var checkedPositions = new HashSet<Position3D>();

            for (var position : positions) {
                addNeighbors(positions, newPositions, checkedPositions, position, true);
            }

            positions = newPositions;
        }
        return positions.size();
    }


    private int part2(String input) {
        var positions = new HashSet<Position4D>();
        char[][] grid = getGrid(input);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '#') {
                    positions.add(new Position4D(i, j, 0, 0));
                }
            }
        }
        for (int i = 0; i < 6; i++) {
            var newPositions = new HashSet<Position4D>();
            var checkedPositions = new HashSet<Position4D>();
            for (var position : positions) {
                addNeighbors(positions, newPositions, checkedPositions, position, true);
            }
            positions = newPositions;
        }
        return positions.size();
    }

    private void addNeighbors(Set<Position3D> position3DS, Set<Position3D> newPosition3DS, Set<Position3D> checkedPosition3DS, Position3D position3D, boolean active) {
        if (!checkedPosition3DS.contains(position3D)) {
            int neighbours = active ? -1 : 0;
            checkedPosition3DS.add(position3D);

            for (int a = -1; a <= 1; a++) {
                for (int b = -1; b <= 1; b++) {
                    for (int c = -1; c <= 1; c++) {
                        var x = new Position3D(position3D.x() + a, position3D.y() + b, position3D.z() + c);
                        if (position3DS.contains(x)) {
                            neighbours++;
                        } else if (active) {
                            addNeighbors(position3DS, newPosition3DS, checkedPosition3DS, x, false);
                        }
                    }
                }
            }
            if ((active && (neighbours == 2 || neighbours == 3)) || (!active && neighbours == 3)) {
                newPosition3DS.add(position3D);
            }
        }
    }

    public void addNeighbors(Set<Position4D> positions, Set<Position4D> newPositions, Set<Position4D> checkedPos, Position4D position, boolean active) {
        if (!checkedPos.contains(position)) {
            long neighbours = active ? -1 : 0;
            checkedPos.add(position);
            for (int a = -1; a <= 1; a++) {
                for (int b = -1; b <= 1; b++) {
                    for (int c = -1; c <= 1; c++) {
                        for (int d = -1; d <= 1; d++) {
                            Position4D x = new Position4D(position.x() + a, position.y() + b, position.z() + c, position.w() + d);
                            if (positions.contains(x)) {
                                neighbours++;
                            } else if (active) {
                                addNeighbors(positions, newPositions, checkedPos, x, false);
                            }
                        }
                    }
                }
            }
            if ((active && (neighbours == 2 || neighbours == 3)) ||
                    (!active && neighbours == 3)) {
                newPositions.add(position);
            }
        }
    }

    private char[][] getGrid(String input) {
        return Arrays.stream(input.split(Constants.EOL))
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

}

record Position3D(int x, int y, int z) {
}

record Position4D(int x, int y, int z, int w) {
}

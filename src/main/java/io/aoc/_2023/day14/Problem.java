package io.aoc._2023.day14;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var inputFile = Utils.readInputFileAsString(2023, 14);

        var problem = new Problem();
        logger.info("Aoc2023, Day12 Problem, Part1: {}", problem.part1(inputFile));
        logger.info("Aoc2023, Day12 Problem, Part2: {}", problem.part2(inputFile));
    }

    private long part1(String input) {
        var lines = input.split(Constants.EOL);
        var grid = getGrid(lines);

        int n = lines[0].length();
        int m = lines.length;

        // Move to North
        for (var i = 0; i < n; i++) {
            for (var j = 0; j < m; j++) {
                var point = new Point(i, j);
                var charAt = grid.get(point);

                if (charAt.equals('O')) {
                    var lastFree = firstFreeSpot(grid, Coordinate.N, point);
                    if (lastFree != null) {
                        grid.put(lastFree, 'O');
                        grid.put(point, '.');
                    }
                }
            }
        }


        var sum = 0L;

        for (var a : grid.entrySet()) {
            if (a.getValue() == 'O') {
                sum += m - a.getKey().y();
            }
        }

        return sum;
    }

    private long part2(String input) {
        var lines = input.split(Constants.EOL);
        var grid = getGrid(lines);

        int n = lines[0].length();
        int m = lines.length;

        var memory = new HashMap<HashMap<Point, Character>, Integer>();
        boolean enableMemory = true;
        int cycles = 1000000000;

        for (var cycle = 1; cycle <= cycles; cycle++) {
            // Rotate North
            for (var i = 0; i < n; i++) {
                for (var j = 0; j < m; j++) {
                    var point = new Point(i, j);
                    var charAt = grid.get(point);

                    if (charAt == 'O') {
                        var lastFree = firstFreeSpot(grid, Coordinate.N, point);
                        if (lastFree != null) {
                            grid.put(lastFree, 'O');
                            grid.put(point, '.');
                        }
                    }
                }
            }

            // Rotate West
            for (var i = 0; i < m; i++) {
                for (var j = 0; j < n; j++) {
                    var point = new Point(j, i);
                    var charAt = grid.get(point);

                    if (charAt == 'O') {
                        var lastFree = firstFreeSpot(grid, Coordinate.W, point);
                        if (lastFree != null) {
                            grid.put(lastFree, 'O');
                            grid.put(point, '.');
                        }
                    }
                }
            }

            // Rotate South
            for (var i = 0; i < n; i++) {
                for (var j = m - 1; j >= 0; j--) {
                    var point = new Point(i, j);
                    var charAt = grid.get(point);

                    if (charAt == 'O') {
                        var lastFree = firstFreeSpot(grid, Coordinate.S, point);
                        if (lastFree != null) {
                            grid.put(lastFree, 'O');
                            grid.put(point, '.');
                        }
                    }
                }
            }

            // East tilt
            for (var i = 0; i < m; i++) {
                for (var j = n - 1; j >= 0; j--) {
                    var point = new Point(j, i);
                    var charAt = grid.get(point);

                    if (charAt == 'O') {
                        var lastFree = firstFreeSpot(grid, Coordinate.E, point);
                        if (lastFree != null) {
                            grid.put(lastFree, 'O');
                            grid.put(point, '.');
                        }
                    }
                }
            }

            if (enableMemory) {
                if (memory.containsKey(grid)) {
                    // Get the index where the grid exist
                    var firstSeenIndex = memory.get(grid);

                    // Determine the repeating window -> after how many iterations you obtain the same grid.
                    var repeatingWindow = cycle - firstSeenIndex;

                    // Getting the number of jumpCycles
                    int jumpCycles = (cycles - firstSeenIndex) % repeatingWindow;

                    // Obtain the new cycle index.
                    cycle = cycles - jumpCycles;
                    enableMemory = false;
                } else {
                    memory.put(new HashMap<>(grid), cycle);
                }
            }
        }

        var sum = 0L;

        for (var a : grid.entrySet()) {
            if (a.getValue() == 'O') {
                sum += m - a.getKey().y();
            }
        }

        return sum;
    }

    private Map<Point, Character> getGrid(String[] lines) {
        var grid = new HashMap<Point, Character>();

        for (var y = 0; y < lines.length; y++) {
            var line = lines[y];

            for (var x = 0; x < line.length(); x++) {
                grid.put(new Point(x, y), line.charAt(x));
            }
        }

        return grid;
    }

    private Point firstFreeSpot(Map<Point, Character> grid, Coordinate direction, Point point) {
        Point lastFree = null;
        var previousPoint = point;

        while (true) {
            previousPoint = switch (direction) {
                case N -> previousPoint.north();
                case S -> previousPoint.south();
                case E -> previousPoint.east();
                case W -> previousPoint.west();
            };
            var charAt = grid.getOrDefault(previousPoint, null);
            if (charAt == null || charAt != '.') {
                break;
            }

            lastFree = previousPoint;
        }

        return lastFree;

    }
}

record Point(int x, int y) {
    public Point north() {
        return new Point(x, y - 1);
    }

    public Point south() {
        return new Point(x, y + 1);
    }

    public Point east() {
        return new Point(x + 1, y);
    }

    public Point west() {
        return new Point(x - 1, y);
    }

    public List<Point> getAdjacentPoints() {
        return List.of(
                new Point(x - 1, y),
                new Point(x + 1, y),
                new Point(x, y - 1),
                new Point(x, y + 1)
        );
    }


    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }
}

enum Coordinate {
    N, W, E, S
}

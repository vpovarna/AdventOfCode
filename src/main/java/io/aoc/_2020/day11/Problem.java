package io.aoc._2020.day11;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Problem {
    public static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 11, "input.txt");
        logger.info("Aoc2020, Day10 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day10 Problem, Part2: {}", problem.part2(input));
    }

    private long part1(String input) {
        var grid = parseInput(input);
        var result = run(grid);
        var nrOfChanges = Integer.MAX_VALUE;

        while (nrOfChanges > 0) {
            result = run(result.grid());
            nrOfChanges = result.nrOfChanges();
        }

        return countOccupiedSeats(result.grid());
    }

    private int part2(String input) {
        return -1;
    }

    private long countOccupiedSeats(Map<Coordinate, String> grid) {
        return grid.values().stream()
                .filter(value -> value.equals("#"))
                .count();
    }


    private void printGrid(Map<Coordinate, String> grid) {
        var n = 10;
        var toPrintGrid = new String[n][n];

        for(var entry : grid.entrySet()) {
            var coordinate = entry.getKey();
            toPrintGrid[coordinate.x()][coordinate.y()] = entry.getValue();
        }

        for (var i = 0; i< n; i++) {
            for (var j= 0; j < n; j++) {
                System.out.print(toPrintGrid[i][j]);
            }
            System.out.println();
        }
    }

    private Result run(Map<Coordinate, String> grid) {
        final Map<Coordinate, String> tmpGrid = new HashMap<>();
        var nrOfSeatChanged = 0;

        for (var entry : grid.entrySet()) {
            var value = entry.getValue();
            var coordinate = entry.getKey();

            if (value.equals("L")) {
                var occupiedSeatsAround = getOccupiedSeatsAround(grid, coordinate);
                if (occupiedSeatsAround == 0) {
                    tmpGrid.put(coordinate, "#");
                    nrOfSeatChanged++;
                    continue;
                }
            }
            if (value.equals("#")) {
                var occupiedSeatsAround = getOccupiedSeatsAround(grid, coordinate);
                if (occupiedSeatsAround >= 4) {
                    tmpGrid.put(coordinate, "L");
                    nrOfSeatChanged++;
                    continue;
                }
            }

            tmpGrid.put(coordinate, entry.getValue());

        }

        return new Result(tmpGrid, nrOfSeatChanged);
    }

    private long getOccupiedSeatsAround(Map<Coordinate, String> grid, Coordinate coordinate) {
        return neighbours(coordinate).stream()
                .filter(grid::containsKey)
                .map(grid::get)
                .filter(s -> s.equals("#"))
                .count();
    }

    private List<Coordinate> neighbours(Coordinate coordinate) {
        return List.of(
                new Coordinate(coordinate.x() - 1, coordinate.y()),
                new Coordinate(coordinate.x() - 1, coordinate.y() - 1),
                new Coordinate(coordinate.x(), coordinate.y() - 1),
                new Coordinate(coordinate.x() + 1, coordinate.y() - 1),
                new Coordinate(coordinate.x() + 1, coordinate.y()),
                new Coordinate(coordinate.x() + 1, coordinate.y() + 1),
                new Coordinate(coordinate.x(), coordinate.y() + 1),
                new Coordinate(coordinate.x() - 1, coordinate.y() + 1)
        );
    }

    private HashMap<Coordinate, String> parseInput(String input) {
        var lines = input.split(Constants.EOL);

        var grid = new HashMap<Coordinate, String>();
        for (var i = 0; i < lines.length; i++) {
            var currentLine = lines[i].trim();
            var chars = currentLine.toCharArray();
            for (var j = 0; j < chars.length; j++) {
                grid.put(new Coordinate(i, j), String.valueOf(chars[j]));
            }
        }
        return grid;
    }

}

record Coordinate(int x, int y) {
}

record Result(Map<Coordinate, String> grid, int nrOfChanges) {}
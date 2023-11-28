package io.aoc._2020.day11;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Problem {
    public static final Logger logger = LoggerFactory.getLogger(Problem.class);

    private final List<Coordinate> directions = List.of(
            new Coordinate(-1, 0),
            new Coordinate(1, 0),
            new Coordinate(0, -1),
            new Coordinate(-1, -1),
            new Coordinate(0, 1),
            new Coordinate(-1, 1),
            new Coordinate(1, -1),
            new Coordinate(1, 1)
    );

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

    private long part2(String input) {
        var grid = parseInput(input);

        var result = runPart2(grid);
        var nrOfChanges = Integer.MAX_VALUE;

        while (nrOfChanges > 0) {
            result = runPart2(result.grid());
            nrOfChanges = result.nrOfChanges();
        }

        return countOccupiedSeats(result.grid());
    }

    private Result run(Map<Coordinate, String> grid) {
        final Map<Coordinate, String> tmpGrid = new HashMap<>();
        var nrOfSeatChanged = 0;

        for (var entry : grid.entrySet()) {
            var value = entry.getValue();
            var coordinate = entry.getKey();

            if (value.equals("L")) {
                var occupiedSeatsAround = getOccupiedSeatsAroundPart1(grid, coordinate);
                if (occupiedSeatsAround == 0) {
                    tmpGrid.put(coordinate, "#");
                    nrOfSeatChanged++;
                    continue;
                }
            }
            if (value.equals("#")) {
                var occupiedSeatsAround = getOccupiedSeatsAroundPart1(grid, coordinate);
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

    private Result runPart2(Map<Coordinate, String> grid) {
        final Map<Coordinate, String> tmpGrid = new HashMap<>();
        var nrOfSeatChanged = 0;


        var lengthX = grid.keySet().stream().map(Coordinate::x).max(Comparator.naturalOrder()).get() + 1;
        var lengthY = grid.keySet().stream().map(Coordinate::y).max(Comparator.naturalOrder()).get() + 1;

        for (var entry : grid.entrySet()) {
            var value = entry.getValue();
            var coordinate = entry.getKey();

            if (value.equals("L")) {
                var occupiedSeatsAround = getOccupiedSeatsPart2(coordinate, grid, lengthX, lengthY);
                if (occupiedSeatsAround == 0) {
                    tmpGrid.put(coordinate, "#");
                    nrOfSeatChanged++;
                    continue;
                }
            }
            if (value.equals("#")) {
                var occupiedSeatsAround = getOccupiedSeatsPart2(coordinate, grid, lengthX, lengthY);
                if (occupiedSeatsAround >= 5) {
                    tmpGrid.put(coordinate, "L");
                    nrOfSeatChanged++;
                    continue;
                }
            }

            tmpGrid.put(coordinate, entry.getValue());

        }

        return new Result(tmpGrid, nrOfSeatChanged);
    }

    private long countOccupiedSeats(Map<Coordinate, String> grid) {
        return grid.values().stream()
                .filter(value -> value.equals("#"))
                .count();
    }

    private long getOccupiedSeatsAroundPart1(Map<Coordinate, String> grid, Coordinate coordinate) {
        return neighbours(coordinate).stream()
                .filter(grid::containsKey)
                .map(grid::get)
                .filter(s -> s.equals("#"))
                .count();
    }

    private List<Coordinate> neighbours(Coordinate coordinate) {
        return directions.stream()
                .map(direction -> new Coordinate(
                        coordinate.x() + direction.x(),
                        coordinate.y() + direction.y())
                ).toList();
    }

    private int getOccupiedSeatsPart2(Coordinate coordinate, Map<Coordinate, String> grid, int lengthX, int lengthY) {
        var occupiedSeats = 0;

        for (var direction : directions) {
            if (checkFirstSeat(coordinate, direction, grid, lengthX, lengthY)) {
                occupiedSeats++;
            }
        }

        return occupiedSeats;
    }

    private static boolean checkFirstSeat(Coordinate coordinate, Coordinate direction, Map<Coordinate, String> grid, int lengthX, int lengthY) {
        var coordinateIterator = new CoordinateIterator(coordinate, direction.x(), direction.y(), lengthX, lengthY);
        while (coordinateIterator.hasNext()) {
            var nextCoordinate = coordinateIterator.next();
            if (grid.get(nextCoordinate).equals("L")) {
                break;
            }
            if (grid.get(nextCoordinate).equals("#")) {
                return true;
            }
        }
        return false;
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

record Result(Map<Coordinate, String> grid, int nrOfChanges) {
}

class CoordinateIterator implements Iterator<Coordinate> {
    private final int xDrift;
    private final int yDrift;
    private final int xMaxSize;
    private final int yMaxSize;
    private Coordinate coordinate;


    public CoordinateIterator(Coordinate coordinate, int xDrift, int yDrift, int xMaxSize, int yMaxSize) {
        this.coordinate = coordinate;
        this.xDrift = xDrift;
        this.yDrift = yDrift;
        this.xMaxSize = xMaxSize;
        this.yMaxSize = yMaxSize;
    }

    @Override
    public boolean hasNext() {
        return (coordinate.x() + xDrift) >= 0 &&
                (coordinate.x() + xDrift) < xMaxSize &&
                (coordinate.y() + yDrift) >= 0 &&
                (coordinate.y() + yDrift) < yMaxSize;
    }

    @Override
    public Coordinate next() {
        coordinate = new Coordinate(coordinate.x() + xDrift, coordinate.y() + yDrift);
        return coordinate;
    }
}
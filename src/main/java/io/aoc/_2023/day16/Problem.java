package io.aoc._2023.day16;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2023, 16);
        logger.info("Aoc2023, Day16 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2023, Day16 Problem, Part2: {}", problem.part2(input));
    }

    private int part1(String input) {
        var grid = getGrid(input);
        return calculateEnergized(grid, new Point(0, 0), Direction.R);
    }

    private int part2(String input) {
        var grid = getGrid(input);
        var lines = input.split(Constants.EOL);

        var maxX = lines[0].length() - 1;
        var maxY = lines.length - 1;

        var max = 0;

        for (var x = 0; x <= maxX; x++) {
            grid.values().forEach(Tile::reset);
            max = Math.max(calculateEnergized(grid, new Point(x, 0), Direction.D), max);
            grid.values().forEach(Tile::reset);
            max = Math.max(calculateEnergized(grid, new Point(x, maxY), Direction.U), max);
        }

        for (var y = 0; y <= maxY; y++) {
            grid.values().forEach(Tile::reset);
            max = Math.max(calculateEnergized(grid, new Point(0, y), Direction.R), max);
            grid.values().forEach(Tile::reset);
            max = Math.max(calculateEnergized(grid, new Point(maxX, y), Direction.L), max);
        }

        return max;
    }


    private HashMap<Point, Tile> getGrid(String input) {
        var grid = new HashMap<Point, Tile>();

        var lines = input.split(Constants.EOL);

        for (var i = 0; i < lines.length; i++) {
            var currentLine = lines[i];

            for (var j = 0; j < currentLine.length(); j++) {
                var tile = new Tile();
                var position = new Point(j, i);
                tile.type = currentLine.charAt(j);
                grid.put(position, tile);
            }
        }

        return grid;
    }

    private int calculateEnergized(HashMap<Point, Tile> grid, Point startPosition, Direction startDirection) {
        var queue = new LinkedList<Step>();
        queue.add(new Step(startPosition, startDirection));

        while (!queue.isEmpty()) {
            var step = queue.removeFirst();

            if (!grid.containsKey(step.position())) {
                continue;
            }

            var tile = grid.get(step.position());
            tile.isEnergized = true;

            if (tile.beams.contains(step.direction())) {
                continue;
            }

            tile.beams.add(step.direction());

            switch (tile.type) {
                case '|' -> {
                    switch (step.direction()) {
                        case R, L -> {
                            queue.add(new Step(step.position().up(), Direction.U));
                            queue.add(new Step(step.position().down(), Direction.D));
                        }
                        case U -> queue.add(new Step(step.position().up(), Direction.U));
                        case D -> queue.add(new Step(step.position().down(), Direction.D));
                    }
                }
                case '-' -> {
                    switch (step.direction()) {
                        case R -> queue.add(new Step(step.position().right(), Direction.R));
                        case L -> queue.add(new Step(step.position().left(), Direction.L));
                        case U, D -> {
                            queue.add(new Step(step.position().left(), Direction.L));
                            queue.add(new Step(step.position().right(), Direction.R));
                        }
                    }
                }
                case '/' -> {
                    switch (step.direction()) {
                        case R -> queue.add(new Step(step.position().up(), Direction.U));
                        case L -> queue.add(new Step(step.position().down(), Direction.D));
                        case U -> queue.add(new Step(step.position().right(), Direction.R));
                        case D -> queue.add(new Step(step.position().left(), Direction.L));
                    }
                }
                case '\\' -> {
                    switch (step.direction()) {
                        case R -> queue.add(new Step(step.position().down(), Direction.D));
                        case L -> queue.add(new Step(step.position().up(), Direction.U));
                        case U -> queue.add(new Step(step.position().left(), Direction.L));
                        case D -> queue.add(new Step(step.position().right(), Direction.R));
                    }
                }
                case '.' -> {
                    switch (step.direction()) {
                        case R -> queue.add(new Step(step.position().right(), Direction.R));
                        case L -> queue.add(new Step(step.position().left(), Direction.L));
                        case U -> queue.add(new Step(step.position().up(), Direction.U));
                        case D -> queue.add(new Step(step.position().down(), Direction.D));
                    }
                }
            }
        }

        return grid.values().stream().filter(tile -> tile.isEnergized).toList().size();
    }
}

record Step(Point position, Direction direction) {
}

class Tile {
    public boolean isEnergized;
    public Character type;
    public Point position;
    public HashSet<Direction> beams = new HashSet<>();

    public Tile() {
        this.isEnergized = false;
        this.type = null;
        this.position = null;
    }
    public void reset() {
        this.isEnergized = false;
        this.beams.clear();
    }
}

enum Direction {
    R, L, U, D
}

record Point(int x, int y) {
    public Point right() {
        return new Point(x + 1, y);
    }

    public Point left() {
        return new Point(x - 1, y);
    }

    public Point up() {
        return new Point(x, y - 1);
    }

    public Point down() {
        return new Point(x, y + 1);
    }
}



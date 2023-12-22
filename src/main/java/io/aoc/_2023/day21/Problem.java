package io.aoc._2023.day21;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.List;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2023, 21, "input.txt");
        logger.info("Aoc2020, Day20 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day20 Problem, Part2: {}", problem.part2(input));
    }

    private long part1(String input) {
        var grid = parseInput(input);
        var startingPoint = getStartingPoint(grid);
        return fill(startingPoint, grid, 64);
    }

    // Solution based on: https://www.youtube.com/watch?v=9UOMZSL0JTg&t=364&ab_channel=HyperNeutrino
    private long part2(String input) {
        var lines = input.split(Constants.EOL);
        var grid = parseInput(input);
        var startingPoint = getStartingPoint(grid);
        var sr = startingPoint.x();
        var sc = startingPoint.y();

        var size = lines.length;
        var steps = 26501365;

        assert lines.length == lines[0].length();
        assert steps % size == size / 2;

        var gridWidth = steps / size - 1;

        var odd = (long) Math.pow((((gridWidth / 2) * 2) + 1), 2);
        var even = (long) Math.pow(((gridWidth + 1) / 2 * 2), 2);

        var oddPoints = fill(new Point(sr, sc), grid,size * 2 + 1);
        var evenPoints = fill(new Point(sr, sc), grid,size * 2);

        var cornerT = fill(new Point(size - 1, sc), grid, size - 1);
        var cornerR = fill(new Point(sr, 0), grid, size - 1);
        var cornerB = fill(new Point(0, sc), grid, size - 1);
        var cornerL = fill(new Point(sr, size - 1), grid, size - 1);

        var smallTr = fill(new Point(size - 1, 0), grid, size / 2 - 1);
        var smallTl = fill(new Point(size - 1, size - 1), grid, size / 2 - 1);
        var smallBr = fill(new Point(0, 0), grid, size / 2 - 1);
        var smallBl = fill(new Point(0, size - 1), grid, size / 2 - 1);

        var largeTr = fill(new Point(size - 1, 0), grid,size * 3 / 2 - 1);
        var largeTl = fill(new Point(size - 1, size - 1), grid, size * 3 / 2 - 1);
        var largeBr = fill(new Point(0, 0), grid, size * 3 / 2 - 1);
        var largeBl = fill(new Point(0, size - 1), grid, size * 3 / 2 - 1);

        return  odd * oddPoints +
                even * evenPoints +
                cornerT + cornerR + cornerB + cornerL +
                (gridWidth + 1) * (smallTr + smallTl + smallBr + smallBl) +
                gridWidth * (largeTr + largeTl + largeBr + largeBl);
    }


    private long fill(Point startingPoint, Map<Point, Character> grid, int count) {
        var ans = new HashSet<Point>();
        var seen = new HashSet<Point>();
        var queue = new ArrayDeque<Step>();

        seen.add(startingPoint);
        queue.add(new Step(startingPoint, count));

        while (!queue.isEmpty()) {
            var currentStep = queue.pop();

            int steps = currentStep.count();
            if (steps % 2 == 0) {
                ans.add(currentStep.point());
            }

            if (steps == 0) {
                continue;
            }

            var currentPointNeighbours = currentStep.point().getAllNeighbours();
            for (var pointNeighbours : currentPointNeighbours) {
                if (!grid.containsKey(pointNeighbours) || grid.get(pointNeighbours) == '#' || seen.contains(pointNeighbours)) {
                    continue;
                }

                seen.add(pointNeighbours);
                queue.add(new Step(pointNeighbours, steps - 1));
            }
        }

        return ans.size();
    }

    private Map<Point, Character> parseInput(String string) {
        var lines = string.split(Constants.EOL);
        var grid = new HashMap<Point, Character>();
        for (var i = 0; i < lines.length; i++) {
            var currentLine = lines[i].toCharArray();
            for (var j = 0; j < currentLine.length; j++) {
                grid.put(new Point(i, j), currentLine[j]);
            }
        }

        return grid;
    }

    private Point getStartingPoint(Map<Point, Character> grid) {
        return grid.entrySet()
                .stream()
                .filter(e -> e.getValue() == 'S')
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid input. No starting point found!"));
    }

}

record Step(Point point, int count) {
}

record Point(int x, int y) {
    public List<Point> getAllNeighbours() {
        return List.of(
                new Point(x + 1, y),
                new Point(x - 1, y),
                new Point(x, y + 1),
                new Point(x, y - 1)
        );
    }
}

package io.aoc._2023.day21;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);
    public static final String BROADCASTER = "broadcaster";

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2023, 21, "input.txt");
        logger.info("Aoc2020, Day20 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day20 Problem, Part2: {}", problem.part2(input));
    }

    private int part1(String input) {
        var grid = parseInput(input);
        var startingPoint = getStartingPoint(grid);

        var ans = new HashSet<Point>();
        var seen = new HashSet<Point>();
        var queue = new ArrayDeque<Step>();

        seen.add(startingPoint);
        queue.add(new Step(startingPoint, 64));

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
                queue.add(new Step(pointNeighbours, steps -1));
            }
        }

        return ans.size();
    }

    private int part2(String input) {
        return 0;
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

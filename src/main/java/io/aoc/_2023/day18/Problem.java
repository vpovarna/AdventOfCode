package io.aoc._2023.day18;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Map;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2023, 18, "input.txt");
        logger.info("Aoc2020, Day17 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day17 Problem, Part2: {}", problem.part2(input));
    }

    // https://www.youtube.com/watch?v=bGWK76_e-LM&t=359s&ab_channel=HyperNeutrino
    private int part1(String input) {
        var lines = input.split(Constants.EOL);
        var startingPoint = new Point(0, 0);
        var boundaryPoints = 0;

        var points = new LinkedList<Point>();
        points.add(startingPoint);

        for (var line : lines) {
            var parts = line.split(Constants.EMPTY_STRING);
            var direction = parts[0];

            var n = Integer.parseInt(parts[1]);
            var directionPoints = getDirections(direction);
            var currentPoint = points.getLast();
            boundaryPoints += n;

            points.add(new Point(currentPoint.r() + directionPoints.r() * n, currentPoint.c() + directionPoints.c() * n));
        }

        var lastPoint = points.getLast();
        var startPoint = points.getFirst();
        points.addFirst(lastPoint);
        points.addLast(startPoint);

        var area = 0;

        // Shoelace formula = https://en.wikipedia.org/wiki/Shoelace_formula
        for (var i = 1; i < points.size() - 1; i++) {
            area += points.get(i).r() * (points.get((i - 1)).c() - points.get((i + 1)).c());
        }
        area = Math.abs(area)/2;

        // Pick's theorem = https://en.wikipedia.org/wiki/Pick%27s_theorem
        var i = area - (boundaryPoints / 2) + 1;
        return i + boundaryPoints;
    }

    private int part2(String input) {
        var lines = input.split(Constants.EOL);
//        var startingPoint = new Point(0, 0);
//        var boundaryPoints = 0;
//
//        var points = new LinkedList<Point>();
//        points.add(startingPoint);

        for (var line : lines) {
            var parts = line.split(Constants.EMPTY_STRING);
            var direction = parts[0];
        }
        return 0;
    }


    private Point getDirections(String direction) {
        var directions = Map.of(
                "U", new Point(-1, 0),
                "D", new Point(1, 0),
                "L", new Point(0, -1),
                "R", new Point(0, 1)
        );
        return directions.get(direction);
    }

}

record Point(int r, int c) {
    @Override
    public String toString() {
       return  "(" + r + ", " + c + ")";
    }
}

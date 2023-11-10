package io.aoc._2020.day03;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 3, "input.txt");
        logger.info("Aoc2020, Day3 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day3 Problem, Part2: {}", problem.part2(input));
    }

    public int part1(String input) {
        return calculateNrOfTrees(input, new Slope(1, 3));
    }

    public long part2(String input) {
        var slopes = List.of(
                new Slope(1, 1),
                new Slope(1, 3),
                new Slope(1, 5),
                new Slope(1, 7),
                new Slope(2, 1)
        );


        var trees =  slopes.stream()
                .map(slope -> calculateNrOfTrees(input, slope))
                .toList();

        var total = 1L;
        for (var treeNr : trees ) {
            total = total * treeNr;
        }

        return total;
    }

    private static int calculateNrOfTrees(String input, Slope slope) {
        var lines = Arrays.stream(input.split(Constants.EOL)).toList();
        var trees = parseInput(lines);

        var bottomDown = lines.size() - 1;
        var maxLeftCoordinate = lines.get(0).length();

        var nrOfTrees = 0;
        var currentPoint = new Point(0, 0);

        while (true) {
            if (currentPoint.x() == bottomDown) {
                return nrOfTrees;
            }
            currentPoint = currentPoint.move(maxLeftCoordinate - 1, slope);
            if (trees.contains(currentPoint)) {
                nrOfTrees += 1;
            }
        }
    }

    private static HashSet<Point> parseInput(List<String> lines) {
        var trees = new HashSet<Point>();

        for (var i = 0; i < lines.size(); i++) {
            var currentLine = lines.get(i);
            for (var j = 0; j < currentLine.length(); j++) {
                if (currentLine.charAt(j) == '#') {
                    trees.add(new Point(i, j));
                }
            }
        }

        return trees;
    }
}

record Point(int x, int y) {
    public Point move(int v, Slope slope) {
        return new Point(x + slope.xDiff(), (y + slope.yDiff()) % v);
    }
}

record Slope(int xDiff, int yDiff){}
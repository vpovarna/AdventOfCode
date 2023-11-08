package io.aoc._2022.day09;

import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        final Problem problem = new Problem();
        final String input = Utils.readInputFileAsString(2022, 9, "input.txt");
        logger.info("Aoc2022, Day9 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2022, Day9 Problem, Part2: {}", problem.part2(input));
    }

    public int part1(String input) {
        var moves = input.lines().map(Move::pars).toList();
        return moveRope(moves, initKnots(2));
    }

    private int part2(String input) {
        var moves = input.lines().map(Move::pars).toList();
        return moveRope(moves, initKnots(10));
    }

    private Coord[] initKnots(int length) {
        var knots = new Coord[length];
        for (int i = 0; i < length; i++) {
            knots[i] = new Coord(0, 0);
        }
        return knots;
    }

    private int moveRope(List<Move> moves, Coord[] knots) {
        var tailVisited = new HashSet<>();
        tailVisited.add(knots[knots.length - 1]);
        for (var move : moves) {
            for (var i = 0; i < move.count(); ++i) {
                knots[0] = knots[0].moveOneStep(move);
                for (int j = 1; j < knots.length; ++j) {
                    knots[j] = calcTail(knots[j - 1], knots[j]);
                }
                tailVisited.add(knots[knots.length - 1]);
            }
        }
        return tailVisited.size();
    }

    private static Coord calcTail(Coord head, Coord tail) {
        if (tail.isTouching(head)) {
            return tail;
        } else if (tail.y() == head.y()) {
            return tail.moveOneStep(new Move(tail.x() < head.x() ? 1 : -1, 0, 0));
        } else if (tail.x() == head.x()) {
            return tail.moveOneStep(new Move(0, tail.y() < head.y() ? 1 : -1, 0));
        } else if (tail.y() < head.y()) {
            return tail.moveOneStep(new Move(tail.x() < head.x() ? 1 : -1, 1, 0));
        } else {
            return tail.moveOneStep(new Move(tail.x() < head.x() ? 1 : -1, -1, 0));
        }
    }

    private static final record Move(int xDif, int yDif, int count) {
        static Move pars(String line) {
            var parts = line.split(" ");
            var steps = Integer.parseInt(parts[1]);
            return switch (parts[0]) {
                case "U" -> new Move(0, -1, steps);
                case "D" -> new Move(0, 1, steps);
                case "L" -> new Move(-1, 0, steps);
                case "R" -> new Move(1, 0, steps);
                default -> throw new IllegalStateException("What to do with: " + parts[0]);
            };
        }
    }

    private static final record Coord(int x, int y) {
        boolean isTouching(Coord coord) {
            return Math.abs(x - coord.x) <= 1 && Math.abs(y - coord.y) <= 1;
        }

        Coord moveOneStep(Move move) {
            return new Coord(x + move.xDif(), y + move.yDif());
        }
    }
}
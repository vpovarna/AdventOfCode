package io.aoc._2022.day12;

import io.aoc._2022.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public int part1(String input) {
        var lines = input.lines().toList();
        return lengthFrom(locate(lines, "S"), lines);
    }

    public int part2(String input) {
        var lines = input.lines().toList();
        return collectAs(lines)
                .parallelStream()
                .mapToInt(start -> lengthFrom(start, lines))
                .min()
                .orElseThrow();
    }

    public Coord locate(List<String> lines, String ch) {
        for (int y = 0; y < lines.size(); y++) {
            int x = lines.get(y).indexOf(ch);
            if (x != -1) {
                return new Coord(x, y);
            }
        }
        throw new NoSuchElementException("can not locate " + ch);
    }

    private int lengthFrom(Coord start, List<String> lines) {
        var visited = new HashSet<>();
        var queue = new ArrayDeque<Path>();
        queue.add(new Path(start, 0));

        while (!queue.isEmpty() && queue.peek().end().value(lines) != 'E') {
            var next = queue.poll();
            if (next == null) {
                throw new IllegalArgumentException("Null element in the list");
            }
            next.end().neighbours().forEach(coord -> {
                if (coord.isValid(lines) && !visited.contains(coord) && coord.isAvailableFrom(next.end(), lines)) {
                    queue.add(new Path(coord, next.stepCount + 1));
                    visited.add(coord);
                }
            });
        }

        if (queue.isEmpty()) {
            return Integer.MAX_VALUE;
        }
        return queue.poll().stepCount();
    }

    private List<Coord> collectAs(List<String> lines) {
        final List<Coord> result= new ArrayList<>();
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x< line.length(); x++) {
                if (line.charAt(x) == 'a') {
                    result.add(new Coord(x, y));
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2022,12, "input.txt");
        logger.info("Aoc2022, Day12 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2022, Day12 Problem, Part2: {}", problem.part2(input));
    }

    private record Coord(int x, int y) {
        List<Coord> neighbours() {
            return List.of(new Coord(x - 1, y), new Coord(x + 1, y), new Coord(x, y - 1), new Coord(x, y + 1));
        }

        boolean isValid(List<String> line) {
            return y >= 0 && y < line.size() && x >= 0 && x < line.get(y).length();
        }

        char value(List<String> lines) {
            return lines.get(y).charAt(x);
        }

        boolean isAvailableFrom(Coord from, List<String> lines) {
            char start = from.value(lines);
            char to = value(lines);
            if (start == 'S') {
                start = 'a';
            }
            if (to == 'E') {
                to = 'z';
            }
            return to <= start + 1;
        }
    }

    private record Path(Coord end, int stepCount) {
    }
}

package io.aoc._2022.day12;

import io.aoc._2022.utils.Utils;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

public class Problem {

    public int part1(String input) {
        var lines = input.lines().toList();
        return lengthFrom(locate(lines, "S"), lines);
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
            next.end().neighbours().forEach(n -> {
                if (n.isValid(lines) && !visited.contains(n) && n.isAvailableFrom(next.end(), lines)) {
                    queue.add(new Path(n, next.stepCount + 1));
                    visited.add(n);
                }
            });
        }

        if (queue.isEmpty()) {
            return Integer.MAX_VALUE;
        }
        return queue.poll().stepCount();
    }

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(12, "input.txt");
        System.out.println(problem.part1(input));
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
            return (int) to <= (int) start + 1;
        }
    }

    private record Path(Coord end, int stepCount) {
    }
}

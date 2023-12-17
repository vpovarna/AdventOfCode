package io.aoc._2023.day17;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class Problem {

    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2023, 17);
        logger.info("Aoc2023, Day16 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2023, Day16 Problem, Part2: {}", problem.part2(input));
    }

    private int part1(String input) {
        var grid = getGrid(input);
        return run(grid);
    }

    private int part2(String input) {
        return 0;
    }

    // Dijkstra's Algorithm (https://www.programiz.com/dsa/dijkstra-algorithm)
    private int run(List<List<Integer>> grid) {
        var queue = new PriorityQueue<>(Comparator.comparing(Player::hl));
        queue.add(new Player(0, new Stats(new Point(0, 0), new Direction(0, 0), 0)));

        var seen = new HashSet<Stats>();

        while (!queue.isEmpty()) {
            var player = queue.poll();
            var playerStats = player.stats();
            var currentPoint = playerStats.point();

            var currentDirection = playerStats.direction();

            // grid size
            var m = grid.size();
            var n = grid.getFirst().size();

            // if we are at the destination
            if (currentPoint.r() == m - 1 && currentPoint.c() == n - 1) {
                return player.hl();
            }

            // we don't want to add heatList since, in a loop, heatLost is always incremented, so we can't prevent loops
            if (seen.contains(playerStats)) {
                continue;
            }

            seen.add(playerStats);

            // && (currentDirection.r() != 0 || currentDirection.c() != 0) might not count
            if (playerStats.n() < 3 && (currentDirection.r() != 0 || currentDirection.c() != 0)) {
                // we can continue going in the same direction
                var nr = currentPoint.r() + currentDirection.r();
                var nc = currentPoint.c() + currentDirection.c();

                // check if the current player point is out of the greed.
                if ((0 <= nr && nr < m) && (0 <= nc && nc < m)) {
                    var stats = new Stats(new Point(nr, nc), currentDirection, playerStats.n() + 1);
                    queue.add(new Player(player.hl() + grid.get(nr).get(nc), stats));
                }
            }

            // Check all directions
            for (var direction : getAllPossibleDirections()) {
                if ((direction.r() != currentDirection.r() || direction.c() != currentDirection.c()) && (direction.r() != -currentDirection.r() || direction.c() != -currentDirection.c())) {
                    var nr = currentPoint.r() + direction.r();
                    var nc = currentPoint.c() + direction.c();

                    if ((nr >= 0 && nr < m) && (nc >= 0 && nc < m)) {
                        var stats = new Stats(new Point(nr, nc), direction, 1);
                        var heatLoss = player.hl() + grid.get(nr).get(nc);
                        queue.add(new Player(heatLoss, stats));
                    }

                }
            }

        }
        return -1;
    }

    private List<Direction> getAllPossibleDirections() {
        return List.of(new Direction(0, 1), new Direction(1, 0), new Direction(0, -1), new Direction(-1, 0));
    }

    private List<List<Integer>> getGrid(String input) {
        return Arrays.stream(input.split(Constants.EOL))
                .map(line ->
                        Arrays.stream(line.split(""))
                                .map(Integer::parseInt)
                                .collect(Collectors.toList())
                )
                .collect(Collectors.toList());
    }
}

record Direction(int r, int c) {
}

record Point(int r, int c) {
}

record Stats(Point point, Direction direction, int n) {
}

record Player(int hl, Stats stats) {
}

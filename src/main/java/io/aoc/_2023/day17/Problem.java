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
        queue.add(new Player(0, new Stats(new Point(0, 0), 0, 0, 0)));

        var seen = new HashSet<Stats>();

        while (!queue.isEmpty()) {
            var player = queue.poll();
            var playerStats = player.stats();
            var currentPoint = playerStats.point();

            // if we are at the destination
            if (currentPoint.r() == grid.size() - 1 && currentPoint.c() == grid.getFirst().size() - 1) {
                return player.hl();
            }

            // we don't want to add heatList since, in a loop, heatLost is always incremented
            if (seen.contains(playerStats)) {
                continue;
            }

            seen.add(playerStats);

            if (playerStats.n() < 3 && (playerStats.dr() != 0 || playerStats.dc() != 0)) {
                // we can continue going in the same direction
                var nr = currentPoint.r() + playerStats.dr();
                var nc = currentPoint.c() + playerStats.dc();
                // check if the current player point is out of the greed.
                if ((nr >= 0 && nr < grid.size()) && (nc >= 0 && nc < grid.size())) {
                    queue.add(new Player(player.hl() + grid.get(nr).get(nc), new Stats(new Point(nr, nc), playerStats.dr(), playerStats.dc(), playerStats.n() + 1)));
                }
            }

            // Check all directions
            for (var neighbour : getNeighbours()) {
                var ndr = neighbour.r();
                var ndc = neighbour.c();
                if ((ndr != playerStats.dr() || ndc != playerStats.dc()) && (ndr != -playerStats.dr() || ndc != -playerStats.dc())) {
                    var nr = currentPoint.r() + ndr;
                    var nc = currentPoint.c() + ndc;
                    if ((nr >= 0 && nr < grid.size()) && (nc >= 0 && nc < grid.size())) {
                        queue.add(new Player(player.hl() + grid.get(nr).get(nc), new Stats(new Point(nr, nc), ndr, ndc, 1)));
                    }

                }
            }

        }
        return -1;
    }

    private List<Point> getNeighbours() {
        return List.of(new Point(0, 1), new Point(1, 0), new Point(0, -1), new Point(-1, 0));
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

record Point(int r, int c) {
}

record Stats(Point point, int dr, int dc, int n) {
}

record Player(int hl, Stats stats) {
}

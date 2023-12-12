package io.aoc._2023.day10;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//TODO: Refactor needed
public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var inputFile = Utils.readInputFileAsString(2023, 10);

        var problem = new Problem();
        logger.info("Aoc2023, Day10 Problem, Part1: {}", problem.part1(inputFile));
        logger.info("Aoc2023, Day10 Problem, Part2: {}", problem.part2(inputFile));
    }

    private int part1(String input) {
        var pipeMap = constructPipeMap(input);
        var loopPipes = constructLoopPipes(pipeMap);
        return loopPipes.values()
                .stream()
                .map(p -> p.distanceFromStart)
                .max(Integer::compareTo)
                .orElseThrow();
    }

    private int part2(String input) {
        var pipeMap = constructPipeMap(input);
        var loopPipes = constructLoopPipes(pipeMap);
        var count = 0;

        for (var y = 1; y < pipeMap.lines - 1; y++) {
            for (var x = 1; x < pipeMap.columns - 1; x++) {
                if (loopPipes.containsKey(new Point(x, y))) {
                    continue;
                }

                // Check if there is odd number of pipes cross to the right, if so - we're inside the loop
                var loopPipeCrossingCount = 0;
                var insidePipe = false;
                CompassDirection pipeCameFrom = null;

                for (var i = x + 1; i < pipeMap.columns; i++) {
                    var pipe = pipeMap.grid.get(new Point(i, y));

                    if (pipe == null || !loopPipes.containsKey(pipe.location)) {
                        continue;
                    }

                    if (pipe.type == PipeType.V) {
                        loopPipeCrossingCount++;
                        continue;
                    }

                    // Here we go "inside" the pipe and store which direction it came from
                    if (pipe.type == PipeType.NE || pipe.type == PipeType.SE) {
                        insidePipe = true;
                        pipeCameFrom = pipe.type == PipeType.NE ? CompassDirection.N : CompassDirection.S;
                        continue;
                    }

                    // If we go outside, and it goes in the same direction, it's not counted as crossing
                    if (insidePipe && pipe.type != PipeType.H) {
                        loopPipeCrossingCount += (pipe.type.name().startsWith(pipeCameFrom.name()) ? 0 : 1);
                        insidePipe = false;
                        pipeCameFrom = null;
                    }
                }

                if (loopPipeCrossingCount % 2 != 0) {
                    count++;
                }
            }
        }

        return count;
    }

    private PipeMap constructPipeMap(String input) {
        var lines = input.trim().split(Constants.EOL);
        var grid = new HashMap<Point, Pipe>();
        Point start = null;

        for (var y = 0; y < lines.length; y++) {
            for (var x = 0; x < lines[y].length(); x++) {
                var charAt = lines[y].charAt(x);
                var point = new Point(x, y);

                if (charAt == '.') {
                    continue;
                }

                grid.put(point, switch (charAt) {
                    case 'S' -> new Pipe(null, point, 0);
                    case '|' -> new Pipe(PipeType.V, point, null);
                    case '-' -> new Pipe(PipeType.H, point, null);
                    case 'F' -> new Pipe(PipeType.SE, point, null);
                    case '7' -> new Pipe(PipeType.SW, point, null);
                    case 'L' -> new Pipe(PipeType.NE, point, null);
                    case 'J' -> new Pipe(PipeType.NW, point, null);
                    default -> throw new RuntimeException("Unknown pipe: " + charAt);
                });

                if (charAt == 'S') {
                    start = new Point(x, y);
                }
            }
        }

        return new PipeMap(grid, lines.length, lines[0].length(), start);
    }

    private HashMap<Point, Pipe> constructLoopPipes(PipeMap pipeMap) {
        var startPipe = pipeMap.grid.get(pipeMap.start);
        var queue = new LinkedList<Pipe>();
        queue.add(startPipe);
        var loopPipes = new HashMap<Point, Pipe>();
        loopPipes.put(startPipe.location, startPipe);

        while (!queue.isEmpty()) {
            var pipe = queue.removeFirst();
            loopPipes.put(pipe.location, pipe);

            for (var cp : pipe.connectionPoints().entrySet()) {
                var nextPipe = pipeMap.grid.get(cp.getValue());

                if (nextPipe != null && nextPipe.distanceFromStart == null && nextPipe.connectionPoints().containsKey(cp.getKey().opposite())) {
                    switch (cp.getKey()) {
                        case N -> {
                            pipe.north = nextPipe;
                            nextPipe.south = pipe;
                        }
                        case E -> {
                            pipe.east = nextPipe;
                            nextPipe.west = pipe;
                        }
                        case S -> {
                            pipe.south = nextPipe;
                            nextPipe.north = pipe;
                        }
                        case W -> {
                            pipe.west = nextPipe;
                            nextPipe.east = pipe;
                        }
                        default -> throw new RuntimeException("Unknown direction");
                    }
                    nextPipe.distanceFromStart = pipe.distanceFromStart + 1;
                    queue.add(nextPipe);
                }
            }
        }

        startPipe.resolveTypeByConnections();

        return loopPipes;
    }

    private record PipeMap(HashMap<Point, Pipe> grid, int lines, int columns, Point start) {
    }

    enum PipeType {V, H, SE, SW, NE, NW}

    private static class Pipe {
        PipeType type;
        Integer distanceFromStart;
        Point location;
        Pipe north = null;
        Pipe east = null;
        Pipe south = null;
        Pipe west = null;

        public Pipe(PipeType type, Point location, Integer distanceFromStart) {
            this.distanceFromStart = distanceFromStart;
            this.location = location;
            this.type = type;
        }

        public Map<CompassDirection, Point> connectionPoints() {
            return switch (type) {
                case V -> Map.of(CompassDirection.S, location.south(), CompassDirection.N, location.north());
                case H -> Map.of(CompassDirection.E, location.east(), CompassDirection.W, location.west());
                case SE -> Map.of(CompassDirection.S, location.south(), CompassDirection.E, location.east());
                case SW -> Map.of(CompassDirection.S, location.south(), CompassDirection.W, location.west());
                case NE -> Map.of(CompassDirection.N, location.north(), CompassDirection.E, location.east());
                case NW -> Map.of(CompassDirection.N, location.north(), CompassDirection.W, location.west());
                case null -> Map.of(
                        CompassDirection.N, location.north(), CompassDirection.W, location.west(),
                        CompassDirection.S, location.south(), CompassDirection.E, location.east()
                );
            };
        }

        public void resolveTypeByConnections() {
            if (type == null) {
                if (north != null) {
                    type = south != null ? PipeType.V : (east != null ? PipeType.NE : PipeType.NW);
                } else {
                    type = south != null ? (east != null ? PipeType.SE : PipeType.SW) : PipeType.H;
                }
            }
        }
    }
}

class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point add(Point delta) {
        return new Point(x + delta.x, y + delta.y);
    }

    public List<Point> surroundingPoints() {
        return List.of(
                new Point(x - 1, y - 1),
                new Point(x, y - 1),
                new Point(x + 1, y - 1),
                new Point(x - 1, y),
                new Point(x + 1, y),
                new Point(x - 1, y + 1),
                new Point(x, y + 1),
                new Point(x + 1, y + 1)
        );
    }

    public Point north() {
        return new Point(x, y - 1);
    }

    public Point east() {
        return new Point(x + 1, y);
    }

    public Point south() {
        return new Point(x, y + 1);
    }

    public Point west() {
        return new Point(x - 1, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    public String toString() {
        return getClass().getName() + "[x=" + x + ",y=" + y + "]";
    }
}

enum CompassDirection {
    N, E, S, W, NE, SE, SW, NW;

    public CompassDirection opposite() {
        return switch (this) {
            case N -> S;
            case E -> W;
            case S -> N;
            case W -> E;
            case NE -> SW;
            case SE -> NW;
            case SW -> NE;
            case NW -> SE;
        };
    }
}
package io.aoc._2023.day05;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);
    private static final int NUMBER_OF_MAPS = 7;
    private static final ExecutorService FIXED_THREAD_POOL = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) {
        var inputFile = Utils.readInputFileAsString(2023, 5);
        var problem = new Problem();

        logger.info("Aoc2023, Day5 Problem, Part1: {}", problem.part1(inputFile));
        logger.info("Aoc2023, Day5 Problem, Part2: {}", problem.part2(inputFile));

        FIXED_THREAD_POOL.shutdown();
    }

    private long part1(String input) {
        var almanac = parseInput(input);
        var maps = almanac.mapCoordinates();
        var seeds = almanac.seeds();

        var location = Long.MAX_VALUE;
        for (var seed : seeds) {
            location = Math.min(location, findCoordinate(seed, maps));
        }

        return location;
    }

    private long part2(String input) {
        final long now = System.currentTimeMillis();

        var almanac = parseInput(input);
        var seeds = almanac.seeds();
        var maps = almanac.mapCoordinates();

        final AtomicLong result = new AtomicLong(Long.MAX_VALUE);

        var seedRanges = new ArrayList<SeedRange>();
        for (int i = 0; i < seeds.size(); i += 2) {
            long start = seeds.get(i);
            long len = seeds.get(i + 1);
            seedRanges.add(new SeedRange(start, len));
        }

        seedRanges.sort(Comparator.comparingLong(SeedRange::start));

        var taskThreads = seedRanges.stream()
                .map(seedRange -> new TaskThread(seedRange.start(), seedRange.len(), maps, result))
                .toList();

        var completableFutures = taskThreads.stream()
                .map(runnable -> CompletableFuture.runAsync(runnable, FIXED_THREAD_POOL))
                .toArray(CompletableFuture<?>[]::new);

        isDone(completableFutures);

        final long time = System.currentTimeMillis() - now;
        logger.info("Finished after {} ms ({} s)", time, time / 1000);
        return result.get();
    }

    public static void isDone(CompletableFuture<?>[] completableFutures) {
        var status = false;
        while (!status) {
            status = CompletableFuture.allOf(completableFutures).isDone();
        }
    }

    private Long findCoordinate(Long target, List<MapCoordinates> maps) {
        var currentNum = target;

        for (var map : maps) {
            for (var coordinates : map.coordinates()) {
                if (coordinates.sourceRangeStart() <= currentNum && currentNum < coordinates.sourceRangeStart() + coordinates.count()) {
                    currentNum = coordinates.destRangeStart() + (currentNum - coordinates.sourceRangeStart());
                    break;
                }
            }
        }
        return currentNum;
    }

    private Almanac parseInput(String input) {
        var parts = input.split(Constants.EMPTY_LINE);
        var seeds = extractNumbers(parts[0]);
        var maps = IntStream.range(0, NUMBER_OF_MAPS)
                .mapToObj(mapNumber -> createMap(parts[mapNumber + 1]))
                .toList();

        return new Almanac(seeds, maps);
    }

    private MapCoordinates createMap(String rowMap) {
        List<Coordinates> coordinates = new ArrayList<>();
        String[] lines = rowMap.split(Constants.EOL);

        for (var i = 1; i < lines.length; i++) {
            var numbers = extractNumbers(lines[i]);
            var coordinate = new Coordinates(numbers.get(0), numbers.get(1), numbers.get(2));
            coordinates.add(coordinate);
        }

        return new MapCoordinates(coordinates);
    }

    private List<Long> extractNumbers(String line) {
        List<Long> list = new ArrayList<>();
        Pattern digitRegex = Pattern.compile("\\d+");
        Matcher matched = digitRegex.matcher(line);

        while (matched.find()) {
            list.add(Long.parseLong(matched.group()));
        }

        return list;
    }
}

record Coordinates(long destRangeStart, long sourceRangeStart, long count) {
}

record MapCoordinates(List<Coordinates> coordinates) {
}

record Almanac(List<Long> seeds, List<MapCoordinates> mapCoordinates) {
}

record SeedRange(long start, long len) {}


class TaskThread implements Runnable {
    private final long startSeedNr;
    private final long count;
    private final List<MapCoordinates> maps;
    private final AtomicLong globalMin;

    public TaskThread(long startSeedNr, long count, List<MapCoordinates> maps, AtomicLong globalMin) {
        this.startSeedNr = startSeedNr;
        this.count = count;
        this.maps = maps;
        this.globalMin = globalMin;
    }

    @Override
    public void run() {
        long smallestValue = Long.MAX_VALUE;
        for (var seed = startSeedNr; seed < startSeedNr + count; seed++) {
            if (globalMin.get() > seed) {
                long currentSeed = seed;

                for (var map : maps) {
                    for (var coordinates : map.coordinates()) {
                        if (coordinates.sourceRangeStart() <= currentSeed && currentSeed < coordinates.sourceRangeStart() + coordinates.count()) {
                            currentSeed = coordinates.destRangeStart() + (currentSeed - coordinates.sourceRangeStart());
                            break;
                        }
                    }
                }
                smallestValue = Math.min(smallestValue, currentSeed);
            }
        }
        globalMin.set(Math.min(globalMin.get(), smallestValue));
    }
}
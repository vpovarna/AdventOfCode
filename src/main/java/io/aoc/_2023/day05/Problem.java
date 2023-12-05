package io.aoc._2023.day05;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);
    private static final int NUMBER_OF_MAPS = 7;

    public static void main(String[] args) {
        var inputFile = Utils.readInputFileAsString(2023, 5);
        var problem = new Problem();

        logger.info("Aoc2023, Day5 Problem, Part1: {}", problem.part1(inputFile));
        logger.info("Aoc2023, Day5 Problem, Part2: {}", problem.part2(inputFile));
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

    private int part2(String input) {
        return 0;
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


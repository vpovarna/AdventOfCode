package io.aoc._2020.day13;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class Problem {

    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 13, "input.txt");
        logger.info("Aoc2020, Day13 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day13 Problem, Part2: {}", problem.part2(input));
    }

    private int part1(String input) {
        var schedule = parseInputPart1(input);
        int timeToDepart = schedule.timeToDepart();
        System.out.println(timeToDepart);
        List<Integer> busses = schedule.busses();
        System.out.println(busses);

        var firstBussToDepartNumber = 0;
        var earliestTimeToDepart = Integer.MAX_VALUE;

        for (var bussNr : busses) {
            var n = getEarliestTimeToDepart(bussNr, timeToDepart);
            var bussTimeToDepart = n * bussNr;
            if (bussTimeToDepart < earliestTimeToDepart) {
                firstBussToDepartNumber = bussNr;
                earliestTimeToDepart = bussTimeToDepart;
            }
        }
        return firstBussToDepartNumber * (earliestTimeToDepart - timeToDepart);
    }

    /**
     * The common number of two prime numbers is equal to the product of them.
     * Ex: 3, 7 have the common number = 21
     * <p>
     * The tine will only increase with the stepSize, as long as the (time + index) % buss != 0
     * https://www.youtube.com/watch?v=4_5mluiXF5I&ab_channel=AlvintheProgrammer
     */
    private long part2(String input) {
        var busses = parseInputPart2(input);
        long time = 0L;
        long stepSize = busses.get(0);

        for (var i = 1; i < busses.size(); i++) {
            var buss = busses.get(i);

            while ((time + i) % buss != 0) {
                time += stepSize;
            }

            stepSize *= buss;
        }


        return time;
    }

    private int getEarliestTimeToDepart(int bussNr, int timeToDepart) {
        return (timeToDepart / bussNr) + 1;
    }

    private Schedule parseInputPart1(String input) {
        var lines = input.split(Constants.EOL);
        if (lines.length != 2) {
            throw new IllegalArgumentException("Invalid input");
        }

        var earliestTimeToDepart = Integer.parseInt(lines[0]);
        var busses = Arrays.stream(lines[1].split(","))
                .filter(x -> !x.equals("x"))
                .map(Integer::parseInt)
                .toList();

        return new Schedule(earliestTimeToDepart, busses);
    }

    private List<Integer> parseInputPart2(String input) {
        var lines = input.split(Constants.EOL);
        if (lines.length != 2) {
            throw new IllegalArgumentException("Invalid input");
        }
        return Arrays.stream(lines[1].split(","))
                .map(x -> (x.equals("x")) ? 1 : Integer.parseInt(x))
                .toList();
    }
}

record Schedule(int timeToDepart, java.util.List<Integer> busses) {
}

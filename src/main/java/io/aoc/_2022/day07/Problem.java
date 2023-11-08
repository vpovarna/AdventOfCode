package io.aoc._2022.day07;

import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public long part1(String input, int maxTotalSize) {
        final Directory root = new PuzzleInputParser().parse(input);
        final List<Directory> directories = root.listWithAllSubDirectories();

        return directories.stream()
                .mapToLong(Directory::totalSize)
                .filter(totalSize -> totalSize < maxTotalSize)
                .sum();
    }

    public long part2(String input, long totalSize, long unusedSpaceNeeded) {
        final Directory root = new PuzzleInputParser().parse(input);

        final long free = totalSize - root.totalSize();
        final long needed = unusedSpaceNeeded - free;

        final List<Directory> directories = root.listWithAllSubDirectories();
        Directory smallestDirectory =
                directories.stream()
                        .filter(directory -> directory.totalSize() >= needed)
                        .min(Comparator.comparing(Directory::totalSize))
                        .orElseThrow();
        return smallestDirectory.totalSize();
    }

    public static void main(String[] args) {
        final Problem problem = new Problem();
        final String input = Utils.readInputFileAsString(2022, 7, "input.txt");
        logger.info("Aoc2022, Day7 Problem, Part1: {}", problem.part1(input, 100_000));
        logger.info("Aoc2022, Day7 Problem, Part2: {}", problem.part2(input, 70_000_000, 30_000_000));
    }

}

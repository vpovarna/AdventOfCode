package io.aoc._2022.day06;

import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public int part1(String input) {
        return findMarkerStartIndex(input, 4);
    }

    public int part2(String input) {
        return findMarkerStartIndex(input, 14);
    }

    protected int findMarkerStartIndex(String input, int chunk) {
        final char[] charArray = input.toCharArray();
        if (charArray.length < chunk) {
            throw new IllegalArgumentException("Input string has less then four chars");
        }

        for (int i = 0; i <= charArray.length - chunk; i++) {
            final Set<Character> characters = new HashSet<>();
            for (int j = i; j < i + chunk; j++) {
                characters.add(charArray[j]);
            }
            if (characters.size() == chunk) {
                return i + chunk;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        final Problem problem = new Problem();
        final String input = Utils.readInputFileAsString(2022, 6, "input.txt");
        logger.info("Aoc2022, Day6 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2022, Day6 Problem, Part2: {}", problem.part2(input));
    }
}

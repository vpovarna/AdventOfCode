package io.aoc._2022.day05;

import io.aoc._2022.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class Day05ProblemTest {
    private final String input = Utils.readInputFileAsString(5, "input.txt");
    private static Problem problem;

    @BeforeAll
    public static void initialization() {
        problem = new Problem();
    }

    @Test
    void part1Test() {
        final StringBuilder result = problem.part1(input);
        Assertions.assertEquals("CMZ", result.toString());
    }

    @Test
    void part2Test() {
        final StringBuilder result = problem.part2(input);
        Assertions.assertEquals("MCD", result.toString());
    }
}

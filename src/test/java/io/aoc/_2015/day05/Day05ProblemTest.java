package io.aoc._2015.day05;

import io.aoc._2015.utils.Utils;
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
    public void part1Test() {
        final String result = problem.part1(input);
        Assertions.assertEquals("CMZ", result);
    }

    @Test
    public void part2Test() {
        final String result = problem.part2(input);
        Assertions.assertEquals("MCD", result);
    }
}

package io.aoc._2022.day04;

import io.aoc.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class Day04ProblemTest {
    private final String input = Utils.readInputFileAsString(2022, 4, "input.txt");
    private static Problem problem;

    @BeforeAll
    public static void initialization() {
        problem = new Problem();
    }

    @Test
    void part1Test() {
        final int result = problem.part1(input);
        Assertions.assertEquals(2, result);
    }

    @Test
    void part2Test() {
        final int result = problem.part2(input);
        Assertions.assertEquals(4, result);
    }
}

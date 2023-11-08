package io.aoc._2022.day08;

import io.aoc.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day08ProblemTest {
    private final String input = Utils.readInputFileAsString(2022, 8, "input.txt");

    private static Problem problem;

    @BeforeAll
    static void initialize() {
        problem = new Problem();
    }

    @Test
    void part1Test() {
        final long result = problem.part1(input);
        Assertions.assertEquals(21, result);
    }

    @Test
    void part2Test() {
        final long result = problem.part2(input);
        Assertions.assertEquals(8, result);
    }
}

package io.aoc._2022.day07;

import io.aoc._2022.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day07ProblemTest {
    private final String input = Utils.readInputFileAsString(2022, 7, "input.txt");

    private static Problem problem;

    @BeforeAll
    static void initialize() {
        problem = new Problem();
    }

    @Test
    void part1Test() {
        final long result = problem.part1(input, 300_000);
        Assertions.assertEquals(95437, result);
    }

    @Test
    void part2Test() {
        final long result = problem.part2(input, 70_000_000, 30_000_000);
        Assertions.assertEquals(24933642, result);
    }
}

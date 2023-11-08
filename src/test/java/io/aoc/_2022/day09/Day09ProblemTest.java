package io.aoc._2022.day09;

import io.aoc._2022.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day09ProblemTest {
    private final String input = Utils.readInputFileAsString(2022, 9, "input.txt");

    private static Problem problem;

    @BeforeAll
    static void initialize() {
        problem = new Problem();
    }

    @Test
    void part1Test() {
        final long result = problem.part1(input);
        Assertions.assertEquals(13, result);
    }

}

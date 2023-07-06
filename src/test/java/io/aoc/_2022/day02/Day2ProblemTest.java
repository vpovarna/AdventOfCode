package io.aoc._2022.day02;

import io.aoc._2022.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day2ProblemTest {

    private static Problem problem;

    @BeforeAll
    public static void initialize() {
        problem = new Problem();
    }
    @Test
    void part1Test() {
        final String inputFile = Utils.readInputFileAsString(2, "input.txt");
        final int result = problem.part1(inputFile);
        Assertions.assertEquals(15, result);
    }

    @Test
    void part2Test() {
        final String inputFile = Utils.readInputFileAsString(2, "input.txt");
        final int result = problem.part2(inputFile);
        Assertions.assertEquals(12, result);
    }
}
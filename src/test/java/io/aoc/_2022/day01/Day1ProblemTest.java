package io.aoc._2022.day01;

import io.aoc._2022.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day1ProblemTest {
    private final String inputFile = Utils.readInputFileAsString(1, "input.txt");
    private static Problem problem;

    @BeforeAll
    public static void initialize() {
        problem = new Problem();
    }
    @Test
    void part1Test() {
        int result = problem.part1(inputFile);
        Assertions.assertEquals(24000, result);
    }

    @Test
    void part2Test() {
        int result = problem.part2(inputFile);
        Assertions.assertEquals(45000, result);
    }

}
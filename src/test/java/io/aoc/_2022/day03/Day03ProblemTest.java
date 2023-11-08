package io.aoc._2022.day03;
import io.aoc.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day03ProblemTest {
    private static Problem problem;
    private final String inputFile = Utils.readInputFileAsString(2022, 3, "input.txt");

    @BeforeAll
    public static void initialize() {
        problem = new Problem();
    }

    @Test
    void part1Test() {
        int result = problem.part1(inputFile);
        Assertions.assertEquals(157, result);
    }

    @Test
    void part2Test() {
        int result = problem.part2(inputFile);
        Assertions.assertEquals(70, result);
    }
}
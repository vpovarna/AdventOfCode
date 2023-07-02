package io.aoc._2015.day03;
import io.aoc._2015.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day03ProblemTest {
    private static Problem problem;
    private final String inputFile = Utils.readInputFileAsString(3, "input.txt");

    @BeforeAll
    public static void initialize() {
        problem = new Problem();
    }

    @Test
    public void part1Test() {
        int result = problem.part1(inputFile);
        Assertions.assertEquals(157, result);
    }

    @Test
    public void part2Test() {
        int result = problem.part2(inputFile);
        Assertions.assertEquals(70, result);
    }
}
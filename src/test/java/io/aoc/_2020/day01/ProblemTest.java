package io.aoc._2020.day01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProblemTest {
    private static String INPUT = """
                1721
                979
                366
                299
                675
                1456
                """;
    @Test
    void part1Test() {
        var problem = new Problem();

        var result = problem.part1(INPUT);
        Assertions.assertEquals(514579, result);
    }

    @Test
    void part2Test() {
        var problem = new Problem();

        var result = problem.part2(INPUT);
        Assertions.assertEquals(241861950, result);
    }
}
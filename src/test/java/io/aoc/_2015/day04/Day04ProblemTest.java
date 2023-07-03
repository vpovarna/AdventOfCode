package io.aoc._2015.day04;

import io.aoc._2015.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day04ProblemTest {
    private final String input = Utils.readInputFileAsString(4, "input.txt");
    @Test
    public void part1Test() {
        final Problem problem = new Problem();
        final int result = problem.part1(input);
        Assertions.assertEquals(2, result);
    }
}

package io.aoc._2020.day07;

import io.aoc.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProblemTest {

    @Test
    void part1Test() {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 7, "input.txt");
        int result = problem.part1(input);
        Assertions.assertEquals(4, result);
    }

    @Test
    void part2Test() {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 7, "input.txt");
        int result = problem.part2(input);
        Assertions.assertEquals(32, result);
    }
}
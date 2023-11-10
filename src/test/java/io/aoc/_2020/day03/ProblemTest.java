package io.aoc._2020.day03;

import io.aoc.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProblemTest {
    @Test
    void part1Test() {
        var input = Utils.readInputFileAsString(2020, 3,"input.txt");
        var problem = new Problem();
        var result  = problem.part1(input);
        Assertions.assertEquals(7, result);
    }

    @Test
    void part2Test() {
        var input = Utils.readInputFileAsString(2020, 3,"input.txt");
        var problem = new Problem();
        var result  = problem.part2(input);
        Assertions.assertEquals(336, result);
    }
}
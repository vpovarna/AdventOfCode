package io.aoc._2020.day08;

import io.aoc.utils.Utils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProblemTest {

    @Test
    void part1Test() {
        var input = Utils.readInputFileAsString(2020, 8, "input.txt");
        var problem = new Problem();
        int result = problem.part1(input);
        assertEquals(5, result);
    }

    @Test
    void part2Test() {
        var input = Utils.readInputFileAsString(2020, 8, "input.txt");
        var problem = new Problem();
        int result = problem.part2(input);
        assertEquals(8, result);
    }
}
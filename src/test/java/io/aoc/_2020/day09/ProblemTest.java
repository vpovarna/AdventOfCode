package io.aoc._2020.day09;

import io.aoc.utils.Utils;
import jdk.jshell.execution.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProblemTest {

    @Test
    void part1Test() {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 9, "input.txt");
        var result = problem.part1(input, 5);
        Assertions.assertEquals(127, result);
    }

    @Test
    void part2Test() {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 9, "input.txt");
        var result = problem.part2(input, 5);
        Assertions.assertEquals(62, result);
    }
}
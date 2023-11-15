package io.aoc._2020.day06;

import io.aoc.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;


class ProblemTest {

    @Test
    void part1Test() {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 6, "input.txt");
        var result = problem.part1(input);
        Assertions.assertEquals(11, result);
    }

    @Test
    void part2Test() {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 6, "input.txt");
        var result = problem.part2(input);
        Assertions.assertEquals(6, result);
    }

    @Test
    void parseGroupTest() {
        var group = """
                ab
                ac
                """;

        var problem = new Problem();
        var result = problem.parseGroup(group);
        Assertions.assertEquals(3, result);
    }

    @Test
    void getPersonAnswersTest() {
        var problem = new Problem();
        var result = problem.getPersonAnswers("abcda");
        Assertions.assertEquals(Set.of('a','b','c','d'), result);
    }

    @Test
    void findCommonQuestionsTest() {
        var problem = new Problem();
        var result = problem.findCommonQuestions(Set.of('a', 'b'), "ac");
        Assertions.assertEquals(Set.of('a'), result);
    }

    @Test
    void getGroupSizeTest() {
        var input = """
                ab
                ac
                """;
        var problem = new Problem();
        var result = problem.getGroupSize(input);
        Assertions.assertEquals(1, result);
    }
}
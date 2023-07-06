package io.aoc._2015.day06;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class Day06ProblemTest {

    private static Problem problem;

    @BeforeAll
    static void initialize() {
        problem = new Problem();
    }

    @ParameterizedTest
    @MethodSource("findStartOfPackerMarkerTestClass")
    void part1Test(String input, int statingIndex) {
        Assertions.assertEquals(statingIndex, problem.part1(input));
    }

    @ParameterizedTest
    @MethodSource("findStartOfMessageMarkerTestClass")
    void part2Test(String input, int statingIndex) {
        Assertions.assertEquals(statingIndex, problem.part2(input));
    }

    private static Stream<Arguments> findStartOfPackerMarkerTestClass() {
        return Stream.of(
                Arguments.of("bvwbjplbgvbhsrlpgdmjqwftvncz", 5),
                Arguments.of("nppdvjthqldpwncqszvftbrmjlhg", 6),
                Arguments.of("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 10),
                Arguments.of("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 11)
        );
    }

    private static Stream<Arguments> findStartOfMessageMarkerTestClass() {
        return Stream.of(
                Arguments.of("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 19),
                Arguments.of("bvwbjplbgvbhsrlpgdmjqwftvncz", 23),
                Arguments.of("nppdvjthqldpwncqszvftbrmjlhg", 23),
                Arguments.of("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 29),
                Arguments.of("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 26)
        );
    }
}
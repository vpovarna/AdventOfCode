package io.aoc._2020.day05;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class ProblemTest {

    @Test
    void getSeatNumberTest() {
        var str = "FBFBBFFRLR";
        var problem = new Problem();
        var result = problem.getSeatNumber(str);
        Assertions.assertEquals(357, result);
    }

    @ParameterizedTest
    @MethodSource("rowDirectionsArguments")
    void findRowPositionTest(String str, int expectedNumber) {
        var problem = new Problem();
        var result = problem.findPosition(str, 0, 127, 'F');
        Assertions.assertEquals(expectedNumber, result);
    }

    @ParameterizedTest
    @MethodSource("columnDirectionsArguments")
    void findColumnPositionTest(String str, int expectedNumber) {
        var problem = new Problem();
        var result = problem.findPosition(str, 0, 7, 'L');
        Assertions.assertEquals(expectedNumber, result);
    }

    static Stream<Arguments> rowDirectionsArguments() {
        return Stream.of(
                Arguments.of("FBFBBFF", 44),
                Arguments.of("BFFFBBF", 70),
                Arguments.of("FFFBBBF", 14)
        );
    }

    static Stream<Arguments> columnDirectionsArguments() {
        return Stream.of(
                Arguments.of("RRR", 7),
                Arguments.of("RRR", 7),
                Arguments.of("RLL", 4)
        );
    }
}
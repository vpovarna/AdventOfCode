package io.aoc._2020.day04;

import io.aoc.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;


class ProblemTest {

    @Test
    void part1Test() {
        var input = Utils.readInputFileAsString(2020, 4,"input.txt");

        var problem = new Problem();
        var result = problem.part1(input);
        Assertions.assertEquals(8, result);
    }

    @Test
    void part2Test() {
        var input = Utils.readInputFileAsString(2020, 4,"input.txt");

        var problem = new Problem();
        var result = problem.part2(input);
        Assertions.assertEquals(4, result);
    }

    @Test
    void buildPassportTest() {
        var rawPassport = """
                ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
                byr:1937 iyr:2017 cid:147 hgt:183cm
                """;
        var passport = new Passport(rawPassport);
        var passportsFields = passport.getPassport();

        var expectedFields = List.of("ecl", "pid", "eyr", "hcl", "byr", "iyr", "cid", "hgt");

        for (var expectedField : expectedFields) {
            Assertions.assertTrue(passportsFields.containsKey(expectedField),
                    "Expected field should contains: " + expectedField);
        }
    }

    static Stream<Arguments> birthYearArguments() {
        return Stream.of(
                Arguments.of(new Passport("byr:2002"),true),
                Arguments.of(new Passport("byr:2003"),false)
        );
    }
    @ParameterizedTest
    @MethodSource("birthYearArguments")
    void isBirthYearValidTest(Passport passport, boolean expectedValue) {
        Assertions.assertEquals(passport.isBirthYearValid(), expectedValue);
    }

    static Stream<Arguments> issuedYearArguments() {
        return Stream.of(
                Arguments.of(new Passport("iyr:2010"), true),
                Arguments.of(new Passport("iyr:2030"), false)
        );
    }

    @ParameterizedTest
    @MethodSource("issuedYearArguments")
    void isIssuedYearValidTest(Passport passport, boolean expectedValue) {
        Assertions.assertEquals(passport.isIssuedYearValid(), expectedValue);
    }

    static Stream<Arguments> issuedNotExpiredArguments() {
        return Stream.of(
                Arguments.of(new Passport("eyr:2010"), false),
                Arguments.of(new Passport("eyr:5533412"), false),
                Arguments.of(new Passport("eyr:2020"), true)
        );
    }

    @ParameterizedTest
    @MethodSource("issuedNotExpiredArguments")
    void isNotExpiredTest(Passport passport, boolean expectedValue) {
        Assertions.assertEquals(passport.isNotExpired(), expectedValue);
    }

    static Stream<Arguments> isHeightValidArguments() {
        return Stream.of(
                Arguments.of(new Passport("hgt:232"), false),
                Arguments.of(new Passport("hgt:100cm"), false),
                Arguments.of(new Passport("hgt:180cm"), true),
                Arguments.of(new Passport("hgt:20in"), false),
                Arguments.of(new Passport("hgt:70in"), true)
        );
    }

    @ParameterizedTest
    @MethodSource("isHeightValidArguments")
    void isHeightValidTest(Passport passport, boolean expectedValue) {
        Assertions.assertEquals(passport.isHeightValid(), expectedValue);
    }

    static Stream<Arguments> isValidColorArguments() {
        return Stream.of(
                Arguments.of(new Passport("ecl:amb"), true),
                Arguments.of(new Passport("ecl:gry"), true),
                Arguments.of(new Passport("ecl:cmn"), false)
        );
    }

    @ParameterizedTest
    @MethodSource("isValidColorArguments")
    void isValidColorTest(Passport passport, boolean expectedValue) {
        Assertions.assertEquals(passport.isValidEyeColor(), expectedValue);
    }

    static Stream<Arguments> isValidPidArguments() {
        return Stream.of(
                Arguments.of(new Passport("pid:22132131"), false),
                Arguments.of(new Passport("pid:012345678"), true),
                Arguments.of(new Passport("pid:012345"), false)
        );
    }

    @ParameterizedTest
    @MethodSource("isValidPidArguments")
    void isValidPidTest(Passport passport, boolean expectedValue) {
        Assertions.assertEquals(passport.isValidPid(), expectedValue);
    }


}
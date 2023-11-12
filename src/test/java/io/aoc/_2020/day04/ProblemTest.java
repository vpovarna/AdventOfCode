package io.aoc._2020.day04;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProblemTest {
    private static String INPUT = """
            ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
            byr:1937 iyr:2017 cid:147 hgt:183cm
                        
            iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
            hcl:#cfa07d byr:1929
                        
            hcl:#ae17e1 iyr:2013
            eyr:2024
            ecl:brn pid:760753108 byr:1931
            hgt:179cm
                        
            hcl:#cfa07d eyr:2025 pid:166559648
            iyr:2011 ecl:brn hgt:59in
            """;

    @Test
    void part1Test() {
        var problem = new Problem();
        var result = problem.part1(INPUT);
        Assertions.assertEquals(2, result);
    }

    @Test
    void buildPassportTest() {
        var rawPassport = """
                ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
                byr:1937 iyr:2017 cid:147 hgt:183cm
                """;
        var problem = new Problem();
        var passportsFields = problem.buildPassport(rawPassport);

        var expectedFields = List.of("ecl", "pid", "eyr", "hcl", "byr", "iyr", "cid", "hgt");

        for (var expectedField : expectedFields) {
            Assertions.assertTrue(passportsFields.containsKey(expectedField),
                    "Expected field should contains: " + expectedField);
        }
    }

    @Test
    void part2Test() {
    }
}
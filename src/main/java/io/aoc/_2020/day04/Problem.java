package io.aoc._2020.day04;

import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static io.aoc.utils.Constants.*;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 4, "input.txt");
        logger.info("Aoc2020, Day4 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day4 Problem, Part2: {}", problem.part2(input));
    }

    public long part1(String input) {
        var rawPassports = input.split(EMPTY_LINE);
        return Arrays.stream(rawPassports)
                .map(this::buildPassport)
                .filter(this::isPassportValid)
                .count();
    }

    public int part2(String input) {
        return -1;
    }

    protected HashMap<String, String> buildPassport(String rawPassport) {
        var passport = new HashMap<String, String>();
        try {
            var lines = rawPassport.split(EOL);
            for (var line : lines) {
                var passportFields = line.split(EMPTY_STRING);
                for (var passportFiled : passportFields) {
                    var field = passportFiled.split(DOTS);
                    passport.put(field[0], field[1]);
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid input: " + rawPassport);
        }
        return passport;
    }

    private boolean isPassportValid(HashMap<String, String> passportFields) {
        var expectedPassportFields = List.of("ecl", "pid", "eyr", "hcl", "byr", "iyr", "hgt");
        for(var expectedField : expectedPassportFields) {
            if (!passportFields.containsKey(expectedField)) {
                return false;
            }
        }
        return true;
    }

}

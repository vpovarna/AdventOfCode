package io.aoc._2020.day04;

import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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
        var rawPassports = input.split("\r\n\r\n");
        return Arrays.stream(rawPassports)
                .map(Passport::new)
                .filter(Passport::areAllFieldsPresent)
                .count();

    }

    public long part2(String input) {
        var rawPassports = input.split("\r\n\r\n");
        return Arrays.stream(rawPassports)
                .map(Passport::new)
                .filter(Passport::isValidPassport)
                .count();
    }

}

class Passport {

    private static final List<String> expectedPassportFields = List.of("ecl", "pid", "eyr", "hcl", "byr", "iyr", "hgt");
    private final HashMap<String, String> passportFields;

    public Passport(String rawPassport) {
        this.passportFields = buildPassport(rawPassport);
    }

    private synchronized HashMap<String, String> buildPassport(String rawPassport) {
        var passport = new HashMap<String, String>();
        try {
            var lines = rawPassport.split(EOL);
            for (var line : lines) {
                var fields = line.split(EMPTY_STRING);
                for (var field : fields) {
                    var parts = field.split(DOTS);
                    passport.put(parts[0], parts[1]);
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid input: " + rawPassport);
        }
        return passport;
    }


    public Map<String, String> getPassport() {
        return passportFields;
    }

    public boolean areAllFieldsPresent() {
        for (var expectedField : expectedPassportFields) {
            if (!passportFields.containsKey(expectedField)) {
                return false;
            }
        }
        return true;
    }

    //byr (Birth Year) - four digits; at least 1920 and at most 2002.
    public boolean isBirthYearValid() {
        var byrValue = passportFields.getOrDefault("byr", "").trim();
        if (byrValue.length() != 4) {
            return false;
        }
        int byr = Integer.parseInt(byrValue);
        return byr >= 1920 && byr <= 2002;
    }

    //iyr (Issue Year) - four digits; at least 2010 and at most 2020.
    public boolean isIssuedYearValid() {
        var iyrValue = passportFields.getOrDefault("iyr", "").trim();
        if (iyrValue.length() != 4) {
            return false;
        }
        int iry = Integer.parseInt(iyrValue);
        return iry >= 2010 && iry <= 2020;
    }

    //eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
    public boolean isNotExpired() {
        var eyrValue = passportFields.getOrDefault("eyr", "").trim();
        if (eyrValue.length() != 4) {
            return false;
        }
        int eyr = Integer.parseInt(eyrValue);
        return eyr >= 2020 && eyr <= 2030;
    }

    //hgt (Height) - a number followed by either cm or in:
    //  If cm, the number must be at least 150 and at most 193.
    //  If in, the number must be at least 59 and at most 76.
    public boolean isHeightValid() {
        var iyrValue = passportFields.getOrDefault("hgt", "").trim();
        if (iyrValue.length() < 4) {
            return false;
        }

        var unit = iyrValue.substring(iyrValue.trim().length() - 2);
        var value = Integer.parseInt(iyrValue.trim().substring(0, iyrValue.trim().length() - 2));
        if (unit.equals("cm")) {
            return value >= 150 && value <= 193;
        } else if (unit.equals("in")) {
            return value >= 59 && value <= 76;
        } else {
            return false;
        }
    }

    //hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
    public boolean isValidHairColor() {
        var colorValue = passportFields.getOrDefault("hcl", "").trim();
        if (colorValue.length() != 7) {
            return false;
        }

        if (colorValue.charAt(0) != '#') {
            return false;
        }

        var validColorChars = Set.of('a', 'b', 'c', 'd', 'e', 'f', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        for (var i = 1; i < colorValue.length(); i++) {
            if (!validColorChars.contains(colorValue.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    //ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
    public boolean isValidEyeColor() {
        var validEyesColor = Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
        var colorValue = passportFields.getOrDefault("ecl", "").trim();
        return validEyesColor.contains(colorValue);
    }

    //pid (Passport ID) - a nine-digit number, including leading zeroes.
    public boolean isValidPid() {
        var pidValue = passportFields.getOrDefault("pid", "").trim();
        return pidValue.length() == 9;
    }

    public boolean isValidPassport() {
        return areAllFieldsPresent() &&
                isValidPid() &&
                isValidEyeColor() &&
                isValidHairColor() &&
                isHeightValid() &&
                isNotExpired() &&
                isIssuedYearValid() &&
                isBirthYearValid();
    }

}
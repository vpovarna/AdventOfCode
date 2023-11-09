package io.aoc._2020.day02;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Predicate;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);
    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 2, "input.txt");
        logger.info("Aoc2020, Day1 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day1 Problem, Part2: {}", problem.part2(input));
    }
    public long part1(String input) {
        return getCount(input, PasswordPolicy::isPasswordValidPart1);
    }

    public long part2(String input) {
        return getCount(input, PasswordPolicy::isPasswordValidPart2);
    }


    private long getCount(String input, Predicate<PasswordPolicy> isPasswordValid) {
        return Arrays.stream(input.split(Constants.EOL))
                .map(String::trim)
                .map(this::createPasswordPolicy)
                .filter(isPasswordValid)
                .count();
    }

    protected PasswordPolicy createPasswordPolicy(String line) {
        try {
            var tokens1 = line.split(": ");
            var tokens2 = tokens1[0].split(" ");
            var tokens3 = tokens2[0].split("-");
            var min = Integer.parseInt(tokens3[0]);
            var max = Integer.parseInt(tokens3[1]);

            var stringChar = tokens2[1];
            var password = tokens1[1];

            return new PasswordPolicy(min, max, stringChar.charAt(0), password);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid input line. Can't create password policy");
        }
    }
}

record PasswordPolicy(int i, int j, char c, String password) {
    public boolean isPasswordValidPart1() {
        var charOccurrence = new HashMap<Character, Integer>();
        for(var ch : password.toCharArray()) {
            var value = charOccurrence.getOrDefault(ch, 0) + 1;
            charOccurrence.put(ch, value);
        }
        return i <= charOccurrence.getOrDefault(c, 0) && charOccurrence.getOrDefault(c, 0) <= j;
    }

    public boolean isPasswordValidPart2() {
        return (password.charAt(i-1) == c && password.charAt(j-1) != c) ||
                (password.charAt(i-1) != c && password.charAt(j-1) == c);
    }

}

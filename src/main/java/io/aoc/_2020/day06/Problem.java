package io.aoc._2020.day06;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.assertj.core.util.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 6, "input.txt");
        logger.info("Aoc2020, Day6 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day6 Problem, Part2: {}", problem.part2(input));
    }

    public long part1(String input) {
        return solve(input, this::parseGroup);
    }

    public int part2(String input) {
        return solve(input, this::getGroupSize);
    }

    private Integer solve(String input, Function<String, Integer> function) {
        var lines = input.split(Constants.EMPTY_LINE);
        return Arrays.stream(lines)
                .parallel()
                .map(String::trim)
                .map(function)
                .reduce(0, Integer::sum);
    }

    @VisibleForTesting
    protected int parseGroup(String group) {
        var uniqueQuestions = new HashSet<Character>();

        var lines = group.split(Constants.EOL);
        for(var questions : lines) {
            for (var question : questions.trim().toCharArray()) {
                uniqueQuestions.add(question);
            }
        }
        return uniqueQuestions.size();
    }

    @VisibleForTesting
    protected int getGroupSize(String group) {
        final String[] lines = group.split(Constants.EOL);
        var firstPersonAnswers = getPersonAnswers(lines[0]);

        if (lines.length == 1) {
            return firstPersonAnswers.size();
        }

        var uniqueAnswers = firstPersonAnswers;
        for (var i = 1; i< lines.length; i++) {
            uniqueAnswers = findCommonQuestions(uniqueAnswers, lines[i]);
        }

        return uniqueAnswers.size();
    }

    @VisibleForTesting
    protected HashSet<Character> getPersonAnswers(String line) {
        var firstPersonAnswers = new HashSet<Character>();
        for (var c : line.toCharArray()) {
            firstPersonAnswers.add(c);
        }
        return firstPersonAnswers;
    }

    @VisibleForTesting
    protected HashSet<Character> findCommonQuestions(Set<Character> uniqueQuestions, String personQuestions) {
        var result = new HashSet<Character>();
        for (var c : personQuestions.toCharArray()) {
            if (uniqueQuestions.contains(c)) {
                result.add(c);
            }
        }
        return result;
    }

}

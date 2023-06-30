package io.aoc._2015.day03;

import io.aoc._2015.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public int part1(String input) {
        return input.lines()
                .map(this::splitWord)
                .map(this::getCommonLetters)
                .filter(set -> set.size() == 1)
                .map(Problem::getFirstString)
                .map(s -> s.charAt(0))
                .mapToInt(this::getPriority)
                .sum();
    }

    private int getPriority(Character character) {
        return (Character.isUpperCase(character)) ? (int) character - 38 : (int) character - 96;
    }

    private static String getFirstString(Set<String> set) {
        return set.stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unable to fetch the first element"));
    }

    private String[] splitWord(String word) {
        final int length = word.length();
        final String substring1 = word.substring(0, length / 2);
        final String substring2 = word.substring(length / 2, length);

        return new String[]{substring1, substring2};
    }

    private Set<String> getCommonLetters(String[] wordParts) {
        final Set<String> firstHalfLetters = Arrays.stream(wordParts[0].split(""))
                .collect(Collectors.toSet());
        return Arrays.stream(wordParts[1].split(""))
                .distinct()
                .filter(firstHalfLetters::contains)
                .collect(Collectors.toSet());
    }

    public static void main(String[] args) {
        final Problem problem = new Problem();
        final String inputFile = Utils.readInputFileAsString(3, "input.txt");
        logger.info("Aoc2022, Day3 Problem, Part1: " + problem.part1(inputFile));
    }
}

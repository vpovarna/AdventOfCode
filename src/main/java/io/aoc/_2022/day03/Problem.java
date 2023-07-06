package io.aoc._2022.day03;

import io.aoc._2022.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public int part1(String input) {
        final Stream<Set<String>> commonLetters = input.lines()
                .map(this::splitWord)
                .map(this::getCommonLetters);
        return calculatePriorities(commonLetters);
    }

    public int part2(String input) {
        final List<List<String>> groupOfLines = groupLines(input.lines());
        final Stream<Set<String>> commonLetters = groupOfLines.stream()
                .map(this::getCommonLettersFromTheThreeLinesGroup);
        return calculatePriorities(commonLetters);
    }

    private int calculatePriorities(Stream<Set<String>> commonLetters) {
        return commonLetters
                .filter(set -> set.size() == 1)
                .map(this::getFirstString)
                .map(s -> s.charAt(0))
                .mapToInt(this::calculatePriority)
                .sum();
    }

    private Set<String> getCommonLettersFromTheThreeLinesGroup(List<String> strings) {
        if (strings.size() != 3) {
            throw new IllegalArgumentException("The provided list doesn't have the exact 3 elements!");
        }

        var firstWord = strings.get(0);
        Set<String> uniqueLetters = getStringUniqueLetters(firstWord);

        for (int i = 1; i < strings.size(); i++) {
            uniqueLetters = getUniqueLetters(strings.get(i), uniqueLetters);
        }
        return uniqueLetters;
    }

    private Set<String> getUniqueLetters(String str, Set<String> firstStringUniqueLetters) {
        return Arrays.stream(str.split(""))
                .distinct()
                .filter(firstStringUniqueLetters::contains)
                .collect(Collectors.toSet());
    }

    private Set<String> getStringUniqueLetters(String firstWord) {
        return Arrays
                .stream(firstWord.split(""))
                .collect(Collectors.toSet());
    }

    private int calculatePriority(Character character) {
        return (Character.isUpperCase(character)) ? (int) character - 38 : (int) character - 96;
    }

    private String getFirstString(Set<String> set) {
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
        final Set<String> firstHalfLetters = getStringUniqueLetters(wordParts[0]);
        return Arrays.stream(wordParts[1].split(""))
                .distinct()
                .filter(firstHalfLetters::contains)
                .collect(Collectors.toSet());
    }

    private List<List<String>> groupLines(Stream<String> lines) {
        final List<List<String>> result = new ArrayList<>();
        for (var iterator = lines.iterator(); iterator.hasNext(); ) {
            result.add(List.of(iterator.next(), iterator.next(), iterator.next()));
        }

        return result;
    }

    public static void main(String[] args) {
        final Problem problem = new Problem();
        final String inputFile = Utils.readInputFileAsString(3, "input.txt");
        logger.info("Aoc2022, Day3 Problem, Part1: {}", problem.part1(inputFile));
        logger.info("Aoc2022, Day3 Problem, Part2: {}", problem.part2(inputFile));
    }
}

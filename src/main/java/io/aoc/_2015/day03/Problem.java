package io.aoc._2015.day03;

import io.aoc._2015.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public int part2(String input) {
        final List<List<String>> groupOfLines = groupOfThreeLines(input.lines());
        return groupOfLines.stream()
                .map(this::getCommonLettersFromTheThreeLinesGroup)
                .map(s -> s.charAt(0))
                .mapToInt(this::getPriority)
                .sum();
    }

    private String getCommonLettersFromTheThreeLinesGroup(List<String> strings) {
        if (strings.size() != 3) {
            throw new IllegalArgumentException("The provided list doesn't have the exact 3 elements!");
        }

        var firstWord = strings.get(0);
         Set<String> firstStringUniqueLetters = Arrays
                 .stream(firstWord.split(""))
                .collect(Collectors.toSet());

        for (int i = 1; i < strings.size(); i++) {
            firstStringUniqueLetters = Arrays.stream(strings.get(i).split(""))
                    .distinct()
                    .filter(firstStringUniqueLetters::contains)
                    .collect(Collectors.toSet());
        }

        return firstStringUniqueLetters.stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unable to fetch the first element"));
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

    private List<List<String>> groupOfThreeLines(Stream<String> lines) {
        final List<List<String>> result = new ArrayList<>();
        for (var iterator = lines.iterator(); iterator.hasNext();) {
            result.add(List.of(iterator.next(), iterator.next(), iterator.next()));
        }

        return result;
    }

    public static void main(String[] args) {
        final Problem problem = new Problem();
        final String inputFile = Utils.readInputFileAsString(3, "input.txt");
        logger.info("Aoc2022, Day3 Problem, Part1: " + problem.part1(inputFile));
        logger.info("Aoc2022, Day3 Problem, Part2: " + problem.part2(inputFile));
    }
}

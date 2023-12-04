package io.aoc._2023.day04;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Problem {

    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var inputFile = Utils.readInputFileAsString(2023, 4);
        var problem = new Problem();

        logger.info("Aoc2023, Day4 Problem, Part1: {}", problem.part1(inputFile));
        logger.info("Aoc2023, Day4 Problem, Part2: {}", problem.part2(inputFile));
    }

    private int part1(String input) {
        return run(input);
    }

    private int part2(String input) {
        return 0;
    }

    private int run(String input) {
        var lines = input.split(Constants.EOL);
        return Arrays.stream(lines)
                .parallel()
                .map(this::getCard)
                .map(this::countWiningNumbers)
                .mapToInt(x -> (int) Math.pow(2, (x - 1)))
                .sum();
    }

    private Card getCard(String line) {
        String[] parts = line.split(" \\| ");
        var numbers = extractNumbers(parts)
                .collect(Collectors.toSet());
        String[] splits = parts[0].split(Constants.DOTS);
        var winningNumbers = extractNumbers(splits)
                .toList();

        return new Card(winningNumbers, numbers);
    }

    private static Stream<Integer> extractNumbers(String[] parts) {
        return Arrays.stream(parts[1].split(Constants.EMPTY_STRING))
                .map(String::trim)
                .map(String::strip)
                .filter(s -> !s.isBlank())
                .map(Integer::parseInt);
    }

    private int countWiningNumbers(Card card) {
        var winingNumbers = card.winningNumbers();

        return card.numbers().stream()
                .filter(winingNumbers::contains)
                .mapToInt(x -> 1)
                .sum();
    }
}

record Card(List<Integer> winningNumbers, Set<Integer> numbers) {
}

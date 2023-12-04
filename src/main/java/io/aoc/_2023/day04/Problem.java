package io.aoc._2023.day04;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
        var list = run(input);

        return list.stream()
                .mapToInt(x -> (int) Math.pow(2, (x - 1)))
                .sum();
    }

    private int part2(String input) {
        var cards = run(input);
        var counts = IntStream.range(0, cards.size())
                .map(x -> 1)
                .toArray();

        for (var i = 0; i < cards.size(); i++) {
            var winningNumbers = cards.get(i);
            var count = counts[i];

            for (var j = 0; j < winningNumbers; j++) {
                counts[i + j + 1] += count;
            }
        }

        return Arrays.stream(counts).sum();

    }

    private List<Integer> run(String input) {
        var lines = input.split(Constants.EOL);
        return Arrays.stream(lines)
                .parallel()
                .map(this::extractCardNumbers)
                .map(Card::cardNumbers)
                .map(this::countWiningNumbers)
                .toList();
    }

    private Card extractCardNumbers(String line) {
        String[] parts = line.split(" \\| ");
        var numbers = extractNumbers(parts)
                .collect(Collectors.toSet());
        String[] splits = parts[0].split(Constants.DOTS);
        var winningNumbers = extractNumbers(splits)
                .toList();
        String numerAsString = extractGameNumber(splits[0]);

        return new Card(Integer.parseInt(numerAsString), new CardNumbers(winningNumbers, numbers));
    }

    private static Stream<Integer> extractNumbers(String[] parts) {
        return Arrays.stream(parts[1].split(Constants.EMPTY_STRING))
                .map(String::trim)
                .map(String::strip)
                .filter(s -> !s.isBlank())
                .map(Integer::parseInt);
    }

    private int countWiningNumbers(CardNumbers cardNumbers) {
        var winingNumbers = cardNumbers.winningNumbers();

        return cardNumbers.numbers().stream()
                .filter(winingNumbers::contains)
                .mapToInt(x -> 1)
                .sum();
    }

    private String extractGameNumber(String part) {
        var pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(part);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }
}

record Card(int cardNumber, CardNumbers cardNumbers) {
}

record CardNumbers(List<Integer> winningNumbers, Set<Integer> numbers) {
}

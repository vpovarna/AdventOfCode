package io.aoc._2023.day01;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    private static final List<String> textNumbers = List.of(
            "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
    );
    private static final Map<String, Integer> textNumberMapping = Map.of(
            "one", 1,
            "two", 2,
            "three", 3,
            "four", 4,
            "five", 5,
            "six", 6,
            "seven", 7,
            "eight", 8,
            "nine", 9
    );

    public static void main(String[] args) {
        var inputFile = Utils.readInputFileAsString(2023, 1);
        var problem = new Problem();

        logger.info("Aoc2023, Day1 Problem, Part1: {}", problem.run(inputFile, problem::getNumberPart1));
        logger.info("Aoc2023, Day1 Problem, Part2: {}", problem.run(inputFile, problem::getNumberPart2));
    }

    private int run(String input, Function<String, Integer> getNumber) {
        return Arrays.stream(input.split(Constants.EOL))
                .map(getNumber)
                .mapToInt(x -> x)
                .sum();
    }

    private int getNumberPart1(String line) {
        var numbers = IntStream.range(0, line.length())
                .mapToObj(line::charAt)
                .filter(Character::isDigit)
                .map(c -> Integer.parseInt(String.valueOf(c)))
                .toList();

        if (numbers.size() == 0) {
            return 0;
        }

        return Integer.parseInt(getNumber(numbers));
    }

    private int getNumberPart2(String line) {

        var lineNumbers = new ArrayList<Integer>();
        for (var currentIndex = 0; currentIndex < line.length(); currentIndex++) {
            var currentChar = line.charAt(currentIndex);
            if (Character.isDigit(currentChar)) {
                lineNumbers.add(Integer.parseInt(String.valueOf(currentChar)));
                continue;
            }

            for (var textNumber : textNumbers) {
                if (currentIndex + textNumber.length() <= line.length()) {
                    var str = line.substring(currentIndex, currentIndex + textNumber.length());
                    if (str.equals(textNumber)) {
                        lineNumbers.add(textNumberMapping.get(textNumber));
                        break;
                    }
                }
            }
        }

        return Integer.parseInt(getNumber(lineNumbers));
    }

    private static String getNumber(List<Integer> numbers) {
        String result;
        if (numbers.size() == 1) {
            result = String.format("%s%s", numbers.get(0), numbers.get(0));
        } else {
            result = String.format("%s%s", numbers.get(0), numbers.get(numbers.size() - 1));
        }
        return result;
    }

}

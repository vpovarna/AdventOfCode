package io.aoc._2023.day07;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.IntStream;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var inputFile = Utils.readInputFileAsString(2023, 7);
        var problem = new Problem();

        logger.info("Aoc2023, Day7 Problem, Part1: {}", problem.part1(inputFile));
        logger.info("Aoc2023, Day7 Problem, Part2: {}", problem.part2(inputFile));
    }

    public static final String LABELS = "AKQJT98765432";

    private int part1(String input) {
        var pokerHands = parseInput(input);
        return IntStream.range(0, pokerHands.size())
                .reduce(0, (t, i) ->
                        t + ((i + 1) * pokerHands.get(i).rank())
                );
    }

    private int part2(String input) {
        return 0;
    }

    private List<PokerHand> parseInput(String input) {
        return Arrays.stream(input.split(Constants.EOL))
                .map(String::trim)
                .map(line -> line.split(Constants.EMPTY_STRING))
                .map(arr -> new PokerHand(arr[0], Integer.parseInt(arr[1])))
                .sorted(this::compare)
                .toList();
    }

    private int compare(PokerHand hand1, PokerHand hand2) {
        var hand1Type = hand1.getType();
        var hand2Type = hand2.getType();

        if (hand1Type.value == hand2Type.value) {
            if (hand1.equals(hand2)) {
                return 0;
            }
            var hand1Cards = hand1.card();
            var hand2Cards = hand2.card();
            for (var i = 0; i < hand1Cards.length(); i++) {
                if (LABELS.indexOf(hand1Cards.charAt(i)) < LABELS.indexOf(hand2Cards.charAt(i))) {
                    return 1;
                }
                if (LABELS.indexOf(hand1Cards.charAt(i)) > LABELS.indexOf(hand2Cards.charAt(i))) {
                    return -1;
                }
            }
            return -1;
        }

        if (hand1Type.value > hand2Type.value) {
            return 1;
        }

        return -1;
    }

}

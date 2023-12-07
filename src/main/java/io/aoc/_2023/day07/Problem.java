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

        logger.info("Aoc2023, Day7 Problem, Part1: {}", problem.run(inputFile, false));
        logger.info("Aoc2023, Day7 Problem, Part2: {}", problem.run(inputFile, true));
    }


    private int run(String input, boolean withJoker) {
        var pokerHands = Arrays.stream(input.split(Constants.EOL))
                .map(String::trim)
                .map(line -> line.split(Constants.EMPTY_STRING))
                .map(arr -> new PokerHand(arr[0], Integer.parseInt(arr[1])))
                .sorted((h1, h2) -> compare(h1, h2, withJoker))
                .toList();

        return IntStream.range(0, pokerHands.size())
                .reduce(0, (t, i) ->
                        t + ((i + 1) * pokerHands.get(i).rank())
                );
    }

    private int compare(PokerHand hand1, PokerHand hand2, boolean withJoker) {
        var labels = (withJoker) ? "AKQT98765432J" : "AKQJT98765432";

        var hand1Type = (withJoker) ? hand1.getTypeWithJoker() : hand1.getTypeWithoutJoker();
        var hand2Type = (withJoker) ? hand2.getTypeWithJoker() : hand2.getTypeWithoutJoker();

        if (hand1Type.value == hand2Type.value) {
            if (hand1.equals(hand2)) {
                return 0;
            }
            var hand1Cards = hand1.card();
            var hand2Cards = hand2.card();
            for (var i = 0; i < hand1Cards.length(); i++) {
                var i1 = labels.indexOf(hand1Cards.charAt(i));
                var i2 = labels.indexOf(hand2Cards.charAt(i));
                if (i1 < i2) {
                    return 1;
                }
                if (i1 > i2) {
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

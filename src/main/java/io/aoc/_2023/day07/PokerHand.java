package io.aoc._2023.day07;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public record PokerHand(String card, Integer rank) {

    public Type getTypeWithoutJoker() {
        var occurrence = new HashMap<String, Integer>();
        for (var c : card.split("")) {
            occurrence.put(c, occurrence.getOrDefault(c, 0) + 1);
        }

        int[] amounts = occurrence.values()
                .stream()
                .mapToInt(x -> x)
                .toArray();

        Arrays.sort(amounts);

        if (Arrays.equals(amounts, new int[]{5})) {
            return Type.FIVE_OF_A_KIND;
        }

        if (Arrays.equals(amounts, new int[]{1, 4})) {
            return Type.FOUR_OF_A_KIND;
        }

        if (Arrays.equals(amounts, new int[]{2, 3})) {
            return Type.FULL_HOUSE;
        }

        if (Arrays.equals(amounts, new int[]{1, 1, 3})) {
            return Type.THREE_OF_A_KIND;
        }

        if (Arrays.equals(amounts, new int[]{1, 2, 2})) {
            return Type.TWO_PAIR;
        }

        if (Arrays.equals(amounts, new int[]{1, 1, 1, 2})) {
            return Type.ONE_PAIR;
        }

        return Type.HIGH_CARD;
    }

    public Type getTypeWithJoker() {
        var occurrence = new HashMap<String, Integer>();
        var jokers = 0;
        for (var c : card.split("")) {
            if (Objects.equals(c, "J")) {
                jokers += 1;
            } else {
                occurrence.put(c, occurrence.getOrDefault(c, 0) + 1);
            }
        }

        int[] amounts = occurrence.values()
                .stream()
                .mapToInt(x -> x)
                .sorted()
                .toArray();

        int lastIndex = amounts.length - 1;
        if ((jokers >= 5) || (amounts[lastIndex] + jokers >= 5)) {
            return Type.FIVE_OF_A_KIND;
        }
        if ((jokers == 4) || (amounts[lastIndex] + jokers >= 4)) {
            return Type.FOUR_OF_A_KIND;
        }

        // Try a full house
        if ((amounts[lastIndex] + jokers) >= 3) {
            var remJokers = amounts[lastIndex] + jokers - 3;
            if ((amounts.length >= 2) && (amounts[lastIndex - 1] + remJokers >= 2) || (remJokers >= 2)) {
                return Type.FULL_HOUSE;
            }
            return Type.THREE_OF_A_KIND;
        }


        if (amounts[lastIndex] + jokers >= 2) {
            var remJokers = amounts[lastIndex] + jokers - 2;
            if ((amounts.length >= 2) && (amounts[lastIndex - 1] + remJokers >= 2) || (remJokers >= 2)) {
                return Type.TWO_PAIR;
            }
            return Type.ONE_PAIR;
        }
        return Type.HIGH_CARD;
    }

    @Override
    public String toString() {
        return String.format("('%s', %d)", card, rank);
    }
}

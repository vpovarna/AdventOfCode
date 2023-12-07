package io.aoc._2023.day07;

import java.util.Arrays;
import java.util.HashMap;

public record PokerHand(String card, Integer rank) {

    public Type getType() {
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

    @Override
    public String toString() {
        return String.format("('%s', %d)", card, rank);
    }
}

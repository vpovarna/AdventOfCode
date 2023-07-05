package io.aoc._2015.day05;

import java.util.*;
import java.util.stream.Collectors;

public final class StackBuilder {

    public static final String EMPTY_CHAR = "";

    private StackBuilder() {
    }

    public static List<Stack<String>> build(String input) {
        final List<String> inputLines = input.lines().collect(Collectors.toList());
        final int size = inputLines.size();

        if (size < 2) {
            throw new IllegalArgumentException("Wrong input provided");
        }

        final int numberOfStacks = (int) Arrays.stream(inputLines.get(size - 1).split(EMPTY_CHAR))
                .filter(s -> !s.isBlank())
                .count();

        List<Stack<String>> stacks = new ArrayList<>(numberOfStacks);

        // Initialize all the stacks from the array list
        for (int i = 0; i < numberOfStacks; i++) {
            stacks.add(new Stack<>());
        }

        for (int i = size - 2; i >= 0; i--) {
            final String[] cargoArray = inputLines.get(i).split(EMPTY_CHAR);
            List<String> acc = new ArrayList<>();
            for (int j = 1; j < cargoArray.length; j = j + 4) {
                acc.add(cargoArray[j]);
            }

            for (int t = 0; t < acc.size(); t++) {
                final String currentCargo = acc.get(t);
                if (!Objects.equals(currentCargo, EMPTY_CHAR)) {
                    stacks.get(t).add(currentCargo);
                }
            }
        }

        return stacks;
    }

}

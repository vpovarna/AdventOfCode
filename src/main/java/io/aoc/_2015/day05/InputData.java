package io.aoc._2015.day05;

import java.util.List;
import java.util.Stack;

public final class InputData {

    private final List<Stack<String>> stacks;
    private final List<Instruction> instructions;

    private InputData(List<Stack<String>> stacks, List<Instruction> instructions) {
        this.stacks = stacks;
        this.instructions = instructions;
    }

    public List<Stack<String>> getStacks() {
        return stacks;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public static InputData parse(String input) {
        final String[] parts = input.split("(?m)^\\s*$");

        if (parts.length != 2) {
            throw new IndexOutOfBoundsException("The provided input should contains two parts split through an empty line");
        }

        final String stackInput = parts[0];
        final String movesInput = parts[1];

        final List<Stack<String>> stacks = StackBuilder.build(stackInput);
        final List<Instruction> instructions = MovesBuilder.build(movesInput);

        return new InputData(stacks, instructions);
    }
}

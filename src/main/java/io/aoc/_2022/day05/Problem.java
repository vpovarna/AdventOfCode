package io.aoc._2022.day05;

import io.aoc._2022.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public StringBuilder part1(String input) {
        final InputData inputData = InputData.parse(input);
        return moveCratesOneByOne(inputData.getStacks(), inputData.getInstructions());
    }

    public StringBuilder part2(String input) {
        final InputData inputData = InputData.parse(input);
        return moveCratesInChunk(inputData.getStacks(), inputData.getInstructions());
    }

    private StringBuilder moveCratesOneByOne(List<Stack<String>> stacks, List<Instruction> instructions) {
        for (Instruction instruction : instructions) {
            final Stack<String> sourceStack = stacks.get(instruction.getSourceStack() - 1);
            final Stack<String> destinationStack = stacks.get(instruction.getDestinationStack() - 1);

            int i = 0;
            while (i < instruction.getNumberOfItems()) {
                String popString = sourceStack.pop();
                if (!Objects.equals(popString, " ")) {
                    destinationStack.push(popString);
                    i += 1;
                }
            }
        }

        return readTopStackCreate(stacks);
    }

    private StringBuilder moveCratesInChunk(List<Stack<String>> stacks, List<Instruction> instructions) {
        for (Instruction instruction : instructions) {
            final Stack<String> sourceStack = stacks.get(instruction.getSourceStack() - 1);
            final Stack<String> destinationStack = stacks.get(instruction.getDestinationStack() - 1);
            List<String> acc = new ArrayList<>();

            int i = 0;

            while (i < instruction.getNumberOfItems()) {
                final String popString = sourceStack.pop();
                if (!Objects.equals(popString, " ")) {
                    acc.add(popString);
                    i += 1;
                }
            }
            for (int j = acc.size() - 1; j >= 0; j--) {
                destinationStack.push(acc.get(j));
            }
        }

        return readTopStackCreate(stacks);
    }

    private StringBuilder readTopStackCreate(List<Stack<String>> stacks) {
        final StringBuilder str = new StringBuilder();
        stacks.forEach(stack -> str.append(stack.pop()));
        return str;
    }

    public static void main(String[] args) {
        final Problem problem = new Problem();
        final String input = Utils.readInputFileAsString(5, "input.txt");
        logger.info("Aoc2022, Day6 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2022, Day6 Problem, Part2: {}", problem.part2(input));
    }

}

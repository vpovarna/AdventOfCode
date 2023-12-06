package io.aoc._2020.day08;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 8, "input.txt");
        logger.info("Aoc2020, Day8 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day8 Problem, Part2: {}", problem.part2(input));
    }

    public int part1(String input) {
        List<Instruction> instructions = parseInput(input);
        return processInstructions(instructions).acc();
    }

    public int part2(String input) {
        var patchedInstructions = patch(parseInput(input));
        return patchedInstructions.stream()
                .map(this::processInstructions)
                .filter(Result::terminated)
                .findFirst()
                .orElse(new Result(0, true))
                .acc();
    }

    private List<List<Instruction>> patch(List<Instruction> instructions) {
        var patchedInstructions = new ArrayList<List<Instruction>>();
        patchedInstructions.add(instructions);

        var i = 0;

        while (i < instructions.size()) {
            var tmpInstructions = new ArrayList<>(instructions);
            var currentInstruction = tmpInstructions.get(i);
            if (currentInstruction.operation().equals("acc")) {
                i++;
                continue;
            }

            if (currentInstruction.operation().equals("nop")) {
                tmpInstructions.set(i, new Instruction("jmp", currentInstruction.steps()));
            } else {
                tmpInstructions.set(i, new Instruction("nop", currentInstruction.steps()));
            }
            patchedInstructions.add(tmpInstructions);
            i++;
        }

        return patchedInstructions;
    }
    private Result processInstructions(List<Instruction> instructions) {
        var acc = 0;
        var i = 0;
        var seen = new HashSet<Integer>();

        while (true) {
            if (i >= instructions.size()) {
                return new Result(acc, true);
            }
            if (seen.contains(i)) {
                return new Result(acc, false);
            } else {
                seen.add(i);
                var instruction = instructions.get(i);
                switch (instruction.operation()) {
                    case "nop" -> i++;
                    case "acc" -> {
                        i++;
                        acc += instruction.steps();
                    }
                    case "jmp" -> i += instruction.steps();
                    default -> throw new IllegalArgumentException("Unknown case");
                }
            }

        }

    }
    private List<Instruction> parseInput(String input) {
        var lines = input.split("\r\n");
        return Arrays.stream(lines)
                .map(this::createInstruction)
                .toList();
    }

    private Instruction createInstruction(String line) {
        var tokens = line.split(Constants.EMPTY_STRING);
        System.out.println(Arrays.toString(tokens));
        int steps;
        try {
            steps = Integer.parseInt(tokens[1]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable transform steps to integer for the input line: " + line);
        }

        return new Instruction(tokens[0], steps);
    }


}

record Instruction(String operation, int steps) {
}

record Result(Integer acc, boolean terminated){}

package io.aoc._2022.day05;

import java.util.List;
import java.util.stream.Collectors;

public class MovesBuilder {

    public static final String SPACE = " ";
    public static final int MOVES_COLUMNS = 6;

    public static List<Instruction> build(String input) {
        return input.lines()
                .map(line -> line.split(SPACE))
                .filter(arr -> arr.length == MOVES_COLUMNS)
                .map(MovesBuilder::getInstruction)
                .collect(Collectors.toList());
    }

    private static Instruction getInstruction(String[] arr) {
        return new Instruction(Integer.parseInt(arr[1]), Integer.parseInt(arr[3]), Integer.parseInt(arr[5]));
    }
}

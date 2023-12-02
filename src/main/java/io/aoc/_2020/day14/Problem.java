package io.aoc._2020.day14;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Problem {

    private final static Logger logger = LoggerFactory.getLogger(Problem.class);
    private List<String> rawData;

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 14, "input.txt");
        logger.info("Aoc2020, Day10 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day10 Problem, Part2: {}", problem.part2(input));
    }

    public long part1(String input) {
        var rowLines = input.split(Constants.EOL);
        var lines = Arrays.stream(rowLines).map(String::trim).toList();

        String mask = "";
        Map<Integer, Long> registers = new HashMap<>();

        for (String com : lines) {
            var substring = com.substring(com.indexOf('=') + 2);
            if (com.startsWith("ma")) {
                mask = substring;
            } else {
                int registerIndex = Integer.parseInt(com.substring(com.indexOf('[') + 1, com.indexOf(']')));
                long val = Long.parseLong(substring);
                long updatedVal = applyMask(mask, val);

                registers.put(registerIndex, updatedVal);
            }
        }

        return getSum(registers
                .values());
    }

    public long part2(String input) {
        var rowLines = input.split(Constants.EOL);
        var lines = Arrays.stream(rowLines).map(String::trim).toList();

        String mask = "";
        Map<Long, Long> registers = new HashMap<>();

        for (String com : lines) {
            var substring = com.substring(com.indexOf('=') + 2);
            if (com.startsWith("ma")) {
                mask = substring;
            } else {
                long registerIndex = Integer.parseInt(com.substring(com.indexOf('[') + 1, com.indexOf(']')));
                long val = Long.parseLong(substring);

                String maskedRegister = applyMaskPt2(mask, Long.toBinaryString(registerIndex));

                Set<Long> registerIndices = getRegisters(maskedRegister);

                for (Long register : registerIndices) {
                    registers.put(register, val);
                }
            }
        }

        return getSum(registers.values());
    }


    private long applyMask(String mask, long value) {
        for (int i = mask.length() - 1; i >= 0; i--) {
            char val = mask.charAt(i);
            long shiftLen = mask.length() - i - 1;
            if (val != 'X') {
                long digit = Character.digit(val, 10);
                long posMask = (1L << shiftLen);

                value = ((value & ~posMask) | ((digit << shiftLen) & posMask));
            }
        }

        return value;
    }

    private String applyMaskPt2(String mask, String value) {
        var updatedVal = "";

        int maskIndex = mask.length() - 1;
        for (int i = value.length() - 1; i >= 0; i--) {
            if (mask.charAt(maskIndex) == '1') {
                updatedVal = '1' + updatedVal;
            } else if (mask.charAt(maskIndex) == '0') {
                updatedVal = value.charAt(i) + updatedVal;
            } else {
                updatedVal = mask.charAt(maskIndex) + updatedVal;
            }
            maskIndex--;
        }

        for (int i = maskIndex; i >= 0; i--) {
            updatedVal = mask.charAt(i) + updatedVal;
        }

        return updatedVal;
    }

    private Set<Long> getRegisters(String mask) {
        Set<Long> masks = new HashSet<>();

        maskPermutations(masks, mask, 0, "");

        return masks;
    }

    private void maskPermutations(Set<Long> masks, String baseMask, int index, String mask) {
        if (mask.length() == baseMask.length()) {
            masks.add(Long.parseLong(mask, 2));
        }

        if (index >= baseMask.length()) return;

        if (baseMask.charAt(index) == 'X') {
            maskPermutations(masks, baseMask, index + 1, mask + '1');
            maskPermutations(masks, baseMask, index + 1, mask + '0');
        } else {
            maskPermutations(masks, baseMask, index + 1, mask + baseMask.charAt(index));
        }
    }

    private long getSum(Collection<Long> registers) {
        return registers
                .stream()
                .mapToLong(aLong -> aLong)
                .sum();
    }
}
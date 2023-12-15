package io.aoc._2023.day15;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2023, 15, "input.txt");
        logger.info("Aoc2020, Day16 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day16 Problem, Part2: {}", problem.part2(input));
    }

    private int part1(String input) {
        return Arrays.stream(input.split(Constants.COMMA))
                .mapToInt(this::getStringHash)
                .sum();
    }
    private int part2(String input) {
        return 0;
    }

    private int getStringHash(String str) {
        var ans = 0;
        for (var c : str.toCharArray()) {
            ans = hash(ans + c);
        }
        return ans;
    }

    private int hash(int c){
        return (c * 17) % 256;
    }

}

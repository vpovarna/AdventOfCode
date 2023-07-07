package io.aoc._2022.day08;

import io.aoc._2022.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public int part1(String input) {
        var trees = input.lines().collect(Collectors.toList());

        int count = 0;
        for (int i = 0; i < trees.size(); i++) {
            for (int j = 0; j < trees.get(i).length(); j++) {
                if (isVisible(i, j, trees))
                    count++;
            }
        }
        return count;
    }

    public int part2(String input) {
        var trees = input.lines().collect(Collectors.toList());
        int maxSS = Integer.MIN_VALUE;
        for (int i = 0; i < trees.size(); i++) {
            for (int j = 0; j < trees.get(i).length(); j++) {
                int ss = scenicScoreCalculator(i, j, trees);
                if (maxSS < ss) {
                    maxSS = ss;
                }
            }
        }
        return maxSS;
    }

    private int scenicScoreCalculator(int i, int j, List<String> trees) {
        char height = trees.get(j).charAt(i);
        return countUp(i, j, height, trees) * countDown(i, j, height, trees) * countLeft(i, j, height, trees)
                * countRight(i, j, height, trees);
    }

    private int countUp(int i, int j, char height, List<String> trees) {
        int count = 0;
        for (int x = j - 1; 0 <= x; x--) {
            if (trees.get(x).charAt(i) < height) {
                count++;
            } else {
                count++;
                return count;
            }
        }
        return count;
    }

    private static int countDown(int i, int j, char height, List<String> trees) {
        int count = 0;
        for (int x = j + 1; x < trees.get(i).length(); x++) {
            if (trees.get(x).charAt(i) < height) {
                count++;
            } else {
                count++;
                return count;
            }
        }
        return count;
    }

    private static int countLeft(int i, int j, char height, List<String> trees) {
        int count = 0;
        for (int x = i - 1; 0 <= x; x--) {
            if (trees.get(j).charAt(x) < height) {
                count++;
            } else {
                count++;
                return count;
            }
        }
        return count;
    }

    private static int countRight(int i, int j, char height, List<String> trees) {
        int count = 0;
        for (int x = i + 1; x < trees.get(j).length(); x++) {
            if (trees.get(j).charAt(x) < height) {
                count++;
            } else {
                count++;
                return count;
            }
        }
        return count;
    }

    private boolean isVisible(int i, int j, List<String> trees) {
        char height = trees.get(j).charAt(i);
        return !hasTallerUp(i, j, height, trees) ||
                !hasTallerDown(i, j, height, trees) ||
                !hasTallerLeft(i, j, height, trees) ||
                !hasTallerRight(i, j, height, trees);
    }

    private boolean hasTallerUp(int i, int j, char height, List<String> trees) {
        boolean hasHigherAbove = false;
        for (int x = 0; x < j && !hasHigherAbove; x++) {
            hasHigherAbove = height <= trees.get(x).charAt(i);
        }
        return hasHigherAbove;
    }

    private boolean hasTallerDown(int i, int j, char height, List<String> trees) {
        boolean hasHigherUnder = false;
        for (int x = j + 1; x < trees.size() && !hasHigherUnder; x++) {
            hasHigherUnder = height <= trees.get(x).charAt(i);
        }
        return hasHigherUnder;
    }

    private boolean hasTallerLeft(int i, int j, char height, List<String> trees) {
        boolean hasHigherLeft = false;
        for (int x = 0; x < i && !hasHigherLeft; x++) {
            hasHigherLeft = height <= trees.get(j).charAt(x);
        }
        return hasHigherLeft;
    }

    private boolean hasTallerRight(int i, int j, char height, List<String> trees) {
        boolean hasHigherRight = false;
        for (int x = i + 1; x < trees.size() && !hasHigherRight; x++) {
            hasHigherRight = height <= trees.get(j).charAt(x);
        }
        return hasHigherRight;
    }

    public static void main(String[] args) {
        final Problem problem = new Problem();
        final String input = Utils.readInputFileAsString(8, "input.txt");
        logger.info("Aoc2022, Day8 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2022, Day8 Problem, Part2: {}", problem.part2(input));
    }

}

package io.aoc._2020.day18;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

public class Problem {

    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 18);
        logger.info("Aoc2020, Day18 Problem, Part1: {}", problem.calculate(input, true));
        logger.info("Aoc2020, Day18 Problem, Part2: {}", problem.calculate(input, false));
    }

    private long calculate(String input, boolean part1) {
        var lines = input.split(Constants.EOL);

        var sum = 0L;

        for (var line : lines) {
            var valStack = new Stack<Long>();
            var optStack = new Stack<Character>();

            var chars = line.toCharArray();

            optStack.push('(');

            for (var c : chars) {
                switch (c) {
                    case ' ':
                        break;
                    case '*':
                        evalUntil("(", valStack, optStack);
                        optStack.push(c);
                        break;
                    case '+':
                        var evalChar = part1 ? "(" : "(*";
                        evalUntil(evalChar, valStack, optStack);
                        optStack.push(c);
                        break;
                    case '(':
                        optStack.push('(');
                        break;
                    case ')':
                        // calculate what's between the parentheses
                        evalUntil("(", valStack, optStack);
                        // remove the open parentheses
                        optStack.pop();
                        break;
                    default:
                        valStack.add(Long.parseLong(String.valueOf(c)));
                        break;
                }
            }
            evalUntil("(", valStack, optStack);

            long lineSum = valStack.peek();
            sum += lineSum;
        }

        return sum;
    }

    private void evalUntil(String ops, Stack<Long> valStack, Stack<Character> optStack) {
        while (!ops.contains(String.valueOf(optStack.peek()))) {
            var currentChar = optStack.pop();
            if (currentChar == '+') {
                valStack.push(valStack.pop() + valStack.pop());
            } else {
                valStack.push(valStack.pop() * valStack.pop());
            }
        }
    }

}

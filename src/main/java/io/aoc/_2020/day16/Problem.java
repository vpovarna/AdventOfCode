package io.aoc._2020.day16;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class Problem {
    private final static Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 16, "input.txt");
        logger.info("Aoc2020, Day16 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day16 Problem, Part2: {}", problem.part2(input));
    }

    private int part1(String input) {
        return run(input);
    }


    private int part2(String input) {
        return 0;
    }

    private int run(String input) {
        var parts = input.split(Constants.EMPTY_LINE);
        var rules = createRules(parts[0]);
        var nearbyTickets = getNearbyTickets(parts[2]);

        return nearbyTickets.stream()
                .filter(ticket -> isInvalidTicket(ticket, rules))
                .mapToInt(x -> x)
                .sum();
    }

    private boolean isInvalidTicket(Integer ticket, List<Rules> rules) {
        return rules.stream()
                .noneMatch(rule -> rule.isTicketValid(ticket));
    }

    private List<Rules> createRules(String input) {
        var lines = input.split("\n");
        return Arrays.stream(lines)
                .map(line -> {
                    String[] parts = line.split(": ");
                    var ruleName = parts[0];
                    var rowRanges = parts[1].split(" or ");
                    return new Rules(ruleName, createRule(rowRanges[0]), createRule(rowRanges[1]));
                })
                .toList();
    }

    private int[] createRule(String rule) {
        String[] parts = rule.split("-");
        return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
    }

    private List<Integer> getNearbyTickets(String input) {
        return Arrays.stream(input.split(Constants.EOL))
                .skip(1)
                .flatMap(line -> Arrays.stream(line.split(","))
                        .map(Integer::parseInt))
                .toList();
    }

}

record Rules(String name, int[] firstRule, int[] secondRule) {

    public boolean isTicketValid(Integer ticketNumber) {
        return (firstRule[0] <= ticketNumber && ticketNumber <= firstRule[1]) ||
                (secondRule[0] <= ticketNumber && ticketNumber <= secondRule[1]);
    }

    @Override
    public String toString() {
        return "[" +
                "name='" + name + '\'' +
                ", firstRule=" + Arrays.toString(firstRule) +
                ", secondRule=" + Arrays.toString(secondRule) +
                ']';
    }
}


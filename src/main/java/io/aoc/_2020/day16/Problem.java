package io.aoc._2020.day16;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 16, "input.txt");
        logger.info("Aoc2020, Day16 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day16 Problem, Part2: {}", problem.part2(input));
    }

    private int part1(String input) {
        var problemInput = parse(input);
        var tickets = problemInput.tickets();
        var rules = problemInput.rules();

        return tickets
                .stream()
                .map(ticketList -> getInvalidTickets(ticketList, rules))
                .mapToInt(t -> t.stream()
                        .mapToInt(x -> x)
                        .sum())
                .sum();
    }


    private int part2(String input) {
        var problemInput = parse(input);
        var tickets = problemInput.tickets();
        var rules = problemInput.rules();

        // keep valid tickets
        tickets.stream()
                .filter(ticketList -> !areAllTicketsValid(ticketList, rules).isEmpty())
                .forEach(System.out::println);
        return 0;
    }

    private List<Integer> getInvalidTickets(List<Integer> tickets, List<Rule> rules) {
        return tickets.stream()
                .filter(ticket -> rules.stream().noneMatch(rule -> rule.isValid().test(ticket)))
                .toList();
    }

    private List<Integer> areAllTicketsValid(List<Integer> tickets, List<Rule> rules) {
        return tickets.stream()
                .filter(ticket -> rules.stream().allMatch(rule -> rule.isValid().test(ticket)))
                .toList();
    }

    private ProblemInput parse(String input) {
        var parts = input.split(Constants.EMPTY_LINE);
        var fields = getFields(parts[0]);
        var tickets = getNearbyTickets(parts[2]);

        return new ProblemInput(fields, tickets);
    }

    private List<Rule> getFields(String input) {
        var lines = input.split(Constants.EOL);
        return Arrays.stream(lines)
                .map(line -> {
                    String[] parts = line.split(": ");
                    var ruleName = parts[0];
                    var rowRanges = parts[1].split(" or ");
                    var firstRule = rowRanges[0].split("-");
                    var secondRule = rowRanges[1].split("-");
                    IntPredicate isValid = ticketNumber ->
                            (Integer.parseInt(firstRule[0]) <= ticketNumber && ticketNumber <= Integer.parseInt(firstRule[1])) ||
                                    (Integer.parseInt(secondRule[0]) <= ticketNumber && ticketNumber <= Integer.parseInt(secondRule[1]));

                    return new Rule(ruleName, isValid);
                })
                .toList();
    }

    private List<List<Integer>> getNearbyTickets(String input) {
        return Arrays.stream(input.split(Constants.EOL))
                .skip(1)
                .map(line -> Arrays.stream(line.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
}

record ProblemInput(List<Rule> rules, List<List<Integer>> tickets) {
}

record Rule(String name, IntPredicate isValid) {
}

package io.aoc._2020.day16;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Problem {
    private final static Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 16, "input.txt");
        logger.info("Aoc2020, Day16 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day16 Problem, Part2: {}", problem.part2(input));
    }

    private int part1(String input) {
        var parts = input.split(Constants.EMPTY_LINE);
        var rules = createRules(parts[0]);
        var nearbyTickets = getNearbyTickets(parts[2]);

        return nearbyTickets.stream()
                .filter(ticket -> isInvalidTicket(ticket, rules))
                .mapToInt(x -> x)
                .sum();
    }

    private long part2(String input) {
        var parts = input.split(Constants.EMPTY_LINE);
        var rules = createRules(parts[0]);
        var validTickets = getTickets(parts[1]);
        var nearbyTickets = getNearbyTicketsAsGrid(parts[2], rules);
        System.out.println(nearbyTickets);
        for (var nearbyTicket: nearbyTickets) {
            System.out.print(nearbyTicket.size());
            System.out.println();
        }
//        var nearbyTicketsByColumn = transposed(nearbyTickets);
//        System.out.println(nearbyTicketsByColumn);

//        var nearbyTicketsRules = new ArrayList<List<String>>();
//        for (var tickets : nearbyTicketsByColumn) {
//            var ticketRules = new ArrayList<String>();
//            for (var rule : rules) {
//                if (areTicketsValid(tickets, rule)) {
//                    ticketRules.add(rule.name());
//                }
//            }
//            nearbyTicketsRules.add(ticketRules);
//        }
//
//        var labels = nearbyTicketsRules.stream()
//                .filter(list -> !list.isEmpty())
//                .toList();
//
//        var result = 1L;
//        var allLabels = labels.getFirst();
//        for (var i = 0; i < allLabels.size(); i++) {
//            if (allLabels.get(i).startsWith("departure")) {
//                result = result * validTickets.get(i);
//            }
//        }
//
//        return result;
        return 0;
    }

    private List<List<Integer>> transposed(List<List<Integer>> nearbyTickets) {
        var arr = new ArrayList<List<Integer>>();
        for (var i = 0; i < nearbyTickets.getFirst().size(); i++) {
            var collectList = new ArrayList<Integer>();
            for (List<Integer> nearbyTicket : nearbyTickets) {
                collectList.add(nearbyTicket.get(i));
            }
            arr.add(collectList);
        }

        return arr;
    }

    private boolean isInvalidTicket(Integer ticket, List<Rules> rules) {
        return rules.stream()
                .noneMatch(rule -> rule.isTicketValid(ticket));
    }

    private boolean areTicketsValid(List<Integer> tickets, Rules rule) {
        return tickets.stream()
                .allMatch(rule::isTicketValid);
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

    private List<List<Integer>> getNearbyTicketsAsGrid(String input, List<Rules> rules) {
        return Arrays.stream(input.split(Constants.EOL))
                .skip(1)
                .map(line -> Arrays.stream(line.split(","))
                        .map(Integer::parseInt)
                        .filter(ticketNumber -> !isInvalidTicket(ticketNumber, rules))
                        .toList())
                .toList();
    }

    private List<Integer> getTickets(String rowTickets) {
        return Arrays.stream(rowTickets.split(Constants.EOL))
                .skip(1)
                .flatMap(line -> Arrays.stream(line.split(Constants.COMMA))
                        .map(Integer::parseInt)
                )
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


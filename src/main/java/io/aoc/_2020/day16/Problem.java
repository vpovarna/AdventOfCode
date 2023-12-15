package io.aoc._2020.day16;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2020, 16, "input.txt");
        logger.info("Aoc2020, Day16 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day16 Problem, Part2: {}", problem.part2(input));
    }

    public int part1(String input) {
        final InputData inputData = getInput(input);

        int failure = 0;
        for (List<Integer> ticket : inputData.tickets()) {
            for (int value : ticket) {
                boolean valid = false;
                for (Field field : inputData.fields()) {
                    if (field.isValid(value)) {
                        valid = true;
                        break;
                    }
                }

                if (!valid) {
                    failure += value;
                }
            }
        }

        return failure;
    }

    public long part2(String input) {
        var inputData = getInput(input);
        var tickets = inputData.tickets();
        tickets = getValidTickets(tickets, inputData);

        final HashMap<String, Set<Integer>> candidates = new HashMap<>();
        final List<String> positions = new ArrayList<>();
        final Set<Integer> all = new HashSet<>();
        for (int i = 0; i < tickets.get(0).size(); i++) {
            positions.add(null);
            all.add(i);
        }

        for (Field field : inputData.fields()) {
            candidates.put(field.name(), new HashSet<>(all));
        }

        while (positions.stream().anyMatch(Objects::isNull)) {
            for (Field field : inputData.fields()) {
                final Set<Integer> pos = candidates.get(field.name());
                for (List<Integer> ticket : tickets) {
                    for (int i = 0; i < ticket.size(); i++) {
                        if (!field.isValid(ticket.get(i))) {
                            pos.remove(i);
                            if (pos.size() == 1) {
                                final int finalPos = new ArrayList<>(pos).get(0);
                                positions.set(finalPos, field.name());
                                for (var candidate : candidates.entrySet()) {
                                    if (candidate.getValue() == pos) {
                                        continue;
                                    }

                                    candidate.getValue().remove(finalPos);
                                    if (candidate.getValue().size() == 1) {
                                        positions.set(new ArrayList<>(candidate.getValue()).get(0), candidate.getKey());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return calculateTotal(tickets, positions);
    }

    private long calculateTotal(List<List<Integer>> tickets, List<String> positions) {
        var ticket = tickets.get(0);
        var total = 1L;
        for (int i = 0; i < ticket.size(); i++) {
            if (positions.get(i).startsWith("departure")) {
                total *= ticket.get(i);
            }
        }

        return total;
    }

    private static List<List<Integer>> getValidTickets(List<List<Integer>> tickets, InputData inputData) {
        final List<List<Integer>> tickets2 = new ArrayList<>();
        for (List<Integer> ticket : tickets) {
            boolean allValid = true;
            for (int value : ticket) {
                boolean valid = false;
                for (Field field : inputData.fields()) {
                    if (field.isValid(value)) {
                        valid = true;
                        break;
                    }
                }

                if (!valid) {
                    allValid = false;
                    break;
                }
            }

            if (allValid) {
                tickets2.add(ticket);
            }
        }
        return tickets2;
    }

    private InputData getInput(String inputFile) {
        final List<String> input = Arrays.stream(inputFile.split(Constants.EOL)).toList();
        final List<Field> fields = new ArrayList<>();
        final List<List<Integer>> tickets = new ArrayList<>();
        int i = 0;
        for (; ; i++) {
            final String line = input.get(i);
            if (line.isEmpty()) {
                break;
            }

            final String[] parts = line.split("(: )|-|( or )");
            fields.add(new Field(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4])));
        }

        i += 2;
        for (; i < input.size(); i++) {
            final String line = input.get(i);
            if (line.isEmpty()) {
                i++;
                continue;
            }

            tickets.add(Arrays.stream(line.split(",")).map(Integer::parseInt).toList());
        }

        return new InputData(fields, tickets);
    }

}

record InputData(List<Field> fields, List<List<Integer>> tickets) {
}

record Field(String name, int lower1, int upper1, int lower2, int upper2) {

    public boolean isValid(int value) {
        return (value >= this.lower1 && value <= this.upper1) || (value >= this.lower2 && value <= this.upper2);
    }
}

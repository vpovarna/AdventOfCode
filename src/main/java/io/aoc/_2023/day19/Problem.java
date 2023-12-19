package io.aoc._2023.day19;

import com.google.common.collect.Range;
import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Problem {

    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2023, 19, "input.txt");
        logger.info("Aoc2020, Day19 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day19 Problem, Part2: {}", problem.part2(input));
    }

    private long part1(String input) {
        var inputParts = input.split(Constants.EMPTY_LINE);
        var workflows = parseWorkflows(inputParts[0]);
        var parts = parseParts(inputParts[1]);

        var acceptedParts = new ArrayList<Map<Character, Integer>>();
        var sum = 0L;

        for (var part : parts) {
            var workflow = workflows.get("in");

            nextWorkflow:
            while (true) {
                for (var condition : workflow.conditions()) {
                    var registerValue = part.get(condition.register());

                    var conditionMatched = (condition.operation() == '<' && registerValue < condition.value()) ||
                            (condition.operation() == '>' && (registerValue > condition.value()));

                    if (conditionMatched) {
                        if (condition.targetWorkflow().equals("A")) {
                            acceptedParts.add(part);
                            break nextWorkflow;
                        }

                        if (condition.targetWorkflow().equals("R")) {
                            break nextWorkflow;
                        }

                        workflow = workflows.get(condition.targetWorkflow());
                        continue nextWorkflow;
                    }
                }

                if (workflow.targetWorkflow().equals("A")) {
                    acceptedParts.add(part);
                    break;
                }

                if (workflow.targetWorkflow().equals("R")) {
                    break;
                }

                workflow = workflows.get(workflow.targetWorkflow());
            }
        }

        for (var part : acceptedParts) {
            for (var es : part.entrySet()) {
                sum += es.getValue();
            }
        }

        return sum;
    }

    public long part2(String input) {
        var inputParts = input.trim().split(Constants.EMPTY_LINE);
        var sum = 0L;
        var workflows = parseWorkflows(inputParts[0]);
        var workflowQueue = new HashMap<String, ArrayList<Map<Character, Range<Integer>>>>();
        var acceptedRanges = new ArrayList<Map<Character, Range<Integer>>>();
        boolean hasUnprocessedRanges = true;

        for (var workflow: workflows.values()) {
            workflowQueue.put(workflow.name(), new ArrayList<>());
        }

        var partRanges = new ArrayList<HashMap<Character, Range<Integer>>>() {{
            add(new HashMap<>() {{
                put('x', Range.closed(1, 4000));
                put('m', Range.closed(1, 4000));
                put('a', Range.closed(1, 4000));
                put('s', Range.closed(1, 4000));
            }});
        }};

        workflowQueue.get("in").addAll(partRanges);

        while (hasUnprocessedRanges) {
            hasUnprocessedRanges = false;

            for (var work: workflowQueue.entrySet()) {
                if (work.getValue().isEmpty()) {
                    continue;
                }

                hasUnprocessedRanges = true;

                var workflow = workflows.get(work.getKey());
                var ranges = work.getValue();
                workflowQueue.put(work.getKey(), new ArrayList<>());

                nextRange:
                for (var range: ranges) {
                    for (var condition: workflow.conditions()) {
                        var registerRange = range.get(condition.register());
                        Range<Integer> conditionRange;
                        Range<Integer> remainingRange;

                        if (condition.operation() == '<') {
                            conditionRange = Range.closed(1, condition.value() - 1);
                            remainingRange = Range.closed(condition.value(), 4000);
                        } else {
                            conditionRange = Range.closed(condition.value() + 1, 4000);
                            remainingRange = Range.closed(1, condition.value());
                        }

                        if (!registerRange.isConnected(conditionRange)) {
                            continue;
                        }

                        var overlapping = registerRange.intersection(conditionRange);
                        var newRange = new HashMap<>(range);
                        newRange.put(condition.register(), overlapping);

                        if (condition.targetWorkflow().equals("A")) {
                            acceptedRanges.add(newRange);
                        } else if (!condition.targetWorkflow().equals("R")) {
                            workflowQueue.get(condition.targetWorkflow()).add(newRange);
                        }

                        if (remainingRange.isConnected(registerRange)) {
                            range.put(condition.register(), remainingRange.intersection(registerRange));
                        } else {
                            continue nextRange;
                        }
                    }

                    if (workflow.targetWorkflow().equals("A")) {
                        acceptedRanges.add(range);
                    } else if (!workflow.targetWorkflow().equals("R")) {
                        workflowQueue.get(workflow.targetWorkflow()).add(range);
                    }
                }
            }
        }

        for (var acceptedRange: acceptedRanges) {
            var rangeProduct = 1L;
            for (var es: acceptedRange.entrySet()) {
                var range = es.getValue();
                rangeProduct *= range.upperEndpoint() - range.lowerEndpoint() + 1;
            }

            sum += rangeProduct;
        }

        return sum;
    }

    private HashMap<String, Workflow> parseWorkflows(String input) {
        var workflows = new HashMap<String, Workflow>();
        var workflowsLines = input.split(Constants.EOL);

        for (var workflowLine : workflowsLines) {
            var conditions = new LinkedList<Condition>();
            var workflowHeaders = matchGroups("(\\w+)\\{(.*)\\}", workflowLine);
            var workflowParts = workflowHeaders.get(1).split(Constants.COMMA);

            for (var i = 0; i < workflowParts.length - 1; i++) {
                var c = matchGroups("(\\w+)([^\\w])([0-9]+):(\\w+)", workflowParts[i]);
                conditions.add(new Condition(c.get(0).charAt(0), c.get(1).charAt(0), Integer.parseInt(c.get(2)), c.get(3)));
            }

            workflows.put(
                    workflowHeaders.get(0),
                    new Workflow(workflowHeaders.get(0), conditions, workflowParts[workflowParts.length - 1])
            );
        }

        return workflows;
    }

    private ArrayList<HashMap<Character, Integer>> parseParts(String input) {
        var parts = new ArrayList<HashMap<Character, Integer>>();
        var partsLines = input.split(Constants.EOL);

        for (var partLine : partsLines) {
            var partRegisters = partLine.substring(1, partLine.length() - 1).split(",");
            var part = new HashMap<Character, Integer>();

            for (var partRegister : partRegisters) {
                var registerParts = partRegister.split("=");
                part.put(registerParts[0].charAt(0), Integer.parseInt(registerParts[1]));
            }

            parts.add(part);
        }

        return parts;
    }

    public static List<String> matchGroups(String regex, String input) {
        var m = Pattern.compile(regex).matcher(input);
        var l = new ArrayList<String>();

        if (!m.find()) {
            return Collections.emptyList();
        }

        for (var i = 1; i <= m.groupCount(); i++) {
            var val = m.group(i);

            if (val != null) {
                l.add(val);
            }
        }

        return l;
    }
}

record Workflow(String name, List<Condition> conditions, String targetWorkflow) {
}

record Condition(Character register, Character operation, Integer value, String targetWorkflow) {
}


package io.aoc._2023.day19;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Pattern;

public class Problem {

    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2023, 19, "input.txt");
        logger.info("Aoc2020, Day17 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day17 Problem, Part2: {}", problem.part2(input));
    }

    private int part1(String input) {
        var inputParts = input.split(Constants.EMPTY_LINE);
        var workflows = parseWorkflow(inputParts[0]);
        var parts = parseParts(inputParts[1]);

        parts.stream().forEach(System.out::println);
        return 0;
    }
    
    private int part2(String input) {
        return 0;
    }

    private Map<String, Workflow> parseWorkflow(String input) {
        var workflows = new HashMap<String, Workflow>();
        var workflowLines = input.split(Constants.EOL);

        for (var workflowLine: workflowLines) {
            var condition = new LinkedList<Condition>();
            var workflowHeaders = matchGroups("(\\w+)\\{(.*)\\}", workflowLine);


        }

        return new HashMap<>();
    }

    private ArrayList<Map<Character, Integer>> parseParts(String input) {
        var partsList = new ArrayList<Map<Character, Integer>>();
        var lines = input.split(Constants.EOL);

        for (var line : lines) {
            var registerParts = line.substring(1, line.length() - 2).split(Constants.COMMA);
            var parts = new HashMap<Character, Integer>();

            for (var registerPart : registerParts) {
                parts.put(registerPart.charAt(0), Integer.parseInt(registerPart.substring(2)));
            }

            partsList.add(parts);
        }
    
        return partsList;
    }

    public static List<String> matchGroups(String regex, String input) {
        var m = Pattern.compile(regex).matcher(input);
        var l = new ArrayList<String>();

        if (!m.find()) {
            return null;
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

record Workflow(String name, List<Condition> conditions, String targetWorkflow){}
record Condition(Character register, Character operation, Integer value, String targetWorkflow){}


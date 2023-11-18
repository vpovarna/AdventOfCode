package io.aoc._2020.day07;

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
        var input = Utils.readInputFileAsString(2020, 7, "input.txt");
        logger.info("Aoc2020, Day7 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day7 Problem, Part2: {}", problem.part2(input));
    }

    public int part1(String input) {
        Map<String, HashSet<String>> bagHashSetMap = buildBagsMap(input);

        var results = new HashSet<>();

        var stack = new ArrayDeque<>(bagHashSetMap.get("shiny gold bag"));
        while (!stack.isEmpty()) {
            var bag = stack.pop();
            if (bagHashSetMap.containsKey(bag)) {
                stack.addAll(bagHashSetMap.get(bag));
            }
            results.add(bag);
        }
        return results.size();
    }

    public int part2(String input) {
        var childrenOf = new HashMap<String, List<Bag>>();
        for (var line : input.split(Constants.EOL)) {
            var bagMapping = parseLine(line.trim());
            childrenOf.put(bagMapping.bag(), bagMapping.children());
        }

        ArrayList<Integer> results = new ArrayList<>();
        countWithChildrenRec(childrenOf, "shiny gold bag", results);
        return results.stream().mapToInt(x -> x).sum();
    }

    private void countWithChildrenRec(Map<String, List<Bag>> childrenOf, String bagName, List<Integer> results) {
        var childrenBags = childrenOf.get(bagName);
        for (var childrenBag : childrenBags) {
            results.add(childrenBag.count() * countWithChildren(childrenOf, childrenBag.bagName()));
        }
    }

    private int countWithChildren(Map<String, List<Bag>> childrenOf, String bagName) {
        // This can be called: countWithChildren(childrenOf, "shiny gold bag") - 1;

        return 1 + childrenOf.get(bagName).stream()
                .map(bag -> bag.count() * countWithChildren(childrenOf, bag.bagName()))
                .mapToInt(x -> x)
                .sum();
    }

    protected Map<String, HashSet<String>> buildBagsMap(String input) {
        var parentsOf = new HashMap<String, HashSet<String>>();
        var lines = input.split(Constants.EOL);
        for (var line : lines) {
            var bagMapping = parseLine(line.trim());
            for (var mapping : bagMapping.children()) {
                String bagName = mapping.bagName();
                HashSet<String> set = parentsOf.getOrDefault(bagName, new HashSet<>());
                set.add(bagMapping.bag());
                parentsOf.put(bagName, set);
            }
        }

        return parentsOf;
    }

    private BagsMapping parseLine(String line) {
        var bag = getBag(line);
        if (bag == null) {
            throw new IllegalArgumentException("Unable to extract the bad name: " + line);
        }

        var childrenBags = extractChildren(line);
        return new BagsMapping(bag, childrenBags);
    }

    private static List<Bag> extractChildren(String line) {
        var pattern = Pattern.compile("(\\d+) ([a-z]+ [a-z]+ bag)");
        var matcher = pattern.matcher(line);

        var childrenBags = new ArrayList<Bag>();
        while (matcher.find()) {
            var substring = matcher.group();

            try {
                var tokens = substring.split(" ", 2);
                var bags = new Bag(Integer.parseInt(tokens[0]), tokens[1]);
                childrenBags.add(bags);
            } catch (Exception e) {
                throw new IllegalArgumentException("Unable to parse: " + substring);
            }
        }

        return childrenBags;
    }

    private static String getBag(String line) {
        var pattern = Pattern.compile("^[a-z]+ [a-z]+ bag");
        var matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

}

record Bag(int count, String bagName) {
}

record BagsMapping(String bag, List<Bag> children) {
}

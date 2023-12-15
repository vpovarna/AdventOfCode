package io.aoc._2023.day15;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2023, 15, "input.txt");
        logger.info("Aoc2023, Day15 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2023, Day15 Problem, Part2: {}", problem.part2(input));
    }

    private int part1(String input) {
        return Arrays.stream(input.split(Constants.COMMA))
                .mapToInt(this::getStringHash)
                .sum();
    }

    private int part2(String input) {
        var box = new HashMap<Integer, Deque<Marker>>();

        var sequence = input.split(Constants.COMMA);
        for (var str : sequence) {
            var instruction = getInstruction(str);
            var instructionLabel = instruction.label();
            var instructionFocalLength = instruction.focalLength();
            var hash = getStringHash(instructionLabel);

            if (instruction.operation() == '=') {
                var marker = new Marker(instructionLabel, instructionFocalLength);

                var deque = box.getOrDefault(hash, new ArrayDeque<>());
                // deque is empty, just add the label marker
                if (deque.isEmpty()) {
                    deque.add(marker);
                    box.put(hash, deque);

                } else {
                    // check if there is already a lens in the box with the same label.
                    var someLabel = getSomeLabel(deque, instructionLabel);

                    // If the label is present, replace the focalLength.
                    if (someLabel.isPresent()) {
                        box.put(hash, replaceLabel(deque, marker));

                        // Update the deque
                    } else {
                        deque.addLast(marker);
                        box.put(hash, deque);
                    }
                }
            }

            if (instruction.operation() == '-') {
                box.computeIfPresent(hash, (key, deque) -> removeLabel(deque, instructionLabel));
            }

        }
        return calculateResult(box);
    }

    private Instruction getInstruction(String str) {
        if (str.contains("=")) {
            var i = str.indexOf("=");
            var label = str.substring(0, i);
            var focalPoint = Integer.parseInt(str.substring(i + 1));
            return new Instruction(label, '=', focalPoint);
        }
        var i = str.indexOf("-");
        var label = str.substring(0, i);
        return new Instruction(label, '-', -1);
    }

    private int getStringHash(String str) {
        var ans = 0;
        for (var c : str.toCharArray()) {
            ans = hash(ans + c);
        }
        return ans;
    }

    private int hash(int c) {
        return (c * 17) % 256;
    }


    private static Optional<String> getSomeLabel(Deque<Marker> deque, String instructionLabel) {
        return deque.stream()
                .map(Marker::label)
                .filter(label -> label.equals(instructionLabel))
                .findAny();
    }

    private static ArrayDeque<Marker> removeLabel(Deque<Marker> deque, String instructionLabel) {
        return deque.stream()
                .filter(marker -> !marker.label().equals(instructionLabel))
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    private Deque<Marker> replaceLabel(Deque<Marker> deque, Marker marker) {
        return deque.stream()
                .map(m -> (m.label().equals(marker.label()) ? new Marker(m.label(), marker.focalLength()) : m))
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    private int calculateResult(HashMap<Integer, Deque<Marker>> box) {
        var ans = 0;
        for (var entry : box.entrySet()) {
            var boxIndex = entry.getKey() + 1;
            var deque = entry.getValue();

            // After clean-up ('-' operation), some dequeues might end up empty.
            // Ignore them here or remove them in the cleanup phase
            if (deque.isEmpty()) {
                continue;
            }

            var i = 1;
            for (var marker : deque) {
                ans += boxIndex * i * marker.focalLength();
                i++;
            }
        }

        return ans;
    }
}

record Instruction(String label, char operation, int focalLength) {
}

record Marker(String label, int focalLength) {
}
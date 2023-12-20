package io.aoc._2023.day20;

import io.aoc.utils.Constants;
import io.aoc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);

    public static void main(String[] args) {
        var problem = new Problem();
        var input = Utils.readInputFileAsString(2023, 20, "input.txt");
        logger.info("Aoc2020, Day20 Problem, Part1: {}", problem.part1(input));
        logger.info("Aoc2020, Day20 Problem, Part2: {}", problem.part2(input));
    }

    private int part1(String input) {
        var dayInput = parseInput(input);
        var broadcastTargets = dayInput.broadcastTargets();
        var modules = dayInput.modules();

        var lo = 0;
        var hi = 0;

        for (var i = 0; i < 1000; i++) {
            lo += 1;

            var queue = new ArrayDeque<BroadcastTarget>();
            for (var target : broadcastTargets) {
                queue.add(new BroadcastTarget("broadcaster", target, Pulse.Lo));
            }

            while (!queue.isEmpty()) {
                var broadcastTarget = queue.pop();
                var origin = broadcastTarget.name();
                var target = broadcastTarget.target();
                var pulse = broadcastTarget.pulse();

                if (pulse == Pulse.Lo) {
                    lo += 1;
                } else {
                    hi += 1;
                }

                if (!modules.containsKey(target)) {
                    continue;
                }

                var module = modules.get(target);

                if (module.type == '%') {
                    if (pulse == Pulse.Lo) {
                        module.memoryOff = !module.memoryOff;
                        var outgoing = (!module.memoryOff) ? Pulse.Hi : Pulse.Lo;
                        for (var output : module.outputs) {
                            queue.add(new BroadcastTarget(module.name, output, outgoing));
                        }
                    }
                } else {
                    module.memory.put(origin, pulse);
                    var allHi = module.memory.values().stream().allMatch(v -> v == Pulse.Hi);
                    var outgoing = (allHi) ? Pulse.Lo : Pulse.Hi;
                    for (var output : module.outputs) {
                        queue.add(new BroadcastTarget(module.name, output, outgoing));
                    }
                }
            }
        }
        return lo * hi;
    }

    private long part2(String input) {
        var dayInput = parseInput(input);
        var broadcastTargets = dayInput.broadcastTargets();
        var modules = dayInput.modules();

        var feeds = getFeeds(modules);
        var feed = feeds.get(0);
        var seen = getSeen(modules, feed);

        var cycleLengths = new HashMap<String, Integer>();
        var presses = 0;

        while (true) {
            presses += 1;
            var queue = new ArrayDeque<BroadcastTarget>();
            for (var target : broadcastTargets) {
                queue.add(new BroadcastTarget("broadcaster", target, Pulse.Lo));
            }

            while (!queue.isEmpty()) {
                var broadcastTarget = queue.pop();
                var origin = broadcastTarget.name();
                var target = broadcastTarget.target();
                var pulse = broadcastTarget.pulse();

                if (!modules.containsKey(target)) {
                    continue;
                }

                var module = modules.get(target);

                if (module.name.equals(feed) && pulse == Pulse.Hi) {
                    var newValue = seen.get(origin);
                    seen.put(origin, newValue + 1);

                    if (!cycleLengths.containsKey(origin)) {
                        cycleLengths.put(origin, presses);
                    } else {
                        assert presses == seen.get(origin) * cycleLengths.get(origin);
                    }
                }

                var allSeen = seen.values().stream().allMatch(v -> v > 0);
                if (allSeen) {
                    var x = 1L;
                    for (var cycleLength : cycleLengths.values()) {
                        x = x * cycleLength / gcd(x, cycleLength);
                    }
                    return x;
                }

                if (module.type == '%') {
                    if (pulse == Pulse.Lo) {
                        module.memoryOff = !module.memoryOff;
                        var outgoing = (!module.memoryOff) ? Pulse.Hi : Pulse.Lo;
                        for (var output : module.outputs) {
                            queue.add(new BroadcastTarget(module.name, output, outgoing));
                        }
                    }
                } else {
                    module.memory.put(origin, pulse);
                    var allHi = module.memory.values().stream().allMatch(v -> v == Pulse.Hi);
                    var outgoing = (allHi) ? Pulse.Lo : Pulse.Hi;
                    for (var output : module.outputs) {
                        queue.add(new BroadcastTarget(module.name, output, outgoing));
                    }
                }

            }
        }
    }

    long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    private static ArrayList<String> getFeeds(Map<String, Module> modules) {
        var feeds = new ArrayList<String>();
        for (var kv : modules.entrySet()) {
            var module = kv.getValue();
            var name = kv.getKey();
            if (module.outputs.contains("rx")) {
                feeds.add(name);
            }
        }
        return feeds;
    }

    private static HashMap<String, Integer> getSeen(Map<String, Module> modules, String feed) {
        var seen = new HashMap<String, Integer>();
        for (var kv : modules.entrySet()) {
            var module = kv.getValue();
            var name = kv.getKey();
            if (module.outputs.contains(feed)) {
                seen.put(name, 0);
            }
        }
        return seen;
    }

    private Input parseInput(String input) {
        List<String> broadcasterTargets = null;
        var modules = new HashMap<String, Module>();

        var lines = input.split(Constants.EOL);
        for (var line : lines) {
            var parts = line.trim().split(" -> ");
            var outputs = Arrays.stream(parts[1].split(", ")).toList();
            if (Objects.equals(parts[0], "broadcaster")) {
                broadcasterTargets = outputs;
                continue;
            }

            var type = parts[0].charAt(0);
            var name = parts[0].substring(1);

            modules.put(name, new Module(name, type, outputs));
        }

        for (var kv : modules.entrySet()) {
            var outputs = kv.getValue().outputs;
            var name = kv.getKey();
            for (var output : outputs) {
                if (modules.containsKey(output) && modules.get(output).type == '&') {
                    var module = modules.get(output);
                    module.memory.put(name, Pulse.Lo);
                }
            }
        }

        return new Input(modules, broadcasterTargets);
    }
}

record Input(Map<String, Module> modules, List<String> broadcastTargets) {
}

record BroadcastTarget(String name, String target, Pulse pulse) {
}

class Module {
    public String name;
    public char type;
    public List<String> outputs;

    public boolean memoryOff = false;
    public Map<String, Pulse> memory;

    public Module(String name, char type, List<String> outputs) {
        this.name = name;
        this.type = type;
        this.outputs = outputs;
        if (type == '%') {
            memoryOff = true;
        } else {
            this.memory = new HashMap<>();
        }
    }

    @Override
    public String toString() {
        return this.name + "{type=" + this.type + ",outputs=" + String.join(",", outputs) + ",memoryOff=" + memoryOff + ",memory=" + this.memory + "}";
    }
}

enum Pulse {
    Lo, Hi
}

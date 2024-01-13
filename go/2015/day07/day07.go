package main

import (
	"aoc/cast"
	"flag"
	"fmt"
	"math"
	"os"
	"regexp"
	"strings"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)

	if err != nil {
		panic(err)
	}

	input := string(bytes)

	fmt.Printf("AoC 2015, Day7, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2015, Day7, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	return assembly(input, 1)
}

func part2(input string) int {
	return assembly(input, 2)
}

func assembly(input string, part int) int {
	wireToRule := map[string]string{}

	for _, inst := range strings.Split(input, "\n") {
		parts := strings.Split(inst, " -> ")
		wireToRule[parts[1]] = parts[0]
	}

	aSignal := dfs(wireToRule, "a", map[string]int{})
	if part == 1 {
		return aSignal
	}

	wireToRule["b"] = fmt.Sprintf("%d", aSignal)
	return dfs(wireToRule, "a", map[string]int{})
}

func dfs(graph map[string]string, entry string, memo map[string]int) int {
	if memValue, ok := memo[entry]; ok {
		return memValue
	}

	if regexp.MustCompile("[0-9]").MatchString(entry) {
		return cast.ToInt(entry)
	}

	sourceRule := graph[entry]
	parts := strings.Split(sourceRule, " ")

	var result int
	switch {
	case len(parts) == 1:
		result = dfs(graph, parts[0], memo)
	case parts[0] == "NOT":
		start := dfs(graph, parts[1], memo)
		result = (math.MaxUint16) ^ start
	case parts[1] == "AND":
		result = dfs(graph, parts[0], memo) & dfs(graph, parts[2], memo)
	case parts[1] == "OR":
		result = dfs(graph, parts[0], memo) | dfs(graph, parts[2], memo)
	case parts[1] == "LSHIFT":
		result = dfs(graph, parts[0], memo) << dfs(graph, parts[2], memo)
	case parts[1] == "RSHIFT":
		result = dfs(graph, parts[0], memo) >> dfs(graph, parts[2], memo)
	}
	memo[entry] = result
	return result
}

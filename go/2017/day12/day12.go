package main

import (
	"aoc/cast"
	"flag"
	"fmt"
	"os"
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
	fmt.Printf("AoC 2017, Day11, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2017, Day11, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	graph := buildGraph(input)

	var count int
	for k := range graph {
		if dfs(graph, k, 0, map[int]bool{}) {
			count++
		}
	}
	return count
}

func part2(input string) int {
	graph := buildGraph(input)

	allKeys := []int{}
	for k := range graph {
		allKeys = append(allKeys, k)
	}

	var groupCount int
	// nodes that have been added to a group (that has been counted)
	hasBeenGrouped := map[int]bool{}


	for target := range graph {
		if !hasBeenGrouped[target] {
			for k := range graph {
				if k != target && !hasBeenGrouped[k] {
					if dfs(graph, k, target, map[int]bool{}) {
						hasBeenGrouped[k] = true
					}
				}
			}
			groupCount++
		}
	}

	return groupCount
	return -1
}

func buildGraph(input string) map[int][]int {
	lines := strings.Split(input, "\n")
	graph := make(map[int][]int, 0)
	for _, line := range lines {
		parts := strings.Split(line, " <-> ")
		id := cast.ToInt(parts[0])
		for _, child := range strings.Split(parts[1], ", ") {
			graph[id] = append(graph[id], cast.ToInt(child))
		}
	}

	return graph
}

func dfs(graph map[int][]int, entry int, target int, visited map[int]bool) bool {
	if visited[entry] {
		return false
	}

	visited[entry] = true

	for _, child := range graph[entry] {
		if child == target || dfs(graph, child, target, visited) {
			return true
		}
	}

	return false
}

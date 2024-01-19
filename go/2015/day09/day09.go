package main

import (
	"flag"
	"fmt"
	"math"
	"os"
	"strings"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

type mapGraph map[string]map[string]int

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)

	if err != nil {
		panic(err)
	}

	input := string(bytes)

	fmt.Printf("AoC 2015, Day9, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2015, Day9, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	minDistance, _ := calculateDistance(input)
	return minDistance
}

func part2(input string) int {
	_, maxDistance := calculateDistance(input)
	return maxDistance
}

func calculateDistance(input string) (int, int) {
	graph := parseInput(input)

	minDistance := math.MaxInt32
	maxDistance := 0

	for k := range graph {
		dfsMin, dfsMax := dfs(graph, k, map[string]bool{k: true})
		minDistance = min(minDistance, dfsMin)
		maxDistance = max(maxDistance, dfsMax)
	}

	return minDistance, maxDistance
}

func dfs(graph mapGraph, entry string, visited map[string]bool) (int, int) {
	if len(visited) == len(graph) {
		return 0, 0
	}

	minDistance := math.MaxInt32
	maxDistance := 0

	for k := range graph {
		if !visited[k] {
			visited[k] = true
			wight := graph[entry][k]
			minRecursive, maxRecursive := dfs(graph, k, visited)
			minDistance = min(minDistance, wight+minRecursive)
			maxDistance = max(maxDistance, wight+maxRecursive)

			delete(visited, k)
		}
	}

	return minDistance, maxDistance
}

func parseInput(input string) mapGraph {
	graph := make(mapGraph)

	for _, line := range strings.Split(input, "\n") {
		var fromTown, toTown string
		var distance int

		fmt.Sscanf(line, "%s to %s = %d", &fromTown, &toTown, &distance)

		if graph[fromTown] == nil {
			graph[fromTown] = map[string]int{}
		}

		if graph[toTown] == nil {
			graph[toTown] = map[string]int{}
		}

		graph[fromTown][toTown] = distance
		graph[toTown][fromTown] = distance
	}

	return graph
}

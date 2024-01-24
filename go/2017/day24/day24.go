package main

import (
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

	fmt.Printf("AoC 2017, Day24, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2017, Day24, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	bestStrength, _ := mount(input)
	return bestStrength
}

func part2(input string) int {
	_, longestBridge := mount(input)
	return calculateStrengthOfBridge(longestBridge)
}

func mount(input string) (bestStrength int, longestBridge [][2]int) {
	edges := getEdges(input)

	bridge := [][2]int{
		{0, 0},
	}

	usedEdges := map[[2]int]bool{}

	for _, edge := range edges {
		usedEdges[edge] = false
	}

	return backtrackBridge(bridge, usedEdges)
}

func backtrackBridge(bridge [][2]int, usedEdges map[[2]int]bool) (bestStrength int, longestBridge [][2]int) {
	lastVal := bridge[len(bridge)-1][1]
	for edge, isUsed := range usedEdges {
		// skip edges that are marked as used
		if !isUsed {
			clonedEdge := edge
			// swap the edge vals if the first doesn't match
			if clonedEdge[0] != lastVal {
				clonedEdge[0], clonedEdge[1] = clonedEdge[1], clonedEdge[0]
			}
			// if match is found, add it to bridge, mark as used
			// add to strength, recurse
			if clonedEdge[0] == lastVal {
				bridge = append(bridge, clonedEdge)
				usedEdges[edge] = true
				strength := clonedEdge[0] + clonedEdge[1]

				// recurse and bestStrength and longestLength
				subStrength, subLongestBridge := backtrackBridge(bridge, usedEdges)

				strength += subStrength

				// if current bridge is longest (or wins tiebreak) set the longestBridge
				if len(bridge) > len(longestBridge) ||
					(len(bridge) == len(longestBridge) &&
						calculateStrengthOfBridge(bridge) > calculateStrengthOfBridge(longestBridge)) {
					// use this hacky append to create a copy of the bridge slice
					// otherwise appends could modify the underlying array
					longestBridge = append([][2]int{}, bridge...)
				}
				// also check if a recursive call had the longest bridge, update longest
				if len(subLongestBridge) > len(longestBridge) ||
					(len(subLongestBridge) == len(longestBridge) &&
						calculateStrengthOfBridge(subLongestBridge) > calculateStrengthOfBridge(longestBridge)) {
					longestBridge = append([][2]int{}, subLongestBridge...)
				}

				// backtrack
				usedEdges[edge] = false
				bridge = bridge[:len(bridge)-1]
				if strength > bestStrength {
					bestStrength = strength
				}
			}
		}
	}

	return bestStrength, longestBridge
}

func calculateStrengthOfBridge(bridge [][2]int) int {
	var sum int
	for _, edge := range bridge {
		sum += edge[0] + edge[1]
	}
	return sum
}

func getEdges(input string) (edges [][2]int) {
	for _, line := range strings.Split(input, "\n") {
		var pair [2]int
		fmt.Sscanf(line, "%d/%d", &pair[0], &pair[1])
		edges = append(edges, pair)
	}
	return edges
}

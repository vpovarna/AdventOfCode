package main

import (
	"aoc/algos"
	"flag"
	"fmt"
	"os"
	"strings"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

var startingPattern = `.#.
..#
###`

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)

	if err != nil {
		panic(err)
	}

	input := string(bytes)

	fmt.Printf("AoC 2017, Day21, Part1 solution is: %d \n", fractalArt(input, 5))
	fmt.Printf("AoC 2017, Day21, Part2 solution is: %d \n", fractalArt(input, 18))
}

func fractalArt(input string, rounds int) int {
	var state [][]string
	for _, line := range strings.Split(startingPattern, "\n") {
		state = append(state, strings.Split(line, ""))
	}

	rules := parseInput(input)

	for i := 0; i < rounds; i++ {
		state = tick(state, rules)
	}

	var count int
	for _, row := range state {
		for _, v := range row {
			if v == "#" {
				count++
			}
		}
	}
	return count
}

func parseInput(input string) map[string][][]string {

	makeGridFromString := func(str string) [][]string {
		var grid [][]string
		for _, line := range strings.Split(str, "/") {
			grid = append(grid, strings.Split(line, ""))
		}
		return grid
	}

	stringifyGrid := func(grid [][]string) (str string) {
		for _, row := range grid {
			for _, v := range row {
				str += v
			}
		}
		return str
	}

	rules := map[string][][]string{}
	for _, line := range strings.Split(input, "\n") {
		parts := strings.Split(line, " => ")
		keyGrid := makeGridFromString(parts[0])
		resultGrid := makeGridFromString(parts[1])

		for i := 0; i < 4; i++ {
			keyGrid = algos.RotateStringGrid(keyGrid)
			rules[stringifyGrid(keyGrid)] = resultGrid
			rules[stringifyGrid(algos.MirrorStringGrid(keyGrid))] = resultGrid
		}

	}

	return rules
}

func tick(grid [][]string, rules map[string][][]string) [][]string {
	var nextState [][]string

	// determine the size of break up the grid by. prioritize 2x2 grids
	var edgeSize int
	if len(grid)%2 == 0 {
		edgeSize = 2
	} else if len(grid)%3 == 0 {
		edgeSize = 3
	} else {
		panic("grid is not evenly divisible by 2 or 3,")
	}

	for r := 0; r < len(grid); r += edgeSize {
		for i := 0; i < edgeSize+1; i++ {
			nextState = append(nextState, []string{})
		}
		for c := 0; c < len(grid[0]); c += edgeSize {
			var strToMatch string
			for i := 0; i < edgeSize; i++ {
				for j := 0; j < edgeSize; j++ {
					strToMatch += grid[r+i][c+j]
				}
			}

			resulting, ok := rules[strToMatch]
			if !ok {
				panic("No matching pattern found for " + strToMatch)
			}

			for i, vals := range resulting {
				nextStateIndex := len(nextState) - edgeSize - 1 + i
				nextState[nextStateIndex] = append(nextState[nextStateIndex], vals...)
			}
		}
	}

	return nextState
}

package main

import (
	"flag"
	"fmt"
	"os"
	"regexp"
	"strings"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

var dirs = [4][2]int{
	{1, 0},  // down
	{0, -1}, // left
	{-1, 0}, // up
	{0, 1},  // right
}

var pathRegexp = regexp.MustCompile("^[-|+A-Z]$")
var capsRegexp = regexp.MustCompile("^[A-Z]$")

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)

	if err != nil {
		panic(err)
	}

	input := string(bytes)

	fmt.Printf("AoC 2017, Day19, Part1 solution is: %s \n", part1(input))
	fmt.Printf("AoC 2017, Day19, Part2 solution is: %d \n", part2(input))
}

func part1(input string) string {
	visitedChars, _ := movePacket(input)
	return visitedChars
}

func part2(input string) int {
	_, steps := movePacket(input)
	return steps
}

func movePacket(input string) (visitedChars string, steps int) {
	grid := parseInput(input)

	// find the starting point
	var row, col int
	for c := 0; c < len(grid[0]); c++ {
		if grid[0][c] == "|" {
			col = c
			break
		}
	}

	// track which index in dirs the current direction, start facing down
	var dirIndex int

	steps = 1

	for {
		inFrontVal := getNextValue(grid, row, col, dirs[dirIndex])

		if pathRegexp.MatchString(inFrontVal) {
			row += dirs[dirIndex][0]
			col += dirs[dirIndex][1]
			steps++

			if capsRegexp.MatchString(inFrontVal) {
				visitedChars += inFrontVal
			}
		} else if inFrontVal == " " {
			// turn right
			dirIndex = (dirIndex + 1) % 4
			if pathRegexp.MatchString(getNextValue(grid, row, col, dirs[dirIndex])) {
				continue
			}

			// turn left
			dirIndex = (dirIndex + 2) % 4
			if pathRegexp.MatchString(getNextValue(grid, row, col, dirs[dirIndex])) {
				continue
			}
			break
		} else {
			panic("unknown char " + inFrontVal)
		}
	}

	return visitedChars, steps
}

func getNextValue(grid [][]string, row, col int, diff [2]int) string {
	inFrontRow := row + diff[0]
	inFrontCol := col + diff[1]

	if inFrontRow < 0 || inFrontRow >= len(grid) || inFrontCol < 0 || inFrontCol >= len(grid[0]) {
		return " "
	}

	return grid[inFrontRow][inFrontCol]
}

func parseInput(input string) [][]string {
	var grid [][]string
	for _, line := range strings.Split(input, "\n") {
		grid = append(grid, strings.Split(line, ""))
	}

	return grid
}

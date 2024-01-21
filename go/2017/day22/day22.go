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

	fmt.Printf("AoC 2017, Day22, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2017, Day22, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	infectedMap := parseInput(input)

	mid := len(strings.Split(input, "\n")) / 2
	current := [2]int{mid, mid}

	var dirIndex, countBursts int

	for i := 0; i < 10000; i++ {
		switch infectedMap[current] {
		case infected:
			infectedMap[current] = clean
			// turn right
			dirIndex = (dirIndex + 1) % 4
		case clean:
			infectedMap[current] = infected
			// turn left
			dirIndex = (dirIndex + 3) % 4
			countBursts++
		default:
			panic("unhandled case")
		}
		// move in the current direction
		current = [2]int{current[0] + dirs[dirIndex][0], current[1] + dirs[dirIndex][1]}
	}
	return countBursts
}

func part2(input string) int {
	infectedMap := parseInput(input)

	mid := len(strings.Split(input, "\n")) / 2
	current := [2]int{mid, mid}

	var dirIndex, countBursts int

	for i := 0; i < 10000000; i++ {
		switch infectedMap[current] {
		case clean:
			infectedMap[current] = weakened
			// turn left
			dirIndex = (dirIndex + 3) % 4
		case weakened:
			infectedMap[current] = infected
			// go in the same direction
			countBursts++
		case infected:
			infectedMap[current] = flagged
			// turn right
			dirIndex = (dirIndex + 1) % 4
		case flagged:
			infectedMap[current] = clean
			// opposite direction
			dirIndex = (dirIndex + 2) % 4
		default:
			panic("unhandled case")
		}
		current = [2]int{current[0] + dirs[dirIndex][0], current[1] + dirs[dirIndex][1]}
	}
	return countBursts
}

type infectedState int

const (
	clean    = iota
	infected = 1
	weakened = 2
	flagged  = 3
)

var dirs = [4][2]int{
	{-1, 0}, // UP
	{0, 1},  // RIGHT
	{1, 0},  // DOWN
	{0, -1}, // LEFT
}

func parseInput(input string) map[[2]int]infectedState {
	ans := map[[2]int]infectedState{}
	for i, line := range strings.Split(input, "\n") {
		for j, c := range strings.Split(line, "") {
			switch c {
			case "#":
				ans[[2]int{i, j}] = infected
			case ".":
				ans[[2]int{i, j}] = clean
			default:
				panic("unsupported character: " + c)
			}
		}
	}
	return ans
}

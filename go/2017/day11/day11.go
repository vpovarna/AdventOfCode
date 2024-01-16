package main

import (
	"aoc/utils"
	"flag"
	"fmt"
	"os"
	"strings"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

var dirIndices = map[string]int{
	"n":  0,
	"ne": 1,
	"se": 2,
	"s":  3,
	"sw": 4,
	"nw": 5,
}

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
	steps := strings.Split(input, ",")
	tallyDirections := make([]int, 6)

	for _, step := range steps {
		tallyDirections[dirIndices[step]]++
	}

	return getDistanceFromOrigin(tallyDirections)
}

func part2(input string) int {
	steps := strings.Split(input, ",")
	tallyDirections := make([]int, 6)

	var furthest int
	for _, step := range steps {
		tallyDirections[dirIndices[step]]++
		distanceFromStart := getDistanceFromOrigin(tallyDirections)
		furthest = max(distanceFromStart, furthest)

	}

	return furthest
}

func getDistanceFromOrigin(tally []int) int {
	for i := range tally {
		if tally[i] != 0 {
			oppositeIndex := (i + 3) % 6
			smaller := min(tally[oppositeIndex], tally[i])
			tally[oppositeIndex] -= smaller
			tally[i] -= smaller
		}
	}

	for i := range tally {
		toLeft := (i + 5) % 6
		toRight := (i + 1) % 6

		if tally[toLeft] > 0 && tally[toRight] > 0 {
			smaller := min(tally[toLeft], tally[toRight])
			tally[toLeft] -= smaller
			tally[toRight] -= smaller
			tally[i] += smaller
		}
	}

	return utils.SumIntSlice(tally)
}

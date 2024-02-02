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
		panic("Unable to read the input file")
	}

	input := string(bytes)

	fmt.Printf("AoC 2022, Day4, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2022, Day4, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	pairsList := parseInput(input)

	total := 0

	for _, pairs := range pairsList {
		if pairs[0].includes(pairs[1]) || pairs[1].includes(pairs[0]) {
			total += 1
		}
	}

	return total
}

func part2(input string) int {
	pairsList := parseInput(input)

	total := 0

	for _, pairs := range pairsList {
		if pairs[0].overlaps(pairs[1]) || pairs[1].overlaps(pairs[0]) {
			total += 1
		}
	}

	return total
}

type pair struct {
	x int
	y int
}

func (p1 *pair) includes(p2 pair) bool {
	return p1.x >= p2.x && p1.x <= p2.y && p1.y >= p2.x && p1.y <= p2.y
}

func (p1 *pair) overlaps(p2 pair) bool {
	return p1.y >= p2.x && p1.y <= p2.y ||
		p1.x >= p2.x && p1.x <= p2.y ||
		p1.x >= p2.x && p1.x <= p2.y && p1.y >= p2.x && p1.y <= p2.y
}

func parseInput(input string) [][2]pair {
	assignments := [][2]pair{}

	for _, line := range strings.Split(input, "\n") {
		pair1 := pair{}
		pair2 := pair{}

		fmt.Sscanf(line, "%d-%d,%d-%d", &pair1.x, &pair1.y, &pair2.x, &pair2.y)

		assignments = append(assignments, [2]pair{pair1, pair2})
	}

	return assignments
}

package main

import (
	"aoc/cast"
	"aoc/mathy"
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

	fmt.Printf("AoC 2022, Day9, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2022, Day9, Part2 solution is: %d \n", part2(input))
}

var directions = map[string][2]int{
	"U": {1, 0},
	"D": {-1, 0},
	"R": {0, 1},
	"L": {0, -1},
}

type instruction struct {
	direction string
	steps     int
}

func part1(input string) int {
	instructions := parseInput(input)

	var head, tail [2]int
	visited := map[[2]int]bool{
		{0, 0}: true,
	}

	for _, instruction := range instructions {
		for instruction.steps > 0 {
			diff := directions[instruction.direction]

			head[0] += diff[0]
			head[1] += diff[1]

			rowDiff := head[0] - tail[0]
			colDiff := head[1] - tail[1]

			if mathy.AbsInt(rowDiff) > 1 {
				tail[0] += rowDiff / 2
				if colDiff != 0 {
					tail[1] += colDiff
				}
			} else if mathy.AbsInt(colDiff) > 1 {
				tail[1] += colDiff / 2
				if rowDiff != 0 {
					tail[0] += rowDiff
				}
			}
			visited[tail] = true
			instruction.steps--
		}
	}
	return len(visited)
}

func part2(input string) int {
	return -1
}

func parseInput(input string) (instructions []instruction) {
	lines := strings.Split(input, "\n")
	for _, i := range lines {
		parts := strings.Split(i, " ")
		instructions = append(instructions, instruction{
			direction: parts[0],
			steps:     cast.ToInt(parts[1]),
		})
	}

	return instructions
}

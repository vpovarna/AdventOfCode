package main

import (
	"flag"
	"fmt"
	"os"
	"strings"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

type point struct {
	x int
	y int
}

func grid() map[point]rune {
	return map[point]rune{
		{0, 0}: '1',
		{0, 1}: '2',
		{0, 2}: '3',
		{1, 0}: '4',
		{1, 1}: '5',
		{1, 2}: '6',
		{2, 0}: '7',
		{2, 1}: '8',
		{2, 2}: '9',
	}
}

func anotherGrid() map[point]rune {
	return map[point]rune{
		{0, 2}: '1',
		{1, 1}: '2',
		{1, 2}: '3',
		{1, 3}: '4',
		{2, 0}: '5',
		{2, 1}: '6',
		{2, 2}: '7',
		{2, 3}: '8',
		{2, 4}: '9',
		{3, 1}: 'A',
		{3, 2}: 'B',
		{3, 3}: 'C',
		{4, 2}: 'D',
	}
}

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)
	if err != nil {
		panic(err)
	}

	input := string(bytes)
	fmt.Printf("AoC 2016, Day2, Part1 solution is: %s \n", part1(&input))
	fmt.Printf("AoC 2016, Day2, Part2 solution is: %s \n", part2(&input))

}

func part1(input *string) string {
	lines := strings.Split(*input, "\n")

	// starting point
	sp := point{1, 1}

	// input grid
	grid := grid()

	return run(lines, grid, &sp)
}

func part2(input *string) string {
	lines := strings.Split(*input, "\n")

	// starting point
	sp := point{2, 0}

	// input grid
	grid := anotherGrid()

	return run(lines, grid, &sp)
}

func run(lines []string, grid map[point]rune, sp *point) string {
	var code strings.Builder

	for _, line := range lines {
		for _, c := range line {
			switch c {
			case 'U':
				if grid[point{sp.x - 1, sp.y}] != 0 {
					sp.x -= 1
				}
				break
			case 'D':
				if grid[point{sp.x + 1, sp.y}] != 0 {
					sp.x += 1
				}
				break
			case 'L':
				if grid[point{sp.x, sp.y - 1}] != 0 {
					sp.y -= 1
				}
				break
			case 'R':
				if grid[point{sp.x, sp.y + 1}] != 0 {
					sp.y += 1
				}
				break
			}
		}
		code.WriteRune(grid[*sp])
	}

	return code.String()
}

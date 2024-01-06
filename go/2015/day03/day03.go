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

	fmt.Printf("AoC 2015, Day3, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2015, Day3, Part2 solution is: %d \n", part2(input))
}

type coordinate struct {
	x int
	y int
}

func part1(input string) int {
	visited := make(map[coordinate]int)

	coord := coordinate{0, 0}
	visited[coord]++

	for _, direction := range strings.Split(input, "") {
		coord = nextHouse(coord, direction)
		visited[coord]++
	}

	return len(visited)
}

func part2(input string) int {
	houseCount := map[coordinate]int{}
	santaCoord := coordinate{0, 0}
	robotCoord := coordinate{0, 0}

	houseCount[santaCoord]++
	houseCount[robotCoord]++

	for i, direction := range strings.Split(input, "") {
		if i%2 == 0 {
			santaCoord = nextHouse(santaCoord, direction)
			houseCount[santaCoord]++
		} else {
			robotCoord = nextHouse(robotCoord, direction)
			houseCount[robotCoord]++
		}
	}

	return len(houseCount)
}

func nextHouse(coord coordinate, direction string) coordinate {
	switch direction {
	case "^":
		coord = coordinate{coord.x, coord.y - 1}
	case "v":
		coord = coordinate{coord.x, coord.y + 1}
	case ">":
		coord = coordinate{coord.x + 1, coord.y}
	case "<":
		coord = coordinate{coord.x - 1, coord.y}
	default:
		panic("unknown direction")
	}
	return coord
}

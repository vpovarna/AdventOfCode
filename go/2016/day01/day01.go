package main

import (
	"flag"
	"fmt"
	"math"
	"os"
	"strconv"
	"strings"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

type point struct {
	x int
	y int
}

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)

	if err != nil {
		panic(err)
	}

	input := string(bytes)
	fmt.Printf("AoC 2016, Day1, Part1 solution is: %f \n", part1(&input))
	fmt.Printf("AoC 2016, Day1, Part2 solution is: %d \n", part2(&input))
}

func part1(input *string) float64 {
	lines := strings.Split(*input, ", ")
	return gridWalk(lines)
}

func part2(input *string) int {
	return 0
}

func gridWalk(instructions []string) float64 {
	startingPoint := point{0, 0}
	orientation := 0

	for _, instruction := range instructions {
		turn := instruction[0]
		rawSteps := instruction[1:]
		steps, err := strconv.Atoi(rawSteps)

		if err != nil {
			fmt.Printf("Unable to transform step: %s\n", rawSteps)
		}

		// Update orientation based on the direction: R / L
		if orientation < 0 {
			orientation += 4
		}

		if turn == 'R' {
			orientation = (orientation + 1) % 4
		} else {
			orientation = (orientation - 1) % 4
		}

		// rewrite this
		for i := 0; i < steps; i++ {
			switch orientation {
			case 0:
				startingPoint.y += 1
				break
			case 1:
				startingPoint.x += 1
				break
			case 2:
				startingPoint.y -= 1
				break
			default:
				startingPoint.x -= 1
				break
			}
		}
	}

	return math.Abs(float64(startingPoint.x)) + math.Abs(float64(startingPoint.y))
}

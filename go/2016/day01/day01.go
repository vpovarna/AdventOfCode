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

func (p *point) distance() float64 {
	return math.Abs(float64(p.x)) + math.Abs(float64(p.y))
}

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)

	if err != nil {
		panic(err)
	}

	input := string(bytes)
	fmt.Printf("AoC 2016, Day1, Part1 solution is: %f \n", part1(&input))
	fmt.Printf("AoC 2016, Day1, Part2 solution is: %f \n", part2(&input))
}

func part1(input *string) float64 {
	lines := strings.Split(*input, ", ")
	return gridWalk(lines, false)
}

func part2(input *string) float64 {
	lines := strings.Split(*input, ", ")
	return gridWalk(lines, true)
}

func gridWalk(instructions []string, uniqueDirections bool) float64 {
	currentPoint := point{0, 0}
	direction := 0

	visited := make(map[point]bool)

	for _, instruction := range instructions {
		turn := instruction[0]
		rawSteps := instruction[1:]
		steps, err := strconv.Atoi(rawSteps)

		if err != nil {
			fmt.Printf("Unable to transform step: %s\n", rawSteps)
		}

		if turn == 'R' {
			direction = (direction + 1) % 4
		} else {
			direction = (direction + 3) % 4
		}

		for i := 0; i < steps; i++ {
			switch direction {
			case 0:
				currentPoint.y += 1
				break
			case 1:
				currentPoint.x += 1
				break
			case 2:
				currentPoint.y -= 1
				break
			default:
				currentPoint.x -= 1
				break
			}

			if uniqueDirections {
				if visited[currentPoint] {
					return currentPoint.distance()
				}

				visited[currentPoint] = true
			}
		}
	}

	return currentPoint.distance()
}

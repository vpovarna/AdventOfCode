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

type coordinate struct {
	x int
	y int
}

func (coord *coordinate) moveUp() {
	coord.x--
}

func (coord *coordinate) moveDown() {
	coord.x++
}

func (coord *coordinate) moveRight() {
	coord.y++
}

func (coord *coordinate) moveLeft() {
	coord.y--
}

func (coord *coordinate) toString() string {
	return fmt.Sprintf("%dx%d", coord.x, coord.y)
}

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)

	if err != nil {
		panic(err)
	}

	input := string(bytes)
	fmt.Printf("AoC 2019, Day3, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2019, Day3, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	lines := strings.Split(input, "\n")
	if len(lines) != 2 {
		panic("invalid input")
	}

	// We need to pass two separate starting points
	coordinatesMap1 := getCoordinates(lines[0], &coordinate{0, 0}, 1)
	coordinatesMap2 := getCoordinates(lines[1], &coordinate{0, 0}, 1)

	dist := math.MaxInt32

	for coord1 := range coordinatesMap1 {
		if _, ok := coordinatesMap2[coord1]; ok {
			dist = min(dist, manhattanDistance(coord1))
		}
	}

	return dist
}

func part2(input string) int {
	lines := strings.Split(input, "\n")
	if len(lines) != 2 {
		panic("invalid input")
	}

	coordinatesMap1 := getCoordinates(lines[0], &coordinate{0, 0}, 1)
	coordinatesMap2 := getCoordinates(lines[1], &coordinate{0, 0}, 1)

	longestDistance := math.MaxInt32

	for key, dist1 := range coordinatesMap1 {
		if dist2, ok := coordinatesMap2[key]; ok {
			longestDistance = min(longestDistance, dist1+dist2)
		}
	}

	return longestDistance
}

func getCoordinates(rowCoordinates string, coord *coordinate, part int) map[string]int {

	coordinates := strings.Split(rowCoordinates, ",")
	runningLength := 0

	coordinatesMap := map[string]int{}

	for _, coordinate := range coordinates {
		direction := coordinate[0]
		steps, _ := strconv.Atoi(coordinate[1:])

		for steps > 0 {
			steps -= 1
			runningLength++

			switch direction {
			case 'U':
				coord.moveUp()
			case 'R':
				coord.moveRight()
			case 'D':
				coord.moveDown()
			case 'L':
				coord.moveLeft()
			default:
				panic("unhandled direction")
			}
			key := coord.toString()

			if part == 2 {
				if _, alreadyVisited := coordinatesMap[key]; !alreadyVisited {
					coordinatesMap[key] = runningLength
				}
			} else {
				coordinatesMap[key] = runningLength
			}
		}
	}
	return coordinatesMap
}

// this works since the starting point is (0, 0)
func manhattanDistance(coord string) int {
	parts := strings.Split(coord, "x")
	x, _ := strconv.Atoi(parts[0])
	y, _ := strconv.Atoi(parts[1])

	if x < 0 {
		x *= -1
	}

	if y < 0 {
		y *= -1
	}

	return x + y
}

package main

import (
	"flag"
	"fmt"
	"os"
	"strconv"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

type Coordinate struct {
	x int
	y int
}

type Point struct {
	coordinate Coordinate
	distance   int
}

func (c *Coordinate) neighbors() [4]Coordinate {
	return [4]Coordinate{
		{c.x + 1, c.y},
		{c.x - 1, c.y},
		{c.x, c.y - 1},
		{c.x, c.y + 1},
	}
}

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)
	if err != nil {
		panic(err)
	}

	input := string(bytes)

	fmt.Printf("AoC 2016, Day13, Part1 solution is: %d \n", part1(&input))
	fmt.Printf("AoC 2016, Day13, Part2 solution is: %d \n", part2(&input))
}

func part1(input *string) int {

	favNumber, err := strconv.Atoi(*input)

	if err != nil {
		panic("unable to convert the input to int")
	}

	return bfs(favNumber, Coordinate{31, 39})
}

func part2(input *string) int {
	return -1
}

func bfs(favNumber int, destination Coordinate) int {
	// Queue represented as a slice of arrays
	queue := []Point{{Coordinate{1, 1}, 0}} // starting point (Coordinate(x, y), distance)

	// HashSet to track the visited coordinates
	visited := map[Coordinate]bool{}

	for len(queue) > 0 {
		front := queue[0]

		currentPoint := front.coordinate
		currentDist := front.distance

		if currentPoint.x == destination.x && currentPoint.y == destination.y {
			return currentDist
		}

		// if not visited
		if !visited[Coordinate{currentPoint.x, currentPoint.y}] {
			for _, nextCoordinate := range currentPoint.neighbors() {
				if nextCoordinate.x >= 0 && nextCoordinate.y >= 0 {
					if isOpen(nextCoordinate.x, nextCoordinate.y, favNumber) {
						queue = append(queue, Point{nextCoordinate, currentDist + 1})
					}
				}
			}
		}
		visited[currentPoint] = true

		// Update queue
		queue = queue[1:]
	}

	return -1
}

func isOpen(x, y int, favNumber int) bool {
	sum := int64((x*x + 3*x + 2*x*y + y + y*y) + favNumber)
	binaryRep := strconv.FormatInt(sum, 2)

	counter := 0

	for _, c := range binaryRep {
		if c == '1' {
			counter += 1
		}
	}
	return counter%2 == 0
}

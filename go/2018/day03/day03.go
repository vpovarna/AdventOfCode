package main

import (
	"flag"
	"fmt"
	"os"
	"strconv"
	"strings"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

type Claim struct {
	id            int
	startingPoint Point
	wide          int
	tall          int
}

type Point struct {
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

	fmt.Printf("AoC 2018, Day3, Part1 solution is: %d \n", part1(&input))
	fmt.Printf("AoC 2018, Day3, Part2 solution is: %d \n", part2(&input))
}

func part1(input *string) int {
	lines := strings.Split(*input, "\r\n")
	overlapAreas, _ := decorate(lines)
	return overlapAreas
}

func part2(input *string) int {
	lines := strings.Split(*input, "\r\n")
	_, id := decorate(lines)
	return id
}

func decorate(lines []string) (int, int) {
	grid := make(map[Point]int)

	// Act like a HashSet. Keep track of the overlap claim ids
	ids := make(map[int]bool)
	overlap := 0

	for _, line := range lines {
		claim := getClaim(line)
		x := claim.startingPoint.x
		y := claim.startingPoint.y

		ids[claim.id] = true

		for i := x; i < x+claim.wide; i++ {
			for j := y; j < y+claim.tall; j++ {
				var point = Point{i, j}
				if grid[point] == 0 {

					// Add claim id to grit, not visited point
					grid[point] = claim.id
				} else if grid[point] == -1 {

					// Already visited. Remove the value from the ids field
					ids[claim.id] = false

				} else {

					// Remove both claims ids since both of them overlaps
					ids[grid[point]] = false
					ids[claim.id] = false

					// Set the grid point to -1
					grid[point] = -1

					// Increase the overlaps counter
					overlap++
				}
			}
		}
	}

	var l []int
	for id := range ids {
		if ids[id] {
			l = append(l, id)
		}
	}

	if len(l) == 0 {
		return overlap, -1
	}

	return overlap, l[0]
}

func getClaim(line string) Claim {
	parts := strings.Split(line, " @ ")

	// drop the # char from the line
	id, _ := strconv.Atoi(parts[0][1:])

	values := strings.Split(parts[1], ": ")
	coordinates := strings.Split(values[0], ",")

	x, _ := strconv.Atoi(coordinates[0])
	y, _ := strconv.Atoi(coordinates[1])
	point := Point{x, y}

	dimensions := strings.Split(values[1], "x")

	wide, _ := strconv.Atoi(dimensions[0])
	tall, _ := strconv.Atoi(dimensions[1])

	return Claim{
		id:            id,
		startingPoint: point,
		wide:          wide,
		tall:          tall,
	}
}

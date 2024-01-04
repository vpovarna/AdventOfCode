package main

import (
	"flag"
	"fmt"
	"os"
	"strconv"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

var dirs = [][2]int{
	{-1, 0},
	{1, 0},
	{0, -1},
	{0, 1},
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

	return bfs(favNumber, [2]int{31, 39})
}

func part2(input *string) int {
	return -1
}

func bfs(favNumber int, destination [2]int) int {
	// Queue represented as a slice of arrays
	queue := [][3]int{[3]int{1, 1, 0}} // starting pint (x, y, distance)

	// HashSet to track the visited coordinates
	visited := map[[2]int]bool{}

	for len(queue) > 0 {
		front := queue[0]

		currentX, currentY := front[0], front[1]
		currentDist := front[2]

		if currentX == destination[0] && currentY == destination[1] {
			return currentDist
		}

		// if not visited
		if !visited[[2]int{currentX, currentY}] {
			for _, dir := range dirs {
				nextX, nextY := dir[0]+currentX, dir[1]+currentY
				if nextX >= 0 && nextY >= 0 {
					if isOpen(nextX, nextY, favNumber) {
						queue = append(queue, [3]int{nextX, nextY, currentDist + 1})
					}
				}
			}
		}
		visited[[2]int{currentX, currentY}] = true

		// Update queue
		queue = queue[1:]
	}

	return -1
}

func isOpen(x, y int, favNumber int) bool {
	sum := int64((x*x + 3*x + 2*x*y + y + y*y) + favNumber)
	binaryRep := strconv.FormatInt(sum, 2)

	occurrence := map[rune]int{}

	for _, c := range binaryRep {
		occurrence[c] += 1
	}
	return occurrence[rune('1')]%2 == 0
}

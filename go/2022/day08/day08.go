package main

import (
	"aoc/cast"
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

	fmt.Printf("AoC 2022, Day8, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2022, Day8, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	grid := parseInput(input)
	visibleCoords := map[[2]int]string{}

	for r := 1; r < len(grid)-1; r++ {
		// from left
		highestFromLeft := -1
		for c := 0; c < len(grid[0])-1; c++ {
			height := grid[r][c]
			if height > highestFromLeft {
				currentPoint := [2]int{r, c}
				visibleCoords[currentPoint] = "L"
				highestFromLeft = height
			}
		}

		// from right
		highestFromRight := -1
		for c := len(grid[0]) - 1; c > 0; c-- {
			height := grid[r][c]
			if height > highestFromRight {
				currentPoint := [2]int{r, c}
				visibleCoords[currentPoint] = "R"
				highestFromRight = height
			}
		}
	}

	for c := 1; c < len(grid[0])-1; c++ {
		// from top
		highestFromTop := -1
		for r := 0; r < len(grid)-1; r++ {
			height := grid[r][c]
			if height > highestFromTop {
				currentPoint := [2]int{r, c}
				visibleCoords[currentPoint] = "T"
				highestFromTop = height
			}
		}

		// from bottom
		highestFromBottom := -1
		for r := len(grid) - 1; r > 0; r-- {
			heigh := grid[r][c]
			if heigh > highestFromBottom {
				currentPoint := [2]int{r, c}
				visibleCoords[currentPoint] = "B"
				highestFromBottom = heigh
			}
		}
	}

	return len(visibleCoords) + 4
}

func part2(input string) int {
	grid := parseInput(input)

	bestScore := 0
	// iterate through every eligible tree
	for r := 1; r < len(grid)-1; r++ {
		for c := 1; c < len(grid[0])-1; c++ {
			score := visible(grid, r, c, -1, 0)
			score *= visible(grid, r, c, 1, 0)
			score *= visible(grid, r, c, 0, -1)
			score *= visible(grid, r, c, 0, 1)

			if score > bestScore {
				bestScore = score
			}
		}
	}

	return bestScore
}

func visible(grid [][]int, r, c, dr, dc int) int {
	count := 0
	startingHeight := grid[r][c]
	r += dr
	c += dc

	for r >= 0 && r < len(grid) && c >= 0 && c < len(grid[0]) {
		height := grid[r][c]
		if height < startingHeight {
			count++
		} else {
			count++
			break
		}

		r += dr
		c += dc
	}

	return count
}

func parseInput(input string) (ans [][]int) {
	lines := strings.Split(input, "\n")
	for _, line := range lines {
		var row []int
		for _, n := range strings.Split(line, "") {
			row = append(row, cast.ToInt(n))
		}
		ans = append(ans, row)
	}
	return ans
}

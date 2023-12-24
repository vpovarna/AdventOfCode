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

	fmt.Printf("AoC 2016, Day8, Part1 solution is: %d \n", part1(&input))
	fmt.Printf("AoC 2016, Day8, Part2 solution is: \n%s \n", part2(&input))
}

func part1(input *string) int {
	count, _ := twoFA(input)
	return count
}

func part2(input *string) string {
	_, finalState := twoFA(input)
	return finalState
}

func twoFA(input *string) (int, string) {
	width := 50
	tall := 6

	var grid [][]bool
	for i := 0; i < tall; i++ {
		grid = append(grid, make([]bool, width))
	}

	for _, instruction := range strings.Split(*input, "\n") {
		if strings.HasPrefix(instruction, "rect") {
			var row, col int
			fmt.Sscanf(instruction, "rect %dx%d", &col, &row)
			for r := 0; r < row; r++ {
				for c := 0; c < col; c++ {
					grid[r][c] = true
				}
			}
		} else if strings.HasPrefix(instruction, "rotate row") {
			var row, by int
			_, err := fmt.Sscanf(instruction, "rotate row y=%d by %d", &row, &by)
			if err != nil {
				panic("parsing error on instruction: " + err.Error())
			}
			for count := 0; count < by; count++ {
				// tmp store the last row index value
				store := grid[row][width-1]
				for i := width - 1; i > 0; i-- {
					grid[row][i] = grid[row][i-1]
				}
				// update the first val with the store value
				grid[row][0] = store
			}
		} else if strings.HasPrefix(instruction, "rotate column") {
			var col, by int
			_, err := fmt.Sscanf(instruction, "rotate column x=%d by %d", &col, &by)
			if err != nil {
				panic("parsing error on instruction: " + err.Error())
			}
			for count := 0; count < by; count++ {
				// tmp store the col row index value
				store := grid[tall-1][col]
				for i := tall - 1; i > 0; i-- {
					grid[i][col] = grid[i-1][col]
				}
				// update the first val with the store value
				grid[0][col] = store
			}
		} else {
			panic("unhandled instruction: " + instruction)
		}
	}

	var count int
	var finalState string
	for _, row := range grid {
		for _, v := range row {
			if v {
				count += 1
				finalState += "#"
			} else {
				finalState += " "
			}
		}
		finalState += "\n"
	}
	return count, finalState
}

func printGrid(grid [][]bool, tall int, wide int) {
	// print grid
	for i := 0; i < tall; i++ {
		for j := 0; j < wide; j++ {
			fmt.Print(grid[i][j])
		}
		fmt.Println()
	}
}

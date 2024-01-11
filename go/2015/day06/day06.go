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

	fmt.Printf("AoC 2015, Day6, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2015, Day6, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	lines := strings.Split(input, "\n")
	grid := [1000][1000]bool{}

	// true  -> turn on
	// false -> turn off
	for _, line := range lines {
		var i1, j1, i2, j2 int

		switch {
		case strings.HasPrefix(line, "turn on"):
			fmt.Sscanf(line, "turn on %d,%d through %d,%d", &i1, &j1, &i2, &j2)
			for i := i1; i <= i2; i++ {
				for j := j1; j <= j2; j++ {
					grid[i][j] = true
				}
			}
		case strings.HasPrefix(line, "turn off"):
			fmt.Sscanf(line, "turn off %d,%d through %d,%d", &i1, &j1, &i2, &j2)
			for i := i1; i <= i2; i++ {
				for j := j1; j <= j2; j++ {
					grid[i][j] = false
				}
			}
		case strings.HasPrefix(line, "toggle"):
			fmt.Sscanf(line, "toggle %d,%d through %d,%d", &i1, &j1, &i2, &j2)
			for i := i1; i <= i2; i++ {
				for j := j1; j <= j2; j++ {
					if !grid[i][j] {
						grid[i][j] = true
					} else {
						grid[i][j] = false
					}
				}
			}
		default:
			fmt.Println(line)
			panic("Unknown instruction")
		}

	}

	// Count lit lights
	var count int
	for i := 0; i < 1000; i++ {
		for j := 0; j < 1000; j++ {
			if grid[i][j] {
				count++
			}
		}
	}
	return count
}

// The phrase turn on actually means that you should increase the brightness of those lights by 1.
// The phrase turn off actually means that you should decrease the brightness of those lights by 1, to a minimum of zero.
// The phrase toggle actually means that you should increase the brightness of those lights by 2.
func part2(input string) int {
	lines := strings.Split(input, "\n")
	grid := [1000][1000]int{}

	// true  -> turn on
	// false -> turn off
	for _, line := range lines {
		var i1, j1, i2, j2 int

		switch {
		case strings.HasPrefix(line, "turn on"):
			fmt.Sscanf(line, "turn on %d,%d through %d,%d", &i1, &j1, &i2, &j2)
			for i := i1; i <= i2; i++ {
				for j := j1; j <= j2; j++ {
					grid[i][j]++
				}
			}
		case strings.HasPrefix(line, "turn off"):
			fmt.Sscanf(line, "turn off %d,%d through %d,%d", &i1, &j1, &i2, &j2)
			for i := i1; i <= i2; i++ {
				for j := j1; j <= j2; j++ {
					tmpVal := grid[i][j] - 1
					if tmpVal < 0 {
						grid[i][j] = 0
					} else {
						grid[i][j] = tmpVal
					}
				}
			}
		case strings.HasPrefix(line, "toggle"):
			fmt.Sscanf(line, "toggle %d,%d through %d,%d", &i1, &j1, &i2, &j2)
			for i := i1; i <= i2; i++ {
				for j := j1; j <= j2; j++ {
					grid[i][j] += 2
				}
			}
		default:
			fmt.Println(line)
			panic("Unknown instruction")
		}

	}

	// Count lit lights
	var count int
	for i := 0; i < 1000; i++ {
		for j := 0; j < 1000; j++ {
			count += grid[i][j]
		}
	}
	return count
}

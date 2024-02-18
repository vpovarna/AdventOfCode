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

	fmt.Printf("AoC 2022, Day10, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2022, Day10, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	x := 1

	output := []int{}

	// add a 0 to have values starting from index 1
	output = append(output, 0)

	lines := strings.Split(input, "\n")

	for _, line := range lines {
		if line == "noop" {
			output = append(output, x)
		} else {
			parts := strings.Split(line, " ")
			count := cast.ToInt(parts[1])

			// wait to cycles
			output = append(output, x)
			output = append(output, x)
			x += count
		}
	}

	output = append(output, x)

	ans := 0
	for i := 20; i <= 220; i += 40 {
		v := i * output[i]
		ans += v
	}

	return ans
}

func part2(input string) int {
	return -1
}

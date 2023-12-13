package main

import (
	"flag"
	"fmt"
	"os"
	"strconv"
	"strings"
)

var inputFile = flag.String("inputFile", "day01.txt", "Relative file path to use as input")

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)

	if err != nil {
		panic(err)
	}

	input := string(bytes)

	fmt.Printf("AoC 2018, Day1, Part1 solution is: %d \n", part1(&input))
	fmt.Printf("AoC 2018, Day1, Part2 solution is: %d \n", part2(&input))
}

func part1(input *string) int {
	result := 0
	for _, line := range strings.Split(*input, "\n") {
		offset, _ := strconv.Atoi(line)
		result += offset
	}

	return result
}

func part2(input *string) int {
	result := 0
	seen := make(map[int]bool)

	for {
		for _, line := range strings.Split(*input, "\n") {
			offset, _ := strconv.Atoi(line)
			result += offset

			if seen[result] {
				return result
			}
			seen[result] = true
		}
	}
}

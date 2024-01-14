package main

import (
	"aoc/utils"
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
	fmt.Printf("AoC 2017, Day5, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2017, Day5, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	jumpOffsets := utils.MapToInt(strings.Split(input, "\n"))
	var cycles, index int

	for index >= 0 && index < len(jumpOffsets) {
		tmpIndex := jumpOffsets[index]
		jumpOffsets[index]++
		index += tmpIndex
		cycles++
	}
	return cycles
}

func part2(input string) int {
	jumpOffsets := utils.MapToInt(strings.Split(input, "\n"))
	var cycles, index int

	for index >= 0 && index < len(jumpOffsets) {
		tmpIndex := jumpOffsets[index]

		if jumpOffsets[index] >= 3 {
			jumpOffsets[index]--
		} else {
			jumpOffsets[index]++
		}

		index += tmpIndex
		cycles++
	}
	return cycles
}

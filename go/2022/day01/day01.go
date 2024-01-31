package main

import (
	"aoc/cast"
	"aoc/mathy"
	"aoc/utils"
	"flag"
	"fmt"
	"os"
	"sort"
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

	fmt.Printf("AoC 2022, Day1, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2022, Day1, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	return utils.MaxInt(parseInput(input)...)
}

func part2(input string) int {
	calories := parseInput(input)
	sort.Ints(sort.IntSlice(calories))

	total := 0
	for i := len(calories) - 1; i >= len(calories)-3; i-- {
		total += calories[i]
	}
	return total
}

func parseInput(input string) []int {
	blocks := strings.Split(input, "\n\n")

	items := []int{}

	for _, block := range blocks {
		lines := strings.Split(block, "\n")
		calories := cast.MapSliceOfStringToInt(lines)

		items = append(items, mathy.SumIntSlice(calories))
	}

	return items
}

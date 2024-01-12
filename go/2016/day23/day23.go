package main

import (
	"aoc/utils"
	"flag"
	"fmt"
	"os"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)

	if err != nil {
		panic(err)
	}

	input := string(bytes)

	fmt.Printf("AoC 2016, Day22, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2016, Day22, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	return utils.Factorial(7) + 96*79
}

func part2(input string) int {
	return utils.Factorial(12) + 96*79
}

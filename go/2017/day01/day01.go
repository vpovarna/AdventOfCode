package main

import (
	"aoc/cast"
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
	fmt.Printf("AoC 2017, Day1, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2017, Day1, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	ans := 0

	for i := 1; i < len(input); i++ {
		if input[i] == input[i-1] {
			ans += cast.ToInt(input[i])
		}
	}

	if input[0] == input[len(input)-1] {
		ans += cast.ToInt(input[0])
	}

	return ans
}

func part2(input string) int {
	ans := 0
	n := len(input)
	for i := 0; i < n/2; i++ {
		if input[i] == input[n/2+i] {
			ans += 2 * cast.ToInt(input[i])
		}
	}

	return ans
}

package main

import (
	"aoc/cast"
	"flag"
	"fmt"
	"os"
	"strings"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

var factors = [2]int{16807, 48271}

const divisor = 2147483647

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)

	if err != nil {
		panic(err)
	}

	input := string(bytes)
	fmt.Printf("AoC 2017, Day15, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2017, Day15, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	return count(input, 40000000, [2]int{1, 1})
}

func part2(input string) int {
	return count(input, 5000000, [2]int{4, 8})
}

func count(input string, rounds int, criteria [2]int) int {
	values := parseInput(input)

	var judgeCount int
	for i := 0; i < rounds; i++ {
		for i, v := range values {
			values[i] = getNextValue(v, factors[i], divisor, criteria[i])
		}

		compareVal := values[0] ^ values[1]
		twoPow16 := 1 << 16
		if (compareVal % twoPow16) == 0 {
			judgeCount++
		}
	}

	return judgeCount
}

func getNextValue(value, factor, divisor, criteria int) int {
	value *= factor
	value %= divisor

	for value%criteria != 0 {
		value *= factor
		value %= divisor
	}

	return value
}

func parseInput(input string) (ans []int) {
	lines := strings.Split(input, "\n")
	for _, line := range lines {
		parts := strings.Split(line, " starts with ")
		ans = append(ans, cast.ToInt(parts[1]))
	}
	return ans
}

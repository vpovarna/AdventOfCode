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
	fmt.Printf("AoC 2017, Day2, Part1 solution is: %d \n", run(getRowHash, input))
	fmt.Printf("AoC 2017, Day2, Part2 solution is: %d \n", run(getEvenlyDividedNumbersResult, input))
}

func run(fn func(values []string) int, input string) int {
	lines := strings.Split(input, "\n")
	ans := 0
	for _, line := range lines {
		ans += fn(strings.Split(line, "\t"))
	}
	return ans
}

func getRowHash(line []string) int {
	maxValue := utils.MaxInt(utils.MapToInt(line)...)
	minValue := utils.MinInt(utils.MapToInt(line)...)
	return maxValue - minValue
}

func getEvenlyDividedNumbersResult(line []string) int {
	values := utils.MapToInt(line)
	for i, val1 := range values {
		for _, val2 := range values[i+1:] {
			max := utils.MaxInt(val1, val2)
			min := utils.MinInt(val1, val2)
			if max%min == 0 {
				return max / min
			}
		}
	}

	return -1
}

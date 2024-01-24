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
		panic(err)
	}

	input := string(bytes)
	fmt.Printf("AoC 2019, Day2, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2019, Day2, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	inputNumbers := parseInput(input)
	inputNumbers[1] = 12
	inputNumbers[2] = 2
	return run(inputNumbers)
}

func part2(input string) int {
	inputNumbers := parseInput(input)

	for n1 := 0; n1 < 100; n1++ {
		for n2 := 0; n2 < 100; n2++ {
			// crete a copy of the inputNumbers slice and run the step on it since we don't wont to change the original array on all iterations
			clone := make([]int, len(inputNumbers))
			copy(clone, inputNumbers)
			clone[1] = n1
			clone[2] = n2
			if run(clone) == 19690720 {
				return 100*n1 + n2
			}
		}
	}
	return -1
}

func run(inputNumbers []int) int {
	index := 0
	for {
		opcode, two, three, four := inputNumbers[index], inputNumbers[index+1], inputNumbers[index+2], inputNumbers[index+3]

		if opcode == 99 {
			return inputNumbers[0]
		} else if opcode == 1 {
			inputNumbers[four] = inputNumbers[two] + inputNumbers[three]
		} else if opcode == 2 {
			inputNumbers[four] = inputNumbers[two] * inputNumbers[three]
		}
		index += 4
	}
}

func parseInput(input string) []int {
	rawNumbers := strings.Split(input, ",")

	inputNumbers := make([]int, len(rawNumbers))
	for i, n := range rawNumbers {
		inputNumbers[i] = cast.ToInt(n)
	}
	return inputNumbers
}

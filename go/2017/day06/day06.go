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
	fmt.Printf("AoC 2017, Day6, Part1 solution is: %d \n", run(input, 1))
	fmt.Printf("AoC 2017, Day6, Part2 solution is: %d \n", run(input, 2))
}

func run(input string, part int) int {
	blocks := utils.MapToInt(strings.Split(input, "\t"))
	visited := make(map[string]int, 0)

	var cycles int
	for {
		maxValue := utils.MaxInt(blocks...)
		maxValueIndex := indexOf(blocks, maxValue)

		blocks[maxValueIndex] = 0
		for i := 0; i < maxValue; i++ {
			newIndex := (i + maxValueIndex + 1) % len(blocks)
			blocks[newIndex]++
		}

		sequence := getSequenceAsString(blocks)

		if val, ok := visited[sequence]; ok {
			if part == 1 {
				return cycles + 1
			}
			return cycles - val
		}
		visited[getSequenceAsString(blocks)] = cycles
		cycles++
	}
}

func indexOf(nums []int, maxValue int) int {
	for i, n := range nums {
		if n == maxValue {
			return i
		}
	}

	return -1
}

func getSequenceAsString(blocks []int) string {
	var sb strings.Builder
	for _, n := range blocks {
		sb.WriteString(fmt.Sprintf("%d", n))
	}

	return sb.String()
}

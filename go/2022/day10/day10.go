package main

import (
	"aoc/cast"
	"aoc/mathy"
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
	fmt.Printf("AoC 2022, Day10, Part2 solution is:\n")
	part2(input)
}

func part1(input string) int {
	output := getOutput(input)
	ans := 0
	for i := 20; i <= 220; i += 40 {
		v := i * output[i-1]
		ans += v
	}

	return ans
}

func part2(input string) {
	output := getOutput(input)

	var draw = strings.Builder{}

	for i := 0; i < len(output); i += 40 {
		for j := 0; j < 40; j++ {
			if mathy.AbsInt(output[i+j]-j) <= 1 {
				draw.WriteString("#")
			} else {
				draw.WriteString(" ")
			}
		}
		draw.WriteString("\n")
	}
	fmt.Println(draw.String())
}

func getOutput(input string) (output []int) {
	x := 1

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

	return output
}

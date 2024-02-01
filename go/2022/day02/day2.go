package main

import (
	"flag"
	"fmt"
	"os"
	"strings"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

const (
	Win  = 6
	Loss = 0
	Draw = 3

	Rock    = 1
	Paper   = 2
	Scissor = 3
)

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)
	if err != nil {
		panic("Unable to read the input file")
	}

	input := string(bytes)

	fmt.Printf("AoC 2022, Day2, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2022, Day2, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	ans := parseInput(input)
	// A, X -> Rock ; B, Y-> Paper ; C, Z -> Scissor

	choices := map[string]int{
		"X": Rock,
		"Y": Paper,
		"Z": Scissor,
	}

	total := 0

	for _, l := range ans {
		switch l[1] {
		case "Y":
			switch l[0] {
			case "A":
				total += Win
			case "B":
				total += Draw
			case "C":
				total += Loss
			default:
				panic("unhandled choice")
			}
		case "Z":
			switch l[0] {
			case "A":
				total += Loss
			case "B":
				total += Win
			case "C":
				total += Draw
			default:
				panic("unhandled choice")
			}
		case "X":
			switch l[0] {
			case "A":
				total += Draw
			case "B":
				total += Loss
			case "C":
				total += Win
			default:
				panic("unhandled choice")
			}
		}
		total += choices[l[1]]
	}

	return total
}

func part2(input string) int {
	return -1
}

func parseInput(input string) (ans [][]string) {
	for _, line := range strings.Split(input, "\n") {
		values := strings.Split(line, " ")
		ans = append(ans, values)
	}

	return ans
}

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

	fmt.Printf("AoC 2017, Day18, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2017, Day18, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	runInstructions(input)
	return -1
}

func part2(input string) int {
	return -1
}

func runInstructions(input string) int {
	registers := map[string]int{}
	lines := strings.Split(input, "\n")
	for i := 0; i < len(lines); i++ {
		line := lines[i]
		parts := strings.Split(line, " ")

		switch parts[0] {
		// set X Y sets register X to the value of Y
		case "set":
			registers[parts[1]] = cast.ToInt(parts[2])
		// add X Y increases register X by the value of Y.
		case "add":
			registers[parts[1]] += cast.ToInt(parts[2])
		// mul X Y sets register X to the result of multiplying the value
		case "mul":
			fmt.Println(parts[2])
			registers[parts[1]] *= cast.ToInt(parts[2])
		// mod X Y sets register X to the remainder of dividing the value
		case "mod":
			registers[parts[1]] = registers[parts[1]] % cast.ToInt(parts[2])
		// jgz X Y jumps with an offset of the value of Y, but only if the value of X is greater than zero.
		// (An offset of 2 skips the next instruction, an offset of -1 jumps to the previous instruction, and so on.)
		case "jgz":
			if cast.ToInt(parts[2]) > 0 {
				i += cast.ToInt(parts[3])
			}
		default:
			panic("Unhandled instruction: " + line)
		}
	}

	fmt.Println(registers)

	return -1
}

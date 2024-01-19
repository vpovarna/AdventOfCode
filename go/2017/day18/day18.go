package main

import (
	"flag"
	"fmt"
	"os"
	"strconv"
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
	return runInstructions(input)
}

func part2(input string) int {
	return -1
}

func runInstructions(input string) int {
	registers := map[string]int{}
	played := 0

	lines := strings.Split(input, "\n")

	i := 0
	for i < len(lines) {
		line := lines[i]
		// fmt.Println(line)

		parts := strings.Split(line, " ")

		var valY int
		if len(parts) == 3 && parts[2] != "" {
			valY = interpret(parts[2], registers)
		}

		switch parts[0] {
		// set X Y sets register X to the value of Y
		case "set":
			registers[parts[1]] = valY
		// add X Y increases register X by the value of Y.
		case "add":
			registers[parts[1]] += valY
		// mul X Y sets register X to the result of multiplying the value
		case "mul":
			registers[parts[1]] *= valY
		// mod X Y sets register X to the remainder of dividing the value
		case "mod":
			registers[parts[1]] %= valY
		// jgz X Y jumps with an offset of the value of Y, but only if the value of X is greater than zero.
		// (An offset of 2 skips the next instruction, an offset of -1 jumps to the previous instruction, and so on.)
		case "jgz":
			if registers[parts[1]] > 0 {
				i += valY
				// We need to add continue to skip the i increment
				continue
			}
		//snd X plays a sound with a frequency equal to the value of X.
		case "snd":
			played = registers[parts[1]]
		//rcv X recovers the frequency of the last sound played, but only when the value of X is not zero. (If it is zero, the command does nothing.)
		case "rcv":
			if registers[parts[1]] != 0 {
				return played
			}
		default:
			panic("Unhandled instruction: " + line)
		}

		// fmt.Println(registers)
		// fmt.Println(played)
		// fmt.Println("===========")
		i += 1
	}

	return -1
}

func interpret(val string, register map[string]int) int {
	var varY int
	if v, err := strconv.Atoi(val); err != nil {
		varY = register[val]
	} else {
		varY = v
	}

	return varY
}

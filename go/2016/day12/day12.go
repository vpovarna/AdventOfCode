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

	fmt.Printf("AoC 2016, Day12, Part1 solution is: %d \n", part1(&input))
	fmt.Printf("AoC 2016, Day12, Part2 solution is: %d \n", part2(&input))
}

func part1(input *string) int {
	register := map[string]int{}
	return operate(input, register)
}

func part2(input *string) int {
	register := map[string]int{}
	register["c"] = 1
	return operate(input, register)
}

func operate(input *string, register map[string]int) int {
	lines := strings.Split(*input, "\n")
	insertIndex := 0

	for insertIndex < len(lines) {
		parts := strings.Split(lines[insertIndex], " ")

		switch parts[0] {
		case "inc":
			register[parts[1]]++
			insertIndex += 1
		case "dec":
			register[parts[1]]--
			insertIndex += 1
		case "jnz":
			value, err := strconv.Atoi(parts[1])
			if err != nil {
				value = register[parts[1]]
			}
			if value != 0 {
				num, err := strconv.Atoi(parts[2])
				if err != nil {
					panic("unable to covert jump value to int")
				}
				insertIndex += num
			} else {
				insertIndex += 1
			}
		case "cpy":
			valX, err := strconv.Atoi(parts[1])
			if err != nil {
				valX = register[parts[1]]
			}
			register[parts[2]] = valX
			insertIndex += 1
		default:
			panic("invalid instruction")
		}
	}

	return register["a"]
}

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

	fmt.Printf("AoC 2017, Day22, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2017, Day22, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	computer := getComputer(input)
	return computer.countMulRun()
}

func part2(input string) int {
	b := 81
	c := 81

	b = b*100 + 100000
	c = b + 17000
	var h int
	for {
		f := 1
		// effectively a prime number checker.
		for d := 2; d*d <= b; d++ {
			if b%d == 0 {
				f = 0
				break
			}
		}

		if f == 0 {
			h++
		}
		if b == c {
			break
		}
		b += 17
	}

	return h
}

type computer struct {
	instructions [][]string
	pointer      int
	registers    map[string]int
}

func (c *computer) countMulRun() (mulRuns int) {
	for c.pointer < len(c.instructions) {
		instruction := c.instructions[c.pointer]
		valX := instruction[1]
		valY := extractValue(instruction[2], c.registers)

		switch instruction[0] {
		case "set":
			c.registers[valX] = valY
		case "sub":
			c.registers[valX] -= valY
		case "mul":
			c.registers[valX] *= valY
			mulRuns++
		case "jnz":
			parsedX := extractValue(valX, c.registers)
			if parsedX != 0 {
				c.pointer += valY
				continue
			}
		default:
			panic("unhandled instruction " + strings.Join(instruction, " "))
		}
		c.pointer++
	}

	return mulRuns
}

func extractValue(v string, register map[string]int) int {
	var parsedX int
	if num, err := strconv.Atoi(v); err != nil {
		parsedX = register[v]
	} else {
		parsedX = num
	}
	return parsedX
}

func getComputer(input string) *computer {
	computer := &computer{registers: map[string]int{}}
	for _, line := range strings.Split(input, "\n") {
		computer.instructions = append(computer.instructions, strings.Split(line, " "))
	}
	return computer
}

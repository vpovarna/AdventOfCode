package main

import (
	"flag"
	"fmt"
	"math"
	"os"
	"strconv"
	"strings"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

const noInput = math.MinInt16

type computer struct {
	instructions [][]string
	pointer      int
	registers    map[string]int
	output       []int
}

func newComputer(input string, programId int) *computer {
	computer := &computer{registers: map[string]int{"p": programId}}
	for _, line := range strings.Split(input, "\n") {
		computer.instructions = append(computer.instructions, strings.Split(line, " "))
	}
	return computer
}

func (c *computer) step(inputNum int) {
	for {
		inst := c.instructions[c.pointer]
		valX := inst[1]
		var valY int
		if len(inst) == 3 && inst[2] != "" {
			valY = interpret(inst[2], c.registers)
		}

		switch inst[0] {
		case "snd":
			c.output = append(c.output, c.registers[valX])
			c.pointer++
		case "set":
			c.registers[valX] = valY
			c.pointer++
		case "add":
			c.registers[valX] += valY
			c.pointer++
		case "mul":
			c.registers[valX] *= valY
			c.pointer++
		case "mod":
			c.registers[valX] %= valY
			c.pointer++
		case "rcv":
			if inputNum == noInput {
				return
			}
			c.registers[valX] = inputNum
			inputNum = noInput
			c.pointer++
		case "jgz":
			var parsedX int
			if num, err := strconv.Atoi(valX); err != nil {
				// err converting, not a number
				parsedX = c.registers[valX]
			} else {
				// no error then a number was parsed
				parsedX = num
			}
			if parsedX > 0 {
				c.pointer += valY + len(c.instructions)
				c.pointer %= len(c.instructions)
			} else {
				c.pointer++
			}
		default:
			panic("unhandled operator " + inst[0])
		}
	}
}

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
	computer := newComputer(input, 0)
	computer.step(noInput)
	return computer.output[len(computer.output)-1]
}

func part2(input string) int {
	program0 := newComputer(input, 0)
	program1 := newComputer(input, 1)

	// prime the computers
	program0.step(noInput)
	program1.step(noInput)

	var sentFrom1 int

	for len(program0.output)+len(program1.output) > 0 {
		// run outputs from program zero through program 1
		for len(program0.output) > 0 {
			v := program0.output[0]
			program0.output = program0.output[1:]
			program1.step(v)
		}
		// and vice versa
		for len(program1.output) > 0 {
			v := program1.output[0]
			program1.output = program1.output[1:]
			program0.step(v)
			sentFrom1++
		}
	}

	return sentFrom1
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

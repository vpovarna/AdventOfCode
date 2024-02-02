package main

import (
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

	fmt.Printf("AoC 2022, Day5, Part1 solution is: %s \n", part1(input))
	fmt.Printf("AoC 2022, Day5, Part2 solution is: %s \n", part2(input))
}

func part1(input string) string {
	stacks, instructions := parseInput(input)

	for _, instruction := range instructions {
		instruction.moveReversed(stacks)
	}

	return getResponse(stacks)
}

func part2(input string) string {
	stacks, instructions := parseInput(input)
	for _, instruction := range instructions {
		instruction.moveInTheSameOrder(stacks)
	}
	return getResponse(stacks)
}

type instruction struct {
	nrCrates int
	from     int
	to       int
}

func (instr *instruction) moveReversed(stack [][]string) {
	fromStack := stack[instr.from]
	toStack := stack[instr.to]

	movingCrates := fromStack[len(fromStack)-instr.nrCrates:]
	stack[instr.from] = fromStack[:len(fromStack)-instr.nrCrates]

	for i := len(movingCrates) - 1; i >= 0; i-- {
		toStack = append(toStack, movingCrates[i])
	}
	stack[instr.to] = toStack
}

func (instr *instruction) moveInTheSameOrder(stack [][]string) {
	fromStack := stack[instr.from]
	toStack := stack[instr.to]

	movingCrates := fromStack[len(fromStack)-instr.nrCrates:]
	stack[instr.from] = fromStack[:len(fromStack)-instr.nrCrates]

	toStack = append(toStack, movingCrates...)
	stack[instr.to] = toStack
}

func parseInput(input string) ([][]string, []instruction) {
	parts := strings.Split(input, "\n\n")
	rawStacks, rawRules := parts[0], parts[1]
	return getStacks(rawStacks), getInstructions(rawRules)
}

func getInstructions(rules string) []instruction {
	instructions := make([]instruction, 0)

	for _, line := range strings.Split(rules, "\n") {
		instruction := instruction{}
		fmt.Sscanf(line, "move %d from %d to %d", &instruction.nrCrates, &instruction.from, &instruction.to)
		instruction.from--
		instruction.to--
		instructions = append(instructions, instruction)
	}

	return instructions
}

func getStacks(rawStacks string) [][]string {
	lines := strings.Split(rawStacks, "\n")

	nrOfStacks := 0
	for _, n := range strings.Split(lines[len(lines)-1], " ") {
		if n != "" {
			nrOfStacks++
		}
	}

	stacks := [][]string{}

	for i := 1; i < len(lines[0]); i += 4 {
		stack := make([]string, 0)

		for j := len(lines) - 2; j >= 0; j-- {
			currentLine := lines[j]
			if currentLine[i] != ' ' {
				stack = append(stack, string(currentLine[i]))
			}
		}
		stacks = append(stacks, stack)
	}
	return stacks
}

func getResponse(stacks [][]string) string {
	sb := strings.Builder{}
	for _, stack := range stacks {
		sb.WriteString(stack[len(stack)-1])
	}
	return sb.String()
}

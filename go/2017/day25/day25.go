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
		panic(err)
	}

	input := string(bytes)

	fmt.Printf("AoC 2017, Day25, Part1 solution is: %d \n", part1(input))
}

func part1(input string) int {
	steps, stateRules := parseInput(input)
	bigArray := make([]int, steps)
	index := steps / 2
	currentStateName := "A"

	for i := 0; i < steps; i++ {
		currentVal := bigArray[index]
		rulesToFollow := stateRules[currentStateName][currentVal]
		// write
		bigArray[index] = rulesToFollow.valToWrite
		if rulesToFollow.direction == "left" {
			index--
		} else {
			index++
		}
		currentStateName = rulesToFollow.nextState
	}

	var total int
	for _, n := range bigArray {
		if n > 0 {
			total++
		}
	}

	return total
}

type ruleSet struct {
	name       string // for debugging only
	valToWrite int
	direction  string
	nextState  string
}

// assume all programs start in state A for now, one less thing to parse...
func parseInput(input string) (steps int, states map[string][2]ruleSet) {
	// a manual parse here would be faster...
	blocks := strings.Split(input, "\n\n")

	fmt.Sscanf(strings.Split(blocks[0], "\n")[1], "Perform a diagnostic checksum after %d steps.", &steps)

	states = map[string][2]ruleSet{}
	for _, block := range blocks[1:] {
		lines := strings.Split(block, "\n")
		var stateName string
		fmt.Sscanf(lines[0], "In state %1s:", &stateName)

		rulesIfZero := ruleSet{name: stateName}
		fmt.Sscanf(strings.Trim(lines[2], " -."), "Write the value %d", &rulesIfZero.valToWrite)
		fmt.Sscanf(strings.Trim(lines[3], " -."), "Move one slot to the %s", &rulesIfZero.direction)
		fmt.Sscanf(strings.Trim(lines[4], " -."), "Continue with state %1s", &rulesIfZero.nextState)

		rulesIfOne := ruleSet{name: stateName}
		fmt.Sscanf(strings.Trim(lines[6], " -."), "Write the value %d", &rulesIfOne.valToWrite)
		fmt.Sscanf(strings.Trim(lines[7], " -."), "Move one slot to the %s", &rulesIfOne.direction)
		fmt.Sscanf(strings.Trim(lines[8], " -."), "Continue with state %1s", &rulesIfOne.nextState)

		states[stateName] = [2]ruleSet{rulesIfZero, rulesIfOne}
	}

	return steps, states
}

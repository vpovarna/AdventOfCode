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
	fmt.Printf("AoC 2017, Day8, Part1 solution is: %d \n", run(input, 1))
	fmt.Printf("AoC 2017, Day8, Part2 solution is: %d \n", run(input, 2))
}

func run(input string, part int) int {
	instructions := strings.Split(input, "\n")
	registry := map[string]int{}

	var maxHighValue int

	for _, instruction := range instructions {
		var targetValue, value, op1, op2 string
		var diff, comparedValue int

		fmt.Sscanf(instruction, "%s %s %d if %s %s %d", &targetValue, &op1, &diff, &value, &op2, &comparedValue)

		// populate registry
		for _, c := range [2]string{targetValue, value} {
			if _, contains := registry[c]; !contains {
				registry[c] = 0
			}
		}

		registerValue := registry[value]

		switch op2 {
		case ">":
			if registerValue > comparedValue {
				updateValue(op1, registry, targetValue, diff, &maxHighValue)
			}
		case "<":
			if registerValue < comparedValue {
				updateValue(op1, registry, targetValue, diff, &maxHighValue)
			}
		case "==":
			if registerValue == comparedValue {
				updateValue(op1, registry, targetValue, diff, &maxHighValue)
			}
		case "!=":
			if registerValue != comparedValue {
				updateValue(op1, registry, targetValue, diff, &maxHighValue)
			}
		case ">=":
			if registerValue >= comparedValue {
				updateValue(op1, registry, targetValue, diff, &maxHighValue)
			}
		case "<=":
			if registerValue <= comparedValue {
				updateValue(op1, registry, targetValue, diff, &maxHighValue)
			}
		default:
			panic("invalid instruction: " + instruction)
		}
	}
	if part == 1 {
		return getMaxValue(registry)
	} else {
		return maxHighValue
	}
}

func updateValue(op1 string, registry map[string]int, key string, diff int, maxHighValue *int) {
	var tmpValue int
	if op1 == "inc" {
		tmpValue = registry[key] + diff
	} else {
		tmpValue = registry[key] - diff
	}
	*maxHighValue = max(*maxHighValue, tmpValue)
	registry[key] = tmpValue

}

func getMaxValue(registry map[string]int) int {
	maxValue := 0
	for _, v := range registry {
		maxValue = max(maxValue, v)
	}

	return maxValue
}

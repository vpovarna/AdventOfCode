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
	fmt.Printf("AoC 2019, Day1, Part1 solution is: %d \n", run(input, fuelNeededFormula))
	fmt.Printf("AoC 2019, Day1, Part2 solution is: %d \n", run(input, calculateFuelNeeded))
}

func run(input string, fn func(n int) int) int {
	var totalFuel int
	for _, n := range strings.Split(input, "\n") {
		totalFuel += fn(cast.ToInt(n))
	}
	return totalFuel
}

func fuelNeededFormula(n int) int {
	return (n / 3) - 2
}

func calculateFuelNeeded(n int) (fuelNeeded int) {
	remaining := n
	for remaining > 0 {
		fuel := fuelNeededFormula(remaining)
		if fuel > 0 {
			fuelNeeded += fuel
			remaining = fuel
		} else {
			remaining = 0
		}
	}

	return fuelNeeded
}

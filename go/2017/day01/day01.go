package main

import (
	"flag"
	"fmt"
	"math"
	"os"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

type point struct {
	x int
	y int
}

func (p *point) distance() float64 {
	return math.Abs(float64(p.x)) + math.Abs(float64(p.y))
}

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)

	if err != nil {
		panic(err)
	}

	input := string(bytes)
	fmt.Printf("AoC 2017, Day1, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2017, Day1, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	return -1
}

func part2(input string) int {
	return -1
}

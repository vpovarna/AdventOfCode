package main

import (
	"aoc/cast"
	"flag"
	"fmt"
	"log"
	"os"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

type llNode struct {
	value int
	next  *llNode
}

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)

	if err != nil {
		panic(err)
	}

	input := string(bytes)

	fmt.Printf("AoC 2017, Day17, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2017, Day17, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	return spinLock(input, 2017, 2017)
}

func part2(input string) int {
	return spinLock(input, 50000000, 0)
}

func spinLock(input string, lastNumToAdd int, valueToFind int) int {
	steps := cast.ToInt(input)

	current := &llNode{value: 0}
	current.next = current
	for i := 1; i <= lastNumToAdd; i++ {
		for j := 0; j < steps; j++ {
			current = current.next
		}

		saveNext := current.next
		current.next = &llNode{
			value: i,
			next:  saveNext,
		}
		current = current.next

		// Monitor progress for part2
		if i%1000000 == 0 {
			log.Println(i, "steps done")
		}
	}

	// find the valueToInt value
	for current.value != valueToFind {
		current = current.next
	}

	return current.next.value
}

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

	fmt.Printf("AoC 2018, Day5, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2018, Day5, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	unmodified := react(input)
	return len(unmodified)
}

func part2(input string) int {
	shortest := len(input)
	for c := 'A'; c <= 'Z'; c++ {
		second := c + 32
		modified := strings.Replace(input, string([]rune{second}), "", -1)
		modified = strings.Replace(modified, string([]rune{c}), "", -1)
		candidate := len(react(modified))
		if candidate < shortest {
			shortest = candidate
		}
	}
	return shortest
}

func react(input string) string {
	current := input
	for {
		transformed := false
		prev := ' '
		for i, c := range current {
			if c-prev == 32 || prev-c == 32 {
				transformed = true
				current = current[0:i-1] + current[i+1:]
				break
			}
			prev = c
		}
		if !transformed {
			break
		}
	}
	return current
}

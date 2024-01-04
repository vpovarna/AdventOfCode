package main

import (
	"flag"
	"fmt"
	"os"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)

	if err != nil {
		panic(err)
	}

	input := string(bytes)
	fmt.Printf("AoC 2015, Day1, Part1 solution is: %d \n", part1(&input))
	fmt.Printf("AoC 2015, Day1, Part2 solution is: %d \n", part2(&input))

	fmt.Println("Welcome to AOC 2015!")
}

func part1(input *string) int {
	count := 0

	for _, c := range *input {
		if c == '(' {
			count += 1
		} else if c == ')' {
			count -= 1
		} else {
			panic("unknown character")
		}
	}

	return count
}

func part2(input *string) int {
	count := 0

	for i, c := range *input {
		if count == -1 {
			return i
		}
		if c == '(' {
			count += 1
		} else if c == ')' {
			count -= 1
		} else {
			panic("unknown character")
		}
	}

	return -1
}

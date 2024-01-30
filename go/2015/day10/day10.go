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

	fmt.Printf("AoC 2015, Day10, Part1 solution is: %d \n", run(input, 40))
	fmt.Printf("AoC 2015, Day10, Part2 solution is: %d \n", run(input, 50))
}

func run(input string, iterations int) int {
	iteration := 0
	for iteration < iterations {
		count := 1

		digits := strings.Split(input, "")
		lastDigit := digits[0]

		var sb strings.Builder

		for i := 1; i < len(digits); i++ {
			if digits[i] == digits[i-1] {
				count++
			} else {
				sb.WriteString(fmt.Sprintf("%d", count))
				sb.WriteString(lastDigit)

				lastDigit = digits[i]
				count = 1
			}
		}
		sb.WriteString(fmt.Sprintf("%d", count))
		sb.WriteString(lastDigit)

		input = sb.String()
		iteration++
	}

	return len(input)
}

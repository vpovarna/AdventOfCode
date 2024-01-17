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

	fmt.Printf("AoC 2015, Day8, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2015, Day8, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	lines := strings.Split(input, "\n")

	var totalChars, stringChars int

	for _, line := range lines {
		totalChars += len(line)

		// skip the first and the last quotes
		for i := 1; i < len(line)-1; i++ {
			currentChar := line[i]
			switch currentChar {
			case '\\':
				nextChar := line[i+1]
				if nextChar == '\\' || nextChar == '"' {
					i++
				} else if nextChar == 'x' {
					i += 3
				}
			}
			stringChars++
		}
	}
	return totalChars - stringChars
}

func part2(input string) int {
	lines := strings.Split(input, "\n")

	var totalChars, encodedLength int

	for _, line := range lines {
		totalChars += len(line)
		encodedLength += 2
		for i := 0; i < len(line); i++ {
			switch line[i] {
			case '\\', '"':
				encodedLength += 2
			default:
				encodedLength++
			}
		}

	}
	return encodedLength - totalChars
}

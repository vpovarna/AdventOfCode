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

	fmt.Printf("AoC 2016, Day9, Part1 solution is: %d \n", part1(&input))
	fmt.Printf("AoC 2016, Day9, Part2 solution is: %d \n", part2(&input))
}

func part1(input *string) int {
	return decompressLength(*input, false)
}

func part2(input *string) int {
	return decompressLength(*input, true)
}

func decompressLength(in string, part2 bool) int {
	var decompressedLen int
	for i := 0; i < len(in); {
		switch in[i] {
		case '(':
			// find index of closing paren, then find total length of substring
			relativeCloseIndex := strings.Index(in[i:], ")")
			closedIndex := relativeCloseIndex + i

			var copyLen, repeat int
			// Extract values
			fmt.Sscanf(in[i:closedIndex+1], "(%dx%d)", &copyLen, &repeat)

			substring := in[closedIndex+1 : closedIndex+1+copyLen]

			// crete the pattern and add repeated length to the final count
			patternLength := len(substring)
			if part2 {
				patternLength = decompressLength(substring, true)
			}

			decompressedLen += patternLength * repeat

			i = closedIndex + 1 + len(substring)
		default:
			decompressedLen++
			i++
		}
	}

	return decompressedLen
}

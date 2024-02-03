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
		panic("Unable to read the input file")
	}

	input := string(bytes)

	fmt.Printf("AoC 2022, Day6, Part1 solution is: %d \n", firstMarkerPosition(input, 4))
	fmt.Printf("AoC 2022, Day6, Part2 solution is: %d \n", firstMarkerPosition(input, 14))
}

func firstMarkerPosition(line string, n int) int {
	for i := n; i < len(line); i++ {
		str := line[i-n : i]
		if !hasRepeatedSignals(str) {
			return i
		}
	}

	return -1
}

func hasRepeatedSignals(str string) bool {
	set := map[string]bool{}

	for _, c := range strings.Split(str, "") {
		if set[c] {
			return true
		} else {
			set[c] = true
		}
	}
	return false
}

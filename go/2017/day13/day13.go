package main

import (
	"flag"
	"fmt"
	"math"
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

	fmt.Printf("AoC 2017, Day13, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2017, Day13, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	freqs := parseFrequencies(input)

	var severity int
	for _, freq := range freqs {
		depthIndex := freq[0]

		// how many picoseconds it take the scanner to return to zero index position
		frequencyToZeroIndex := (freq[1] - 1) * 2

		if depthIndex%frequencyToZeroIndex == 0 {
			severity += depthIndex * freq[1]
		}

	}
	return severity
}

func part2(input string) int {
	freqs := parseFrequencies(input)

	for delay := 0; delay < math.MaxInt32; delay++ {
		var gotCaught bool
		for _, freq := range freqs {
			depthIndex := freq[0]

			// how many picoseconds it take the scanner to return to zero index position
			frequencyToZeroIndex := (freq[1] - 1) * 2

			if (depthIndex+delay)%frequencyToZeroIndex == 0 {
				gotCaught = true
			}
		}

		if !gotCaught {
			return delay
		}
	}

	panic("Can't determine the delay in order to not get caught")
}

func parseFrequencies(input string) [][2]int {
	lines := strings.Split(input, "\n")
	freq := make([][2]int, 0)
	for _, line := range lines {
		var depth, rng int
		fmt.Sscanf(line, "%d: %d", &depth, &rng)
		freq = append(freq, [2]int{depth, rng})
	}

	return freq
}

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
	fmt.Printf("AoC 2016, Day6, Part1 solution is: %s \n", run(&input, true))
	fmt.Printf("AoC 2016, Day6, Part2 solution is: %s \n", run(&input, false))
}

func run(input *string, extractMostFrequentCharacter bool) string {
	lines := strings.Split(*input, "\n")

	lineLength := len(lines[0])
	var message strings.Builder

	for i := 0; i < lineLength; i++ {
		occurrence := make(map[string]int)
		for j := 0; j < len(lines); j++ {
			line := lines[j]
			c := string(line[i])
			occurrence[c] += 1
		}
		if extractMostFrequentCharacter {
			message.WriteString(mostFrequentCharacter(occurrence))
		} else {
			message.WriteString(leastFrequentCharacter(occurrence))
		}

	}
	return message.String()
}

func mostFrequentCharacter(occurrence map[string]int) string {
	c := ""
	count := 0

	for k, v := range occurrence {
		if v > count {
			count = v
			c = k
		}
	}

	return c
}

func leastFrequentCharacter(occurrence map[string]int) string {
	c := ""
	count := math.MaxInt32

	for k, v := range occurrence {
		if v < count {
			count = v
			c = k
		}
	}

	return c
}

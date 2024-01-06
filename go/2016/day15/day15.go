package main

import (
	"flag"
	"fmt"
	"os"
	"strings"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

type disc struct {
	number    int
	positions int
	starting  int
}

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)

	if err != nil {
		panic(err)
	}

	input := string(bytes)

	fmt.Printf("AoC 2016, Day15, Part1 solution is: %d \n", part1(&input))
	fmt.Printf("AoC 2016, Day15, Part2 solution is: %d \n", part2(&input))
}

func part1(input *string) int {
	discs := parseInput(input)
	return timeIsEverything(discs)
}

func part2(input *string) int {
	discs := parseInput(input)
	discs = append(discs, &disc{
		number:    len(discs) + 1,
		positions: 11,
		starting:  0,
	})
	return timeIsEverything(discs)
}

func timeIsEverything(discs []*disc) int {
	t := 0
	for {
		var capsuleCollides bool
		for _, d := range discs {
			timeSinceDrop := d.number
			position := d.starting + t + timeSinceDrop
			position = position % d.positions
			if position != 0 {
				capsuleCollides = true
			}
		}
		if !capsuleCollides {
			break
		}
		t++
	}

	return t
}

func parseInput(input *string) []*disc {
	var discs []*disc

	lines := strings.Split(*input, "\n")

	for _, line := range lines {
		d := disc{}
		fmt.Sscanf(line, "Disc #%d has %d positions; at time=0, it is at position %d.", &d.number, &d.positions, &d.starting)
		discs = append(discs, &d)
	}

	return discs
}

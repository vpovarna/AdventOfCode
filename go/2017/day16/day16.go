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

	fmt.Printf("AoC 2017, Day14, Part1 solution is: %s \n", part1(input))
	fmt.Printf("AoC 2017, Day14, Part2 solution is: %s \n", part2(input))
}

func part1(input string) string {
	chars := strings.Split("abcdefghijklmnop", "")
	for _, instruction := range strings.Split(input, ",") {
		chars = danceMove(instruction, chars)
	}
	return strings.Join(chars, "")
}

func part2(input string) string {
	chars := strings.Split("abcdefghijklmnop", "")

	seen := map[string]int{}
	for i := 0; i < 1000000000; i++ {
		for _, instruction := range strings.Split(input, ",") {
			chars = danceMove(instruction, chars)
		}
		state := strings.Join(chars, "")

		if lastSeenIdex, ok := seen[state]; ok {
			diff := i - lastSeenIdex
			for i+diff < 1000000000 {
				i += diff
			}
		}

		seen[state] = i
	}
	return strings.Join(chars, "")
}

func danceMove(instruction string, str []string) []string {
	switch instruction[0] {
	case 's':
		var steps int
		fmt.Sscanf(instruction, "s%d", &steps)
		str = rotateRight(str, steps)
	case 'x':
		var i1, i2 int
		fmt.Sscanf(instruction, "x%d/%d", &i1, &i2)
		str[i1], str[i2] = str[i2], str[i1]
	case 'p':
		var c1, c2 string
		fmt.Sscanf(instruction, "p%1s/%1s", &c1, &c2)
		i1 := getIndex(str, c1)
		i2 := getIndex(str, c2)
		str[i1], str[i2] = str[i2], str[i1]
	default:
		panic("unhandled command: " + instruction)
	}
	return str
}

func getIndex(str []string, ch string) int {
	for i, c := range str {
		if c == ch {
			return i
		}
	}

	return -1
}

func rotateRight(str []string, steps int) []string {
	n := len(str)
	for i := 0; i < steps; i++ {
		str = append([]string{str[n-1]}, str[0:n-1]...)
	}

	return str
}

package main

import (
	"aoc/cast"
	"flag"
	"fmt"
	"os"
	"regexp"
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

	fmt.Printf("AoC 2015, Day11, Part1 solution is: %s \n", part1(input))
	fmt.Printf("AoC 2015, Day11, Part2 solution is: %s \n", part2(input))
}

func part1(input string) string {
	pw := input
	for !isValid(pw) {
		pw = generatePassword(pw)
	}

	return pw
}

func part2(input string) string {
	firstValidPassword := part1(input)
	pw := generatePassword(firstValidPassword)

	for !isValid(pw) {
		pw = generatePassword(pw)
	}

	return pw
}

func generatePassword(input string) string {
	chars := strings.Split(input, "")
	for i := len(chars) - 1; i >= 0; i-- {
		if chars[i] == "z" {
			chars[i] = "a"
		} else {
			asciVal := cast.ToASCIICode(chars[i]) + 1
			chars[i] = cast.ASCIIIntToChar(asciVal)
			break
		}
	}

	return strings.Join(chars, "")
}

func isValid(in string) bool {
	rule1 := func(in string) bool {
		for i := 2; i < len(in); i++ {
			if in[i-2]+1 == in[i-1] && in[i-1]+1 == in[i] {
				return true
			}
		}
		return false
	}

	rule2 := func(in string) bool {
		return !regexp.MustCompile("[iol]").MatchString(in)
	}

	rule3 := func(in string) bool {
		pairs := map[string]bool{}
		for i := 1; i < len(in); i++ {
			if in[i-1] == in[i] {
				pairs[in[i-1:i+1]] = true
			}

		}

		return len(pairs) >= 2
	}

	return rule1(in) && rule2(in) && rule3(in)
}

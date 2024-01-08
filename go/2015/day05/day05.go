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

	fmt.Printf("AoC 2015, Day5, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2015, Day5, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	nice := 0
	lines := strings.Split(input, "\n")
	for _, line := range lines {
		if getNumberOfVowels(line) >= 3 && appearsTwice(line) && pureStrings(line) {
			nice++
		}
	}
	return nice
}

func part2(input string) int {
	nice := 0
	lines := strings.Split(input, "\n")
	for _, line := range lines {
		if containsAPairOfTwoLetters(line) && oneLetterRepeated(line) {
			nice++
		}
	}
	return nice
}

// It contains at least three vowels (aeiou only), like aei, xazegov, or aeiouaeiouaeiou.
func getNumberOfVowels(str string) int {
	vowels := 0
	for _, c := range str {
		if strings.ContainsRune("aeiou", c) {
			vowels++
		}
	}

	return vowels
}

// It contains at least one letter that appears twice in a row, like xx, abcdde (dd), or aabbccdd (aa, bb, cc, or dd).
func appearsTwice(str string) bool {
	for i := 1; i < len(str); i++ {
		if str[i] == str[i-1] {
			return true
		}
	}

	return false
}

// It does not contain the strings ab, cd, pq, or xy, even if they are part of one of the other requirements.
func pureStrings(str string) bool {
	badStr := map[string]bool{
		"ab": true,
		"cd": true,
		"pq": true,
		"xy": true,
	}

	for i := 1; i < len(str); i++ {
		currentStr := fmt.Sprintf("%c%c", str[i-1], str[i])
		if _, contains := badStr[currentStr]; contains {
			return false
		}
	}

	return true
}

// It contains a pair of any two letters that appears at least twice in the string without overlapping, like xyxy (xy) or aabcdefgaa (aa), but not like aaa (aa, but it overlaps).
func containsAPairOfTwoLetters(str string) bool {
	for i := 0; i < len(str)-2; i++ {
		toMatch := str[i : i+2]
		for j := i + 2; j < len(str)-1; j++ {
			if str[j:j+2] == toMatch {
				return true
			}
		}
	}

	return false
}

// It contains at least one letter which repeats with exactly one letter between them, like xyx, abcdefeghi (efe), or even aaa.
func oneLetterRepeated(str string) bool {
	for i := 2; i < len(str); i++ {
		if str[i-2] == str[i] {
			return true
		}
	}

	return false
}

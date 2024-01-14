package main

import (
	"flag"
	"fmt"
	"os"
	"reflect"
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

	fmt.Printf("AoC 2017, Day4, Part1 solution is: %d \n", run(input, isValidPart1))
	fmt.Printf("AoC 2017, Day4, Part2 solution is: %d \n", run(input, isValidPart2))
}

func run(input string, fn func(string) bool) int {
	ans := 0

	for _, line := range strings.Split(input, "\n") {
		if fn(line) {
			ans += 1
		}
	}
	return ans
}

func isValidPart1(line string) bool {
	visited := map[string]bool{}

	for _, word := range strings.Split(line, " ") {
		if visited[word] {
			return false
		}

		visited[word] = true
	}

	return true
}

func isValidPart2(line string) bool {
	words := strings.Split(line, " ")
	for i, word1 := range words {
		for _, word2 := range words[i+1:] {
			if areAnagrams(word1, word2) {
				return false
			}
		}
	}

	return true
}

func areAnagrams(str1, str2 string) bool {
	str1Occurrence := getOccurrenceMap(str1)
	str2Occurrence := getOccurrenceMap(str2)
	return reflect.DeepEqual(str1Occurrence, str2Occurrence)
}

func getOccurrenceMap(str string) map[string]int {
	occurrence := map[string]int{}
	for _, c := range strings.Split(str, "") {
		occurrence[c]++
	}
	return occurrence
}

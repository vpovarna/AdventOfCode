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

	fmt.Printf("AoC 2022, Day3, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2022, Day3, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	priorities := getPriorities()
	total := 0

	for _, line := range strings.Split(input, "\n") {
		m := len(line) / 2
		commonChars := getCommonChar(line[:m], line[m:])
		if len(commonChars) != 1 {
			panic("expected a single char")
		}
		total += priorities[commonChars[0]]
	}

	return total

}

func part2(input string) int {
	// priorities := getPriorities()
	total := 0

	// lines := strings.Split(input, "\n")

	// for i := 2; i < len(lines); i++ {
	// 	str1 := lines[i-2]
	// 	str2 := lines[i-1]
	// 	str3 := lines[i]

	// }
	return total

}

func getCommonChar(str1, str2 string) []rune {
	copart1Map := getStringSet(str1)
	commonChars := []rune{}
	uniqueChars := map[rune]bool{}

	for _, c := range str2 {
		if copart1Map[c] {
			if !uniqueChars[c] {
				commonChars = append(commonChars, c)
				uniqueChars[c] = true
			}
		}
	}
	return commonChars
}

func getPriorities() map[rune]int {
	priorities := map[rune]int{}
	for i := 0; i < 26; i++ {
		priorities[rune('a'+i)] = i + 1
		priorities[rune('A'+i)] = i + 27
	}

	return priorities
}

func getStringSet(input string) map[rune]bool {
	occurrence := map[rune]bool{}

	for _, c := range input {
		occurrence[c] = true
	}
	return occurrence
}

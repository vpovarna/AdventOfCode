package main

import (
	"bytes"
	"flag"
	"fmt"
	"os"
	"strings"
)

type runeTuple struct {
	a rune
	b rune
}

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)

	if err != nil {
		panic(err)
	}

	input := string(bytes)

	fmt.Printf("AoC 2018, Day2, Part1 solution is: %d \n", part1(&input))
	fmt.Printf("AoC 2018, Day2, Part2 solution is: %s \n", part2(&input))
}

func part1(input *string) int {
	m, n := 0, 0
	for _, line := range strings.Split(*input, "\r\n") {
		a, b := countAppearsLetters(countCharacters(strings.Trim(line, "\n")))
		m += a
		n += b
	}

	return m * n
}

func part2(input *string) string {
	lines := strings.Split(*input, "\r\n")
	for i := 0; i < len(lines); i++ {
		for j := i + 1; j < len(lines); j++ {
			line1 := lines[i]
			line2 := lines[j]

			if diff(line1, line2) == 1 {
				return getCommonCharters(line1, line2)
			}
		}
	}
	return ""
}

func getCommonCharters(line1, line2 string) string {
	var buffer bytes.Buffer

	runes, _ := zipLines(line1, line2)

	for _, runeTuple := range runes {
		if runeTuple.a == runeTuple.b {
			buffer.WriteRune(runeTuple.a)
		}
	}

	return buffer.String()

}

func diff(line1, line2 string) int {
	count := 0

	runes, _ := zipLines(line1, line2)

	for _, runeTemplate := range runes {
		if runeTemplate.a != runeTemplate.b {
			count += 1
		}
	}
	return count
}

func countCharacters(str string) map[rune]int {
	frequency := make(map[rune]int)

	for _, char := range str {
		frequency[char] = frequency[char] + 1
	}

	return frequency
}

func countAppearsLetters(frequency map[rune]int) (int, int) {
	m, n := 0, 0

	for _, count := range frequency {
		if count == 2 {
			m = 1
		}

		if count == 3 {
			n = 1
		}
	}

	return m, n
}

// Utility method
func zipLines(str1 string, str2 string) ([]runeTuple, error) {
	if len(str1) != len(str2) {
		return nil, fmt.Errorf("zip: arguments must be of same length")
	}

	r := make([]runeTuple, len(str1), len(str2))

	for i, c := range str1 {
		r[i] = runeTuple{c, rune(str2[i])}
	}

	return r, nil
}

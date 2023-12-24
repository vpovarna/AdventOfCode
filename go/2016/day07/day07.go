package main

import (
	"flag"
	"fmt"
	"os"
	"strings"
)

var inputFile = flag.String("inputFile", "input.txt", "Received file path to use as input")

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)

	if err != nil {
		panic(err)
	}

	input := string(bytes)

	fmt.Printf("AoC 2016, Day7, Part1 solution is: %d \n", part1(&input))
	fmt.Printf("AoC 2016, Day7, Part2 solution is: %d \n", part2(&input))

}

func part1(input *string) int {
	insideBraces, outsideBraces := parseInput(input)

	var count int
	for i := range insideBraces {
		inside, outside := insideBraces[i], outsideBraces[i]
		var insidesHaveABBA bool
		for _, str := range inside {
			if hasABBA(str) {
				insidesHaveABBA = true
				break
			}
		}
		if !insidesHaveABBA {
			for _, str := range outside {
				if hasABBA(str) {
					count += 1
					break
				}
			}
		}
	}

	return count
}

func part2(input *string) int {
	insideBraces, outsideBraces := parseInput(input)

	var count int

	for i := range insideBraces {
		inside, outside := insideBraces[i], outsideBraces[i]

		insideABAs := findABAs(inside)
		outsideABAs := findABAs(outside)

		for aba := range insideABAs {
			// create bab
			bab := fmt.Sprintf("%s%s%s", aba[1:2], aba[0:1], aba[1:2])
			if outsideABAs[bab] {
				count++
				break
			}
		}
	}

	return count
}

func hasABBA(word string) bool {
	if len(word) <= 3 {
		return false
	}

	for i := 0; i < len(word)-3; i++ {
		if word[i] == word[i+3] && word[i+1] == word[i+2] && word[i] != word[i+2] {
			return true
		}
	}

	return false

}

func findABAs(strs []string) map[string]bool {
	found := map[string]bool{}

	for _, str := range strs {
		for i := 2; i < len(str); i++ {
			if (str[i-2] == str[i]) && str[i-1] != str[i] {
				found[str[i-2:i+1]] = true
			}
		}
	}

	return found

}

func parseInput(input *string) (insides, outsides [][]string) {
	lines := strings.Split(*input, "\n")
	for _, line := range lines {
		var collectChars string
		var insideBraces, outsideBraces []string

		for _, rn := range line + "[" {
			switch char := string(rn); char {
			case "[":
				outsideBraces = append(outsideBraces, collectChars)
				collectChars = ""
			case "]":
				insideBraces = append(insideBraces, collectChars)
				collectChars = ""
			default:
				collectChars += char
			}
		}
		insides = append(insides, insideBraces)
		outsides = append(outsides, outsideBraces)
	}
	return insides, outsides

}

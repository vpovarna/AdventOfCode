package main

import (
	"flag"
	"fmt"
	"os"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

type graphNode struct {
	name   string
	weight int
	edges  []string
}

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)

	if err != nil {
		panic(err)
	}

	input := string(bytes)
	fmt.Printf("AoC 2017, Day9, Part1 solution is: %d \n", streamProcessing(input, 1))
	fmt.Printf("AoC 2017, Day9, Part2 solution is: %d \n", streamProcessing(input, 2))
}

func streamProcessing(input string, part int) int {
	var totalScore, garbageCount int

	var inGarbage bool
	var openCurlies int

	for i := 0; i < len(input); i++ {
		char := string(input[i])
		if inGarbage {
			switch char {
			case "!":
				i++
			case ">":
				inGarbage = false
			default:
				garbageCount++ // part 2
			}
		} else {
			switch char {
			case "{":
				openCurlies++
			case "}":
				totalScore += openCurlies // part 1
				openCurlies--
			case "<":
				inGarbage = true
			}
		}
	}

	if part == 1 {
		return totalScore
	}
	return garbageCount
}

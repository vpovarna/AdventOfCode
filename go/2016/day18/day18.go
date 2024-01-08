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

	fmt.Printf("AoC 2016, Day16, Part1 solution is: %d \n", compute(input, 40))
	fmt.Printf("AoC 2016, Day16, Part2 solution is: %d \n", compute(input, 400000))
}

func compute(input string, nrOfRows int) int {
	tiles := generateTiles(input, nrOfRows)
	return countTiles(tiles)
}

func generateTiles(input string, numRows int) []string {
	lastRow := "." + input + "."
	patterns := "^^. .^^ ^.. ..^"

	var actualRows []string

	for len(actualRows) < numRows {
		actualRows = append(actualRows, lastRow[1:len(lastRow)-1])

		var nextRow strings.Builder

		nextRow.WriteString(".")
		for i := 1; i < len(lastRow)-1; i++ {
			threeAbove := lastRow[i-1 : i+2]
			if strings.Contains(patterns, threeAbove) {
				nextRow.WriteString("^")
			} else {
				nextRow.WriteString(".")
			}
		}
		nextRow.WriteString(".")

		lastRow = nextRow.String()
	}

	return actualRows
}

func countTiles(tiles []string) int {
	var count int
	for _, row := range tiles {
		for _, c := range row {
			if c == '.' {
				count++
			}
		}
	}

	return count
}

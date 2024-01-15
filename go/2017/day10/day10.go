package main

import (
	"aoc/utils"
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
	fmt.Printf("AoC 2017, Day10, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2017, Day10, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	n := 256

	circularList := make([]int, n)

	for i := 0; i < n; i++ {
		circularList[i] = i
	}

	currentPosition := 0
	inputLengths := utils.MapToInt(strings.Split(input, ","))

	skipSize := 0

	for _, length := range inputLengths {

		// reverse sub-list [currentPosition : currentPosition + position]
		endPosition := currentPosition + length - 1
		reverseSubList(circularList, currentPosition, endPosition, n)

		// current position moves forward by the length
		currentPosition = (currentPosition + length + skipSize) % n
		skipSize += 1
	}
	return circularList[0] * circularList[1]
}
func part2(input string) int {
	return -1
}

func reverseSubList(circularList []int, startIndex, endIndex, n int) {
	l := startIndex
	r := endIndex

	for l < r {
		tmpValue := circularList[l%n]
		circularList[l%n] = circularList[r%n]
		circularList[r%n] = tmpValue

		l++
		r--
	}
}

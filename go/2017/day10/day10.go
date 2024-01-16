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
	fmt.Printf("AoC 2017, Day10, Part2 solution is: %s \n", part2(input))
}

func part1(input string) int {
	n := 256

	circularList := make([]int, n)

	for i := 0; i < n; i++ {
		circularList[i] = i
	}

	var skipSize, currentPosition int
	inputLengths := utils.MapToInt(strings.Split(input, ","))

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
func part2(input string) string {
	n := 256

	circularList := make([]int, n)

	for i := 0; i < n; i++ {
		circularList[i] = i
	}

	var skipSize, currentPosition int
	inputLengths := parseInputASCII(input)

	for round := 0; round < 64; round++ {
		for _, length := range inputLengths {
			if length > 0 {
				// reverse sub-list [currentPosition : currentPosition + position]
				endPosition := currentPosition + length - 1
				reverseSubList(circularList, currentPosition, endPosition, n)
			}

			// current position moves forward by the length
			currentPosition = (currentPosition + length + skipSize) % n
			skipSize += 1
		}
	}

	var denseHash []int
	for i := 0; i < 16; i++ {
		var xord int
		for j := 16 * i; j < (i+1)*16; j++ {
			xord ^= circularList[j]

		}

		denseHash = append(denseHash, xord)
	}

	var hexdHash string
	for _, dense := range denseHash {
		hexdHash += fmt.Sprintf("%02x", dense)
	}

	return hexdHash
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

func parseInputASCII(input string) (ans []int) {
	for _, char := range input {
		ans = append(ans, int(char))
	}
	// add default lengths to end
	ans = append(ans, 17, 31, 73, 47, 23)
	return ans
}

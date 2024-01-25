package main

import (
	"flag"
	"fmt"
	"os"
	"strconv"
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
	fmt.Printf("AoC 2019, Day4, Part1 solution is: %d \n", run(input, 1))
	fmt.Printf("AoC 2019, Day4, Part2 solution is: %d \n", run(input, 2))
}

func run(input string, part int) int {
	ranges := strings.Split(input, "-")
	from, _ := strconv.Atoi(ranges[0])
	to, _ := strconv.Atoi(ranges[1])

	count := 0
	for i := from; i <= to; i++ {
		digits := makeDigitsSlice(i)
		if part == 2 {
			digits = shrinkLargeGroups(digits)
		}
		if isIncreasing(digits) && hasDuplicate(digits) {
			count++
		}
	}
	return count
}

// Going from left to right, the digits never decrease; they only ever increase or stay the same (like 111123 or 135679).
func isIncreasing(digits []int) bool {
	for i := 1; i < len(digits); i++ {
		if digits[i] < digits[i-1] {
			return false
		}
	}
	return true
}

// Two adjacent digits are the same (like 22 in 122345).
func hasDuplicate(digits []int) bool {
	count := 0
	for i := 1; i < len(digits); i++ {
		if digits[i] == digits[i-1] {
			count++
		}
	}

	return count > 0
}

// TODO: TO review this
func shrinkLargeGroups(digits []int) []int {
	for i := 0; i < len(digits)-2; i++ {
		if digits[i] == digits[i+1] && digits[i] == digits[i+2] {
			// figure out how many items to remove
			removeUpTo := i + 1
			for removeUpTo < len(digits) && digits[i] == digits[removeUpTo] {
				removeUpTo++
			}

			// copy the values into a new slice
			newSli := make([]int, 0)
			for j := 0; j <= i; j++ {
				newSli = append(newSli, digits[j])
			}
			for removeUpTo < len(digits) {
				newSli = append(newSli, digits[removeUpTo])
				removeUpTo++
			}
			digits = newSli
		}
	}

	return digits
}

func makeDigitsSlice(num int) []int {
	result := make([]int, 6)
	for i := 5; num > 0; i-- {
		result[i] = num % 10
		num -= num % 10
		num /= 10
	}
	return result
}

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

	fmt.Printf("AoC 2016, Day19, Part1 solution is: %s \n", part1(input, "abcdefgh"))
	fmt.Printf("AoC 2016, Day19, Part2 solution is: %d \n", part2(input))
}

func part1(input string, sourceStr string) string {
	instructions := strings.Split(input, "\n")
	currentStr := strings.Split(sourceStr, "")
	for _, instruction := range instructions {
		fmt.Println(instruction)
		t := scramble(instruction, currentStr)
		fmt.Println(strings.Join(t, ""))
		currentStr = t
	}

	return strings.Join(currentStr, "")
}

func part2(input string) int {
	return -1
}

func scramble(instruction string, str []string) []string {
	switch {
	case strings.HasPrefix(instruction, "swap position"):
		var i1, i2 int
		fmt.Sscanf(instruction, "swap position %d with position %d", &i1, &i2)
		str[i1], str[i2] = str[i2], str[i1]
	case strings.HasPrefix(instruction, "swap letter"):
		var s1, s2 string
		fmt.Sscanf(instruction, "swap letter %1s with letter %1s", &s1, &s2)
		i1, i2 := getIndex(str, s1), getIndex(str, s2)
		str[i1], str[i2] = str[i2], str[i1]
	case strings.HasPrefix(instruction, "reverse positions"):
		var i1, i2 int
		fmt.Sscanf(instruction, "reverse positions %d through %d", &i1, &i2)
		for i1 < i2 {
			str[i1], str[i2] = str[i2], str[i1]
			i1++
			i2--
		}
	case strings.HasPrefix(instruction, "rotate left"):
		var steps int
		fmt.Sscanf(instruction, "rotate left %d step", &steps)
		str = rotateLeft(str, steps)
	case strings.HasPrefix(instruction, "rotate right"):
		var steps int
		fmt.Sscanf(instruction, "rotate right %d step", &steps)
		str = rotateRight(str, steps)
	case strings.HasPrefix(instruction, "move position"):
		var i1, i2 int
		fmt.Sscanf(instruction, "move position %d to position %d", &i1, &i2)
		store := str[i1]

		// remove char from i1
		copy(str[i1:], str[i1+1:])

		// shift elements
		for i := len(str) - 1; i >= i2+1; i-- {
			str[i] = str[i-1]
		}
		str[i2] = store
	case strings.HasPrefix(instruction, "rotate based"):
		var letter string
		fmt.Sscanf(instruction, "rotate based on position of letter %1s", &letter)
		index := getIndex(str, letter)
		if index == len(str)-1 {
			index++
		}
		str = rotateRight(str, index+1)
	default:
		fmt.Println(instruction)
		panic("unknown command")
	}

	return str
}

func rotateRight(str []string, steps int) []string {
	n := len(str)
	for i := 0; i < steps; i++ {
		str = append([]string{str[n-1]}, str[0:n-1]...)
	}

	return str
}

func getIndex(str []string, ch string) int {
	for i, c := range str {
		if c == ch {
			return i
		}
	}

	return -1
}

func rotateLeft(str []string, steps int) []string {
	for i := 0; i < steps; i++ {
		str = append(str[1:], str[0])
	}

	return str
}

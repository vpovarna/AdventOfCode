package main

import (
	"flag"
	"fmt"
	"math"
	"os"
	"regexp"
	"strconv"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)

	if err != nil {
		panic(err)
	}

	input := string(bytes)

	fmt.Printf("AoC 2016, Day25, Part1 solution is: %d \n", part1(input))
}

func part1(input string) int {
	for i := 0; i < math.MaxInt32; i++ {
		if assemblyComputer(input, i) {
			return i
		}
	}
	return -1
}

func assemblyComputer(input string, registerAInitialValue int) bool {
	pattern := regexp.MustCompile("^(01){1,}$")
	var outputs string

	var a, b, d int

	a = registerAInitialValue
	d = a + 2532
	for {
		a = d
		for a != 0 {
			b = a % 2
			a /= 2
			outputs += strconv.Itoa(b)
			if len(outputs)%2 == 0 {
				if !pattern.MatchString(outputs) {
					return false
				} else if len(outputs) > 100 {
					return true
				}
			}
		}
	}
}

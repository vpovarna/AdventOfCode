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
	fmt.Printf("AoC 2017, Day6, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2017, Day6, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	instructions := strings.Split(input, "\n")
	registry := map[string]int{}

	for _, instruction := range instructions {
		var t, v, op1, op2 string
		var n1, n2 int

		fmt.Sscanf(instruction, "%s %s %d if %s %s %d", &t, &op1, &n1, &v, &op2, &n2)

		// populate registry
		for _, c := range [2]string{t, v} {
			if _, contains := registry[c]; !contains {
				registry[c] = 0
			}
		}

		fmt.Println(instruction)
		fmt.Println(op2)
		switch op2 {
		case ">":
			if registry[v] > n2 {
				if op1 == "inc" {
					registry[t] += n1
				} else {
					registry[t] -= n1
				}
			}
		case "<":
			if registry[v] < n2 {
				if op1 == "inc" {
					registry[t] += n1
				} else {
					registry[t] -= n1
				}
			}
		case "==":
			if registry[v] == n2 {
				if op1 == "inc" {
					registry[t] += n1
				} else {
					registry[t] -= n1
				}
			}
		case ">=":
			if registry[v] >= n2 {
				if op1 == "inc" {
					registry[t] += n1
				} else {
					registry[t] -= n1
				}
			}
		case "<=":
			if registry[v] <= n2 {
				if op1 == "inc" {
					registry[t] += n1
				} else {
					registry[t] -= n1
				}
			}
		default:
			panic("invalid operation")
		}
		fmt.Println(registry)
		fmt.Println("=====")
	}
	fmt.Println(registry)

	return -1
}

func part2(input string) int {
	return -1
}

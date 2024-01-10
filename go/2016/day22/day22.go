package main

import (
	"flag"
	"fmt"
	"os"
	"regexp"
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

	fmt.Printf("AoC 2016, Day22, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2016, Day22, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	allNodes := parseInput(input)
	for _, node := range allNodes {
		fmt.Println(*node)
	}
	return -1
}

func part2(input string) int {
	return -1
}

type node struct {
	coord [2]int
	size  int
	used  int
	avail int
}

func parseInput(input string) map[[2]int]*node {
	allNodes := map[[2]int]*node{}

	spaces := regexp.MustCompile("[ ]{2,}")
	for _, line := range strings.Split(input, "\n")[2:] {
		str := spaces.ReplaceAllString(line, " ")

		var percentage int
		n := node{}

		fmt.Sscanf(str, "/dev/grid/node-x%d-y%d %dT %dT %dT %d%",
			&n.coord[0], &n.coord[1], &n.size, &n.used, &n.avail, &percentage)

		allNodes[n.coord] = &n
	}

	return allNodes
}

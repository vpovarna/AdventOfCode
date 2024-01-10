package main

import (
	"aoc/utils"
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
	var viable int

	for i1, n1 := range allNodes {
		for i2, n2 := range allNodes {
			if i1 == i2 || n1.used == 0 {
				continue
			}

			if n2.avail >= n1.used {
				viable++
			}
		}
	}

	return viable
}

func part2(input string) int {
	nodes := parseInput(input)

	var maxRow, maxCol int
	var emptyDiskPos [2]int
	for c, n := range nodes {
		maxRow = utils.MaxInt(c[0], maxRow)
		maxCol = utils.MaxInt(c[1], maxCol)
		// getting the starting node, i.e. has zero used space
		if n.used == 0 && n.size > 0 {
			emptyDiskPos[0] = n.coord[1]
			emptyDiskPos[1] = n.coord[0]
		}
	}

	//Create grid
	grid := make([][]node, maxRow+1)
	for i := range grid {
		grid[i] = make([]node, maxCol+1)
	}
	for c, n := range nodes {
		grid[c[0]][c[1]] = *n
	}

	distToPayload := bfs(grid, emptyDiskPos, [2]int{maxRow, 0}, maxRow, maxCol)
	distToAccessPoint := bfs(grid, [2]int{maxRow, 0}, [2]int{0, 0}, maxRow, maxCol)

	return distToPayload + 5*(distToAccessPoint-1)
}

func bfs(grid [][]node, start [2]int, target [2]int, maxRow, maxCol int) int {
	visited := map[[2]int]bool{start: true}
	queue := []nodeWeight{{start, 0}}

	for len(queue) > 0 {
		currentNode := queue[0]
		queue = queue[1:]

		if currentNode.coord[0] == target[0] && currentNode.coord[1] == target[1] {
			return currentNode.steps
		}

		for _, direction := range directions() {
			nextRow := currentNode.coord[0] + direction[0]
			nextCol := currentNode.coord[1] + direction[1]

			if nextRow < 0 || nextCol < 0 || nextRow > maxRow || nextCol > maxCol {
				continue
			}

			n := [2]int{nextRow, nextCol}
			if !visited[n] && !isLargeDisk(grid[nextRow][nextCol]) {
				visited[n] = true
				queue = append(queue, nodeWeight{n, currentNode.steps + 1})
			}
		}

	}
	return -1
}

func isLargeDisk(node node) bool {
	return float32(node.used)/float32(node.size) >= 0.95
}

func directions() [4][2]int {
	return [4][2]int{
		{0, 1},
		{0, -1},
		{1, 0},
		{-1, 0},
	}
}

type nodeWeight struct {
	coord [2]int
	steps int
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

func printGrid(grid [][]node) {
	for _, line := range grid {
		for _, n := range line {
			fmt.Print(n)
		}
		fmt.Println()
	}
}

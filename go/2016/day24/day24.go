package main

import (
	"aoc/cast"
	"aoc/utils"
	"flag"
	"fmt"
	"math"
	"os"
	"regexp"
	"strings"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

type bfsNode struct {
	row, col int // 0,0 is top left
	distance int
}

var dirs = [][2]int{
	{0, -1},
	{0, 1},
	{-1, 0},
	{1, 0},
}

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)

	if err != nil {
		panic(err)
	}

	input := string(bytes)

	fmt.Printf("AoC 2016, Day24, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2016, Day24, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	lines := strings.Split(input, "\n")
	grid := buildGrid(lines)
	return cleaningRobot(grid, 1)
}

func part2(input string) int {
	lines := strings.Split(input, "\n")
	grid := buildGrid(lines)
	return cleaningRobot(grid, 2)
}

func buildGrid(lines []string) [][]string {
	var grid [][]string
	for _, l := range lines {
		grid = append(grid, strings.Split(l, ""))
	}

	return grid
}

// allows passing through points of interest
func bfsGetEdgeWeights(grid [][]string, start [2]int) []int {

	poiToDistance := map[string]int{
		grid[start[0]][start[1]]: 0,
	}

	queue := []bfsNode{
		{start[0], start[1], 0},
	}

	visited := map[[2]int]bool{}

	for len(queue) > 0 {
		front := queue[0]
		queue = queue[1:]

		if visited[[2]int{front.row, front.col}] {
			continue
		}

		visited[[2]int{front.row, front.col}] = true

		if regexp.MustCompile("[0-9]").MatchString(grid[front.row][front.col]) {
			poiToDistance[grid[front.row][front.col]] = front.distance
		}

		for _, d := range dirs {
			nextRow, nextCol := front.row+d[0], front.col+d[1]
			// don't need to check for going out of bounds because there are walls
			// surrounding everything
			if grid[nextRow][nextCol] != "#" {
				queue = append(queue, bfsNode{
					row:      nextRow,
					col:      nextCol,
					distance: front.distance + 1,
				})
			}
		}
	}

	distances := make([]int, len(poiToDistance))
	for numStr, dist := range poiToDistance {
		distances[cast.ToInt(numStr)] = dist
	}
	return distances
}

func cleaningRobot(grid [][]string, part int) int {
	var graph [][]int
	for r, row := range grid {
		for c, cell := range row {
			if regexp.MustCompile("[0-9]").MatchString(cell) {
				poi := cell
				distancesFromPOI := bfsGetEdgeWeights(grid, [2]int{r, c})
				// initialize graph size
				if graph == nil {
					for i := 0; i < len(distancesFromPOI); i++ {
						graph = append(graph, make([]int, len(distancesFromPOI)))
					}
				}
				graph[cast.ToInt(poi)] = distancesFromPOI
			}
		}
	}

	// then a recursive, backtracking dfs on that weighted graph to determine
	// the shortest total path
	returnToZero := part != 1
	return dfs(graph, 0, map[int]bool{0: true}, returnToZero)
}

func dfs(graph [][]int, entryIndex int, visited map[int]bool, returnToZero bool) (minWeightSum int) {
	// if all nodes have been visited, return zero for part 1, or the distance
	// from the entryIndex to the zero POI
	if len(graph) == len(visited) {
		if returnToZero {
			return graph[entryIndex][0]
		}
		return 0
	}

	// get the minimum distance from a recursive call
	minDistance := math.MaxInt32
	for i, val := range graph[entryIndex] {
		if !visited[i] {
			visited[i] = true

			dist := val + dfs(graph, i, visited, returnToZero)
			minDistance = utils.MinInt(minDistance, dist)

			delete(visited, i)
		}
	}

	return minDistance
}

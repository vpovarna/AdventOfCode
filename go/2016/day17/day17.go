package main

import (
	"crypto/md5"
	"flag"
	"fmt"
	"os"
	"regexp"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

var openPattern = regexp.MustCompile("[b-f]")

type node struct {
	coords   [2]int
	path     string
	distance int
}

type direction struct {
	char    string
	rowDiff int
	colDiff int
}

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)

	if err != nil {
		panic(err)
	}

	input := string(bytes)

	fmt.Printf("AoC 2016, Day17, Part1 solution is: %s \n", part1(input))
	fmt.Printf("AoC 2016, Day17, Part2 solution is: %s \n", part2(input))
}

func part1(input string) string {
	return bfs(input, 1)
}

func part2(input string) string {
	return bfs(input, 2)
}

func bfs(input string, part int) string {
	// queue can be represented as a slice and it's initialized with the input string
	queue := []node{{
		coords:   [2]int{0, 0},
		path:     input,
		distance: 0,
	}}

	var longestPath string

	for len(queue) > 0 {
		front := queue[0]
		queue = queue[1:]

		// [2]int{3,3} -> finish point
		if front.coords == [2]int{3, 3} {
			validPath := front.path[len(input):]

			if part == 1 {
				return validPath
			}

			if len(longestPath) < len(validPath) {
				longestPath = validPath
			}

			continue
		}

		hash := getMd5Hash(front.path)
		for i, direction := range directions() {
			nextRow := front.coords[0] + direction.rowDiff
			nextCol := front.coords[1] + direction.colDiff
			// check boundaries
			if nextRow >= 0 && nextRow < 4 && nextCol >= 0 && nextCol < 4 {
				if openPattern.MatchString(hash[i : i+1]) {
					queue = append(queue, node{
						coords:   [2]int{nextRow, nextCol},
						path:     front.path + direction.char,
						distance: front.distance + 1,
					})
				}
			}
		}
	}

	// part 2. eventually will get to the end.
	return fmt.Sprintf("%d", len(longestPath))
}

func getMd5Hash(str string) string {
	return fmt.Sprintf("%x", md5.Sum([]byte(str)))
}

func directions() []direction {
	return []direction{
		{"U", -1, 0},
		{"D", 1, 0},
		{"L", 0, -1},
		{"R", 0, +1},
	}
}

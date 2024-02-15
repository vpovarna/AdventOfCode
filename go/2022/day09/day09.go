package main

import (
	"aoc/cast"
	"aoc/mathy"
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
		panic("Unable to read the input file")
	}

	input := string(bytes)

	fmt.Printf("AoC 2022, Day9, Part1 solution is: %d \n", run(input, 2))
	fmt.Printf("AoC 2022, Day9, Part2 solution is: %d \n", run(input, 10))
}

type instruction struct {
	dir string
	val int
}

func run(input string, size int) int {
	instructions := parseInput(input)

	rope := initRope(size)

	visited := map[[2]int]bool{}
	for _, inst := range instructions {
		for inst.val > 0 {
			rope.moveOneSpace(inst.dir)
			visited[rope.tail.coords] = true
			inst.val--
		}
	}

	return len(visited)
}

func parseInput(input string) (ans []instruction) {
	for _, line := range strings.Split(input, "\n") {
		ans = append(ans, instruction{
			dir: line[:1],
			val: cast.ToInt(line[2:]),
		})
	}
	return ans
}

type node struct {
	coords [2]int // row, col still
	next   *node
}

type rope struct {
	head, tail *node
}

func initRope(length int) rope {
	head := &node{}
	itr := head

	// start at 1 to account for head already being created
	for i := 1; i < length; i++ {
		itr.next = &node{}
		itr = itr.next
	}

	return rope{
		head: head,
		tail: itr,
	}
}

func (r rope) moveOneSpace(dir string) {
	diffs := map[string][2]int{
		"U": {1, 0},
		"D": {-1, 0},
		"L": {0, -1},
		"R": {0, 1},
	}

	diff := diffs[dir]
	r.head.coords[0] += diff[0]
	r.head.coords[1] += diff[1]

	r.head.updateTrailer()
}

func (n *node) updateTrailer() {
	if n.next == nil {
		return
	}

	rowDiff := n.coords[0] - n.next.coords[0]
	colDiff := n.coords[1] - n.next.coords[1]

	if mathy.AbsInt(rowDiff) > 1 && mathy.AbsInt(colDiff) > 1 {
		n.next.coords[0] += rowDiff / 2
		n.next.coords[1] += colDiff / 2
	} else if mathy.AbsInt(rowDiff) > 1 {
		// see part1 for math logic
		n.next.coords[0] += rowDiff / 2
		n.next.coords[1] += colDiff
	} else if mathy.AbsInt(colDiff) > 1 {
		n.next.coords[1] += colDiff / 2
		n.next.coords[0] += rowDiff
	} else {
		return
	}

	// go to next node
	n.next.updateTrailer()
}

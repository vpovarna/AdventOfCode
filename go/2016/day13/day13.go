package main

import (
	"flag"
	"fmt"
	"os"
	"strconv"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

type Point struct {
	x int
	y int
}

func (p *Point) getNeighbors() []Point {
	return []Point{
		{p.x + 1, p.y},
		{p.x - 1, p.y},
		{p.x, p.y - 1},
		{p.x, p.y + 1},
	}
}

func (p *Point) checkSpace(favNumber int) bool {
	sum := int64((p.x*p.x + 3*p.x + 2*p.x*p.y + p.y + p.y*p.y) + favNumber)
	binaryRep := strconv.FormatInt(sum, 2)

	occurrence := map[rune]int{}

	for _, c := range binaryRep {
		occurrence[c] += 1
	}
	return occurrence[rune('1')]%2 == 0
}

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)
	if err != nil {
		panic(err)
	}

	input := string(bytes)

	fmt.Printf("AoC 2016, Day13, Part1 solution is: %d \n", part1(&input))
	fmt.Printf("AoC 2016, Day13, Part2 solution is: %d \n", part2(&input))
}

func part1(input *string) int {

	favNumber, err := strconv.Atoi(*input)

	if err != nil {
		panic("unable to convert the input to int")
	}

	// draw grid
	for i := 0; i < 7; i++ {
		for j := 0; j < 10; j++ {
			p := Point{i, j}
			if p.checkSpace(favNumber) {
				fmt.Print(".")
			} else {
				fmt.Print("#")
			}
		}
		fmt.Println()
	}

	// fmt.Println(favNumber)
	// p := Point{5, 0}
	// fmt.Println(p.checkSpace(favNumber))

	return -1
}

func part2(input *string) int {
	return -1
}

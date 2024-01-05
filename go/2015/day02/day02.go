package main

import (
	"aoc/cast"
	"aoc/utils"
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

	fmt.Printf("AoC 2015, Day2, Part1 solution is: %d \n", part1(&input))
	fmt.Printf("AoC 2015, Day2, Part2 solution is: %d \n", part2(&input))

}

func part1(input *string) int {
	lines := strings.Split(*input, "\n")
	sum := 0
	for _, line := range lines {
		dimensions := strings.Split(line, "x")

		l := cast.ToInt(dimensions[0])
		w := cast.ToInt(dimensions[1])
		h := cast.ToInt(dimensions[2])
		sum += getSurfaceAreaP1(l, w, h)
	}
	return sum
}

func part2(input *string) int {
	lines := strings.Split(*input, "\n")
	sum := 0
	for _, line := range lines {
		dimensions := strings.Split(line, "x")

		l := cast.ToInt(dimensions[0])
		w := cast.ToInt(dimensions[1])
		h := cast.ToInt(dimensions[2])
		sum += getSurfaceAreaP2(l, w, h)
	}
	return sum
}

func getSurfaceAreaP1(l, w, h int) int {
	return 2*l*w + 2*w*h + 2*h*l + utils.MinInt(l*w, w*h, h*l)
}

func getSurfaceAreaP2(l, w, h int) int {
	sides := []int{
		2 * (l + w),
		2 * (w + h),
		2 * (l + h),
	}

	return utils.MinInt(sides...) + l*w*h
}

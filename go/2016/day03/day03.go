package main

import (
	"flag"
	"fmt"
	"os"
	"strconv"
	"strings"
)

type triangle struct {
	a int
	b int
	c int
}

func (t *triangle) isValid() bool {
	return t.a+t.b > t.c && t.b+t.c > t.a && t.a+t.c > t.b
}

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)

	if err != nil {
		panic(err)
	}

	input := string(bytes)

	fmt.Printf("AoC 2016, Day3, Part1 solution is: %d \n", part1(&input))
	fmt.Printf("AoC 2016, Day3, Part2 solution is: %d \n", part2(&input))
}

func part1(input *string) int {
	lines := strings.Split(*input, "\n")
	ans := 0

	for _, line := range lines {
		cleanLine := strings.TrimSpace(line)
		parts := strings.Fields(cleanLine)

		a, _ := strconv.Atoi(parts[0])
		b, _ := strconv.Atoi(parts[1])
		c, _ := strconv.Atoi(parts[2])

		triangle := triangle{a, b, c}

		if triangle.isValid() {
			ans += 1
		}
	}
	return ans
}

func part2(input *string) int {
	lines := strings.Split(*input, "\n")
	ans := 0

	for i := 0; i < len(lines)-2; i += 2 {
		l1 := strings.TrimSpace(lines[i])
		l2 := strings.TrimSpace(lines[i+1])
		l3 := strings.TrimSpace(lines[i+2])

		p1 := strings.Fields(l1)
		p2 := strings.Fields(l2)
		p3 := strings.Fields(l3)

		for j := 0; j < 3; j++ {
			a, _ := strconv.Atoi(p1[j])
			b, _ := strconv.Atoi(p2[j])
			c, _ := strconv.Atoi(p3[j])
			triangle := triangle{a, b, c}
			if triangle.isValid() {
				ans += 1
			}
		}
	}
	return ans
}

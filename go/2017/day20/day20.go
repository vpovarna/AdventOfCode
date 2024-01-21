package main

import (
	"flag"
	"fmt"
	"math"
	"os"
	"sort"
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

	fmt.Printf("AoC 2017, Day20, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2017, Day20, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	particles := parseInput(input)
	sort.Slice(particles, func(i, j int) bool {
		pI, pJ := particles[i], particles[j]

		if pI.acceleration != pJ.acceleration {
			return sumAbs(pI.acceleration) < sumAbs(pJ.acceleration)
		}

		if pI.velocity != pJ.velocity {
			return sumAbs(pJ.velocity) < sumAbs(pI.velocity)
		}

		return sumAbs(pI.positions) < sumAbs(pJ.positions)
	})
	return particles[0].index
}

func part2(input string) int {
	particles := parseInput(input)

	for i := 0; i < math.MaxInt16; i++ {
		particles = tick(particles)
		particles = removeCollisions(particles)
	}
	return len(particles)
}

type particle struct {
	index        int
	positions    [3]int
	velocity     [3]int
	acceleration [3]int
}

func sumAbs(nums [3]int) int {
	return int(math.Abs(float64(nums[0])) + math.Abs(float64(nums[1])) + math.Abs(float64(nums[2])))
}

func tick(particles []particle) []particle {
	var nextState []particle
	for _, p := range particles {
		for i, acc := range p.acceleration {
			p.velocity[i] += acc
		}
		for i, vel := range p.velocity {
			p.positions[i] += vel
		}

		nextState = append(nextState, p)
	}

	return nextState
}

func removeCollisions(particles []particle) []particle {
	set := map[[3]int]int{}
	for _, p := range particles {
		set[p.positions]++
	}

	var nextState []particle
	for _, p := range particles {
		if count, ok := set[p.positions]; ok && count == 1 {
			nextState = append(nextState, p)
		}
	}

	return nextState
}

func parseInput(input string) (particles []particle) {
	for i, line := range strings.Split(input, "\n") {
		p := particle{index: i}

		fmt.Sscanf(line, "p=<%d,%d,%d>, v=<%d,%d,%d>, a=<%d,%d,%d>",
			&p.positions[0],
			&p.positions[1],
			&p.positions[2],
			&p.velocity[0],
			&p.velocity[1],
			&p.velocity[2],
			&p.acceleration[0],
			&p.acceleration[1],
			&p.acceleration[2],
		)

		particles = append(particles, p)
	}

	return particles
}

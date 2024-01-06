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

	fmt.Printf("AoC 2016, Day16, Part1 solution is: %s \n", part1(input))
	fmt.Printf("AoC 2016, Day16, Part2 solution is: %s \n", part2(input))
}

func part1(input string) string {
	diskLength := 272
	disk := getDragon(input, diskLength)
	return getDragonChecksum(disk, diskLength)
}

func part2(input string) string {
	diskLength := 35651584
	disk := getDragon(input, diskLength)
	return getDragonChecksum(disk, diskLength)
}

func getDragon(input string, diskLength int) string {
	disk := input

	for len(disk) < diskLength {
		var sb strings.Builder
		sb.WriteString(disk)
		sb.WriteByte('0')
		// append the reverse string
		for i := len(disk) - 1; i >= 0; i-- {
			if disk[i] == '1' {
				sb.WriteByte('0')
			} else {
				sb.WriteByte('1')
			}
		}

		disk = sb.String()
	}

	return disk
}

func getDragonChecksum(disk string, diskLength int) string {
	disk = disk[0:diskLength]
	for len(disk)%2 == 0 {
		var sb strings.Builder
		for i := 0; i < len(disk); i += 2 {
			if disk[i] == disk[i+1] {
				sb.WriteByte('1')
			} else {
				sb.WriteByte('0')
			}
		}
		disk = sb.String()
	}
	return disk
}

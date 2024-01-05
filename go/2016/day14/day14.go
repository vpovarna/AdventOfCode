package main

import (
	"crypto/md5"
	"flag"
	"fmt"
	"os"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)

	if err != nil {
		panic(err)
	}

	input := string(bytes)

	fmt.Printf("AoC 2016, Day14, Part1 solution is: %d \n", part1(&input))
	fmt.Printf("AoC 2016, Day14, Part2 solution is: %d \n", part2(&input))
}

func part1(input *string) int {
	return run(input, 1)
}

func part2(input *string) int {
	return run(input, 2)

}

func run(input *string, part int) int {
	cycle := 1

	if part == 2 {
		cycle = 2016 + 1
	}

	keys := []string{}
	hashes := map[string]string{}

	index := 0
	for {
		var h string
		h = hash(input, index, cycle, hashes)

		if c := hasTriples(h); c != "" {
			for i := index + 1; i <= index+1000; i++ {
				h = hash(input, i, cycle, hashes)

				if hasFiveInARow(c, h) {
					keys = append(keys, h)
					break
				}
			}
		}

		if len(keys) >= 64 {
			return index
		}
		index += 1
	}
}

func hash(input *string, i int, cycles int, hashes map[string]string) string {
	hashed := fmt.Sprintf("%s%d", *input, i)

	if hashes[hashed] != "" {
		return hashes[hashed]
	}

	for i := 0; i < cycles; i++ {
		// tmpHash := hashed
		hashed = fmt.Sprintf("%x", md5.Sum([]byte(hashed)))
		// hashes[tmpHash] = hashed
	}
	return hashed

}

func hasTriples(str string) string {
	for i := 2; i < len(str); i++ {
		if str[i] == str[i-1] && str[i-1] == str[i-2] {
			return string(str[i])
		}
	}

	return ""
}

func hasFiveInARow(char string, hash string) bool {
	for i := 4; i < len(hash); i++ {
		if char[0] == hash[i] && hash[i] == hash[i-1] && hash[i-1] == hash[i-2] && hash[i-2] == hash[i-3] && hash[i-3] == hash[i-4] {
			return true
		}
	}
	return false
}

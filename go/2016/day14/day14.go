package main

import (
	"crypto/md5"
	"flag"
	"fmt"
	"os"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

type FuncStringString func(string, int) string

var memHash map[string]string = map[string]string{}

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
	return run(generateHash, input, 1)
}

func part2(input *string) int {

	return run(generateStretchedHash, input, 2)
}

func run(fn func(string, int) string, input *string, part int) int {
	keys := []string{}
	hashes := make(map[int]string)

	index := 0
	for {
		h := lookupAndGenerate(fn, *input, index, hashes)

		if c := hasTriples(h); c != "" {
			for i := index + 1; i <= index+1000; i++ {
				h = lookupAndGenerate(fn, *input, i, hashes)

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

func lookupAndGenerate(fn func(string, int) string, salt string, index int, hashes map[int]string) string {
	if _, contains := hashes[index]; !contains {
		hash := fn(salt, index)
		hashes[index] = hash
		return hash
	}

	return hashes[index]
}

func generateHash(str string, index int) string {
	return fmt.Sprintf("%x", md5.Sum([]byte(fmt.Sprintf("%s%d", str, index))))
}

func generateStretchedHash(str string, index int) string {
	hashed := fmt.Sprintf("%s%d", str, index)
	i := 0
	for i < 2017 {
		hashed = fmt.Sprintf("%x", md5.Sum([]byte(hashed)))
		i++
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

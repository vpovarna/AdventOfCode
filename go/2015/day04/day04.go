package main

import (
	"crypto/md5"
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

	fmt.Printf("AoC 2015, Day4, Part1 solution is: %d \n", run(input, 5))
	fmt.Printf("AoC 2015, Day4, Part2 solution is: %d \n", run(input, 6))
}

func run(input string, numberOfZeros int) int {
	prefix := strings.Repeat("0", numberOfZeros)
	index := 0
	for {
		str := fmt.Sprintf("%s%d", input, index)
		hash := generateHash(str)
		hasPrefix := strings.HasPrefix(hash, prefix)
		if hasPrefix {
			return index
		}
		index++
	}
}

func generateHash(str string) string {
	return fmt.Sprintf("%x", md5.Sum([]byte(str)))
}

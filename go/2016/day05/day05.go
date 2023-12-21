package main

import (
	"crypto/md5"
	"encoding/hex"
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
	fmt.Printf("AoC 2016, Day5, Part1 solution is: %s \n", part1(&input))
}

func part1(input *string) string {
	index := 1
	var password []string

	for {
		s := fmt.Sprintf("%s%d", *input, index)
		md5 := getMD5Hash(s)
		if md5[0:5] == "00000" {
			password = append(password, string(md5[5]))
			if len(password) == 8 {
				break
			}
		}
		index += 1
	}
	return strings.Join(password, "")
}

func getMD5Hash(text string) string {
	hash := md5.Sum([]byte(text))
	return hex.EncodeToString(hash[:])
}

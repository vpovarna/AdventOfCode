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
	fmt.Printf("AoC 2016, Day5, Part2 solution is: %s \n", part2(&input))
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

func part2(input *string) string {
	index := 1
	cap := 8

	var password [8]string

	for {
		s := fmt.Sprintf("%s%d", *input, index)
		md5 := getMD5Hash(s)
		if md5[0:5] == "00000" {
			// The the int value by converting the rune to int and substract 0
			i := int(md5[5]) - '0'
			
			// If the idex is greater then the max idex or if we index is already field, move fw
			if i > 7 || password[i] != "" {
				index += 1
				continue
			}

			password[i] = string(md5[6])
			cap -= 1

			if cap == 0 {
				break
			}
		}
		index += 1
	}

	return strings.Join(password[:], "")
}

func getMD5Hash(text string) string {
	hash := md5.Sum([]byte(text))
	return hex.EncodeToString(hash[:])
}

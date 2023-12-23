package main

import (
	"flag"
	"fmt"
	"os"
)

var inputFile = flag.String("inputFile", "input.txt", "Received file path to use as input")

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)

	if err != nil {
		panic(err)
	}

	input := string(bytes)

	fmt.Println(input)

	fmt.Println("==========")
	fmt.Println(isAbba("qwer"))
}

func isAbba(word string) bool {
	if len(word) < 3 {
		return false
	}

	if len(word) == 4 {
		return word[0] == word[3] && word[1] == word[2] && word[0] != word[2]
	}

	return false

}

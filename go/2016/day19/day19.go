package main

import (
	"aoc/cast"
	"flag"
	"fmt"
	"os"
	"strconv"

	"github.com/gammazero/deque"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)

	if err != nil {
		panic(err)
	}

	input := string(bytes)

	fmt.Printf("AoC 2016, Day19, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2016, Day19, Part2 solution is: %d \n", part2(input))
}

// math solution: find the biggest 2 power which can subtracted from the input number. So: input = 2pow(n) + l. The answer is: 2 * l + 1
// https://www.youtube.com/watch?v=uCsD3ZGzMgE&ab_channel=Numberphile
// binary code: represent the number in binary. For 41, the binary rep is: 101001. Take the first number and move it to the end and recalculate the number
func part1(input string) int {
	n := cast.ToInt(input)
	var elves deque.Deque[int]

	for i := 1; i <= n; i++ {
		elves.PushBack(i)
	}

	// resize the deque by reading every two elements
	for elves.Len() > 1 {
		thief := elves.PopFront()
		elves.PushBack(thief)
		elves.PopFront()
	}

	return elves.At(0)
}

func part1BinarySolution(input string) int {
	n := int64(cast.ToInt(input))
	binaryRep := strconv.FormatInt(n, 2)

	winingNumber := binaryRep[1:] + "1"
	fmt.Println(binaryRep)

	nr, _ := strconv.ParseInt(winingNumber, 2, 64)
	return int(nr)
}
func part2(input string) int {
	n := cast.ToInt(input)
	var firstHalf deque.Deque[int]
	var secondHalf deque.Deque[int]

	for i := 1; i <= (n+1)/2; i++ {
		firstHalf.PushBack(i)
	}

	for i := (n+1)/2 + 1; i <= n; i++ {
		secondHalf.PushBack(i)
	}

	for firstHalf.Len() > 0 && secondHalf.Len() > 0 {
		if firstHalf.Len() > secondHalf.Len() {
			firstHalf.PopBack()
		} else {
			secondHalf.PopBack()
		}

		secondHalf.PushFront(firstHalf.PopFront())
		firstHalf.PushBack(secondHalf.PopBack())
	}

	if firstHalf.Len() == 0 {
		return secondHalf.At(0)
	} else {
		return firstHalf.At(0)
	}
}

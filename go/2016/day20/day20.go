package main

import (
	"aoc/utils"
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

	fmt.Printf("AoC 2016, Day19, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2016, Day19, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	mergeIntervals := mergeInterval(getSortedIntervals(input))
	return mergeIntervals[0][1] + 1
}

func part2(input string) int {
	mergeIntervals := mergeInterval(getSortedIntervals(input))
	return countTotalAllowedIps(mergeIntervals)
}

func readInput(input string) [][2]int {
	lines := strings.Split(input, "\n")

	ans := [][2]int{}
	for _, line := range lines {
		var r [2]int
		fmt.Sscanf(line, "%d-%d", &r[0], &r[1])
		ans = append(ans, r)
	}
	return ans
}

func getSortedIntervals(input string) [][2]int {
	intervals := readInput(input)
	sort.Slice(intervals, func(i, j int) bool {
		if intervals[i][0] != intervals[j][0] {
			return intervals[i][0] < intervals[j][0]
		}

		// Sort based on the second value too
		return intervals[i][1] < intervals[j][1]
	})

	return intervals
}

func mergeInterval(intervals [][2]int) [][2]int {
	merged := [][2]int{{}}

	for _, currentInterval := range intervals {
		lastIndex := len(merged) - 1
		if merged[lastIndex][1] >= currentInterval[0]-1 {
			// Update the second index of the last interval by determine the max between his value and second index value of the currentInterval
			merged[lastIndex][1] = utils.MaxInt(merged[lastIndex][1], currentInterval[1])
		} else {
			merged = append(merged, currentInterval)
		}
	}

	return merged

}

func countTotalAllowedIps(mergedInterval [][2]int) int {
	if mergedInterval[len(mergedInterval)-1][1] != math.MaxUint32 {
		mergedInterval = append(mergedInterval, [2]int{math.MaxUint32, 0})
	}

	var totalAllowedIps int

	for i := 1; i < len(mergedInterval); i++ {
		totalAllowedIps += mergedInterval[i][0] - mergedInterval[i-1][1] - 1
	}

	return totalAllowedIps
}

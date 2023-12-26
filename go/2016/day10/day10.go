package main

import (
	"aoc/utils"
	"flag"
	"fmt"
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

	fmt.Printf("AoC 2016, Day10, Part1 solution is: %d \n", part1(&input))
	fmt.Printf("AoC 2016, Day10, Part2 solution is: %d \n", part2(&input))
}

func part1(input *string) int {
	return balanceBots(input, []int{17, 61})
}

func part2(input *string) int {
	return balanceBots(input, nil)
}

func balanceBots(input *string, part1CompareValues []int) int {
	rules := getRule(input)
	botsMap := getBootsMap(input)

	outputs := map[int]int{}

	// for loop conditional is for part 2. part 1 returns from inside the loop.
	for outputs[0] == 0 || outputs[1] == 0 || outputs[2] == 0 {
		for _, r := range rules {
			if len(botsMap[r.botID]) == 2 {
				sort.Ints(botsMap[r.botID])
				low := utils.Min(botsMap[r.botID][0], botsMap[r.botID][1])
				high := utils.Max(botsMap[r.botID][0], botsMap[r.botID][1])

				// part 1 return value
				if len(part1CompareValues) != 0 &&
					low == part1CompareValues[0] && high == part1CompareValues[1] {
					return r.botID
				}
				var outputIndex, receivingBot int
				if strings.Contains(r.lowRule, "output") {
					_, err := fmt.Sscanf(r.lowRule, "low to output %d", &outputIndex)
					if err != nil {
						panic(err)
					}
					outputs[outputIndex] = low
				} else {
					_, err := fmt.Sscanf(r.lowRule, "low to bot %d", &receivingBot)
					if err != nil {
						panic(err)
					}
					botsMap[receivingBot] = append(botsMap[receivingBot], low)
				}
				if strings.Contains(r.highRule, "output") {
					_, err := fmt.Sscanf(r.highRule, "high to output %d", &outputIndex)
					if err != nil {
						panic(err)
					}
					outputs[outputIndex] = high
				} else {
					_, err := fmt.Sscanf(r.highRule, "high to bot %d", &receivingBot)
					if err != nil {
						panic(err)
					}
					botsMap[receivingBot] = append(botsMap[receivingBot], high)
				}
				botsMap[r.botID] = []int{}
			}
		}
	}

	// part 2 output
	return outputs[0] * outputs[1] * outputs[2]
}

type rule struct {
	botID    int
	lowRule  string
	highRule string
}

func getBootsMap(input *string) (botsMap map[int][]int) {
	botsMap = map[int][]int{}

	for _, line := range strings.Split(*input, "\n") {
		if strings.Contains(line, "value") {
			var botId, value int
			_, err := fmt.Sscanf(line, "value %d goes to bot %d", &value, &botId)
			if err != nil {
				panic(err)
			}
			botsMap[botId] = append(botsMap[botId], value)
		}
	}

	return botsMap
}

func getRule(input *string) (rules []rule) {
	for _, line := range strings.Split(*input, "\n") {
		if strings.Contains(line, "value") {
			continue
		}

		parts := strings.Split(line, " gives ")
		var botId int
		fmt.Sscanf(parts[0], "bot %d", &botId)
		rowRules := strings.Split(parts[1], " and ")
		r := rule{botId, rowRules[0], rowRules[1]}
		rules = append(rules, r)
	}
	return rules
}

package main

import (
	"fmt"
	"sort"
)

func main() {

	fmt.Printf("AoC 2022, Day9, Part1 solution is: %d \n", part1(true))
	fmt.Printf("AoC 2022, Day9, Part2 solution is: %d \n", part2(true))
}

func part1(useRealInput bool) int {
	monkeys := initInput()
	if !useRealInput {
		monkeys = intExample()
	}

	inspectedCounts := make([]int, len(monkeys))
	for round := 0; round < 20; round++ {
		for i, monkey := range monkeys {
			for _, item := range monkey.items {
				newItemValue := monkey.operation(item) / 3
				if newItemValue%monkey.testDivisibleBy == 0 {
					monkeys[monkey.trueMonkey].items = append(monkeys[monkey.trueMonkey].items, newItemValue)
				} else {
					monkeys[monkey.falseMonkey].items = append(monkeys[monkey.falseMonkey].items, newItemValue)
				}
			}
			inspectedCounts[i] += len(monkey.items)
			monkeys[i].items = []int{}
		}
	}

	sort.Ints(inspectedCounts)

	return inspectedCounts[len(inspectedCounts)-1] * inspectedCounts[len(inspectedCounts)-2]
}

func part2(useRealInput bool) int {
	monkeys := initInput()
	if !useRealInput {
		monkeys = intExample()
	}

	bigMod := 1
	for _, m := range monkeys {
		bigMod *= m.testDivisibleBy
	}

	inspectedCounts := make([]int, len(monkeys))
	for round := 0; round < 10000; round++ {
		for i, monkey := range monkeys {
			for _, item := range monkey.items {
				newItemVal := monkey.operation(item)
				newItemVal %= bigMod

				if newItemVal%monkey.testDivisibleBy == 0 {
					monkeys[monkey.trueMonkey].items = append(
						monkeys[monkey.trueMonkey].items, newItemVal)
				} else {
					monkeys[monkey.falseMonkey].items = append(
						monkeys[monkey.falseMonkey].items, newItemVal)
				}

			}
			inspectedCounts[i] += len(monkey.items)
			monkeys[i].items = []int{}
		}
	}

	sort.Ints(inspectedCounts)
	return inspectedCounts[len(inspectedCounts)-1] * inspectedCounts[len(inspectedCounts)-2]
}

type monkey struct {
	items                   []int
	operation               func(int) int
	testDivisibleBy         int
	trueMonkey, falseMonkey int
}

func intExample() []monkey {
	return []monkey{
		{
			items: []int{79, 98},
			operation: func(run int) int {
				return run * 19
			},
			testDivisibleBy: 23,
			trueMonkey:      2,
			falseMonkey:     3,
		},
		{
			items: []int{54, 65, 75, 74},
			operation: func(num int) int {
				return num + 6
			},
			testDivisibleBy: 19,
			trueMonkey:      2,
			falseMonkey:     0,
		},
		{
			items: []int{79, 60, 97},
			operation: func(num int) int {
				return num * num
			},
			testDivisibleBy: 13,
			trueMonkey:      1,
			falseMonkey:     3,
		},
		{
			items: []int{74},
			operation: func(num int) int {
				return num + 3
			},
			testDivisibleBy: 17,
			trueMonkey:      0,
			falseMonkey:     1,
		},
	}
}

func initInput() []monkey {
	return []monkey{
		{
			items: []int{77, 69, 76, 77, 50, 58},
			operation: func(run int) int {
				return run * 11
			},
			testDivisibleBy: 5,
			trueMonkey:      1,
			falseMonkey:     5,
		},
		{
			items: []int{75, 70, 82, 83, 96, 64, 62},
			operation: func(run int) int {
				return run + 8
			},
			testDivisibleBy: 17,
			trueMonkey:      5,
			falseMonkey:     6,
		},
		{
			items: []int{53},
			operation: func(run int) int {
				return run * 3
			},
			testDivisibleBy: 2,
			trueMonkey:      0,
			falseMonkey:     7,
		},
		{
			items: []int{85, 64, 93, 64, 99},
			operation: func(run int) int {
				return run + 4
			},
			testDivisibleBy: 7,
			trueMonkey:      7,
			falseMonkey:     2,
		},
		{
			items: []int{61, 92, 71},
			operation: func(run int) int {
				return run * run
			},
			testDivisibleBy: 3,
			trueMonkey:      2,
			falseMonkey:     3,
		},
		{
			items: []int{79, 73, 50, 90},
			operation: func(run int) int {
				return run + 2
			},
			testDivisibleBy: 11,
			trueMonkey:      4,
			falseMonkey:     6,
		},
		{
			items: []int{50, 89},
			operation: func(run int) int {
				return run + 3
			},
			testDivisibleBy: 13,
			trueMonkey:      4,
			falseMonkey:     3,
		},
		{
			items: []int{83, 56, 64, 58, 93, 91, 56, 65},
			operation: func(run int) int {
				return run + 5
			},
			testDivisibleBy: 19,
			trueMonkey:      1,
			falseMonkey:     0,
		},
	}
}

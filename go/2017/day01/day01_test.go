package main

import "testing"

type testCase struct {
	input  string
	result int
}

var testCasesPart1 = []testCase{
	{"1122", 3},
	{"91212129", 9},
}

var testCasesPart2 = []testCase{
	{"1212", 6},
	{"123425", 4},
	{"1221", 0},
	{"123123", 12},
	{"12131415", 4},
}

func TestPart1(t *testing.T) {
	for _, test := range testCasesPart1 {
		output := part1(test.input)
		if output != test.result {
			t.Errorf("Output %d not equal to expected %d", output, test.result)
		}
	}
}

func TestPart2(t *testing.T) {
	for _, test := range testCasesPart2 {
		output := part2(test.input)
		if output != test.result {
			t.Errorf("Output %d not equal to expected %d", output, test.result)
		}
	}
}

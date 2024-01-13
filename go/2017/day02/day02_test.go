package main

import (
	"strings"
	"testing"
)

type testCase struct {
	input  string
	result int
}

var testDataPart1 = []testCase{
	{"5 1 9 5", 8},
	{"7 5 3", 4},
	{"2 4 6 8", 6},
}

var testDataPart2 = []testCase{
	{"5 9 2 8", 4},
	{"9 4 7 3", 3},
	{"3 8 6 5", 2},
}

func TestGetRowHash(t *testing.T) {
	for _, test := range testDataPart1 {
		output := getRowHash(strings.Split(test.input, " "))
		if output != test.result {
			t.Errorf("Output %d not equal to expected %d", output, test.result)
		}
	}
}

func TestGetEvenlyDividedNumbers(t *testing.T) {
	for _, test := range testDataPart2 {
		output := getEvenlyDividedNumbersResult(strings.Split(test.input, " "))
		if output != test.result {
			t.Errorf("Output %d not equal to expected %d", output, test.result)
		}
	}
}

package main

import "testing"

type testCase struct {
	line   string
	result bool
}

var testCases = []testCase{
	{"aa bb cc dd ee", true},
	{"aa bb cc dd aa", false},
	{"aa bb cc dd aaa", true},
}

func TestHasDuplicate(t *testing.T) {
	for _, testCase := range testCases {
		output := isValidPart1(testCase.line)
		if output != testCase.result {
			t.Errorf("Output: %t Expected: %t", output, testCase.result)
		}
	}
}

type anagramsTestCase struct {
	word1          string
	word2          string
	expectedResult bool
}

var testStringsOccurrence = []anagramsTestCase{
	{"oiii", "iiio", true},
	{"abcde", "abcd", false},
}

func TestAreAnagrams(t *testing.T) {
	for _, testCase := range testStringsOccurrence {
		result := areAnagrams(testCase.word1, testCase.word2)
		if result != testCase.expectedResult {
			t.Errorf("Output: %t Expected: %t", result, testCase.expectedResult)
		}
	}
}

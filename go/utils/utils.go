package utils

import "aoc/cast"

func Max(x, y int) int {
	if x < y {
		return y
	}
	return x
}

func Min(x, y int) int {
	if x < y {
		return x
	}
	return y
}

func MinInt(nums ...int) int {
	minNum := nums[0]

	for _, n := range nums {
		if n < minNum {
			minNum = n
		}
	}

	return minNum
}

func MaxInt(nums ...int) int {
	maxNum := nums[0]
	for _, n := range nums {
		if n > maxNum {
			maxNum = n
		}
	}
	return maxNum
}

func Factorial(n int) int {
	ans := 1

	for i := 1; i <= n; i++ {
		ans *= i
	}

	return ans
}

func MapToInt(values []string) []int {
	var intValues []int

	for _, value := range values {
		intValues = append(intValues, cast.ToInt(value))
	}

	return intValues
}

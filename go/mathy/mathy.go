package mathy

func SumIntSlice(values []int) int {
	total := 0
	for _, value := range values {
		total += value
	}

	return total
}

func AbsInt(value int) int {
	if value < 0 {
		return value * -1
	}
	return value
}

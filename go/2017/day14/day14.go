package main

import (
	"flag"
	"fmt"
	"os"
	"strconv"
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

	fmt.Printf("AoC 2017, Day14, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2017, Day14, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	diskState := getDiskState(input)
	var usedCount int
	for _, row := range diskState {
		for _, b := range row {
			if b {
				usedCount++
			}
		}
	}

	return usedCount
}

func part2(input string) int {
	return numIslands(getDiskState(input))
}

func getDiskState(input string) [][]bool {
	// 128x128 grid, free (false) or used (true)
	diskState := make([][]bool, 128)
	for i := 0; i < 128; i++ {
		// hash input = {puzzleInput}-{0 through 127}
		hashKeyRaw := fmt.Sprintf("%s-%d", input, i)
		hexHash := calcHexKnotHash(hashKeyRaw)

		// convert hex-hash to bits
		bitsHash := transformHexToBits(hexHash)

		if len(bitsHash) != 128 {
			panic(fmt.Sprintf("expected bit hash to be 128 chars long, got %d", len(bitsHash)))
		}

		// set disk state for this row
		diskState[i] = make([]bool, 128)
		for b, bit := range bitsHash {
			switch bit {
			case '0':
				diskState[i][b] = false // free
			case '1':
				diskState[i][b] = true // used
			}
		}
	}

	return diskState
}

func calcHexKnotHash(input string) string {
	lengths := parseInputASCII(input)
	nums := make([]int, 256)
	for i := range nums {
		nums[i] = i
	}
	var position, skipSize int

	// 64 rounds of hashing
	for i := 0; i < 64; i++ {
		for _, length := range lengths {
			if length > 0 {
				nums = reverse(nums, position, position+length-1)
			}
			position += skipSize + length
			position %= len(nums)
			skipSize++
		}
	}

	var denseHash []int
	for i := 0; i < 16; i++ {
		var xord int
		for j := i * 16; j < (i+1)*16; j++ {
			xord ^= nums[j]
		}
		denseHash = append(denseHash, xord)
	}
	var hexdHash string
	for _, dense := range denseHash {
		// use %x to get hexadecimal version & 02 ensures leading 0 if needed
		hexdHash += fmt.Sprintf("%02x", dense)
	}

	return hexdHash
}

func parseInputASCII(input string) (ans []int) {
	for _, char := range input {
		ans = append(ans, int(char))
	}
	// add default lengths to end
	ans = append(ans, 17, 31, 73, 47, 23)
	return ans
}

func reverse(nums []int, left, right int) []int {
	right %= len(nums)
	if right < left {
		right += len(nums)
	}

	for left < right {
		leftModded := left % len(nums)
		rightModded := right % len(nums)
		nums[leftModded], nums[rightModded] = nums[rightModded], nums[leftModded]
		left++
		right--
	}

	return nums
}

func transformHexToBits(hex string) string {
	var bits string
	for _, char := range strings.Split(hex, "") {
		baseTen, err := strconv.ParseInt(char, 16, 64)
		if err != nil {
			panic("strconv error " + err.Error())
		}
		bits += fmt.Sprintf("%04b", baseTen)
	}
	return bits
}

func numIslands(grid [][]bool) int {
	var count int

	directions := [4][2]int{
		{0, 1},
		{0, -1},
		{1, 0},
		{-1, 0},
	}
	for i := 0; i < len(grid); i++ {
		for j := 0; j < len(grid[0]); j++ {
			if grid[i][j] {
				// zero out connected cells (i.e. zero out this island)
				queue := [][2]int{{i, j}}
				for len(queue) > 0 {
					// pop off queue
					currentRow, currentCol := queue[0][0], queue[0][1]
					grid[currentRow][currentCol] = false
					queue = queue[1:]

					// check neighbors, add to queue if true
					for _, dir := range directions {
						nextRow, nextCol := currentRow+dir[0], currentCol+dir[1]
						if nextRow >= 0 && nextRow < len(grid) && nextCol >= 0 && nextCol < len(grid[0]) {
							if grid[nextRow][nextCol] {
								queue = append(queue, [2]int{nextRow, nextCol})
							}
						}
					}
				}
				count++
			}
		}
	}

	return count
}

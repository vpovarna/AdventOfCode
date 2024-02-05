package main

import (
	"aoc/cast"
	"aoc/utils"
	"flag"
	"fmt"
	"math"
	"os"
	"strings"
)

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)
	if err != nil {
		panic("Unable to read the input file")
	}

	input := string(bytes)

	fmt.Printf("AoC 2022, Day7, Part1 solution is: %d \n", part1(input))
	fmt.Printf("AoC 2022, Day7, Part2 solution is: %d \n", part2(input))
}

func part1(input string) int {
	root := parseInput(input)
	return sumDirsUnder100000(root)
}

func part2(input string) int {
	root := parseInput(input)
	totalSpaceAvailable := 70000000
	spaceNeeded := 30000000

	directoryMinSize := spaceNeeded - (totalSpaceAvailable - root.totalSize)
	return findSmallestDirToDelete(root, directoryMinSize)
}

func sumDirsUnder100000(itr *dir) int {
	SizeLimit := 100000

	sum := 0
	if itr.totalSize <= SizeLimit {
		sum += itr.totalSize
	}
	for _, child := range itr.childDirs {
		sum += sumDirsUnder100000(child)
	}
	return sum
}

func findSmallestDirToDelete(currentDir *dir, directoryMinSize int) int {
	smallest := math.MaxInt64
	if currentDir.totalSize >= directoryMinSize {
		smallest = utils.MinInt(smallest, currentDir.totalSize)
	}

	for _, childDirs := range currentDir.childDirs {
		smallest = utils.MinInt(smallest, findSmallestDirToDelete(childDirs, directoryMinSize))
	}

	return smallest
}

type dir struct {
	name      string
	parentDir *dir
	files     map[string]int  // map contains the name of the file and size
	childDirs map[string]*dir // map containing the directory name an a pointer to actual dir
	totalSize int
}

func parseInput(input string) *dir {
	root := &dir{
		name:      "root",
		childDirs: map[string]*dir{},
	}

	currentDir := root
	commands := strings.Split(input, "\n")

	index := 0

	for index < len(commands) {
		cmd := commands[index]
		switch cmd[0:1] {
		case "$":
			if cmd == "$ ls" {
				index++
			} else {
				changeDir := strings.Split(cmd, "cd ")[1]
				changeDir = strings.TrimSpace(changeDir)
				// if the change dir is .. => we need to go up one level
				if changeDir == ".." {
					currentDir = currentDir.parentDir
				} else {
					// if change dir doesn't exist, we need to create it
					if _, ok := currentDir.childDirs[changeDir]; !ok {
						currentDir.childDirs[changeDir] = &dir{
							name:      changeDir,
							parentDir: currentDir,
							childDirs: map[string]*dir{},
							files:     map[string]int{},
						}
					}
					currentDir = currentDir.childDirs[changeDir]
				}
				index++
			}
		default:
			//listening a dir content and add them to the current dir in case they don't exist
			if strings.HasPrefix(cmd, "dir") {
				childDirName := cmd[4:]
				if _, ok := currentDir.childDirs[childDirName]; !ok {
					currentDir.childDirs[childDirName] = &dir{
						name:      childDirName,
						parentDir: currentDir,
						childDirs: map[string]*dir{},
						files:     map[string]int{},
					}
				}
			} else {
				// file name
				parts := strings.Split(cmd, " ")
				currentDir.files[parts[1]] = cast.ToInt(parts[0])
			}
			index++
		}
	}

	populateFileSize(root)
	return root
}

func populateFileSize(currentDir *dir) int {
	totalSize := 0
	for _, childDir := range currentDir.childDirs {
		totalSize += populateFileSize(childDir)
	}

	for _, size := range currentDir.files {
		totalSize += size
	}

	currentDir.totalSize = totalSize
	return totalSize
}

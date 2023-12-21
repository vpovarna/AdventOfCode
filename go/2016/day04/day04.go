package main

import (
	"flag"
	"fmt"
	"os"
	"regexp"
	"sort"
	"strconv"
	"strings"
)

type room struct {
	name       string
	roomNumber int
	checksum   string
	occurrence map[rune]int
}

type freqEntry struct {
	char  rune
	count int
}

type SortEntryByCountAndAlpha []freqEntry

func (a SortEntryByCountAndAlpha) Len() int      { return len(a) }
func (a SortEntryByCountAndAlpha) Swap(i, j int) { a[i], a[j] = a[j], a[i] }
func (a SortEntryByCountAndAlpha) Less(i, j int) bool {
	if a[i].count == a[j].count {
		return a[i].char < a[j].char
	}
	return a[i].count > a[j].count
}

func (r *room) getObservedChars(size int) []freqEntry {
	occurrence := r.occurrence
	observed := make([]freqEntry, 5)
	for k, v := range occurrence {
		entry := freqEntry{k, v}
		observed = append(observed, entry)
		sort.Sort(SortEntryByCountAndAlpha(observed))
		observed = observed[0:5]
	}
	return observed
}

func (r *room) decryptRoomName() int {
	name := r.name
	roomNumber := r.roomNumber

	var sb strings.Builder

	for _, c := range name {
		if c == '-' {
			sb.WriteRune(' ')
		}
		sb.WriteRune(rune('a' + (int(c)-'a'+roomNumber)%26))
		if sb.String() == "northpole" {
			return roomNumber
		}
	}
	return -1
}

var inputFile = flag.String("inputFile", "input.txt", "Relative file path to use as input")

func main() {
	flag.Parse()
	bytes, err := os.ReadFile(*inputFile)

	if err != nil {
		panic(err)
	}

	input := string(bytes)

	fmt.Printf("AoC 2016, Day4, Part1 solution is: %d \n", part1(&input))
	fmt.Printf("AoC 2016, Day4, Part2 solution is: %d \n", part2(&input))
}

func part1(input *string) int {
	lines := strings.Split(*input, "\n")
	sum := 0

	for _, line := range lines {
		room := getRoom(line)
		freqEntries := room.getObservedChars(5)
		inputChecksum := room.checksum
		id := room.roomNumber

		var sb strings.Builder
		for _, fq := range freqEntries {
			sb.WriteRune(fq.char)
		}

		if sb.String() == inputChecksum {
			sum += id
		}
	}

	return sum
}

func part2(input *string) int {
	lines := strings.Split(*input, "\n")
	for _, line := range lines {
		room := getRoom(line)
		nr := room.decryptRoomName()
		if (nr) > -1 {
			return nr
		}
	}
	return -1
}

func getRoom(row string) room {
	re := regexp.MustCompile("([a-z-]+)-([0-9]+)\\[([a-z]+)\\]")

	parsed := re.FindStringSubmatch(row)

	if len(parsed) != 4 {
		fmt.Printf("Invalid row: %s \n", row)
	}

	name := parsed[1]
	id, _ := strconv.Atoi(parsed[2])
	check := parsed[3]

	occurrence := make(map[rune]int)

	for _, c := range name {
		if c == '-' {
			continue
		}
		occurrence[c] += 1
	}

	return room{
		name:       name,
		roomNumber: id,
		occurrence: occurrence,
		checksum:   check,
	}
}

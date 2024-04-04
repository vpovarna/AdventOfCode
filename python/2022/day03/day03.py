from typing import List
from string import ascii_lowercase, ascii_uppercase

key = ascii_lowercase + ascii_uppercase


def part1(input: str) -> int:
    total = 0
    for line in parse_input(input):
        n = len(line)

        sub1 = line[: (n // 2)]
        sub2 = line[(n // 2) :]

        c = list(set(sub1).intersection(sub2))
        total += key.index(c[0]) + 1

    return total


def part2(input: str) -> int:
    lines = parse_input(input)

    total = 0
    for i in range(0, len(lines), 3):
        group = lines[i : (i + 3)]
        c = list(set(group[0]).intersection(group[1]).intersection(group[2]))
        total += key.index(c[0]) + 1

    return total


def parse_input(input: str) -> List[str]:
    with open(input) as fn:
        raw_data = fn.read().strip()
        parts = raw_data.split("\n")
    return parts


def main():
    puzzle_input_path = '2022/day03/input.txt'
    print(f"AoC2022 Day3, Part1 solution is: {part1(puzzle_input_path)}")
    print(f"AoC2022 Day3, Part2 solution is: {part2(puzzle_input_path)}")


if __name__ == "__main__":
    main()

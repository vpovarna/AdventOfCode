from typing import List
from collections import defaultdict


def part1(puzzle_input_path: str) -> int:
    lines = parse_input(puzzle_input_path)

    coordinates = defaultdict(int)

    for line in lines:
        parts = line.split(" -> ")
        assert len(parts) == 2

        x1, y1 = [int(p) for p in parts[0].split(",")]
        x2, y2 = [int(p) for p in parts[1].split(",")]

        if x1 == x2:
            for y in range(min(y1, y2), max(y1, y2) + 1):
                coordinates[(x1, y)] += 1

        if y1 == y2:
            for x in range(min(x1, x2), max(x1, x2) + 1):
                coordinates[(x, y1)] += 1

    return len([k for k, v in coordinates.items() if v >= 2])


def part2(puzzle_input_path: str) -> int:
    return -1


def parse_input(puzzle_input_path: str) -> List[str]:
    with open(puzzle_input_path) as fn:
        return fn.read().split("\n")


def main():
    puzzle_input_path = "2021/day05/input.txt"
    print(f"AoC2021 Day5, Part1 solution is: {part1(puzzle_input_path)}")
    print(f"AoC2021 Day5, Part2 solution is: {part2(puzzle_input_path)}")


if __name__ == '__main__':
    main()

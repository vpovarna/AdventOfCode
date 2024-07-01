from typing import List
from collections import defaultdict


def run(lines: List[str], with_diagonals: bool) -> int:
    coordinates = defaultdict(int)

    for line in lines:
        parts = line.split(" -> ")
        assert len(parts) == 2

        x1, y1 = [int(p) for p in parts[0].split(",")]
        x2, y2 = [int(p) for p in parts[1].split(",")]

        # Case: 2, 2 -> 2, 1
        if x1 == x2:
            min_y = min(y1, y2)
            max_y = max(y1, y2)
            for y in range(min_y, max_y + 1):
                coordinates[(x1, y)] += 1
        # Case: 0,9 -> 5,9
        elif y1 == y2:
            min_x = min(x1, x2)
            max_x = max(x1, x2)
            for x in range(min_x, max_x + 1):
                coordinates[(x, y1)] += 1
        elif not with_diagonals:
            continue
        # Case: 8, 0 -> 0, 8
        elif x1 == y2 and y1 == x2:
            max_x = max(x1, x2)
            min_x = min(x1, x2)
            diff = max_x - min_x
            for d in range(0, diff + 1):
                coordinates[(max_x - d, min_x + d)] += 1
        # Case: 0, 0 -> 8, 8
        elif x1 == y1 and x2 == y2:
            for d in range(min(x1, x2), max(y1, y2) + 1):
                coordinates[(d, d)] += 1
        # Case: 6, 4 -> 2, 0
        elif (x1 > x2 and y1 > y2) or (x1 < x2 and y1 < y2):
            max_x = max(x1, x2)
            min_x = min(x1, x2)
            min_y = min(y1, y2)
            diff = max_x - min_x
            for d in range(0, diff + 1):
                coordinates[(min_x + d, min_y + d)] += 1
        # Case: 5,5 -> 8,2
        elif (x1 < x2 and y1 > y2) or (x1 > x2 and y1 < y2):
            max_x = max(x1, x2)
            min_x = min(x1, x2)
            max_y = max(y1, y2)
            diff = max_x - min_x
            for d in range(0, diff + 1):
                coordinates[(min_x + d, max_y - d)] += 1

    return len([k for k, v in coordinates.items() if v >= 2])


def parse_input(puzzle_input_path: str) -> List[str]:
    with open(puzzle_input_path) as fn:
        return fn.read().split("\n")


def main():
    puzzle_input_path = "2021/day05/input.txt"
    lines = parse_input(puzzle_input_path)
    print(f"AoC2021 Day5, Part1 solution is: {run(lines, False)}")
    print(f"AoC2021 Day5, Part2 solution is: {run(lines, True)}")


if __name__ == '__main__':
    main()

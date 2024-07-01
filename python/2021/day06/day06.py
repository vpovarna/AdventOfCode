from typing import List
from collections import deque


def part1(numbers: List[int]) -> int:
    q = deque(numbers)

    #TODO: Refactor using a map for part2
    for i in range(80):
        new_lantern_fish = 0
        for _ in range(len(q)):
            cur = q.popleft()
            if cur == 0:
                q.append(6)
                new_lantern_fish += 1
            else:
                q.append(cur - 1)
        for _ in range(new_lantern_fish):
            q.append(8)
        # print(f"After {i+1} days: {q}")

    return len(q)


def part2(lines: List[int]) -> int:
    return -1


def parse_input(input_file_path: str) -> List[int]:
    with open(input_file_path, 'r') as fn:
        parts = fn.read().strip().split(',')
        return [int(x.strip()) for x in parts]


def main():
    puzzle_input_path = "2021/day06/input.txt"
    lines = parse_input(puzzle_input_path)
    print(f"AoC2021 Day6, Part1 solution is: {part1(lines)}")
    print(f"AoC2021 Day6, Part2 solution is: {part2(lines)}")


if __name__ == '__main__':
    main()

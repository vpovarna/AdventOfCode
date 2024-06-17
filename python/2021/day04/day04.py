import re
from dataclasses import dataclass
from typing import List


@dataclass
class Grid:
    def __init__(self, raw_grid: str):
        self.grid = parse_board(raw_grid)

    def __repr__(self):
        """
        For debugging purpose
        """
        for grid_lines in self.grid:
            print(grid_lines)


def parse_board(raw_grid: str) -> list[list[int]]:
    lines = raw_grid.strip().split("\n")
    return [[int(i) for i in re.split(" +", line.strip())] for line in lines]


def part1(puzzle_input_path: str) -> int:
    blocks = read_input(puzzle_input_path)
    numbers = blocks[0]
    row_grids = blocks[1:]

    grid = Grid(row_grids[0])
    print(grid.__repr__())
    return -1


def part2(puzzle_input_path: str) -> int:
    return -1


def read_input(puzzle_input_path: str):
    with open(puzzle_input_path) as fn:
        return fn.read().strip().split("\n\n")


def main():
    puzzle_input_path = "2021/day04/input.txt"
    print(f"AoC2021 Day4, Part1 solution is: {part1(puzzle_input_path)}")
    print(f"AoC2021 Day4, Part2 solution is: {part2(puzzle_input_path)}")


if __name__ == '__main__':
    main()

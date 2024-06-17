import re
from dataclasses import dataclass
from typing import List

BOARD_SIZE = 5


@dataclass
class Grid:
    def __init__(self, raw_board: List[List[int]]):
        self.grid = [
            [
                [raw_board[i][j], False] for j in range(BOARD_SIZE)
            ]
            for i in range(BOARD_SIZE)
        ]

    def mark_number(self, number: int) -> None:
        for i in range(BOARD_SIZE):
            for j in range(BOARD_SIZE):
                t = self.grid[i][j]
                if t[0] == number:
                    t[1] = True

    def detect_win(self) -> bool:
        for row in range(BOARD_SIZE):
            if all([self.grid[row][i][1] for i in range(BOARD_SIZE)]):
                return True

        for col in range(BOARD_SIZE):
            if all([self.grid[i][col][1] for i in range(BOARD_SIZE)]):
                return True
        return False

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

    grid = Grid(parse_board(row_grids[0]))
    grid.mark_number(19)
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

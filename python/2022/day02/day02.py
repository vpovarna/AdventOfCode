from typing import List
from enum import Enum


class Hand(Enum):
    ROCK = 1
    PAPER = 2
    SCISSOR = 3


class Score(Enum):
    WIN = 6
    LOSS = 0
    DRAW = 3


def part1(input) -> int:
    choices = {"X": Hand.ROCK, "Y": Hand.PAPER, "Z": Hand.SCISSOR}

    total = 0
    for line in parse_input(input):
        parts = line.split(" ")
        assert len(parts) == 2

        if parts[1] == "Y":
            match parts[0]:
                case "A":
                    total += Score.WIN.value
                case "B":
                    total += Score.DRAW.value
                case "C":
                    total += Score.LOSS.value
                case _:
                    raise Exception("unsupported case")

        if parts[1] == "Z":
            match parts[0]:
                case "A":
                    total += Score.LOSS.value
                case "B":
                    total += Score.WIN.value
                case "C":
                    total += Score.DRAW.value
                case _:
                    raise Exception("unsupported case")
                
        if parts[1] == "X":
            match parts[0]:
                case "A":
                    total += Score.DRAW.value
                case "B":
                    total += Score.LOSS.value
                case "C":
                    total += Score.WIN.value
                case _:
                    raise Exception("unsupported case")

        total += choices[parts[1]].value
    return total


def part2(input) -> int:
    return -1


def parse_input(input: str) -> List[str]:
    with open(input) as fn:
        raw_data = fn.read().strip()
        parts = raw_data.split("\n")
    return parts


def main():
    puzzle_input_path = '2022/day02/input.txt'
    print(f"AoC2022 Day1, Part1 solution is: {part1(puzzle_input_path)}")
    print(f"AoC2022 Day1, Part2 solution is: {part2(puzzle_input_path)}")


if __name__ == "__main__":
    main()

from typing import List


def part1(input) -> int:
    loads = get_calories(input)
    return max(loads)


def part2(input: str) -> int:
    loads = get_calories(input)
    loads.sort()
    return sum(loads[-3:])


def parse_input(input: str) -> List[str]:
    with open(input) as fn:
        raw_data = fn.read().strip()
        parts = raw_data.split("\n\n")
    return parts


def get_calories(input: str) -> List[int]:
    loads = []
    for part in parse_input(input):
        calories = [
            int(x) for x in part.split()
        ]  # this can be written as: map(int, part.split())
        loads.append(sum(calories))
    return loads


def main():
    puzzle_input_path = "2022/day01/input.txt"
    print(f"AoC2022 Day1, Part1 solution is: {part1(puzzle_input_path)}")
    print(f"AoC2022 Day1, Part2 solution is: {part2(puzzle_input_path)}")


if __name__ == "__main__":
    main()

from typing import List


def part1(puzzle_input_path: str) -> int:
    lines = read_input(input_file=puzzle_input_path)
    horizontal, depth = 0, 0

    for line in lines:
        direction, value = line.split(" ")
        value = int(value)

        match direction:
            case "forward":
                horizontal += value
            case "up":
                depth -= value
            case "down":
                depth += value
            case _:
                raise Exception("Unsupported direction")

    return horizontal * depth


def part2(puzzle_input_path: str) -> int:
    lines = read_input(input_file=puzzle_input_path)
    horizontal, depth, aim = 0, 0, 0

    for line in lines:
        direction, value = line.split(" ")
        value = int(value)

        match direction:
            case "forward":
                horizontal += value
                depth += aim * value
            case "up":
                aim -= value
            case "down":
                aim += value
            case _:
                raise Exception("Unsupported direction")

    return horizontal * depth


def read_input(input_file: str) -> List[str]:
    with open(input_file) as fn:
        return fn.read().strip().splitlines()


def main():
    puzzle_input_path = "2021/day02/input.txt"
    print(f"AoC2021 Day2, Part1 solution is: {part1(puzzle_input_path)}")
    print(f"AoC2021 Day2, Part2 solution is: {part2(puzzle_input_path)}")


if __name__ == '__main__':
    main()

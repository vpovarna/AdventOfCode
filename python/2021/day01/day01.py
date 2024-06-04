from typing import List


def part1(puzzle_input_path: str) -> int:
    depths = parse_input(input_file=puzzle_input_path)
    return calculate_measurements(depths)


def part2(puzzle_input_path: str) -> int:
    depths = parse_input(input_file=puzzle_input_path)

    depths_zip = zip(depths[:len(depths) - 2], depths[1:len(depths) - 1], depths[2:])
    depths = [depths[0] + depths[1] + depths[2] for depths in depths_zip]

    return calculate_measurements(depths)


def calculate_measurements(depths: List[int]):
    depths_zip = zip(depths[:len(depths) - 1], depths[1:])
    return sum([1 for depths in depths_zip if depths[0] < depths[1]])


def parse_input(input_file: str) -> List[int]:
    with open(input_file) as fn:
        return list(map(int, fn.read().strip().splitlines()))


def main():
    puzzle_input_path = "2021/day01/input.txt"
    print(f"AoC2021 Day1, Part1 solution is: {part1(puzzle_input_path)}")
    print(f"AoC2021 Day1, Part2 solution is: {part2(puzzle_input_path)}")


if __name__ == '__main__':
    main()

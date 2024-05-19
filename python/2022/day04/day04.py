from typing import Callable, List


def run(input: str, fn: Callable[[List[int], List[int]], bool]) -> int:
    count = 0
    for line in parse_input(input):
        parts = line.split(",")
        assert len(parts) == 2

        elf1 = [int(x) for x in parts[0].split("-")]
        elf2 = [int(x) for x in parts[1].split("-")]

        if fn(elf1, elf2):
            count += 1

    return count


def part1_condition(elf1: List[int], elf2: List[int]) -> bool:
    assert len(elf1) == 2
    assert len(elf2) == 2

    (x1, x2) = elf1
    (y1, y2) = elf2

    return (x1 <= y1 <= y2 <= x2) or (y1 <= x1 <= x2 <= y2)


def part2_condition(elf1: List[int], elf2: List[int]) -> bool:
    assert len(elf1) == 2
    assert len(elf2) == 2

    (x1, x2) = elf1
    (y1, y2) = elf2

    return (
        x1 <= y1 <= y2 <= x2
        or y1 <= x1 < +x2 <= y2
        or x1 <= y1 <= x2
        or x1 <= y2 <= x2
        or y1 <= x1 <= y2
        or y1 <= x2 <= y2
    )


def parse_input(input: str) -> List[str]:
    with open(input) as fn:
        raw_data = fn.read().strip()
        parts = raw_data.split("\n")
    return parts


def main():
    puzzle_input_path = "2022/day04/input.txt"
    print(f"AoC2022 Day4, Part1 solution is: {run(puzzle_input_path, part1_condition)}")
    print(f"AoC2022 Day4, Part2 solution is: {run(puzzle_input_path, part2_condition)}")


if __name__ == "__main__":
    main()

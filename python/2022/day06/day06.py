from typing import List


def run(input: str, distinct_chars_nr: int) -> int:
    lines = parse_input(input)
    assert len(lines) == 1

    return get_index(lines[0], distinct_chars_nr)


def get_index(word: str, distinct_chars_nr: int) -> int:
    for i in range(distinct_chars_nr, len(word)):
        substr = word[i - distinct_chars_nr : i]
        if len(set(substr)) == distinct_chars_nr:
            return i
    return -1


def parse_input(input: str) -> List[str]:
    with open(input) as fn:
        raw_data = fn.read()
        parts = raw_data.split("\n")
    return parts


def main():
    puzzle_input_path = "2022/day06/input.txt"
    print(f"AoC2022 Day6, Part1 solution is: {run(input=puzzle_input_path, distinct_chars_nr=4)}")
    print(f"AoC2022 Day6, Part2 solution is: {run(input=puzzle_input_path, distinct_chars_nr=14)}")


if __name__ == "__main__":
    main()

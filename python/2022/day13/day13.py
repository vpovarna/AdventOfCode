def compare(a: str, b: str) -> int:
    return -1


def part1(input: str) -> int:
    parts = parse_input(input)

    ans = 0

    for i, block in enumerate(parts):
        a, b = map(eval, block.split("\n"))
        if compare(a, b) == 1:
            print(i)
            ans += i + 1

    return ans


def part2(input: str) -> int:
    return -1


def parse_input(input: str) -> list[str]:
    with open(input) as fn:
        return fn.read().strip().split("\n\n")


def main():
    puzzle_input_path = "2022/day13/input.txt"
    print(f"AoC2022 Day13, Part1 solution is: {part1(input=puzzle_input_path)}")
    print(f"AoC2022 Day13, Part2 solution is: {part2(input=puzzle_input_path)}")


if __name__ == "__main__":
    main()

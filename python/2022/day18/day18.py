def part1(input_str: str) -> int:
    lines = parse_input(input_str)
    cube_sides_list = []
    for line in lines:
        a, b, c = map(int, line.split(','))
        cube_sides_list.append((a, b, c))

    ans = 0
    for cube_side in cube_sides_list:
        a, b, c = cube_side
        for a_diff, b_diff, c_diff in [(1, 0, 0), (-1, 0, 0), (0, -1, 0), (0, 1, 0), (0, 0, -1), (0, 0, 1)]:
            if (a + a_diff, b + b_diff, c + c_diff) not in cube_sides_list:
                ans += 1
    return ans


def part2(input_str: str) -> int:
    return -1


def parse_input(input_str: str) -> list[str]:
    with open(input_str) as fn:
        return fn.read().strip().splitlines()


def main():
    puzzle_input_path = "2022/day18/input.txt"
    print(f"AoC2022 Day15, Part1 solution is: {part1(input_str=puzzle_input_path)}")
    print(f"AoC2022 Day15, Part2 solution is: {part2(input_str=puzzle_input_path)}")


if __name__ == "__main__":
    main()

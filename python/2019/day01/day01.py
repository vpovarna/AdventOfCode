with open("input.txt") as f:
    input_data = f.read().strip()


def part1() -> int:
    masses = _parse_input()
    ans = 0
    for mas in masses:
        ans += mas // 3 - 2
    return ans


def part2() -> int:
    masses = _parse_input()
    ans = 0

    for mas in masses:
        acc = 0

        while mas > 0:
            mas = mas // 3 - 2
            if mas > 0:
                acc += mas
        ans += acc

    return ans


def part2_with_recursion() -> int:
    def helper(mas: int) -> int:
        if mas <= 0:
            return 0
        return mas // 3 - 2 + helper(mas // 3 - 2)

    masses = _parse_input()
    ans = 0
    for mas in masses:
        ans += helper(mas)
    return ans


def _parse_input() -> list[int]:
    return [int(x) for x in input_data.split("\n")]


if __name__ == '__main__':
    print(f"Part 1: {part1()}")
    print(f"Part 2: {part2()}")
    print(f"Part 2 with recursion: {part2_with_recursion()}")

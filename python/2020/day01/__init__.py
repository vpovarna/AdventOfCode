with open("input.txt") as f:
    input_data = [int(x.strip()) for x in f.readlines()]


def part1() -> int:
    visited_numbers = set()

    for n in input_data:
        if 2020 - n in visited_numbers:
            return n * (2020 - n)
        visited_numbers.add(n)
    return -1


def part2() -> int:
    for i, n in enumerate(input_data):
        seen = set()
        target = 2020 - n

        for m in input_data[i + 1:]:
            if target - m in seen:
                return n * m * (target - m)
            seen.add(m)

    return -1


if __name__ == '__main__':
    print(f"Part1 solution is: {part1()}")
    print(f"Part2 solution is: {part2()}")

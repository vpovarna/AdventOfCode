with open("input.txt") as f:
    input_data = f.read().strip()


def part1() -> int:
    digits = [int(d) for d in input_data]
    total = 0
    for i, d in enumerate(digits):
        if i == len(digits) - 1:
            if digits[i] == digits[0]:
                total += d
            continue

        if digits[i] == digits[i + 1]:
            total += digits[i]

    return total


def part2() -> int:
    digits = [int(d) for d in input_data]
    total = 0
    N = len(digits)
    for i in range(N):
        if digits[i] == digits[(N // 2 + i) % N]:
            total += digits[i]
    return total


if __name__ == '__main__':
    print(f"Part 1 solution is {part1()}")
    print(f"Part 2 solution is {part2()}")

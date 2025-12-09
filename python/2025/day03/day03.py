with open("2025/day03/input.txt") as f:
    input_data = f.read().strip()


def part1() -> int:
    lines = input_data.split("\n")

    total = 0

    for line in lines:
        digits = [int(c) for c in line]
        max_digit = max(digits[0 : len(digits) - 1])
        max_digit_index = 0
        for i, digit in enumerate(digits[: len(digits) - 1]):
            if digit == max_digit:
                max_digit_index = i
                break

        next_max_value = 0
        for i in range(max_digit_index + 1, len(digits)):
            next_max_value = max(next_max_value, digits[i])

        total += int(f"{max_digit}{next_max_value}")

    return total


def max_k(line: str, k: int = 12) -> int:
    digits = [int(c) for c in line]
    n = len(digits)

    start = 0
    chosen = []

    for i in range(k):
        end = n - (k - i)
        segment = digits[start : end + 1]
        max_digit = max(segment)
        max_digit_index = segment.index(max_digit) + start

        chosen.append(max_digit)
        start = max_digit_index + 1

    s = ""
    for c in chosen:
        s += str(c)
    return int(s)


def part2() -> int:
    total = 0
    for line in input_data.split("\n"):
        total += max_k(line=line)
    return total


if __name__ == "__main__":
    print(part1())
    print(part2())

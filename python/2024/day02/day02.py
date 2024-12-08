with open("input.txt") as f:
    input_lines = f.read().strip().split("\n")
    lines = [list(map(int, line.split(" "))) for line in input_lines]


def sliding_window(levels: list[int], window_size: int) -> list[int]:
    for i in range(len(levels) - window_size + 1):
        yield levels[i:i + window_size]


def is_increasing(levels: list[int]) -> bool:
    for window in sliding_window(levels, 2):
        if window[0] >= window[1]:
            return False

    return True


def is_decreasing(levels: list[int]) -> bool:
    for window in sliding_window(levels, 2):
        if window[0] <= window[1]:
            return False

    return True


def is_safe(levels: list[int]) -> bool:
    if not (is_increasing(levels) or is_decreasing(levels)):
        return False

    for i in range(1, len(levels)):
        diff = abs(levels[i] - levels[i - 1])
        if not (1 <= diff <= 3):
            return False

    return True


def is_really_safe(levels: list[int]) -> bool:
    if is_safe(levels):
        return True

    for i in range(len(levels)):
        tmp_list = levels[:i] + levels[i + 1:]
        if is_safe(tmp_list):
            return True
    return False


def part1() -> int:
    ans = 0
    for line in lines:
        if is_safe(line):
            ans += 1
    return ans


def part2() -> int:
    ans = 0
    for line in lines:
        if is_really_safe(line):
            ans += 1
    return ans


if __name__ == '__main__':
    print(part1())
    print(part2())

import re

with open("input.txt") as f:
    line = f.read().strip()


def part1() -> int:
    ans = 0
    matches = re.findall(r"mul\((\d+),(\d+)\)", line)

    for match in matches:
        ans += int(match[0]) * int(match[1])
    return ans


def part2() -> int:
    ans = 0
    matches = re.findall(r"(?:mul\((\d+),(\d+)\))|(do\(\)|don't\(\))", line)

    enabled = True

    for match in matches:
        if match[2] == "" and enabled:
            ans += int(match[0]) * int(match[1])
        elif match[2] == "do()":
            enabled = True
        else:
            enabled = False

    return ans


if __name__ == '__main__':
    print(f"Part1: {part1()}")
    print(f"Part2: {part2()}")

with open("input.txt") as f:
    input_data = f.read().strip()


def part1() -> int:
    twice_count = 0
    thrice_count = 0

    for line in input_data.split("\n"):
        d = build_occurrence(line.strip())
        values = d.values()
        if 2 in values:
            twice_count += 1
        if 3 in values:
            thrice_count += 1
    return twice_count * thrice_count


def part2() -> str:

    return ""


def build_occurrence(line: str) -> dict[str, int]:
    d = {}
    for c in line:
        v = d.get(c, 0) + 1
        d[c] = v
    return d


def compare_two_lines(line1: str, line2: str) -> str | None:
    result = ""
    for i in range(len(line1)):
        if line1[i] == line2[i]:
            result += line1[i]

    if len(result) == len(line1) - 1:
        return result
    else:
        return None


if __name__ == '__main__':
    print(f"Part1 solution is: {part1()}")

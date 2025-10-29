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
    lines = input_data.split("\n")
    N = len(lines)
    for i in range(0, N):
        for j in range(i + 1, N):
            is_true, result = compare_two_lines(lines[i].strip(), lines[j].strip())
            if is_true:
                return result
    return ""


def part2_optimal() -> str:
    lines = input_data.strip().split("\n")
    seen = set()

    for line in lines:
        current_variants = [line[:i] + line[i + 1:] for i in range(len(line))]
        for v in current_variants:
            if v in seen:
                return v
        seen.update(current_variants)


def compare_two_lines(line1: str, line2: str) -> (bool, str):
    error_budget = 0
    common_chars = ""
    for i in range(len(line1)):
        if line1[i] != line2[i]:
            error_budget += 1
            if error_budget > 1:
                return False, ""
            continue
        common_chars += line1[i]
    return True, common_chars


def build_occurrence(line: str) -> dict[str, int]:
    d = {}
    for c in line:
        v = d.get(c, 0) + 1
        d[c] = v
    return d


if __name__ == '__main__':
    print(f"Part1 solution is: {part1()}")
    print(f"Part2 solution is: {part2_optimal()}")

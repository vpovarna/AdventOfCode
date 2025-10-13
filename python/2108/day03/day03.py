with open("input.txt") as f:
    instructions = f.read().strip().split("\n")


def part1() -> int:
    points = set()
    overlaps = set()

    for instruction in instructions:
        i, j, n, m, _ = _parse_line(instruction)

        for x in range(i, i + n):
            for y in range(j, j + m):
                if (x, y) in points:
                    overlaps.add((x, y))
                points.add((x, y))
    return (len(overlaps))


def part2() -> list:
    points = {}
    all_claims = set()

    for idx, instruction in enumerate(instructions):
        i, j, n, m, claim_number = _parse_line(instruction)
        all_claims.add(claim_number)

        for x in range(i, i + n):
            for y in range(j, j + m):
                if (x, y) not in points:
                    points[(x, y)] = []
                points[(x, y)].append(claim_number)

    overlap_claims = set()
    for claim_list in points.values():
        if len(claim_list) > 1:
            overlap_claims.update(claim_list)

    non_overlapping_claims = all_claims - overlap_claims
    return non_overlapping_claims.pop()


def _parse_line(line: str) -> list:
    parts = line.split(": ")
    i, j = parts[0].split(" ")[2].split(",")
    n, m = parts[1].split("x")
    claim_nr = parts[0].split(" ")[0][1:]
    return [int(i), int(j), int(n), int(m), int(claim_nr)]


if __name__ == '__main__':
    print(f"Part1 solution is: {part1()}")
    print(f"Part2 solution is: {part2()}")

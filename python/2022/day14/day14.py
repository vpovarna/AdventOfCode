def part1(input: str) -> int:
    lines = parse_input(input)
    fields = get_fields(lines)

    max_y = max([coords[1] for coords in fields])

    ans = 0
    is_sand_falling = True

    while is_sand_falling:
        is_sand_falling = simulate_sand_part1(fields, max_y)
        if is_sand_falling:
            ans += 1

    return ans


def part2(input: str) -> int:
    lines = parse_input(input)
    fields = get_fields(lines)

    max_y = max([coords[1] for coords in fields])

    ans = 0

    while True:
        x, y = simulate_sand_part2(fields, max_y)
        fields.add((x, y))
        ans += 1
        if (x, y) == (500, 0):
            break

    return ans


def simulate_sand_part1(fields: set[(int, int)], max_y: int) -> bool:
    x, y = 500, 0

    while y < max_y:
        if (x, y + 1) not in fields:
            y += 1
            continue

        if (x - 1, y + 1) not in fields:
            x -= 1
            y += 1
            continue

        if (x + 1, y + 1) not in fields:
            x += 1
            y += 1
            continue

        fields.add((x, y))
        return True

    return False


def simulate_sand_part2(filled: set[(int, int)], max_y: int) -> tuple[int, int]:
    x, y = 500, 0

    if (x, y) in filled:
        return (x, y)

    while y <= max_y:
        if (x, y + 1) not in filled:
            y += 1
            continue

        if (x - 1, y + 1) not in filled:
            x -= 1
            y += 1
            continue

        if (x + 1, y + 1) not in filled:
            x += 1
            y += 1
            continue

        break

    return (x, y)


def get_fields(lines: list[str]) -> set[(int, int)]:
    fields = set()

    for line in lines:
        coordinates = []

        for str_coord in line.split(" -> "):
            x, y = map(int, str_coord.split(","))
            coordinates.append((x, y))

        for i in range(1, len(coordinates)):
            cx, cy = coordinates[i]
            px, py = coordinates[i - 1]

            if cy != py:
                assert cx == px
                for y in range(min(cy, py), max(cy, py) + 1):
                    fields.add((cx, y))

            if cx != px:
                assert cy == py
                for x in range(min(cx, px), max(cx, px) + 1):
                    fields.add((x, cy))

    return fields


def parse_input(input: str) -> list[str]:
    with open(input) as fs:
        return fs.read().strip().split("\n")


def main():
    puzzle_input_path = "2022/day14/input.txt"
    print(f"AoC2022 Day14, Part1 solution is: {part1(input=puzzle_input_path)}")
    print(f"AoC2022 Day14, Part2 solution is: {part2(input=puzzle_input_path)}")


if __name__ == "__main__":
    main()

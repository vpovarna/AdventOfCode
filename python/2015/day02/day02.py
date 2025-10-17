with open("input.txt") as f:
    input_data = f.read().strip()


def part1() -> int:
    lines = input_data.split("\n")
    total = 0
    for line in lines:
        l, w, h = line.split("x")
        l, w, h = int(l), int(w), int(h)
        sides = [l * w, w * h, h * l]
        smallest_side = min(sides)
        total += 2 * sum(sides) + smallest_side
    return total


def part2() -> int:
    return 1


if __name__ == '__main__':
    print(f"Part1 solution is: {part1()}")
    print(f"Part2 solution is: {part2()}")

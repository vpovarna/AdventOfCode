directions = {"R": [1, 0], "U": [0, 1], "D": [0, -1], "L": [-1, 0]}

def touching(x1, y1, x2, y2):
    return abs(x1 - x2) <= 1 and abs(y1 - y2) <= 1

def move(hx, hy, tx, ty):
    if not touching(hx, hy, tx, ty):
        sign_x = 0 if hx == tx else (hx - tx) / abs(hx - tx)
        sign_y = 0 if hy == ty else (hy - ty) / abs(hy - ty)

        tx += sign_x
        ty += sign_y
    return (tx, ty)


def part1(input: str) -> int:
    lines = parse_input(input)
    
    tx, ty = 0, 0
    hx, hy = 0, 0

    tail_visited = set()
    tail_visited.add((tx, ty))

    for line in lines:
        op, amount = line.split(" ")
        amount = int(amount)
        dx, dy = directions[op]

        for _ in range(amount):
            hx += dx
            hy += dy
            tx, ty = move(hx, hy, tx, ty)
            tail_visited.add((tx, ty))

    return len(tail_visited)


def part2(input: str) -> int:
    return -1


def parse_input(input: str) -> list[str]:
    with open(input) as fn:
        return fn.read().strip().split("\n")


def main():
    puzzle_input_path = "2022/day09/input.txt"
    print(f"AoC2022 Day9, Part1 solution is: {part1(input=puzzle_input_path)}")
    print(f"AoC2022 Day9, Part2 solution is: {part2(input=puzzle_input_path)}")


if __name__ == "__main__":
    main()

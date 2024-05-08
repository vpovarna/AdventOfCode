directions = {"R": [1, 0], "U": [0, 1], "D": [0, -1], "L": [-1, 0]}


def touching(x1, y1, x2, y2):
    return abs(x1 - x2) <= 1 and abs(y1 - y2) <= 1


def move(knots: list[(int, int)]) -> list[(int, int)]:
    for i in range(1, len(knots)):
        hx, hy = knots[i - 1]
        tx, ty = knots[i]

        if not touching(hx, hy, tx, ty):
            sign_x = 0 if hx == tx else (hx - tx) / abs(hx - tx)
            sign_y = 0 if hy == ty else (hy - ty) / abs(hy - ty)

            tx += sign_x
            ty += sign_y
        knots[i] = [tx, ty]

    return knots


def run(input: str, knots_size: int) -> int:
    lines = parse_input(input)

    knots = [[0, 0] for _ in range(knots_size)]

    tail_visited = set()
    tail_visited.add(tuple(knots[-1]))

    for line in lines:
        op, amount = line.split(" ")
        amount = int(amount)
        dx, dy = directions[op]

        for _ in range(amount):
            knots[0][0] += dx
            knots[0][1] += dy

            knots = move(knots)
            tail_visited.add(tuple(knots[-1]))

    return len(tail_visited)


def parse_input(input: str) -> list[str]:
    with open(input) as fn:
        return fn.read().strip().split("\n")


def main():
    puzzle_input_path = "2022/day09/input.txt"
    print(
        f"AoC2022 Day9, Part1 solution is: {run(input=puzzle_input_path, knots_size=2)}"
    )
    print(
        f"AoC2022 Day9, Part2 solution is: {run(input=puzzle_input_path, knots_size=10)}"
    )


if __name__ == "__main__":
    main()

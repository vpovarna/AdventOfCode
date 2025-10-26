with open("input.txt") as f:
    input_data = f.read().strip()


def part1() -> int:
    visited = set()

    x, y = 0, 0

    visited.add((x, y))
    for direction in input_data:
        x, y = update_position(direction, x, y)
        visited.add((x, y))

    return len(visited)


def part2() -> int:
    visited = set()
    x_santa, y_santa = 0, 0
    x_robo, y_robo = 0, 0

    visited.add((x_santa, y_santa))

    for i, direction in enumerate(input_data):
        if i % 2 == 0:
            x_santa, y_santa = update_position(direction, x_santa, y_santa)
            visited.add((x_santa, y_santa))
        else:
            x_robo, y_robo = update_position(direction, x_robo, y_robo)
            visited.add((x_robo, y_robo))

    return len(visited)


def update_position(direction, x, y):
    if direction == '^':
        y += 1
    elif direction == 'v':
        y -= 1
    elif direction == '>':
        x += 1
    elif direction == '<':
        x -= 1
    return x, y


if __name__ == '__main__':
    print(f"Part1 solution: {part1()}")
    print(f"Part1 solution: {part2()}")

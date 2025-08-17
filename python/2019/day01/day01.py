with open("input.txt") as f:
    input_data = f.read()

STARTING_POINT = (8, 1)


def part1() -> int:
    wire1_instructions, wire2_instructions = __parse_input()

    visited_points_wire1 = get_wire_visited_points(instructions=wire1_instructions)
    visited_points_wire2 = get_wire_visited_points(instructions=wire2_instructions)

    crossing_points = visited_points_wire1 & visited_points_wire2 - {STARTING_POINT}
    min_manhattan_distances = float("inf")
    for (c1, c2) in crossing_points:
        (x, y) = STARTING_POINT
        manhattan_distance = abs(c1 - x) + abs(c2 - y)
        min_manhattan_distances = min(manhattan_distance, min_manhattan_distances)

    return min_manhattan_distances


def part2() -> int:
    return 1


def get_wire_visited_points(instructions: list[str]):
    x, y = STARTING_POINT
    visited_points_wire1 = set()
    visited_points_wire1.add((x, y))
    for instruction in instructions:
        direction, steps = instruction[0], int(instruction[1:])
        match direction:
            case "R":
                for i in range(0, steps):
                    y = y + 1
                    visited_points_wire1.add((x, y))
            case "U":
                for i in range(0, steps):
                    x = x - 1
                    visited_points_wire1.add((x, y))
            case "L":
                for i in range(0, steps):
                    y = y - 1
                    visited_points_wire1.add((x, y))
            case "D":
                for i in range(0, steps):
                    x = x + 1
                    visited_points_wire1.add((x, y))
            case _:
                print("something else")
    return visited_points_wire1


def __print_grid(visited_points: set) -> None:
    for i in range(0, 100):
        for j in range(0, 100):
            if (i, j) in visited_points:
                print('0', end='')
            else:
                print('.', end='')
        print()


def __parse_input() -> (list[str], list[str]):
    lines = input_data.splitlines()
    assert len(lines) == 2

    wire1_instructions: list[str] = lines[0].split(",")
    wire2_instructions: list[str] = lines[1].split(",")

    return wire1_instructions, wire2_instructions


if __name__ == '__main__':
    print(f"Part1 solution is: {part1()}")

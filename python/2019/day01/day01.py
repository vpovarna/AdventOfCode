with open("input.txt") as f:
    input_data = f.read()

STARTING_POINT = (8, 1)


def part1() -> int:
    wire1_instructions, wire2_instructions = __parse_input()

    crossing_points, visited_points_wire1_with_steps, visited_points_wire2_with_steps = (
        __get_crossing_points_coordinates(wire1_instructions, wire2_instructions))

    min_manhattan_distances = float("inf")
    for (c1, c2) in crossing_points:
        (x, y) = STARTING_POINT
        manhattan_distance = abs(c1 - x) + abs(c2 - y)
        min_manhattan_distances = min(manhattan_distance, min_manhattan_distances)

    return min_manhattan_distances


def part2() -> int:
    wire1_instructions, wire2_instructions = __parse_input()

    crossing_points, visited_points_wire1_with_steps, visited_points_wire2_with_steps = (
        __get_crossing_points_coordinates(wire1_instructions, wire2_instructions))

    min_steps = float("inf")
    for (i, j) in crossing_points:
        steps = visited_points_wire1_with_steps.get((i, j)) + visited_points_wire2_with_steps.get((i, j))
        min_steps = min(min_steps, steps)

    return min_steps


def __get_crossing_points_coordinates(wire1_instructions, wire2_instructions):
    visited_points_wire1_with_steps = get_wire_visited_points_with_steps(instructions=wire1_instructions)
    visited_points_wire2_with_steps = get_wire_visited_points_with_steps(instructions=wire2_instructions)
    crossing_points = visited_points_wire1_with_steps.keys() & visited_points_wire2_with_steps.keys() - {STARTING_POINT}
    return crossing_points, visited_points_wire1_with_steps, visited_points_wire2_with_steps


def get_wire_visited_points_with_steps(instructions: list[str]) -> dict:
    x, y = STARTING_POINT
    visited_points_wire = {(x, y): 0}
    count = 1

    # Direction mapping: (dx, dy)
    directions = {
        "R": (0, 1),
        "U": (-1, 0),
        "L": (0, -1),
        "D": (1, 0),
    }

    for instruction in instructions:
        direction, steps = instruction[0], int(instruction[1:])
        dx, dy = directions.get(direction, (0, 0))

        for _ in range(steps):
            x += dx
            y += dy
            visited_points_wire[(x, y)] = count
            count += 1

    return visited_points_wire


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
    print(f"Part2 solution is: {part2()}")

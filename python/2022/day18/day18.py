from collections import deque


offsets = [
    (0, 0, 0.5),
    (0, 0.5, 0),
    (0.5, 0, 0),
    (0, 0, -0.5),
    (0, -0.5, 0),
    (-0.5, 0, 0),
]


def part1(input_str: str) -> int:
    cube_sides_list = get_cube_sides_list(input_str)
    faces, _, _ = get_common_faces(cube_sides_list)
    return list(faces.values()).count(1)


def part2(input_str: str) -> int:
    cube_sides_list = get_cube_sides_list(input_str)
    # for fast access
    droplet = set(cube_sides_list)
    faces, min_sides, max_sides = get_common_faces(cube_sides_list)

    mx, my, mz = min_sides
    Mx, My, Mz = max_sides

    q = deque([min_sides])
    air = {min_sides}

    while q:
        x, y, z = q.popleft()

        for dx, dy, dz in offsets:
            nx, ny, nz = k = (x + dx * 2, y + dy * 2, z + dz * 2)

            if not (mx <= nx <= Mx and my <= ny <= My and mz <= nz <= Mz):
                continue

            if k in droplet or k in air:
                continue

            air.add(k)
            q.append(k)

    free = set()

    for x, y, z in air:
        for dx, dy, dz in offsets:
            free.add((x + dx, y + dy, z + dz))

    total = 0
    for key in faces:
        if key in free:
            total += 1

    return total


def parse_input(input_str: str) -> list[str]:
    with open(input_str) as fn:
        return fn.read().strip().splitlines()


def get_common_faces(
    cube_sides_list: list[tuple[int, int, int]]
) -> tuple[dict, tuple[int, int, int], tuple[int, int, int]]:
    faces = {}

    mx = my = mz = float("inf")
    Mx = My = Mz = -float("inf")

    for x, y, z in cube_sides_list:
        mx, my, mz = min(mx, x), min(my, y), min(mz, z)
        Mx, My, Mz = max(Mx, x), max(My, y), max(Mz, z)

        for a_diff, b_diff, c_diff in offsets:
            k = (x + a_diff, y + b_diff, z + c_diff)
            if k not in faces:
                faces[k] = 0
            faces[k] += 1

    min_sides = (mx - 1, my - 1, mz - 1)
    max_sides = (Mx + 1, My + 1, Mz + 1)

    return faces, min_sides, max_sides


def get_cube_sides_list(input_str: str) -> list[tuple[int, int, int]]:
    lines = parse_input(input_str)
    cube_sides_list = []
    for line in lines:
        a, b, c = map(int, line.split(","))
        cube_sides_list.append((a, b, c))
    return cube_sides_list


def main():
    puzzle_input_path = "2022/day18/input.txt"
    print(f"AoC2022 Day18, Part1 solution is: {part1(input_str=puzzle_input_path)}")
    print(f"AoC2022 Day18, Part2 solution is: {part2(input_str=puzzle_input_path)}")


if __name__ == "__main__":
    main()

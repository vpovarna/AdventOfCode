import copy

with open("input.txt") as f:
    lines = f.readlines()
    grid = [list(row.strip()) for row in lines]

M, N = len(grid), len(grid[0])

DIRECTIONS = {
    'N': (-1, 0),
    'S': (1, 0),
    'E': (0, 1),
    'W': (0, -1)
}

DIRECTIONS_ORDER = ['N', 'E', 'S', 'W']


def get_stating_point() -> (int, int):
    for i in range(0, len(lines)):
        for j, c in enumerate(lines[i]):
            if c == '^':
                return i, j

    return -1, -1


def move_direction(i: int, j: int, direction: str) -> (int, int):
    di, dj = DIRECTIONS[direction]
    return i + di, j + dj


def is_loop(i: int, j: int, current_gid: list[list[str]], current_direction: str) -> bool:
    visited = set()
    while 0 <= i < M and 0 <= j < N and current_gid[i][j] != "#":
        new_i, new_j = move_direction(i, j, current_direction)
        if 0 <= new_i < M and 0 <= new_j < N and current_gid[new_i][new_j] == "#":
            current_direction = DIRECTIONS_ORDER[(DIRECTIONS_ORDER.index(current_direction) + 1) % 4]
            continue
        if (new_i, new_j, i, j) in visited:
            return True
        visited.add((new_i, new_j, i, j))
        i = new_i
        j = new_j

    return False


def part1() -> int:
    i, j = get_stating_point()
    visited = set()

    current_direction = DIRECTIONS_ORDER[0]

    while 0 <= i < M and 0 <= j < N and grid[i][j] != "#":
        new_i, new_j = move_direction(i, j, current_direction)
        if 0 <= new_i < M and 0 <= new_j < N and grid[new_i][new_j] == "#":
            current_direction = DIRECTIONS_ORDER[(DIRECTIONS_ORDER.index(current_direction) + 1) % 4]
            continue
        visited.add((i, j))
        i = new_i
        j = new_j

    return len(visited)


def part2() -> int:
    x, y = get_stating_point()

    number_of_loops = 0

    for i in range(0, M):
        for j in range(0, N):
            if grid[i][j] == "#" or grid[i][j] == "^":
                continue
            new_grid = copy.deepcopy(grid)
            new_grid[i][j] = '#'
            if is_loop(x, y, new_grid, 'N'):
                number_of_loops += 1

    return number_of_loops


if __name__ == '__main__':
    print(f"Part1: {part1()}")
    print(f"Part2: {part2()}")

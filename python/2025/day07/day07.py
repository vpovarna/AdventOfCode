from collections import deque
from functools import cache

with open("2025/day07/input.txt") as f:
    input_data = f.read().strip()


def read_grid() -> dict:
    grid = {}

    lines = input_data.split("\n")
    for i, line in enumerate(lines):
        for j, c in enumerate(line):
            grid[(i, j)] = c

    return grid


def find_starring_point(grid: dict) -> tuple[int, int]:
    for k, v in grid.items():
        if v == "S":
            return k
    return None


def part1() -> int:
    total = 0

    grid = read_grid()
    starting_point = find_starring_point(grid=grid)

    width = len(input_data.split("\n")[0])
    max_line = len(input_data.split("\n"))

    # DFS Algorithm
    points = deque([starting_point])

    visited = set()
    while points:
        x, y = points.pop()

        if (x, y) in visited:
            continue
        visited.add((x, y))

        below_point = (x + 1, y)

        if x + 1 < max_line and grid[below_point] == ".":
            points.appendleft(below_point)

        if x + 1 < max_line and grid[(x + 1, y)] == "^":
            total += 1

            if y - 1 >= 0:
                points.appendleft((x + 1, y - 1))
            if y + 1 < width:
                points.appendleft((x + 1, y + 1))

    return total


def part2() -> int:

    grid = read_grid()
    starting_point = find_starring_point(grid=grid)
    max_line = len(input_data.split("\n"))


    @cache
    def solve(current_point: tuple[int, int]) -> int:
        x, y = current_point
        if x >= max_line:
            # reach the bottom
            return 1

        if grid[current_point] == "." or grid[current_point] == "S":
            return solve((x + 1, y))
        elif grid[current_point] == "^":
            return solve((x + 1, y - 1)) + solve((x + 1, y + 1))

    return solve(starting_point)


def part2_without_cache() -> int:
    grid = read_grid()
    starting_point = find_starring_point(grid)
    lines = input_data.split("\n")
    max_x = len(lines)
    max_y = len(lines[0])

    memo = {}

    def solve(point: tuple[int, int]) -> int:
        if point in memo:
            return memo[point]

        x, y = point

        if y < 0 or y >= max_y:
            memo[point] = 0
            return 0

        if x >= max_x:
            memo[point] = 1
            return 1

        cell = grid[(x, y)]

        if cell == "." or cell == "S":
            res = solve((x + 1, y))
        elif cell == "^":
            res = solve((x + 1, y - 1)) + solve((x + 1, y + 1))
        else:
            res = 0

        memo[point] = res
        return res

    return solve(starting_point)


if __name__ == "__main__":
    print(f"{part1()}")
    print(f"{part2()}")
    print(f"{part2_without_cache()}")

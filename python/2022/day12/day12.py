from string import ascii_lowercase
from collections import deque


def part1(input: str):
    grid = get_grid(parse_input(input))
    n = len(grid)
    m = len(grid[0])

    for i in range(n):
        for j in range(m):
            if grid[i][j] == "S":
                start = i, j
            if grid[i][j] == "E":
                end = i, j

    # BFS
    q = deque()
    q.append((0, start[0], start[1]))

    visited = {(start[0], start[1])}

    while q:
        steps, i, j = q.popleft()
        for ii, jj in [(-1, 0), (1, 0), (0, -1), (0, 1)]:
            new_i, new_j = ii + i, jj + j
            # out of bound check
            if new_i < 0 or new_j < 0 or new_i >= n or new_j >= m:
                continue
            # already visited
            if (new_i, new_j) in visited:
                continue
            # compare height
            if height(grid[new_i][new_j]) - height(grid[i][j]) > 1:
                continue
            # check if we reach the end.
            if new_i == end[0] and new_j == end[1]:
                return steps + 1

            visited.add((new_i, new_j))
            q.append((steps + 1, new_i, new_j))

    return -1


def part2(input: str):
    grid = get_grid(parse_input(input))
    n = len(grid)
    m = len(grid[0])

    ## Go backwards, from E to the first a
    for i in range(n):
        for j in range(m):
            if grid[i][j] == "S":
                grid[i][j] = "a"
            if grid[i][j] == "E":
                start = i, j

    # BFS
    q = deque()
    q.append((0, start[0], start[1]))
    visited = {(start[0], start[1])}

    while q:
        steps, i, j = q.popleft()
        for ii, jj in [(-1, 0), (1, 0), (0, -1), (0, 1)]:
            new_i, new_j = ii + i, jj + j
            # out of bound check
            if new_i < 0 or new_j < 0 or new_i >= n or new_j >= m:
                continue
            # already visited
            if (new_i, new_j) in visited:
                continue
            # compare height
            if height(grid[new_i][new_j]) - height(grid[i][j]) < -1:
                continue
            # check if we reach the end.
            if grid[new_i][new_j] == "a":
                return steps + 1

            visited.add((new_i, new_j))
            q.append((steps + 1, new_i, new_j))

    return -1


def height(s: str):
    if s in ascii_lowercase:
        return ascii_lowercase.index(s)
    if s == "S":
        return 0
    if s == "E":
        return 25


def parse_input(input: str) -> list[str]:
    with open(input) as fn:
        return fn.read().strip().splitlines()


def get_grid(lines: list[str]) -> list[list[str]]:
    return [list(line) for line in lines]


def main():
    puzzle_input_path = "2022/day12/input.txt"
    print(f"AoC2022 Day12, Part1 solution is: {part1(input=puzzle_input_path)}")
    print(f"AoC2022 Day12, Part2 solution is: {part2(input=puzzle_input_path)}")


if __name__ == "__main__":
    main()

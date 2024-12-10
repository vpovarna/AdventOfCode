from collections import deque
from typing import Callable

with open("input.txt") as f:
    lines = f.read().strip().strip("\n")
    grid = [list(map(int, list(line))) for line in lines.split("\n")]

rows = len(grid)
cols = len(grid[0])

directions = [(-1, 0), (1, 0), (0, -1), (0, 1)]

# Find all trailheads (positions with height 0)
trail_heads = [(x, y) for x in range(rows) for y in range(cols) if grid[x][y] == 0]


def bfs(i: int, j: int) -> int:
    visited = set()
    queue = deque([(i, j)])
    height_positions = set()

    while queue:
        x, y = queue.popleft()
        if (x, y) in visited:
            continue

        visited.add((x, y))

        if grid[x][y] == 9:
            height_positions.add((x, y))

        for dx, dy in directions:
            new_x, new_y = x + dx, y + dy
            if 0 <= new_x < rows and 0 <= new_y < cols and (new_x, new_y) not in visited:
                if grid[new_x][new_y] - grid[x][y] == 1:
                    queue.append((new_x, new_y))

    return len(height_positions)


def dfs(i: int, j: int) -> int:
    if grid[i][j] == 9:
        return 1

    ans = 0
    for dx, dy in directions:
        new_x, new_y = i + dx, j + dy
        if 0 <= new_x < rows and 0 <= new_y < cols and grid[new_x][new_y] - grid[i][j] == 1:
            ans += dfs(new_x, new_y)

    return ans


def run(search_function: Callable[[int, int], int]) -> int:
    trail_scores = {}

    for (x, y) in trail_heads:
        trail_scores[(x, y)] = search_function(x, y)

    return sum(trail_scores.values())


if __name__ == '__main__':
    print(f"Part1: {run(bfs)}")
    print(f"Part2: {run(dfs)}")

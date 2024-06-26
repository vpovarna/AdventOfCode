import numpy as np


def part1(input: str) -> int:
    lines = parse_input(input)
    grid = transform_to_grid(lines)

    n = len(grid)
    m = len(grid[0])

    grid = np.array(grid)
    ans = 0

    for i in range(n):
        for j in range(m):
            h = grid[i, j]

            if j == 0 or np.amax(grid[i, :j]) < h:
                ans += 1
            elif j == m - 1 or np.amax(grid[i, (j + 1) :]) < h:
                ans += 1
            elif i == 0 or np.amax(grid[:i, j]) < h:
                ans += 1
            elif i == n - 1 or np.amax(grid[(i + 1) :, j]) < h:
                ans += 1

    return ans


def part2(input: str) -> int:
    lines = parse_input(input)
    grid = transform_to_grid(lines)

    n = len(grid)
    m = len(grid[0])

    grid = np.array(grid)
    ans = 0

    directions = [[0, 1], [0, -1], [1, 0], [-1, 0]]

    for i in range(n):
        for j in range(m):
            h = grid[i, j]
            score = 1

            for dir_i, dir_j in directions:
                new_i, new_j = i + dir_i, j + dir_j
                dist = 0

                while (0 <= new_i < n and 0 <= new_j < m) and grid[new_i, new_j] < h:
                    dist += 1
                    new_i, new_j = new_i + dir_i, new_j + dir_j

                    if (0 <= new_i < n and 0 <= new_j < m) and grid[new_i, new_j] >= h:
                        dist += 1

                score *= dist

            ans = max(ans, score)

    return ans


def parse_input(input: str) -> list[int]:
    with open(input) as fn:
        return fn.read().strip().split()


def transform_to_grid(lines: list[int]) -> list[int]:
    return [list(map(int, list(line))) for line in lines]


def main():
    puzzle_input_path = "2022/day08/input.txt"
    print(f"AoC2022 Day8, Part1 solution is: {part1(input=puzzle_input_path)}")
    print(f"AoC2022 Day8, Part2 solution is: {part2(input=puzzle_input_path)}")


if __name__ == "__main__":
    main()

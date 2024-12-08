with open("input.txt") as f:
    grid = f.read().strip().split("\n")

M = len(grid)
N = len(grid[0])

directions = [(-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 0), (0, 1), (1, -1), (1, 0), (1, 1)]


def has_xmax(i: int, j: int, direction: tuple[int, int]) -> bool:
    for (idx, c) in enumerate("XMAS"):
        x = i + idx * direction[0]
        y = j + idx * direction[1]

        if not (0 <= x < M and 0 <= y < N) or grid[x][y] != c:
            return False

    return True


def has_xmax_on_diagonals(i: int, j: int) -> bool:
    if not (1 <= i < M - 1 and 1 <= j < N - 1):
        return False
    if grid[i][j] != "A":
        return False

    diag_1 = f"{grid[i - 1][j - 1]}{grid[i + 1][j + 1]}"
    diag_2 = f"{grid[i - 1][j + 1]}{grid[i + 1][j - 1]}"

    return (diag_1 == "MS" or diag_1 == "SM") and (diag_2 == "MS" or diag_2 == "SM")


def part1() -> int:
    ans = 0

    for i in range(M):
        for j in range(N):
            for direction in directions:
                if has_xmax(i, j, direction):
                    ans += 1
    return ans


def part2() -> int:
    ans = 0

    for i in range(M):
        for j in range(N):
            if has_xmax_on_diagonals(i, j):
                ans += 1
    return ans


if __name__ == '__main__':
    print(f"Part 1: {part1()}")
    print(f"Part 2: {part2()}")

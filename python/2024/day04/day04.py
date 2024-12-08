with open("input.txt") as f:
    lines = f.read().strip().split("\n")

M = len(lines)
N = len(lines[0])

directions = [(-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 0), (0, 1), (1, -1), (1, 0), (1, 1)]


def has_xmax(i: int, j: int, direction: tuple[int, int]) -> bool:
    for (idx, c) in enumerate("XMAS"):
        x = i + idx * direction[0]
        y = j + idx * direction[1]

        if 0 <= x < M and 0 <= y < N:
            return False
        if lines[x][y] != c:
            return False

    return True


def part1() -> int:
    ans = 0

    for i in range(M):
        for j in range(N):
            for direction in directions:
                if has_xmax(i, j, direction):
                    ans += 1
    return ans


def part2() -> int:
    1


if __name__ == '__main__':
    print(f"Part 1: {part1()}")
    print(f"Part 2: {part2()}")

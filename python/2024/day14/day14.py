import re
from functools import reduce
from typing import List

WIDTH = 101
HEIGHT = 103
M = HEIGHT // 2
N = WIDTH // 2

nr_of_moves = 100

QUADRANTS = [
    ((0, M), (0, N)),
    ((M + 1, M + M + 1), (0, N)),
    ((0, M), (N + 1, N + N + 1)),
    ((M + 1, M + M + 1), (N + 1, N + N + 1))
]

with open("input.txt", 'r') as f:
    def parse_line(line: str) -> List[int]:
        return [int(x) for x in re.findall(r"[+-]?\d+", line)]


    lines = [parse_line(line) for line in f.read().split("\n")]


def part1() -> int:
    positions = get_final_positions(seconds=nr_of_moves)

    counts = []
    for ((q1_start, q1_end), (q2_start, q2_end)) in QUADRANTS:
        count = 0
        for i in range(q1_start, q1_end):
            for j in range(q2_start, q2_end):
                if (j, i) in positions:
                    v = positions.count((j, i))
                    count += v
        counts.append(count)

    product = reduce(lambda x, y: x * y, counts)
    return product


def part2() -> int:
    min_sf = float('inf')
    best_iteration = 0

    for second in range(WIDTH * HEIGHT):
        positions = get_final_positions(seconds=second)
        sf = calculate_result(positions)
        # print(sf, second)
        if sf < min_sf:
            min_sf = sf
            best_iteration = second
    return best_iteration


def get_final_positions(seconds: int):
    positions = []
    for line in lines:
        i, j, vi, vj = line
        i = (i + seconds * vi) % WIDTH
        j = (j + seconds * vj) % HEIGHT
        positions.append((i, j))
    return positions


def calculate_result(positions):
    tl = bl = tr = br = 0
    for px, py in positions:
        if px == M or py == N:
            continue
        if px < M:
            if py < N:
                tl += 1
            else:
                bl += 1
        else:
            if py < M:
                tr += 1
            else:
                br += 1
    sf = tl * bl * tr * br
    return sf


if __name__ == '__main__':
    print(f"Part1: {part1()}")
    print(f"Part2: {part2()}")

import itertools
import re
from typing import List

with open("input.txt", 'r') as f:
    blocks = f.read().split("\n\n")

# Generate combinations
all_values = list(itertools.product(range(101), repeat=2))


def parse_block(block: str) -> List[int]:
    return [int(num) for num in re.findall(r"\d+", block)]


def part1() -> int:
    all_costs = 0
    for block in blocks:
        numbers = parse_block(block)
        min_cost = float('inf')

        for (i, j) in all_values:
            if (i * numbers[0] + j * numbers[2] == numbers[4]) and (i * numbers[1] + j * numbers[3] == numbers[5]):
                actual_cost = i * 3 + j
                min_cost = min(min_cost, actual_cost)

        if min_cost != float('inf'):
            all_costs += min_cost
    return all_costs


def part2() -> int:
    all_costs = 0
    for block in blocks:
        ax, ay, bx, by, px, py = parse_block(block)
        px += 10000000000000
        py += 10000000000000

        # Solving the equations: ax * i + bx * j = px and ay * j + by * j = py
        i = (px * by - bx * py) / (ax * by - ay * bx)
        j = (px * ay - py * ax) / (bx * ay - by * ax)

        # Costs m
        if i % 1 == j % 1 == 0:
            all_costs += int(i * 3 + j)
    return all_costs


if __name__ == '__main__':
    print(f"Part1: {part1()}")
    print(f"Part2: {part2()}")

from dataclasses import dataclass
from typing import List


@dataclass
class Equation:
    target_value: int
    numbers: List[int]


with open("input.txt") as f:
    lines = f.readlines()
    equations = []

    for line in lines:
        parts = line.split(": ")
        target_value = int(parts[0])
        numbers = list(map(int, parts[1].strip().split(' ')))
        equations.append(Equation(target_value, numbers))


def is_valid(target: int, ns: list[int], part2: bool) -> bool:
    if len(ns) == 1:
        return ns[0] == target
    if is_valid(target, [ns[0] + ns[1]] + ns[2:], part2):
        return True
    if is_valid(target, [ns[0] * ns[1]] + ns[2:], part2):
        return True
    if part2 and is_valid(target, [int(str(ns[0]) + str(ns[1]))] + ns[2:], part2):
        return True
    return False


def run(is_part2: bool = False) -> int:
    total = 0

    for equation in equations:
        if is_valid(equation.target_value, equation.numbers, is_part2):
            total += equation.target_value

    return total


if __name__ == '__main__':
    print(f"Part 1: {run(is_part2=False)}")
    print(f"Part 2: {run(is_part2=True)}")

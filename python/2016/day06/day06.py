from typing import Callable

with open("input.txt") as f:
    input_data = f.read().strip()


def solve(selector_func: Callable) -> str:
    solution = ""

    lines = input_data.split("\n")
    n = len(lines[0])
    for i in range(0, n):
        occurrence = {}
        for line in lines:
            v = occurrence.get(line[i], 0) + 1
            occurrence[line[i]] = v
        c = selector_func(occurrence, key=occurrence.get)
        solution += c

    return solution


if __name__ == '__main__':
    print(f"Part1 solution: {solve(max)}")
    print(f"Part1 solution: {solve(min)}")

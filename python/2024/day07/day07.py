from dataclasses import dataclass
from typing import List

import tqdm


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


def generate_operations(arr: List[int], current: str = "") -> List[str]:
    if len(arr) == 1:
        return [current + str(arr[0])]

    results = []

    first = arr[0]
    rest = arr[1:]

    for expression in generate_operations(rest, ""):
        results.append(f"{current}{first} + {expression}")
        results.append(f"{current}{first} * {expression}")

    return results


def generate_and_calculate(arr, current_result=None) -> set[int]:
    if len(arr) == 1:
        if current_result is None:
            return {arr[0]}
        else:
            return {current_result + arr[0], current_result * arr[0]}

    unique_results = set()

    if current_result is None:
        current_result = arr[0]
        rest = arr[1:]
        return generate_and_calculate(rest, current_result)

    rest = arr[1:]
    unique_results.update(generate_and_calculate(rest, current_result + arr[0]))
    unique_results.update(generate_and_calculate(rest, current_result * arr[0]))

    return unique_results


def generate_and_calculate_with_concat(arr, current_result=None) -> set[int]:
    if len(arr) == 1:
        if current_result is None:
            return {arr[0]}
        else:
            return {
                current_result + arr[0],  # Addition
                current_result * arr[0],  # Multiplication
                int(str(current_result) + str(arr[0])),  # Concatenation
            }

    unique_results = set()

    if current_result is None:
        current_result = arr[0]
        rest = arr[1:]
        return generate_and_calculate_with_concat(rest, current_result)

    rest = arr[1:]
    unique_results.update(
        generate_and_calculate_with_concat(rest, current_result + arr[0])
    )  # Addition
    unique_results.update(
        generate_and_calculate_with_concat(rest, current_result * arr[0])
    )  # Multiplication
    unique_results.update(
        generate_and_calculate_with_concat(rest, int(str(current_result) + str(arr[0])))
    )  # Concatenation

    return unique_results


def part1() -> int:
    total = 0

    for equation in equations:
        for result in generate_and_calculate(equation.numbers):
            if result == equation.target_value:
                total += equation.target_value if result == equation.target_value else 0
                break

    return total


def part2() -> int:
    total = 0

    for equation in equations:
        for result in generate_and_calculate_with_concat(equation.numbers):
            if result == equation.target_value:
                total += equation.target_value if result == equation.target_value else 0
                break

    return total


if __name__ == '__main__':
    print(f"Part 1: {part1()}")
    print(f"Part 2: {part2()}")

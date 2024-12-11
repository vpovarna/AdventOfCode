from functools import cache
from copy import deepcopy

with open("input.txt") as f:
    stones = [int(x) for x in f.read().strip().split(" ")]


@cache
def count(stone: int, steps: int) -> int:
    if steps == 0:
        return 1
    if stone == 0:
        return count(1, steps - 1)

    stone_str = str(stone)
    length = len(stone_str)
    if length % 2 == 0:
        return count(int(stone_str[: length // 2]), steps - 1) + count(int(stone_str[length // 2:]), steps - 1)
    return count(stone * 2024, steps - 1)


def part1() -> int:
    global stones
    stones_copy = deepcopy(stones)

    for _ in range(25):
        output = []

        for stone in stones_copy:
            if stone == 0:
                output.append(1)
                continue

            stone_str = str(stone)
            length = len(stone_str)
            if length % 2 == 0:
                output.append(int(stone_str[: length // 2]))
                output.append(int(stone_str[length // 2:]))
            else:
                output.append(stone * 2024)
        stones_copy = output

    return len(stones_copy)


def part2() -> int:
    return sum(count(stone, 75) for stone in stones)


if __name__ == '__main__':
    print(f"Par1: {part1()}")
    print(f"Par2: {part2()}")

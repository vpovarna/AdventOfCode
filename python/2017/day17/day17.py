INPUT_STEPS = 371

def part1() -> int:
    arr = [0]
    start_index = 0

    for i in range(0, 2017):
        arr, start_index = vector_update(arr, start_index, i + 1)

    return arr[start_index + 1]


def part2() -> int:
    position = 0
    value_after_zero = 0

    for i in range(1, 50000001):
        position = (position + INPUT_STEPS) % i + 1

        if position == 1:
            value_after_zero = i

    return value_after_zero

def vector_update(arr: list[int], start_index: int, value: int) -> (list[int], int):
    for i in range(0, INPUT_STEPS):
        j = (start_index + i + 1) % len(arr)
    arr.insert(j + 1, value)
    return arr, j + 1


if __name__ == '__main__':
    print(f"Part1: {part1()}")
    print(f"Part2: {part2()}")

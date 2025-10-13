with open("input.txt") as f:
    input_data = f.read().strip()


def run(step: int, length: int) -> int:
    ans = 0

    for i in range(0, length):
        if input_data[i] == input_data[(i + step) % length]:
            ans += int(input_data[i])

    return ans


if __name__ == '__main__':
    n = len(input_data)
    print(f"Part1 solution: {run(step=1, length=n)}")
    print(f"Part1 solution: {run(step=n // 2, length=n)}")

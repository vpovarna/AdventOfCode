with open("input.txt") as f:
    input = f.read().strip()


def part1() -> int:
    arr = []
    idx = 0

    for i, c in enumerate(input):

        if i % 2 != 0:
            arr += [-1] * int(c)
        else:
            arr += [idx] * int(c)
            idx += 1

    blanks = [i for i, c in enumerate(arr) if c == -1]

    for i in blanks:
        # clean up the -1 from the end of the array
        while arr[-1] == -1:
            arr.pop()
        if len(arr) <= i:
            break
        arr[i] = arr.pop()

    return sum([i * x for i, x in enumerate(arr)])


if __name__ == '__main__':
    print(f"Part1: {part1()}")

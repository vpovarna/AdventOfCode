with open("input.txt") as f:
    input = f.read().strip()


def part1() -> int:
    files = []
    idx = 0

    for i, c in enumerate(input):

        if i % 2 != 0:
            files += [-1] * int(c)
        else:
            files += [idx] * int(c)
            idx += 1

    blanks = [i for i, c in enumerate(files) if c == -1]

    for i in blanks:
        # clean up the -1 from the end of the array
        while files[-1] == -1:
            files.pop()
        if len(files) <= i:
            break
        files[i] = files.pop()

    # calculate checksum
    return sum([i * x for i, x in enumerate(files)])


def part2() -> int:
    # dictionary containing a pair of the number of positions and the index of the file
    files = {}
    blanks = []

    idx = 0
    pos = 0

    for i, c in enumerate(input):
        x = int(c)
        if i % 2 == 0:
            files[idx] = (pos, x)
            idx += 1
        else:
            blanks.append((pos, x))
        pos += x

    while idx > 0:
        idx -= 1
        pos, size = files[idx]
        for i, (start, length) in enumerate(blanks):
            if start > pos:
                blanks = blanks[:i]
                break
            if size <= length:
                files[idx] = (start, size)
                if size == length:
                    blanks.pop(i)
                else:
                    blanks[i] = (start + size, length - size)
                break

    # calculate checksum
    total = 0
    for i, (pos, size) in files.items():
        for x in range(pos, pos + size):
            total += x * i

    return total


if __name__ == '__main__':
    print(f"Part1: {part1()}")
    print(f"Part2: {part2()}")

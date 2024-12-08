with open("input.txt") as f:
    grid = [line.strip() for line in f.readlines()]

rows = len(grid)
cols = len(grid[0])


def get_antennas() -> dict[str, list[int]]:
    antennas = {}
    for i, row in enumerate(grid):
        for j, c in enumerate(row):
            if c != ".":
                if antennas.get(c) is None:
                    antennas[c] = []
                antennas[c].append((i, j))
    return antennas


def part1():
    antennas = get_antennas()

    antinodes = set()
    for array in antennas.values():
        for i in range(len(array)):
            for j in range(i + 1, len(array)):
                r1, c1 = array[i]
                r2, c2 = array[j]
                antinodes.add((2 * r1 - r2, 2 * c1 - c2))
                antinodes.add((2 * r2 - r1, 2 * c2 - c1))

    # filter the antinodes and count
    count = 0

    for (r, c) in antinodes:
        if 0 <= r < rows and 0 <= c < cols:
            count += 1
    return count


def part2():
    antennas = get_antennas()

    antinodes = set()
    for array in antennas.values():
        for i in range(len(array)):
            for j in range(len(array)):
                if i == j:
                    continue

                r1, c1 = array[i]
                r2, c2 = array[j]
                dr = r2 - r1
                dc = c2 - c1

                r = r1
                c = c1

                while 0 <= r < rows and 0 <= c < cols:
                    antinodes.add((r, c))
                    r += dr
                    c += dc

    return len(antinodes)


if __name__ == '__main__':
    print(f"Part 1: {part1()}")
    print(f"Part 2: {part2()}")

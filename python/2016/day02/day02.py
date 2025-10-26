with open("input.txt") as f:
    lines = f.read().strip()


def part1() -> str:
    keypad = [
        [1, 2, 3],
        [4, 5, 6],
        [7, 8, 9]
    ]

    x, y = 1, 1
    code = ""
    for line in lines.split("\n"):
        for d in line:
            if d == 'U':
                x = max(0, x - 1)
            elif d == 'D':
                x = min(2, x + 1)
            elif d == 'L':
                y = max(0, y - 1)
            else:
                y = min(2, y + 1)
        code += str(keypad[x][y])
    return code


def part2() -> str:
    keypad = [
        ['.', '.', 1, '.', '.'],
        ['.', 2, 3, 4, '.'],
        [5, 6, 7, 8, 9],
        ['.', 'A', 'B', 'C', '.'],
        ['.', '.', 'D', '.', '.']
    ]

    code = ""

    x, y = 2, 0

    for line in lines.split("\n"):
        for d in line.strip():
            if d == 'U':
                if x > 0 and keypad[x - 1][y] != '.':
                    x -= 1
            elif d == 'D':
                if x < 4 and keypad[x + 1][y] != '.':
                    x += 1
            elif d == 'L':
                if y > 0 and keypad[x][y - 1] != '.':
                    y -= 1
            else:
                if y < 4 and keypad[x][y + 1] != '.':
                    y += 1
        code += str(keypad[x][y])

    return code


if __name__ == '__main__':
    print(f"Part1 solution: {part1()}")
    print(f"Part1 solution: {part2()}")

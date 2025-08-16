with open("input.txt") as f:
    input_data = f.read().strip()



def part1() -> int:
    floor_nr = 0

    for c in input_data:
        if c == "(":
            floor_nr += 1
        elif c == ")":
            floor_nr -= 1

    return floor_nr

def part2() -> int:
    position = 0

    for i, c in enumerate(input_data):
        if c == "(":
            position += 1
        elif c == ")":
            position -= 1
        if position == -1:
            return i + 1
    return -1


if __name__ == '__main__':
    print(f"Part1 solution: {part1()}")
    print(f"Part1 solution: {part2()}")

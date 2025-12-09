with open("2025/day01/input.txt") as f:
    lines = f.read().strip()


def part1() -> int:
    current_point = 50
    count = 0

    for line in lines.split("\n"):
        direction, steps = line[0], int(line[1:])
        if direction == "L":
            current_point = (current_point - steps) % 100
        else:
            current_point = (current_point + steps) % 100

        if current_point == 0:
            count += 1

    return count

def part2() -> int:
    current_point = 50
    count = 0

    for line in lines.split("\n"):
        direction, steps = line[0], int(line[1:])
        if direction == "L":
            for _ in range(steps):
                current_point = (current_point - 1) % 100
                if current_point == 0:
                    count += 1
        else:
            for _ in range(steps):
                current_point = (current_point +1) % 100
                if current_point == 0:
                    count += 1

    return count


if __name__ == "__main__":
    # print(part1())
    print(part2())
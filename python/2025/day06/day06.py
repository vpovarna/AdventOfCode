with open("2025/day06/input.txt") as f:
    input_data = f.read().strip()


def parse_data() -> dict:
    grid = []
    lines = input_data.split("\n")

    for i in range(len(lines) - 1):
        line = lines[i]
        numbers = [int(l.strip()) for l in line.split(" ") if l != ""]
        grid.append(numbers)

    operations = [o.strip() for o in lines[-1].split(" ") if o != ""]

    return grid, operations


def part1() -> int:
    total = 0

    grid, operations = parse_data()
    m = len(grid)
    n = len(grid[0])

    for i in range(n):
        if operations[i] == "+":
            result = 0
            for j in range(m):
                result += grid[j][i]
            total += result
        else:
            result = 1
            for j in range(m):
                result *= grid[j][i]
            total += result

    return total


def part2() -> int:
    total = 0
    grid, operations = parse_data()
    m = len(grid)
    n = len(grid[0])

    steps = []
    for i in range(n):
        max_size = 0
        for j in range(m):
            max_size = max(max_size, len(str(grid[j][i])))
        steps.append(max_size)

    lines = input_data.split("\n")
    lines = lines[0 : len(lines) - 1]

    start_index = 0
    for i in range(len(steps)):
        step = steps[i]
        acc = []
        for line in lines:
            sub_str = line[start_index: start_index +step]
            acc.append(sub_str)
        
        total += cephalopod_math(column=acc, step=step, operation=operations[i])
        start_index += step + 1

    return total

def cephalopod_math(column:list[int], step:int, operation:str) -> int:
    total = 0

    if operation == "*":
        total = 1
    
    for i in range(step -1, -1, -1):
        concat_nr = ""
        for nr in column:
            if nr[i] == ' ':
                continue
            concat_nr += nr[i]
        if operation == "+":
            total += int(concat_nr)
        else:
            total *= int(concat_nr)
    return total


if __name__ == "__main__":
    # print(part1())
    print(part2())

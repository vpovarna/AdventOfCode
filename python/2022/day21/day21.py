import sympy

OPS = {
    "+": lambda x, y: x + y,
    "-": lambda x, y: x - y,
    "*": lambda x, y: x * y,
    "/": lambda x, y: x / y,
}


def part1(input_str: str) -> int:
    lines = parse_input(input_str)

    monkeys = {}
    for line in lines:
        monkey_name, expression = line.split(": ")
        if expression.isdigit():
            monkeys[monkey_name] = int(expression)
        else:
            monkeyA, operation, monkeyB = expression.split(" ")
            if monkeyA in monkeys and monkeyB in monkeys:
                monkeys[monkey_name] = OPS[operation](
                    monkeys[monkeyA], monkeys[monkeyB]
                )
            else:
                lines.append(line)

    return int(monkeys["root"])


def part2(input_str: str) -> int:
    lines = parse_input(input_str)

    monkeys = {"humn": sympy.Symbol("x")}

    for line in lines:
        monkey_name, expression = line.split(": ")
        if monkey_name in monkeys:
            continue

        if expression.isdigit():
            monkeys[monkey_name] = sympy.Integer(expression)

        else:
            monkeyA, operation, monkeyB = expression.split(" ")
            if monkeyA in monkeys and monkeyB in monkeys:
                if monkey_name == "root":
                    return sympy.solve(monkeys[monkeyA] - monkeys[monkeyB])[0]
                monkeys[monkey_name] = OPS[operation](
                    monkeys[monkeyA], monkeys[monkeyB]
                )
            else:
                lines.append(line)

    return -1


def parse_input(input_str: str) -> list[str]:
    with open(input_str) as fn:
        return fn.read().strip().splitlines()


def main():
    puzzle_input_path = "2022/day21/input.txt"
    print(f"AoC2022 Day21, Part1 solution is: {part1(input_str=puzzle_input_path)}")
    print(f"AoC2022 Day21, Part2 solution is: {part2(input_str=puzzle_input_path)}")


if __name__ == "__main__":
    main()

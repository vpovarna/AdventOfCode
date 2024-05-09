from dataclasses import dataclass


@dataclass
class Monkey:
    items: list[int]
    operation: str
    test_factor: int
    true_monkey_target: int
    false_monkey_target: int
    items_check_count: int = 0

    def update_item_check_counter(self):
        self.items_check_count += len(self.items)


def part1(input: str) -> int:
    monkeys = parse_input(input)
    return run(monkeys=monkeys, iterations=20, fn=lambda x: x // 3)


def part2(input: str) -> int:
    monkeys = parse_input(input)

    mod_value = 1
    for monkey in monkeys:
        mod_value *= monkey.test_factor

    return run(monkeys=monkeys, iterations=10000, fn=lambda x: x % mod_value)


def run(monkeys: list[Monkey], iterations: int, fn) -> int:
    for _ in range(iterations):
        for monkey in monkeys:
            for item in monkey.items:
                item = eval(monkey.operation)(item)
                item = fn(item)
                if item % monkey.test_factor == 0:
                    monkeys[monkey.true_monkey_target].items.append(item)
                else:
                    monkeys[monkey.false_monkey_target].items.append(item)
            monkey.update_item_check_counter()
            monkey.items = []

        # print_stats(monkeys)

    items_count = [monkey.items_check_count for monkey in monkeys]
    items_count.sort()
    ans = items_count[-1] * items_count[-2]
    return ans


def parse_input(input: str) -> list[Monkey]:

    monkeys = []

    for block in read_input(input):
        lines = block.split("\n")
        monkey_items = list(map(int, lines[1].split(": ")[1].split(", ")))
        monkey_operation = "lambda old: " + lines[2].split(" = ")[1]
        monkey_divisible_value = int(lines[3].split(" ")[-1])
        monkey_true_id = int(lines[4].split(" ")[-1])
        monkey_false_id = int(lines[5].split(" ")[-1])

        monkey = Monkey(
            items=monkey_items,
            operation=monkey_operation,
            test_factor=monkey_divisible_value,
            true_monkey_target=monkey_true_id,
            false_monkey_target=monkey_false_id,
        )

        monkeys.append(monkey)

    return monkeys


def read_input(input: str) -> list[str]:
    with open(input) as fn:
        return fn.read().strip().split("\n\n")


def print_stats(monkeys: list[Monkey]):
    for i, monkey in enumerate(monkeys):
        print(f"Monkey-{i}: {monkey.items}, items-count: {monkey.items_check_count}")
    print("======================")


def main():
    puzzle_input_path = "2022/day11/input.txt"
    print(f"AoC2022 Day10, Part1 solution is: {part1(input=puzzle_input_path)}")
    print(f"AoC2022 Day10, Part2 solution is: {part2(input=puzzle_input_path)}")


if __name__ == "__main__":
    main()

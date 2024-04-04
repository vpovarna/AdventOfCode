from dataclasses import dataclass
from typing import List


@dataclass
class Action:
    nr_of_crates: int
    from_stack: int
    to_stack: int


def create_actions(input: str) -> List[Action]:
    actions = []
    for line in input.split("\n"):
        parts = line.split(" ")
        actions.append(
            Action(
                nr_of_crates=int(parts[1]),
                from_stack=int(parts[3]),
                to_stack=int(parts[5]),
            )
        )
    return actions


def create_stacks(input: str) -> List[List[str]]:
    lines = input.split("\n")
    nr_of_stacks = [int(x) for x in lines[-1].split(" ") if x != ""]
    stacks = [[] for _ in range(len(nr_of_stacks))]

    for i in range(len(lines) - 1):
        current_line = lines[i]
        for j in range(1, len(current_line), +4):
            stack_index = j // 4
            if current_line[j] != " ":
                stacks[stack_index].append(current_line[j])

    return stacks


def run(input: str, reverse: bool) -> str:
    stacks = create_stacks(parse_input(input)[0])
    actions = create_actions(parse_input(input)[1])

    for action in actions:
        to_stack = action.to_stack
        from_stack = action.from_stack
        nr_of_crates = action.nr_of_crates

        creates = stacks[from_stack - 1][:nr_of_crates]
        if reverse:
            creates.reverse()

        stacks[to_stack - 1] = creates + stacks[to_stack - 1]
        stacks[from_stack - 1] = stacks[from_stack - 1][action.nr_of_crates :]

    first_crates = [stack[0] for stack in stacks]
    return "".join(first_crates)


def parse_input(input: str) -> List[str]:
    with open(input) as fn:
        raw_data = fn.read()
        parts = raw_data.split("\n\n")
    return parts


def main():
    puzzle_input_path = '2022/day05/input.txt'
    print(f"AoC2022 Day5, Part1 solution is: {run(input=puzzle_input_path, reverse = True)}")
    print(f"AoC2022 Day5, Part2 solution is: {run(input=puzzle_input_path, reverse = False)}")


if __name__ == "__main__":
    main()

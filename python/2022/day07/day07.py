class TreeNode:
    def __init__(self, is_dir: bool, name: str, size: int = None) -> None:
        self.is_dir = is_dir
        self.name = name
        self.size = size
        self.children = []
        self.parent = None

    def add_child(self, child):
        child.parent = self
        self.children.append(child)

    def get_size(self):
        if self.is_dir:
            total_size = 0
            for child in self.children:
                total_size += child.get_size()
            return total_size
        else:
            return self.size

    def print_children(self, level):
        if self.is_dir:
            print("--" * level + self.name + " (total=" + str(self.get_size()) + ")")
        else:
            print("--" * level + self.name + " (file=" + str(self.get_size()) + ")")
        if len(self.children) > 0:
            for child in self.children:
                child.print_children(level + 1)


class Tree:
    def __init__(self) -> None:
        self.root = TreeNode(is_dir=Tree, name="root")
        self.current = self.root

    def reset_to_root(self):
        self.current = self.root

    def go_up_one_level(self):
        self.current = self.current.parent

    def go_to_child(self, name):
        self.current = list(
            filter(lambda child: child.name == name, self.current.children)
        )[0]

    def add_new_child(self, child):
        self.current.add_child(child)


def part1(input: str) -> int:

    # https://github.com/GalaxyInfernoCodes/Advent_Of_Code_2022/blob/main/Day07_Python/AdventOfCode_Day07_Py.ipynb
    for line in parse_input(input):
        print(line)

    return -1


def part2(input: str) -> int:
    return -1


def parse_input(input: str) -> list[str]:
    with open(input) as fn:
        raw_data = fn.read()
        parts = raw_data.split("\n")
    return parts


def main():
    puzzle_input_path = "2022/day07/input.txt"
    print(f"AoC2022 Day5, Part1 solution is: {part1(input=puzzle_input_path)}")
    print(f"AoC2022 Day5, Part2 solution is: {part2(input=puzzle_input_path)}")


if __name__ == "__main__":
    main()

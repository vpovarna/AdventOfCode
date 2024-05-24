class Node:
    def __init__(self, n) -> None:
        self.n = n
        self.left = None
        self.right = None

    def __repr__(self) -> str:
        return (
            f"Node(value = {self.n}, LeftNode = {self.left}, RightNode = {self.right})"
        )


def part1(input_str: str) -> int:
    nodes = parse_input(input_str)

    for i in range(len(nodes)):
        nodes[i].right = nodes[(i + 1) % len(nodes)]
        nodes[i].left = nodes[(i - 1) % len(nodes)]

    m = len(nodes) - 1

    for node in nodes:
        if node.n == 0:
            zeroNode = node
            continue
        tmpNode = node
        if node.n > 0:
            for _ in range(node.n % m):
                tmpNode = tmpNode.right
            if node == tmpNode:
                continue
            # remove the current node
            node.right.left = node.left
            node.left.right = node.right
            
            tmpNode.right.left = node
            node.right = tmpNode.right
            tmpNode.right = node
            node.left = tmpNode
        else:
            for _ in range(-node.n % m):
                tmpNode = tmpNode.left
            if node == tmpNode:
                continue
            # remove the current node
            node.left.right = node.right
            node.right.left = node.left
            
            tmpNode.left.right = node
            node.left = tmpNode.left
            tmpNode.left = node
            node.right = tmpNode

    ans = 0

    for _ in range(3):
        for _ in range(1000):
            zeroNode = zeroNode.right
        ans += zeroNode.n


    return ans

def part2(input_str: str) -> int:
    return -1


def parse_input(input_str: str) -> list[Node]:
    with open(input_str) as fn:
        lines = fn.read().strip().splitlines()

    return [Node(int(x)) for x in lines]


def main():
    puzzle_input_path = "2022/day20/input.txt"
    print(f"AoC2022 Day20, Part1 solution is: {part1(input_str=puzzle_input_path)}")
    print(f"AoC2022 Day20, Part2 solution is: {part2(input_str=puzzle_input_path)}")


if __name__ == "__main__":
    main()

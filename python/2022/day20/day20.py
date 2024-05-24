class Node:
    def __init__(self, n) -> None:
        self.n = n
        self.left = None
        self.right = None

    def __repr__(self) -> str:
        return (
            f"Node(value = {self.n}, LeftNode = {self.left}, RightNode = {self.right})"
        )


def run(input_str: str, cycles: int, decryption_key: int) -> int:
    nodes = [Node(int(x) * decryption_key) for x in parse_input(input_str)]

    for i in range(len(nodes)):
        nodes[i].right = nodes[(i + 1) % len(nodes)]
        nodes[i].left = nodes[(i - 1) % len(nodes)]

    m = len(nodes) - 1

    for _ in range(cycles):
        zeroNode = mix_list(nodes, m)

    ans = 0
    ans = calculate_groove_coordinates(zeroNode)

    return ans


def mix_list(nodes, m) -> Node:
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

    return zeroNode


def calculate_groove_coordinates(zeroNode: Node) -> int:
    ans = 0

    for _ in range(3):
        for _ in range(1000):
            zeroNode = zeroNode.right
        ans += zeroNode.n
    return ans


def parse_input(input_str: str) -> list[str]:
    with open(input_str) as fn:
        return fn.read().strip().splitlines()


def main():
    puzzle_input_path = "2022/day20/input.txt"
    print(
        f"AoC2022 Day20, Part1 solution is: {run(input_str=puzzle_input_path, cycles=1, decryption_key=1)}"
    )
    print(
        f"AoC2022 Day20, Part2 solution is: {run(input_str=puzzle_input_path, cycles=10, decryption_key=811589153)}"
    )


if __name__ == "__main__":
    main()

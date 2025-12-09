with open("2025/day04/input.txt") as f:
    input_data = f.read().strip()


def get_neighbors(i: int, j: int) -> list:
    return [
        (i - 1, j - 1),
        (i - 1, j),
        (i - 1, j + 1),
        (i, j - 1),
        (i, j + 1),
        (i + 1, j - 1),
        (i + 1, j),
        (i + 1, j + 1),
    ]


def build_grid(n: int, m: int, lines: list[str]) -> dict:
    grid = {}

    for i in range(n):
        for j in range(m):
            grid[(i, j)] = lines[i][j]

    return grid


def part1() -> int:
    total = 0

    lines = input_data.split("\n")
    n = len(lines[0])
    m = len(lines)

    grid = build_grid(n, m, lines)

    removable_nodes = get_removable_nodes_list(n, m, grid)
    return len(removable_nodes)


def get_removable_nodes_list(n: int, m: int, grid: list[list[str]]) -> list:
    removed_nodes = list()

    for i in range(n):
        for j in range(m):
            if grid[(i, j)] == ".":
                continue

            neighbors = get_neighbors(i, j)

            count = 0
            for neighbor in neighbors:
                if neighbor in grid and grid[neighbor] == "@":
                    count += 1

            if count < 4:
                removed_nodes.append((i, j))

    return removed_nodes


def update_grid(grid: dict, removed_nodes: list) -> dict:
    for node in removed_nodes:
        grid[node] = "."

    return grid


def part2() -> int:
    total = 0

    lines = input_data.split("\n")
    n = len(lines[0])
    m = len(lines)

    grid = build_grid(n, m, lines)

    removable_nodes = [(0, 0)]

    while len(removable_nodes) > 0:
        removable_nodes = get_removable_nodes_list(n, m, grid)
        total += len(removable_nodes)
        grid = update_grid(grid, removable_nodes)

    return total


if __name__ == "__main__":
    print(f"{part1()}")
    print(f"{part2()}")

def part1(input_str: str) -> int:
    (board, path) = parse_input(input_str)
    commands = get_instructions(path)
    adj, bound_row, bound_col = parse_board(board)

    # Instructions

    return -1


def part2(input_str) -> int:
    return -1


def parse_input(input_str: str) -> list[str]:
    with open(input_str) as fn:
        return fn.read().strip().split("\n\n")


def get_instructions(path: str) -> list[int]:
    commands = []
    cur_num = ""

    for idx in range(len(path)):
        if path[idx].isdigit():
            cur_num += path[idx]
        else:
            commands.append(int(cur_num))
            cur_num = ""
            commands.append(path[idx])

    if cur_num != "":
        commands.append(int(cur_num))

    return commands


def parse_board(raw_board: str):
    board = raw_board.split("\n")

    nr_rows = len(board)
    nr_cols = max([len(row) for row in board])

    bound_row = [[nr_cols, -1] for _ in range(nr_rows)]
    bound_col = [[nr_rows, -1] for _ in range(nr_cols)]

    adj = set()
    for row, line in enumerate(board):
        for col in range(len(line)):
            c = line[col]
            if c == ".":
                adj.add((row, col))

            if c in [".", "#"]:
                bound_row[row][0] = min(bound_row[row][0], col)
                bound_row[row][1] = max(bound_row[row][1], col)
                bound_col[col][0] = min(bound_col[col][0], row)
                bound_col[col][1] = max(bound_col[col][1], row)

    return adj, bound_row, bound_col


def main():
    puzzle_input_path = "2022/day22/input.txt"
    print(f"AoC2022 Day22, Part1 solution is: {part1(input_str=puzzle_input_path)}")
    print(f"AoC2022 Day22, Part2 solution is: {part2(input_str=puzzle_input_path)}")


if __name__ == '__main__':
    main()

interesting_points = [20, 60, 100, 140, 180, 220]


def part1(input: str) -> int:
    lines = parse_input(input)
    
    ans = 0
    
    X = 1
    op = 0
    
    for line in lines:
        parts = line.split(" ")
        if parts[0] == "noop":
          op += 1
          ans += update_ans(op, X)

        elif parts[0] == "addx":
            value = int(parts[1])
            
            op += 1
            ans += update_ans(op, X)

            op += 1
            ans += update_ans(op, X)

            X += value
    
    return ans

def update_ans(op: int, X: int) -> int:
    global interesting_points
    return 0 if op not in interesting_points else op * X


def part2(input: str) -> int:
    return -1


def parse_input(input: str) -> list[str]:
    with open(input) as fn:
        return fn.read().strip().split("\n")


def main():
    puzzle_input_path = "2022/day10/input.txt"
    print(f"AoC2022 Day10, Part1 solution is: {part1(input=puzzle_input_path)}")
    print(f"AoC2022 Day10, Part2 solution is: {part2(input=puzzle_input_path)}")


if __name__ == "__main__":
    main()

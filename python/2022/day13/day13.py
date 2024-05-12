
def part1(input: str) -> int:
    parts = parse_input(input)

    ans = 0

    for i, block in enumerate(parts):
        a, b = map(eval, block.split("\n"))
        if compare(a, b) == 1:
            ans += i + 1

    return ans


def part2(input: str) -> int:
    return -1


def compare(a, b) -> int:
    if isinstance(a, list) and isinstance(b, int):
        b = [b]
    
    if isinstance(a, int) and isinstance(b, list):
        a = [a]
    
    if isinstance(a, int) and isinstance(b, int):
        if a < b:
            return 1
        if a == b:
            return 0
        return -1

    i = 0
    j = 0
    
    while i < len(a) and j < len(b):
        x = compare(a[i], b[j])
        if x == 1:
            return 1
        if x == -1:
            return -1
            
        i += 1
        j += 1

    if i == len(a):
        if j == len(b):
            return 0
        return 1 # a ended first

    # a did't end, b is shorter
    return -1

def parse_input(input: str) -> list[str]:
    with open(input) as fn:
        return fn.read().strip().split("\n\n")


def main():
    puzzle_input_path = "2022/day13/input.txt"
    print(f"AoC2022 Day13, Part1 solution is: {part1(input=puzzle_input_path)}")
    print(f"AoC2022 Day13, Part2 solution is: {part2(input=puzzle_input_path)}")


if __name__ == "__main__":
    main()

from functools import cmp_to_key

def part1(input: str) -> int:
    parts = parse_input(input)

    ans = 0

    for i, block in enumerate(parts):
        a, b = map(eval, block.split("\n"))
        if compare(a, b) == 1:
            ans += i + 1

    return ans


def part2(input: str) -> int:
    parts = parse_input(input)
    
    parsed_lists = []
    
    for part in parts:
        l1, l2 = map(eval, part.split("\n"))
        parsed_lists.append(l1)
        parsed_lists.append(l2)
        
        
    parsed_lists.append([[2]])
    parsed_lists.append([[6]])
    
    lists = sorted(parsed_lists, key=cmp_to_key(compare), reverse=True)
    
    a, b = 0, 0 
    
    for i, list in enumerate(lists):
        if list == [[2]]:
            a = i + 1
        if list == [[6]]:
            b = i + 1

    return a * b

    

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
    
    while i < len(a) and i < len(b):
        x = compare(a[i], b[i])
        if x == 1:
            return 1
        if x == -1:
            return -1
            
        i += 1

    if i == len(a):
        if i == len(b):
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

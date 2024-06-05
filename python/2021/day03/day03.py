from typing import List


def part1(puzzle_input_path: str) -> int:
    input_data = read_input(input_file=puzzle_input_path)

    gama_rate = ""
    epsilon_rate = ""

    for i in range(len(input_data[0])):
        one_count = 0
        zero_count = 0
        for j in range(len(input_data)):
            current_digit = input_data[j][i]
            if current_digit == "1":
                one_count += 1
            else:
                zero_count += 1
        gama_rate += get_gama_rate_winning_bit(one_count, zero_count)
        epsilon_rate += get_epsilon_rate_winning_bit(one_count, zero_count)

    gama_rate = int(gama_rate, 2)
    epsilon_rate = int(epsilon_rate, 2)

    return gama_rate * epsilon_rate


def get_gama_rate_winning_bit(one_count: int, zero_count: int) -> str:
    return "1" if one_count > zero_count else "0"


def get_epsilon_rate_winning_bit(one_count: int, zero_count: int) -> str:
    return "0" if one_count > zero_count else "1"


def part2(puzzle_input_path: str) -> int:
    return -1


def read_input(input_file: str) -> List[str]:
    with open(input_file) as fn:
        return fn.read().strip().splitlines()


def main():
    puzzle_input_path = "2021/day03/input.txt"
    print(f"AoC2021 Day3, Part1 solution is: {part1(puzzle_input_path)}")
    print(f"AoC2021 Day3, Part2 solution is: {part2(puzzle_input_path)}")


if __name__ == '__main__':
    main()

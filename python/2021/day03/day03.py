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


def part2(puzzle_input_path: str) -> int:
    input_data = read_input(input_file=puzzle_input_path)

    oxygen_generator_numbers = input_data.copy()
    co2_scrubber_numbers = input_data.copy()

    i = 0
    while len(oxygen_generator_numbers) > 1:
        winning_bit = get_winning_bit(count_bits(i, oxygen_generator_numbers))
        oxygen_generator_numbers = filter_numbers_by_winning_bit(i, winning_bit, oxygen_generator_numbers)
        i += 1

    i = 0
    while len(co2_scrubber_numbers) > 1:
        looser_bit = get_looser_bit(count_bits(i, co2_scrubber_numbers))
        co2_scrubber_numbers = filter_numbers_by_winning_bit(i, looser_bit, co2_scrubber_numbers)
        i += 1

    oxygen_rating = int(oxygen_generator_numbers[0], 2)
    co2_rating = int(co2_scrubber_numbers[0], 2)

    return oxygen_rating * co2_rating


def count_bits(position: int, numbers: list[str]) -> dict[str, int]:
    counts = {"0": 0, "1": 0}
    for j in range(len(numbers)):
        current_digit = numbers[j][position]
        counts[current_digit] += 1
    return counts


def get_winning_bit(counts: dict[str, int]) -> str:
    return "1" if counts["1"] >= counts["0"] else "0"


def get_looser_bit(counts: dict[str, int]) -> str:
    return "0" if counts["1"] >= counts["0"] else "1"


def filter_numbers_by_winning_bit(position: int, winning_bit: str, numbers: list[str]) -> list[str]:
    return [number for number in numbers if number[position] == winning_bit]


def get_gama_rate_winning_bit(one_count: int, zero_count: int) -> str:
    return "1" if one_count > zero_count else "0"


def get_epsilon_rate_winning_bit(one_count: int, zero_count: int) -> str:
    return "0" if one_count > zero_count else "1"


def read_input(input_file: str) -> List[str]:
    with open(input_file) as fn:
        return fn.read().strip().splitlines()


def main():
    puzzle_input_path = "2021/day03/input.txt"
    print(f"AoC2021 Day3, Part1 solution is: {part1(puzzle_input_path)}")
    print(f"AoC2021 Day3, Part2 solution is: {part2(puzzle_input_path)}")


if __name__ == '__main__':
    main()

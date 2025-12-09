with open("2025/day02/input.txt") as f:
    data_input = f.read().strip()


def part1() -> int:
    count = 0

    input_ranges = data_input.split(",")
    for input_range in input_ranges:
        start, end = input_range.split("-")
        for nr in range(int(start), int(end) + 1):
            nr_str = str(nr)
            if len(nr_str) % 2 != 0:
                continue
            
            m = len(nr_str) // 2
            left = nr_str[:m]
            right = nr_str[m:]

            if left == right:
                count += nr
    return count


def part2() -> int:
    count = 0

    input_ranges = data_input.split(",")
    for input_range in input_ranges:
        start, end = input_range.split("-")
        for nr in range(int(start), int(end) + 1):
            nr_str = str(nr)
            if __is_valid_str(nr_str):
                count += nr

    return count


def __is_valid_str(number: str) -> bool:

    if len(number) == 1:
        return False
    
    for i in range(2, len(number) + 1):
        if len(number) % i != 0:
            continue

        substring_length = len(number) // i
        substring = number[:substring_length]
        if (substring * i == number):
            return True

    return False


if __name__ == "__main__":
    print(part1())
    print(part2())

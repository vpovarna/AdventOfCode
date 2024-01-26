import sys
from typing import List


def main():
    with open(sys.argv[1]) as fin:
        lines = fin.read().strip().split("\n")

    # max length
    n = len(lines[0])

    o2_rem = lines.copy()
    pos = 0
    while pos < n and len(o2_rem) > 1:
        winning_bit = winning_bit_value(o2_rem, pos)
        o2_rem = filter_on_position_and_wining_bit(o2_rem, pos, winning_bit)
        pos += 1

    co2_rem = lines.copy()
    pos = 0
    while pos < n and len(co2_rem) > 1:
        least_common_bit = least_common_bit_value(co2_rem, pos)
        co2_rem = filter_on_position_and_wining_bit(co2_rem, pos, least_common_bit)
        pos += 1

    o2_value = "".join(o2_rem)
    co2_value = "".join(co2_rem)

    return int(o2_value, 2) * int(co2_value, 2)


def winning_bit_value(data: List[str], pos: int) -> str:
    one_count = 0
    for line in data:
        one_count += 1 if line[pos] == '1' else 0

    zero_count = len(data) - one_count
    return '1' if one_count >= zero_count else '0'


def least_common_bit_value(data: List[str], pos: int) -> str:
    one_count = 0
    for line in data:
        one_count += 1 if line[pos] == '1' else 0

    zero_count = len(data) - one_count
    return '0' if zero_count <= one_count else '1'


def filter_on_position_and_wining_bit(data: List[str], pos: int, winning_bit: str) -> List[str]:
    return [line for line in data if line[pos] == winning_bit]


if __name__ == "__main__":
    print(main())

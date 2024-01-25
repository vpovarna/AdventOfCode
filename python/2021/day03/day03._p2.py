import sys
from typing import List


def main():
    with open(sys.argv[1]) as fin:
        lines = fin.read().strip().split("\n")

    # max length
    N = len(lines[0])

    number_list = lines

    for i in range(0, N):
        while len(number_list) > 1:
           wining_bit = get_wining_bit(number_list)



def get_wining_bit(number_list: List[str]) -> int:
    for j in range(0, len(number_list)):
        nr_of_once, nr_of_zeros = 0, 0
        if number_list[j] == '0':
            nr_of_zeros += 1
        else:
            nr_of_once += 1

        return 1 if nr_of_once > nr_of_zeros else 0


if __name__ == "__main__":
    main()

import sys


def main():
    file = open(sys.argv[1], encoding='utf-8')

    lines = file.readlines()
    values = [*map(int, lines)]

    r = [x + y + z for x, y, z in zip(values, values[1:], values[2:])]

    ans = sum(y > x for x, y in zip(r, r[1:]))
    print(f"Day01 part2 solution is: {ans}")


if __name__ == "__main__":
    main()

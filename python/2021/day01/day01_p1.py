import sys


def main():
    file = open(sys.argv[1], encoding='utf-8')

    lines = file.readlines()
    values = [*map(int, lines)]

    ans = sum( y > x for x, y in zip(values, values[1:]))
    print(f"Day01 part1 solution is: {ans}")


if __name__ == "__main__":
    main()

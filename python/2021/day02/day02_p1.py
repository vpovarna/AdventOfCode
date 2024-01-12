import sys


def main():
    file = open(sys.argv[1], encoding='utf-8')
    lines = file.readlines()

    h, d = 0, 0

    for line in lines:
        a,b = line.split(" ")
        b = int(b)

        if a == "forward":
            h += b
        elif a == "down":
            d +=b
        else:
            d -=b

    ans = h * d
    print(f"Day02 part1 solution is: {ans}")


if __name__ == "__main__":
    main()
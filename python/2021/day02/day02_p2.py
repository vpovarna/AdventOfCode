import sys


def main():
    file = open(sys.argv[1], encoding='utf-8')
    lines = file.readlines()
    
    a, h, d = 0, 0, 0

    for line in lines:
        instr,value = line.split(" ")
        value = int(value)

        if instr == "forward":
            h += value
            d += a * value
        elif instr == "down":
            a += value
        else:
            a -= value

    ans = h * d
    print(f"Day02 part2 solution is: {ans}")


if __name__ == "__main__":
    main()

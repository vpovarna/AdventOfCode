import sys


def main():
    file = open(sys.argv[1], encoding='utf-8')
    lines = file.readlines()

    max_row = len(lines)
    max_col = len(lines[0])

    gama = ""
    epsilon = ""

    one_count, zero_count = 0, 0

    for i in range(0, max_col -1):
        for j in range(0, max_row -1):
            line = lines[j].strip()
            if (line[i] == "1"):
                one_count +=1
            else:
                zero_count +=1
        if one_count > zero_count:
            gama += "1"
            epsilon += "0"
        else:
            gama += "0"
            epsilon += "1"
        one_count, zero_count = 0, 0

    ans = int(gama, 2) * int(epsilon, 2)
    print(f"Day03 part1 solution is: {ans}")


if __name__ == "__main__":
    main()

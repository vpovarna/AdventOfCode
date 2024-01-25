import sys


def main():
    with open(sys.argv[1]) as fin:
        lines = fin.read().strip().split("\n")

    # max length
    N = len(lines[0])

    gama_rate = [None] * N
    epsilon_rate = [None] * N

    for i in range(N):
        zeros = sum([lines[j][i] == "0" for j in range(len(lines))])
        ones = sum([lines[j][i] == "1" for j in range(len(lines))])

        if zeros > ones:
            gama_rate[i] = "0"
            epsilon_rate[i] = "1"
        else:
            gama_rate[i] = "1"
            epsilon_rate[i] = "0"

    ans = int("".join(gama_rate), 2) * int("".join(epsilon_rate), 2)
    print(f"Day03 part1 solution is: {ans}")


if __name__ == "__main__":
    main()

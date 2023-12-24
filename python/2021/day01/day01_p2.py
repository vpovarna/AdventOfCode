"""Module providing a function printing python version."""
import sys

file = open(sys.argv[1], encoding='utf-8')

lines = file.readlines()
values = [*map(int, lines)]

r = [x + y + z for x, y, z in zip(values, values[1:], values[2:])]

print(sum(y > x for x, y in zip(r, r[1:])))

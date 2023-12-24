"""Module providing a function printing python version."""
import sys

file = open(sys.argv[1], encoding='utf-8')

lines = file.readlines()
values = [*map(int, lines)]

total = sum( y > x for x, y in zip(values, values[1:]))
print(total)

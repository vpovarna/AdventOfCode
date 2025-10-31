from collections import Counter
from dataclasses import dataclass

with open("input.txt") as f:
    input_lines = f.read().strip()


def run(is_second_part: bool = False) -> int:
    total = 0
    lines = input_lines.split("\n")
    for line in lines:
        instruction = _parse_line(line)
        if is_second_part:
            total += 1 if instruction.has_password_valid_part2() else 0
        else:
            total += 1 if instruction.has_password_valid_part1() else 0
    return total


def _parse_line(line: str) -> (int, int, str, str):
    parts = line.split(": ")
    p = parts[0].split(" ")
    pos1, pos2 = p[0].split("-")

    return Instruction(parts[1], int(pos1), int(pos2), p[1])


@dataclass
class Instruction:
    password: str
    pos1: int
    pos2: int
    ch: str

    def has_password_valid_part1(self) -> bool:
        occurrence = Counter(self.password)
        return self.pos1 <= occurrence.get(self.ch, - 1) <= self.pos2

    def has_password_valid_part2(self) -> bool:
        return ((self.password[self.pos1 - 1] == self.ch and self.password[self.pos2 - 1] != self.ch)
                or (self.password[self.pos1 - 1] != self.ch and self.password[self.pos2 - 1] == self.ch))


if __name__ == '__main__':
    print(f"Solution Part1 {run()}")
    print(f"Solution Part2 {run(is_second_part=True)}")

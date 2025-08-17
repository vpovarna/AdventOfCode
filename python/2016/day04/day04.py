import re

pattern = r"^(.*)-(\d+)\[(.*)\]$"

with open("input.txt") as f:
    data_input = [line.strip() for line in f.readlines()]


def part1() -> int:
    count = 0
    for line in data_input:
        encrypted_name, sector, checksum = __extract_parts(line)

        occurrence = __build_occurrence_map(encrypted_name)
        sorted_keys = sorted(occurrence.items(), key=lambda item: (-item[1], item[0]))
        s = ""
        for c, _ in sorted_keys[0:len(checksum)]:
            s = s + c
        if s == checksum:
            count += sector

    return count


def part2() -> int:
    for line in data_input:
        encrypted_name, sector, checksum = __extract_parts(line)
        decrypted = []
        for char in encrypted_name:
            if char == '-':
                decrypted.append(' ')
            else:
                shifted = (ord(char) - ord('a') + sector) % 26
                decrypted.append(chr(ord('a') + shifted))
        decrypted_message = ''.join(decrypted)
        if decrypted_message == "northpole object storage":
            return sector

    return -1


def __build_occurrence_map(encrypted_name: str) -> dict:
    d = {}

    for c in encrypted_name:
        if c == "-":
            continue
        if c.isdigit():
            continue
        v = d.get(c, 0)
        d[c] = v + 1

    return d


def __extract_parts(line: str) -> (str, int, str):
    match = re.match(pattern, line)

    if match:
        return match.group(1), int(match.group(2)), match.group(3)


if __name__ == '__main__':
    print(f"Day04 solution for part1 is: {part1()}")
    print(f"Day04 solution for part2 is: {part2()}")

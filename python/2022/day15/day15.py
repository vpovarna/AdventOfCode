from dataclasses import dataclass
from typing import TypeAlias


Y = 2000000


@dataclass()
class Point:
    x: int
    y: int


def manhattan_distance(point1, point2) -> int:
    return abs(point1.x - point2.x) + abs(point1.y - point2.y)
    # return abs(a[0] - b[0]) + abs(a[1] - b[1])


def part1(input_str: str) -> int:
    sensors, beacons = get_input_coordinates(parse_input(input_str))
    dists = get_distances(sensors, beacons)

    intervals = []

    for i, s in enumerate(sensors):
        dx = dists[i] - abs(s.y - Y)
        if dx <= 0:
            continue
        intervals.append((s.x - dx, s.x + dx))

    # Add interval that are on the Y line
    allowed_x = get_allowed_beacons(beacons)

    min_x = min(i[0] for i in intervals)
    max_x = max(i[1] for i in intervals)

    ans = 0

    for x in range(min_x, max_x + 1):
        if x in allowed_x:
            continue

        for left, right in intervals:
            if left <= x <= right:
                ans += 1
                break

    return ans


def get_distances(sensors: list[Point], beacons: list[Point]) -> list[int]:
    dists = []

    for i in range(len(sensors)):
        dists.append(manhattan_distance(sensors[i], beacons[i]))
    return dists


def get_allowed_beacons(beacons: list[Point]) -> set[int]:
    allowed_x = set()
    for beacon in beacons:
        if beacon.y == Y:
            allowed_x.add(beacon.x)
    return allowed_x


def part2(input_str: str) -> int:
    sensors, beacons = get_input_coordinates(parse_input(input_str))

    n = len(sensors)
    dists = []
    for i in range(n):
        dists.append(manhattan_distance(sensors[i], beacons[i]))

    pos_lines, neg_lines = get_lines(sensors, dists)

    pos = None
    neg = None

    for i in range(2 * n):
        for j in range(i + 2, 2 * n):
            a, b = pos_lines[i], pos_lines[j]

            if abs(a - b) == 2:
                pos = min(a, b) + 1

            a, b = neg_lines[i], neg_lines[j]
            if abs(a - b) == 2:
                neg = min(a, b) + 1

    x, y = (pos + neg) // 2, (neg - pos) // 2
    return x * 4000000 + y


def get_lines(sensors: list[Point], dists: list[int]) -> tuple[list[int], list[int]]:
    pos_lines = []
    neg_lines = []

    for i, point in enumerate(sensors):
        d = dists[i]
        neg_lines.extend([point.x + point.y - d, point.x + point.y + d])
        pos_lines.extend([point.x - point.y - d, point.x - point.y + d])
    return pos_lines, neg_lines


InputCoordinates: TypeAlias = tuple[list[Point], list[Point]]


def get_input_coordinates(lines: list[str]) -> InputCoordinates:
    sensors = []
    beacons = []

    for line in lines:
        parts = line.split(" ")
        sx = int(parts[2][2:-1])
        sy = int(parts[3][2:-1])
        sensor_point = Point(sx, sy)
        sensors.append(sensor_point)
        bx = int(parts[8][2:-1])
        by = int(parts[9][2:])
        beacon_point = Point(bx, by)
        beacons.append(beacon_point)

    return (sensors, beacons)


def parse_input(input: str) -> list[str]:
    with open(input) as fn:
        return fn.read().strip().split("\n")


def main():
    puzzle_input_path = "2022/day15/input.txt"
    print(f"AoC2022 Day15, Part1 solution is: {part1(input_str=puzzle_input_path)}")
    print(f"AoC2022 Day15, Part2 solution is: {part2(input_str=puzzle_input_path)}")


if __name__ == "__main__":
    main()

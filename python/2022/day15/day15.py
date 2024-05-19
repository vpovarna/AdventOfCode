from dataclasses import dataclass
from typing import TypeAlias


@dataclass()
class Point:
    x: int
    y: int


def manhattan_distance(point1, point2) -> int:
    return abs(point1.x - point2.x) + abs(point1.y - point2.y)
    # return abs(a[0] - b[0]) + abs(a[1] - b[1])


def part1(input: str) -> int:
    lines = parse_input(input)

    sensors, beacons = get_input_coordinates(lines)

    N = len(sensors)
    dists = []
    for i in range(N):
        dists.append(manhattan_distance(sensors[i], beacons[i]))

    Y = 2000000
    intervals = []

    for i, s in enumerate(sensors):
        dx = dists[i] - abs(s.y - Y)
        if dx <= 0:
            continue
        intervals.append((s.x - dx, s.x + dx))

    # Add interval that are on the Y line
    allowed_x = set()
    for beacon in beacons:
        if beacon.y == Y:
            allowed_x.add(beacon.x)

    min_x = min([i[0] for i in intervals])
    max_x = max([i[1] for i in intervals])

    ans = 0

    for x in range(min_x, max_x + 1):
        if x in allowed_x:
            continue

        for left, right in intervals:
            if left <= x <= right:
                ans += 1
                break

    return ans


def part2(input: str) -> int:
    return -1


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
    print(f"AoC2022 Day15, Part1 solution is: {part1(input=puzzle_input_path)}")
    print(f"AoC2022 Day15, Part2 solution is: {part2(input=puzzle_input_path)}")


if __name__ == "__main__":
    main()

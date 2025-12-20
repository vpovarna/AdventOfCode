import math


with open("2025/day09/input.txt") as f:
    input_data = f.read().strip().splitlines()


def get_points(input_data):
    points = []
    for point in input_data:
        a, b = point.split(",")
        points.append((int(a), int(b)))
    return points


def calculate_area(point1: tuple[int, int], point2: tuple[int, int]) -> int:
    return (abs(point1[0] - point2[0]) + 1) * (abs(point1[1] - point2[1]) + 1)


def part1() -> int:
    points = get_points(input_data)

    max_value = 0
    for i, point in enumerate(points):
        for j in range(i + 1, len(points)):
            area = calculate_area(point, points[j])
            max_value = max(max_value, area)
    return max_value


if __name__ == "__main__":
    print(f"{part1()}")

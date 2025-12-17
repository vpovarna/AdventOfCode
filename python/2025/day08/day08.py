from functools import cache
from collections import defaultdict, Counter, deque


with open("2025/day08/input.txt") as f:
    input_data = f.read().strip()


def solve(limit: None | int = None) -> int:
    boxes = []
    for line in input_data.splitlines():
        x, y, z = [int(x) for x in line.split(",")]
        boxes.append((x, y, z))

    boxes_distances = []
    for i, (x1, y1, z1) in enumerate(boxes):
        for j, (x2, y2, z2) in enumerate(boxes):
            distance = (x1 - x2) ** 2 + (y1 - y2) ** 2 + (z1 - z2) ** 2
            if i > j:
                boxes_distances.append((distance, i, j))
    boxes_distances = sorted(boxes_distances)

    UF = {i: i for i in range(len(boxes))}

    def find(x):
        if x == UF[x]:
            return x
        UF[x] = find(UF[x])
        return UF[x]

    def mix(x, y):
        UF[find(x)] = find(y)

    connections = 0
    for t, (_, i, j) in enumerate(boxes_distances):
        if limit and t == limit:
            SZ = defaultdict(int)
            for x in range(len(boxes)):
                SZ[find(x)] += 1
            S = sorted(SZ.values())
            return S[-1] * S[-2] * S[-3]
        if find(i) != find(j):
            connections += 1
            if connections == len(boxes) - 1:
                return boxes[i][0] * boxes[j][0]
            mix(i, j)


if __name__ == "__main__":
    print(f"{solve(limit=1000)}")
    print(f"{solve()}")

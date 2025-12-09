with open("2025/day05/input.txt") as f:
    input_data = f.read().strip()


def parse_input() -> tuple[list[list[int]], list[int]]:
    parts = input_data.split("\n\n")
    lines = parts[0]
    fresh_ids = [int(i) for i in parts[1].split("\n")]

    intervals = []

    for line in lines.split("\n"):
        a, b = line.split("-")
        intervals.append([int(a), int(b)])

    intervals.sort()
    concat_intervals = []
    concat_intervals.append(intervals[0])

    for i in range(1, len(intervals)):
        last_interval = concat_intervals[-1]
        current_interval = intervals[i]

        if current_interval[0] > last_interval[1]:
            concat_intervals.append(current_interval)
        else:
            new_interval = [
                min(last_interval[0], current_interval[0]),
                max(last_interval[1], current_interval[1]),
            ]
            concat_intervals[-1] = new_interval

    return concat_intervals, fresh_ids


def part1() -> int:
    count = 0
    concat_intervals, fresh_ids = parse_input()
    for id in fresh_ids:
        for interval in concat_intervals:
            if id <= interval[1] and id >= interval[0]:
                count += 1
    return count


def part2() -> int:
    concat_intervals, _ = parse_input()
    total = 0
    for interval in concat_intervals:
        total += interval[1] - interval[0] + 1
    return total


if __name__ == "__main__":
    print(part1())
    print(part2())

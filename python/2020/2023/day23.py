def part1() -> int:
    arr = [3, 8, 9, 1, 2, 5, 4, 6, 7]
    current_position = 0

    for i in range(0, 8):
        arr, current_position = shuffle(arr, current_position)
        print(arr)
        print(current_position)


def shuffle(arr: list[int], current_position: int) -> (list[int], int):
    label = arr[current_position]
    label = arr[current_position]
    n = len(arr)

    items = [arr[(current_position + i) % n] for i in range(1, 4)]

    arr = [x for x in arr if x not in items]

    destination_label = label - 1
    while destination_label in items or destination_label < min(arr):
        if destination_label < min(arr):
            destination_label = max(arr)
        else:
            destination_label -= 1

    dest_index = arr.index(destination_label)
    arr = arr[:dest_index + 1] + items + arr[dest_index + 1:]

    current_position = (arr.index(label) + 1) % len(arr)
    return arr, current_position


if __name__ == '__main__':
    print(f"Print part1: {part1()}")

from functools import reduce


def task_1(input_data):
    sorted_data = sorted(data)

    target_position = sorted_data[len(input_data) / 2]

    result = reduce(lambda acc, curr: acc + abs(target_position - curr), sorted_data, 0)
    print(result)


def task_2(input_data):
    def fuel(a, b):
        start, end = sorted([a, b])
        return reduce(lambda acc, curr: acc + curr, [y for y in range(1, end-start+1)], 0)

    target_position = reduce(lambda acc, curr: acc + curr, input_data, 1) / len(input_data)

    result = reduce(lambda acc, curr: acc + fuel(target_position, curr), input_data, 0)
    print(result)


if __name__ == '__main__':
    data = [int(x) for x in open("./1.txt").read().split(',')]

    task_1(data)
    task_2(data)

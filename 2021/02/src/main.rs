use std::fs;

fn task_1(data: &Vec<(&str, i32)>) {
    let result = data
        .iter()
        .fold((0 ,0), | acc, (direction, offset) | match *direction {
            "forward" => (acc.0 + *offset, acc.1),
            "down" => (acc.0, acc.1 + *offset),
            "up" => (acc.0, acc.1 - *offset),
            _ => acc,
        });

    println!("task-1: {}", result.0 * result.1);
}

fn task_2(data: &Vec<(&str, i32)>) {
    let result = data
        .iter()
        .fold((0 ,0, 0), | acc, (direction, offset) | match *direction {
            "forward" => (acc.0 + *offset, acc.1 + acc.2 * *offset, acc.2),
            "down" => (acc.0, acc.1, acc.2 + *offset),
            "up" => (acc.0, acc.1, acc.2 - *offset),
            _ => acc,
        });

    println!("task-2: {}", result.0 * result.1);
}

fn main() {
    let input = fs::read_to_string("/Users/lino/dev/advent-of-code-21/02/1.txt")
        .unwrap();

    let parsed_input = input
        .split('\n')
        .map(|line| {
            let mut words = line.splitn(2, ' ');

            (words.next().unwrap(), words.next().unwrap().parse::<i32>().unwrap())
        })
        .collect::<Vec<(&str, i32)>>();

    task_1(&parsed_input);
    task_2(&parsed_input);
}

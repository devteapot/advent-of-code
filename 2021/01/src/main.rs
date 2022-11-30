use std::fs;
use itertools::Itertools;

fn task_1(data: &Vec<i32>) {
    let result = data
        .iter()
        .tuple_windows()
        .fold (0, |acc, (fst, snd)|  acc + if *fst < *snd { 1 } else { 0 });

    println!("task-1: {}", result)
}

fn task_2(data: &Vec<i32>) {
    let result = data
        .iter()
        .tuple_windows::<(_, _, _)>()
        .map(|(a, b, c)| a + b + c)
        .tuple_windows()
        .fold (0, |acc, (fst, snd)|  acc + if fst < snd { 1 } else { 0 });

    println!("task-2: {}", result)
}

fn main() {
    let data = fs::read_to_string("/Users/lino/dev/advent-of-code-21/01/1.txt")
        .unwrap()
        .split('\n')
        .map(|x| x.parse::<i32>().unwrap())
        .collect::<Vec<i32>>();

    task_1(&data);
    task_2(&data);
}

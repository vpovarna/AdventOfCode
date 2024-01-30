#[allow(dead_code)]
use std::fs::read_to_string;

pub fn solve() {
    let input = String::from("src/year2015/resources/day1.txt");

    let lines = read_to_string(&input).unwrap();

    println!("AoC 2015, Day1, Part1 solution: {}", part1(&lines));
    println!("AoC 2015, Day2, Part2 solution: {}", part2(&lines));
}

fn part1(lines: &String) -> i32 {
    let mut res: i32 = 0;
    for c in lines.chars().into_iter() {
        if c == '(' {
            res +=1;
        } else {
            res -=1;
        }
    }
    res
}

fn part2(lines: &String) -> usize {
    let mut res: i32 = 0;
    for (i, c) in lines.chars().enumerate() {
        if c == '(' {
            res +=1
        } else { 
            res -=1;
            if res < 0 {
                return i + 1;
            }
        }
    }

    unreachable!()
}

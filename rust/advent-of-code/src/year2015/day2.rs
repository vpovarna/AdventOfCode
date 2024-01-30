use std::fs::read_to_string;

use itertools::Itertools;

pub fn solve() {
    let input_file_path = String::from("src/year2015/resources/day2.txt");

    println!(
        "AoC2015, Day2, Part1 solution is: {}",
        part1(&input_file_path)
    );
    println!(
        "AoC2015, Day2, Part2 solution is: {}",
        part2(&input_file_path)
    );
}

fn part1(input: &String) -> usize {
    parse_input(input)
        .iter()
        .map(calculate_surface_area)
        .fold(0, |t, surface_area| t + surface_area)
}

fn part2(input: &String) -> usize {
    parse_input(input)
        .iter()
        .map(calculate_ribbon_needed_surface)
        .fold(0, |t, surface_area| t + surface_area)
}

fn calculate_surface_area(sizes: &(usize, usize, usize)) -> usize {
    let (l, w, h) = sizes;
    let a = l * w;
    let b = l * h;
    let c = w * h;

    let min_area = (a.min(b)).min(c);

    2 * (a + b + c) + min_area
}

fn calculate_ribbon_needed_surface(sizes: &(usize, usize, usize)) -> usize {
    let (l, w, h) = sizes;
    let ribbon_size = [l, w, h].into_iter().sorted().take(2).sum::<usize>();

    (l * w * h) + 2 * ribbon_size
}

fn parse_input(input: &String) -> Vec<(usize, usize, usize)> {
    read_lines(input)
        .iter()
        .map(parse_line)
        .map(|sizes: Vec<usize>| (sizes[0], sizes[1], sizes[2])) // TODO: try struct impl
        .collect_vec()
}

fn parse_line(line: &String) -> Vec<usize> {
    line.split('x')
        .map(|size: &str| size.parse().unwrap())
        .collect_vec()
}

fn read_lines(input: &String) -> Vec<String> {
    read_to_string(input)
        .unwrap()
        .lines()
        .map(String::from)
        .collect()
}

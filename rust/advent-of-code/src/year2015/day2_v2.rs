use crate::utils::read_lines;

use itertools::Itertools;

#[derive(Debug)]
struct Present {
    l: usize,
    w: usize,
    h: usize,
}

impl Present {
    pub fn new(l: usize, w: usize, h: usize) -> Present {
        Present { l, w, h }
    }

    pub fn calculate_surface_area(self: &Self) -> usize {
        let a = self.l * self.w;
        let b = self.l * self.h;
        let c = self.h * self.w;

        let min_area = (a.min(b)).min(c);

        2 * (a + b + c) + min_area
    }

    pub fn calculate_ribbon_needed_surface(self: &Self) -> usize {
        let ribbon_size = [self.l, self.w, self.h]
            .into_iter()
            .sorted()
            .take(2)
            .sum::<usize>();

        (self.l * self.w * self.h) + 2 * ribbon_size
    }
}

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
        .map(|present| present.calculate_surface_area())
        .sum()
}

fn part2(input: &String) -> usize {
    parse_input(input)
    .iter()
    .map(|present| present.calculate_ribbon_needed_surface())
    .sum()
}

fn parse_input(input: &String) -> Vec<Present> {
    read_lines(input)
        .iter()
        .map(|line| parse_line(line))
        .filter(|dim| dim.len() == 3)
        .map(|dim| Present::new(dim[0], dim[1], dim[2]))
        .collect_vec()
}

fn parse_line(line: &String) -> Vec<usize> {
    line.split('x')
        .map(|size: &str| size.parse().unwrap())
        .collect_vec()
}

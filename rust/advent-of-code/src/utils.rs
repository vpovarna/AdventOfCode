use std::fs::read_to_string;

pub fn read_lines(input: &String) -> Vec<String> {
    read_to_string(input)
        .unwrap()
        .lines()
        .map(String::from)
        .collect()
}

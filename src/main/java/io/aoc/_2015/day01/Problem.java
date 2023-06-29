package io.aoc._2015.day01;

import io.aoc._2015.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Problem {
    private static Logger logger = LoggerFactory.getLogger(Problem.class);
    public static void main(String[] args) {
        Utils utils = new Utils();
        List<String> strings = utils.readInputFile("1", "input.txt");
        strings.forEach(line -> logger.info(line));
    }
}

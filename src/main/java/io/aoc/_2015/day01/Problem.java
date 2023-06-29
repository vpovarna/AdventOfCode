package io.aoc._2015.day01;

import io.aoc._2015.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Problem {
    private static final Logger logger = LoggerFactory.getLogger(Problem.class);
    public static void main(String[] args) {
        var problem = new Problem();
        var is = problem.getFileFromResourceAsStream("day1/input.txt");
        logger.info("test");
//        Files.readString(Path.of())

//        Utils utils = new Utils();
//        List<String> strings = utils.readInputFile("1", "input.txt");
//        strings.forEach(logger::info);
    }

    private InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }
}

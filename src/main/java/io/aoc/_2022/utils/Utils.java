package io.aoc._2022.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Utils {
    private final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static List<String> readInputFileLineByLine(int day, String fileName) {
        final Utils utils = new Utils();

        final String dayNumber = getDayNumberAsString(day);
        final String resourceFile = String.format("%s/%s", dayNumber, fileName);
        final InputStream inputStream = utils.getFileFromResourceAsStream(resourceFile);
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.toList());
    }

    public static String readInputFileAsString(int day, String fileName) {
        final Utils utils = new Utils();
        final String defaultFileName = Objects.requireNonNullElse(fileName, "input.txt");

        final String dayNumber = getDayNumberAsString(day);
        final String resourceFile = String.format("%s/%s", dayNumber, defaultFileName);
        final InputStream inputStream = utils.getFileFromResourceAsStream(resourceFile);
        try {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Unable to generate the string: " + e.getMessage());
        }
    }

    private static String getDayNumberAsString(int day) {
        return (day > 0 && day <= 9) ? String.format("day0%s", day) : String.valueOf(day);
    }

    private InputStream getFileFromResourceAsStream(String fileName) {
        final ClassLoader classLoader = getClass().getClassLoader();
        final InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream != null) {
            return inputStream;
        } else {
            throw new IllegalArgumentException("File not found!");
        }
    }
}

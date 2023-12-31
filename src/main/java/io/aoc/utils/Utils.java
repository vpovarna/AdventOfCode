package io.aoc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class Utils {

    public static final String DEFAULT_FILE_NAME = "input.txt";

    public static String readInputFileAsString(int year, int day) {
        return readInputFileAsString(year, day, DEFAULT_FILE_NAME);
    }

    public static String readInputFileAsString(int year, int day, String fileName) {
        final Utils utils = new Utils();
        final String defaultFileName = Objects.requireNonNullElse(fileName, "input.txt");

        final String dayNumber = getDayNumberAsString(day);
        final String yearNumber = getYearNumberAsString(year);
        final String resourceFile = String.format("%s/%s/%s", yearNumber, dayNumber, defaultFileName);
        final InputStream inputStream = utils.getFileFromResourceAsStream(resourceFile);
        try {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to generate the string: " + e.getMessage());
        }
    }

    private static String getYearNumberAsString(int year) {
        if (year >= 2015 && year <= 2023) {
            return String.format("%s", year);
        } else {
            throw new IllegalArgumentException("Unsupported Year");
        }
    }

    private static String getDayNumberAsString(int day) {
        return (day > 0 && day <= 9) ? String.format("day0%s", day) : String.format("day%s", day);
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

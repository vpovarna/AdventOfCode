package io.aoc._2022.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class Utils {

    public static String readInputFileAsString(int day, String fileName) {
        final Utils utils = new Utils();
        final String defaultFileName = Objects.requireNonNullElse(fileName, "input.txt");

        final String dayNumber = getDayNumberAsString(day);
        final String resourceFile = String.format("%s/%s", dayNumber, defaultFileName);
        final InputStream inputStream = utils.getFileFromResourceAsStream(resourceFile);
        try {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to generate the string: " + e.getMessage());
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

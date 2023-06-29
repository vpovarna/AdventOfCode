package io.aoc._2015.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public final class Utils {
    private final Logger logger = LoggerFactory.getLogger(Utils.class);

    public List<String> readInputFile(String day, String fileName) {
        var classLoader = this.getClass().getClassLoader();
        var resourceFile = String.format("day%s/%s", day, fileName);
        var file = Objects.requireNonNull(classLoader.getResource(resourceFile)).getFile();

        if (file == null) {
            throw new IllegalArgumentException(String.format("File %s doesn't exist", resourceFile));
        }

        try {
            Files.readAllLines(Path.of(file));
        } catch (IOException e) {
            logger.error("Unable to read: {}, exception: {}", file, e.getMessage());
        }
        return List.of();
    }
}

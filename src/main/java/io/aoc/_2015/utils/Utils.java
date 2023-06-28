package io.aoc._2015.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Utils {
    private final Logger logger = LoggerFactory.getLogger(Utils.class);

    public List<String> readInputFile(String fileName) {
        try {
            Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            logger.error("Unable to read: {}, exception: {}", fileName, e.getMessage());
        }
        return List.of();
    }
}

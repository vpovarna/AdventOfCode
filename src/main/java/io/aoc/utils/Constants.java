package io.aoc.utils;

public final class Constants {
    public static final String EOL = "\n";
    public static final String EMPTY_LINE = getOsName().startsWith("Windows") ? "\r\n\r\n" : "\n\n";
    public static final String EMPTY_STRING = " ";
    public static final String DOTS = ":";
    public static final String DASH = "|";
    public static final String COMMA = ",";

    private static String OS = null;

    private static String getOsName() {
        if (OS == null) {
            OS = System.getProperty("os.name");
        }
        return OS;
    }


    private Constants() {
        throw new IllegalArgumentException("Static class, don't need to instance it");
    }
}

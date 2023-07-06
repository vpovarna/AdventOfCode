package io.aoc._2022.day07;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Directory implements DirectoryEntry {

    private final String name;
    private final Directory parent;
    private final List<DirectoryEntry> entries;

    Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
        this.entries = new ArrayList<>();
    }

    void add(DirectoryEntry entry) {
        if (entry instanceof Directory) {
            var dir = (Directory) entry;
            if (!dir.getParent().equals(this)) {
                throw new IllegalArgumentException("Directory doesn't have this directory as parent");
            }
        }
        entries.add(entry);
    }

    Directory findDirectory(String name) {
        for (DirectoryEntry entry : entries) {
            if (entry instanceof Directory) {
                var dir = (Directory) entry;
                if (dir.getName().equals(name)) {
                    return dir;
                }
            }
        }
        return null;
    }

    List<Directory> listWithAllSubDirectories() {
        List<Directory> result = new ArrayList<>();
        addDirsRecursively(this, result);
        return result;
    }

    private static void addDirsRecursively(Directory root, List<Directory> result) {
        result.add(root);
        final List<Directory> subDirectories = root.getSubDirectories();
        for (Directory subDirectory : subDirectories) {
            addDirsRecursively(subDirectory, result);
        }
    }

    List<Directory> getSubDirectories() {
        return entries.stream()
                .filter(Directory.class::isInstance)
                .map(Directory.class::cast)
                .collect(Collectors.toList());
    }

    long totalSize() {
        long totalSize = 0;
        for (DirectoryEntry entry : entries) {
            if (entry instanceof Directory) {
                var dir = (Directory) entry;
                totalSize += dir.totalSize();
            } else if (entry instanceof File) {
                var file = (File) entry;
                totalSize += file.getSize();
            }
        }
        return totalSize;
    }

    @Override
    public String getName() {
        return name;
    }

    public Directory getParent() {
        return parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Directory directory = (Directory) o;
        return Objects.equals(name, directory.name) && Objects.equals(parent, directory.parent) && Objects.equals(entries, directory.entries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parent, entries);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("- %s (dir)%n", name));

        for (DirectoryEntry entry : entries) {
            result.append(entry.toString()).append("\n"); // "indent" adds a line break at the end
        }
        return result.toString().stripTrailing();
    }
}

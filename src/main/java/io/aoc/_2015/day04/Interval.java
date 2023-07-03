package io.aoc._2015.day04;

class Interval {
    private final int x;
    private final int y;

    public Interval(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Interval[x=" + x + ", y=" + y +"]";
    }

    public static boolean checkIfTwoIntervalsFullyOverlaps(Interval interval1, Interval interval2) {
        return (interval1.x <= interval2.x && interval1.y >= interval2.y) ||
                (interval2.x <= interval1.x && interval2.y >= interval1.y);
    }

    public static boolean checkIfTwoIntervalsOverlaps(Interval interval1, Interval interval2) {
        return (interval1.x <= interval2.x && interval2.x <= interval1.y) ||
                (interval2.x <= interval1.x && interval1.x <= interval2.y) ||
                (interval1.x <= interval2.x && interval1.y >= interval2.y) ||
                (interval1.x >= interval2.x && interval1.y <= interval2.y);
    }
}

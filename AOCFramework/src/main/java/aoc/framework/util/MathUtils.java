package aoc.framework.util;

import java.util.*;

public final class MathUtils {

    private MathUtils() {
        // Empty as this is a static class
    }

    public static List<Long> range(long floor, long ceiling) {
        List<Long> result = new LinkedList<>();

        for (long i = floor; i <= ceiling; i++) {
            result.add(i);
        }

        return result;
    }

    public static List<Long> range(long ceiling) {
        return range(0, ceiling);
    }

}

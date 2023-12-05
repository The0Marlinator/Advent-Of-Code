package aoc.utils;
import java.util.LinkedList;
import java.util.List;

public class MathUtils {

    public static List<Long> range(long floor, long ceiling) {
        List<Long> result = new LinkedList<>();

        for(long i = floor; i<=ceiling; i++) {
            result.add(i);
        }

        return result;
    }

}

package aoc.framework.util;

import java.util.LinkedList;
import java.util.List;

public final class ListUitls {

    public static <T> List<T> appendFirstToList(T value, List<T> list) {
        List<T> result = new LinkedList<>();
        result.addFirst(value);
        return result;
    }
}

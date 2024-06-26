package aoc.framework.util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public final class CollectionUitls {

    public static <T> List<T> appendFirstToList(T value, List<T> list) {
        List<T> result = new LinkedList<>();
        result.addFirst(value);
        result.addAll(list);
        return result;
    }

}

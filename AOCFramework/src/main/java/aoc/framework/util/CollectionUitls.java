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

    public static <T> List<Integer> findIndexOfAll(List<T> list, T value) {
        List<Integer> result = new LinkedList<>();

        for(int i = 0; i<list.size(); i++) {
            if (list.get(i).equals(value)) {
                result.add(i);
            }
        }
        return result;
    }

    public static <T> List<T> replaceIndexInListAsNewList(List<T> list, int index, T value) {
        List<T> result = new LinkedList<>(list);
        result.set(index, value);
        return result;
    }

    public static <T> Set<T> addToSet(T value, Set<T> set) {
        Set<T> result = new HashSet<>();
        result.add(value);
        result.addAll(set);
        return result;
    }
}

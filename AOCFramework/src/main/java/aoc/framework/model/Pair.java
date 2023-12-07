package aoc.framework.model;

public class Pair<T, V> {

    private final T first;

    private final V second;

    public Pair(T first, V second) {
        this.first = first;
        this.second = second;
    }

    public Pair<V, T> flip(){
        return new Pair<>(second, first);
    }

    public T getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "<"+first+", "+second+">";
    }
}

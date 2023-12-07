package aoc.framework.model;

public class Pair<T, V> {

    public T first;
    public V second;

    public Pair(T first, V second) {
        this.first = first;
        this.second = second;
    }

    public Pair<V, T> flip(){
        return new Pair<>(second, first);
    }

    @Override
    public String toString() {
        return "<"+first+", "+second+">";
    }
}

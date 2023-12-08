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

    @Override
    public int hashCode() {
        return first.hashCode()*10+second.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Pair<?,?> ) {
            return first.equals(((Pair<?, ?>) other).first) && second.equals(((Pair<?, ?>) other).second);
        }
        return false;
    }
}

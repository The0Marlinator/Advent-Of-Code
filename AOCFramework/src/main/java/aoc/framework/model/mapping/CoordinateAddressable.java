package aoc.framework.model.mapping;

import aoc.framework.util.MathUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class CoordinateAddressable<T> {

    public abstract T get(Coordinate coordinate);

    public abstract void set(Coordinate c, T value);

    public abstract int xLength();

    public abstract int yLength();

    public Optional<Coordinate> findAny(T value) {
        for (int y = 0; y < yLength(); y++) {
            for (int x = 0; x < xLength(); x++) {
                T valueInCollection = get(new Coordinate(x, y));
                if (valueInCollection.equals(value)) {
                    return Optional.of(new Coordinate(x, y));
                }
            }
        }
        return Optional.empty();
    }

    public List<Coordinate> findAll(T value) {
        List<Coordinate> result = new LinkedList<>();
        for (int y = 0; y < yLength(); y++) {
            for (int x = 0; x < xLength(); x++) {
                T valueInCollection = get(new Coordinate(x, y));
                if (valueInCollection.equals(value)) {
                    result.add(new Coordinate(x, y));
                }
            }
        }
        return result;
    }

}

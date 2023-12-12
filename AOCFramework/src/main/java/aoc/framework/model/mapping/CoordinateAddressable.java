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

    public abstract void insertRow(int y, T value);

    public abstract void insertColumn(int x, T value);

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

    public List<Integer> findEmptyRows(Predicate<T> isEmpty) {
        List<Integer> result = new LinkedList<>();
        for (int y = 0; y < yLength(); y++) {

            int finalY = y;
            boolean rowIsEmpty = MathUtils.range(xLength() - 1L).stream()
                    .map(x -> get(new Coordinate(x.intValue(), finalY)))
                    .allMatch(isEmpty);
            if (rowIsEmpty) {
                result.add(y);
            }

        }
        return result;
    }

    public List<Integer> findEmptyColumns(Predicate<T> isEmpty) {
        List<Integer> result = new LinkedList<>();
        for (int x = 0; x < yLength(); x++) {

            int finalX = x;
            boolean columnIsEmpty = MathUtils.range(yLength() - 1L).stream()
                    .map(y -> get(new Coordinate(finalX, y.intValue())))
                    .allMatch(isEmpty);
            if (columnIsEmpty) {
                result.add(x);
            }

        }
        return result;
    }

}

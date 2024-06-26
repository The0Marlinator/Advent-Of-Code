package aoc.framework.model.mapping;

import aoc.framework.exception.AOCException;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CoordinateMap<T> extends CoordinateAddressable<T> {

    private final List<List<T>> data;

    public CoordinateMap(List<List<T>> dataInput) {
        data = dataInput;
    }

    public CoordinateMap(CoordinateAddressable<T> other) {
        data = new LinkedList<>();
        for (int x = 0; x < other.xLength(); x++) {
            data.add(x, new LinkedList<>());
            for (int y = 0; y < other.yLength(); y++) {
                data.get(x).add(other.get(new Coordinate(x, y)));
            }
        }
    }

    @Override
    public T get(Coordinate location) {
        return data.get((int) location.x()).get((int) location.y());
    }

    @Override
    public void set(Coordinate c, T value) {
        data.get((int) c.x()).set((int) c.y(), value);
    }

    public boolean isLegalCoordinate(Coordinate c) {
        return c.x() >= 0 && c.y() >= 0 && c.x() < data.size() && c.y() < data.getFirst().size();
    }

    @Override
    public int xLength() {
        return data.size();
    }

    @Override
    public int yLength() {
        return data.getFirst().size();
    }

    public void floodFill(Coordinate start, Predicate<Coordinate> isBoundary, T fillType) {
        Queue<Coordinate> toFill = new ArrayDeque<>();
        Set<Coordinate> visited = new HashSet<>();
        toFill.add(start);
        while (!toFill.isEmpty()) {
            Coordinate current = toFill.poll();
            if (!visited.contains(current) && Boolean.FALSE.equals(isBoundary.test(current)) && !get(current).equals(fillType)) {
                set(current, fillType);

                visited.add(current);

                toFill.addAll(findAllSurrounding(current)
                        .filter(Predicate.not(visited::contains))
                        .toList()
                );
            }
        }
    }

    public CoordinateMap<T> transpose() {
        List<List<T>> newData = new LinkedList<>();

        for (int y = 0; y < yLength(); y++) {
            newData.add(new LinkedList<>());
            for (int x = 0; x < xLength(); x++) {
                newData.get(y).add(data.get(x).get(y));
            }
        }
        return new CoordinateMap<>(newData);
    }

    public Stream<Coordinate> findAllSurrounding(Coordinate c) {
        return c.getAllSurrounding(c)
                .stream()
                .filter(this::isLegalCoordinate);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < yLength(); i++) {
            StringBuilder xResult = new StringBuilder();
            for (int j = 0; j < xLength(); j++) {
                xResult.append(data.get(j).get(i)).append(" ");
            }
            result.append(i).append(" [").append(xResult).append("]\n");
        }
        return result.toString();
    }
}

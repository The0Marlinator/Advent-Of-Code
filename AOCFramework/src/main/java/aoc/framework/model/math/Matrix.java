package aoc.framework.model.math;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Matrix<T> {

    private final T[][] data;

    public Matrix(T[][] dataInput) {
        data = dataInput;
    }

    public T get(Coordinate location) {
        return data[location.x()][location.y()];
    }

    public void setValue(Coordinate c, T value) {
        data[c.x()][c.y()] = value;
    }

    public Optional<Coordinate> findAny(T value) {
        for (int y = 0; y < data[0].length; y++) {
            for (int x = 0; x < data.length; x++) {
                if (data[x][y].equals(value)) {
                    return Optional.of(new Coordinate(x, y));
                }
            }
        }
        return Optional.empty();
    }

    public List<Coordinate> findAll(T value) {
        List<Coordinate> result = new LinkedList<>();
        for (int y = 0; y < data[0].length; y++) {
            for (int x = 0; x < data.length; x++) {
                if (data[x][y].equals(value)) {
                    result.add(new Coordinate(x, y));
                }
            }
        }
        return result;
    }

    public boolean isLegalCoordinate(Coordinate c) {
        return c.x() >= 0 && c.y() >= 0 && c.x() < data.length && c.y() < data[0].length;
    }

    public int xLength() {
        return data.length;
    }
    public int yLength() {
        return data[0].length;
    }
    public T[][] getData() {
        return data;
    }

    public void floodFill(Coordinate start, Predicate<Coordinate> isBoundary, T fillType) {
        Queue<Coordinate> toFill = new ArrayDeque<>();
        Set<Coordinate> visited = new HashSet<>();
        toFill.add(start);
        while (!toFill.isEmpty()) {
            Coordinate current = toFill.poll();
            if(!visited.contains(current) && Boolean.FALSE.equals(isBoundary.test(current))) {
                data[current.x()][current.y()] = fillType;

                visited.add(current);

                toFill.addAll(findAllSurrounding(current)
                        .filter(Predicate.not(visited::contains))
                        .toList()
                );
            }
        }
    }

    public Stream<Coordinate> findAllSurrounding(Coordinate c) {
        return c.getAllSurrounding(c)
                .stream()
                .filter(this::isLegalCoordinate);
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for(int i = 0; i <yLength(); i++) {
            StringBuilder xResult = new StringBuilder();
            for(int j = 0; j<xLength(); j++) {
                xResult.append(data[j][i]).append(" ");
            }
            result.append(i).append(" [").append(xResult).append("]\n");
        }
        return result.toString();
    }
}

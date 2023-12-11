package aoc.framework.model.math;

import java.util.*;
import java.util.function.Predicate;

public class Matrix<T> {

    private T[][] data;

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

    public void floodFill(Coordinate start, Predicate<Coordinate> isBoundary, T fillType) {
        Queue<Coordinate> toFill = new ArrayDeque<>();
        Set<Coordinate> visited = new HashSet<>();
        toFill.add(start);
        while (!toFill.isEmpty()) {
            Coordinate current = toFill.poll();
            if(!visited.contains(current) && Boolean.FALSE.equals(isBoundary.test(current))) {
                data[current.x()][current.y()] = fillType;
                visited.add(current);
                for(int i = -1; i<2; i++ ){
                    for(int j = -1; j<2; j++) {
                        Coordinate future = new Coordinate(current.x()+i, current.y()+j);
                        if(isLegalCoordinate(future)) {
                            toFill.add(future);
                        }
                    }
                }
            }
        }
    }

    public T[][] getData() {
        return data;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("   ");
        for(int j = 0; j<xLength(); j++) {
            result.append(j).append(" ");
        }
        result.append("\n");

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

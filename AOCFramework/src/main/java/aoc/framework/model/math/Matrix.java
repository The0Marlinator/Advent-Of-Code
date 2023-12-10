package aoc.framework.model.math;

import java.util.Optional;

public record Matrix<T>(T[][] data) {
    public T get(Coordinate location) {
        return data[location.x()][location.y()];
    }

    public Optional<Coordinate> findLocationOfValue(T value) {
        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data.length; x++) {
                if (data[x][y].equals(value)) {
                    return Optional.of(new Coordinate(x, y));
                }
            }
        }
        return Optional.empty();
    }

    public boolean isLegalCoordinate(Coordinate c) {
        return c.x() >= 0 && c.y() >= 0 && c.y() < data[0].length && c.x() < data.length;
    }

    public int xLength() {
        return data.length;
    }
}

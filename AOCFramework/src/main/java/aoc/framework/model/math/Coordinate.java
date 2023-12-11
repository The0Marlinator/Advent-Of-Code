package aoc.framework.model.math;

import aoc.framework.util.MathUtils;

import java.util.List;
import java.util.Map;

public record Coordinate(int x, int y) {

    public boolean isAbove(Coordinate other) {
        return y < other.y;
    }

    public boolean isBelow(Coordinate other) {
        return y > other.y;
    }

    public boolean isLeft(Coordinate other) {
        return x < other.x;
    }

    public boolean isRight(Coordinate other) {
        return x > other.x;
    }

    public boolean isDiagonalTopLeft(Coordinate other) {
        return isAbove(other) && isLeft(other);
    }

    public boolean isDiagonalBottomLeft(Coordinate other) {
        return isBelow(other) && isLeft(other);
    }

    public boolean isDiagonalTopRight(Coordinate other) {
        return isAbove(other) && isRight(other);
    }

    public boolean isDiagonalBottomRight(Coordinate other) {
        return isBelow(other) && isRight(other);
    }

    public List<Coordinate> getCoordinatesBetweenX(Coordinate other) {
        return MathUtils.range(x, other.x).stream()
                .map(x -> new Coordinate(x.intValue(), y))
                .toList();
    }
}

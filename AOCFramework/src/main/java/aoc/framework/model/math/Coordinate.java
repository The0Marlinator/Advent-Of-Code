package aoc.framework.model.math;

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
}

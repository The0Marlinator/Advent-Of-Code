package aoc.framework.model.math;

import java.util.LinkedList;
import java.util.List;

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

    public List<Coordinate> getAllSurrounding(Coordinate current) {
        List<Coordinate> result = new LinkedList<>();
        for (int xDt = -1; xDt <= 1; xDt++) {
            for (int yDt = -1; yDt <= 1; yDt++) {
                Coordinate surroundingCoordinate = new Coordinate(current.x + xDt, current.y + yDt);
                if(!current.equals(surroundingCoordinate)){
                    result.add(surroundingCoordinate);
                }

            }
        }
        return result;
    }
}

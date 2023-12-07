package aoc.framework.model;

import aoc.framework.util.MathUtils;

import java.util.List;

public class Range {

    public static Range EMPTY_RANGE = new Range(0,0);

    long floor;
    long ceiling;

    public Range(long floor, long ceiling) {
        if(floor > ceiling) {
            throw new IllegalArgumentException("Cannot create Range with smaller floor is greater that the ceiling");
        }
        this.floor = floor;
        this.ceiling = ceiling;
    }
    public Range(long floor) {
        this.floor = floor;
        this.ceiling = floor;
    }

    public long getFloor() {
        return floor;
    }

    public long getCeiling() {
        return ceiling;
    }

    public long length() {
        return ceiling - floor;
    }

    public List<Long> getAllNumbersInRange() {
        return MathUtils.range(floor, ceiling);
    }

    public boolean inRange(Long value) {
        return value>=floor && value<=ceiling;
    }

    public boolean isEmpty() {
        return ceiling == floor;
    }

    public Range overlapping(Range other) {
        if(other.ceiling < this.floor || other.floor > this.ceiling){
            return EMPTY_RANGE;
        }
        if(other.floor >= this.floor) {
            if (other.ceiling <= this.ceiling) {
                return new Range(other.floor, other.ceiling);
            } else {
                return new Range(other.floor, this.ceiling);
            }
        } else {
            if(other.ceiling <= this.ceiling) {
                return new Range(this.floor, other.ceiling);
            } else {
                return new Range(this.floor, this.ceiling);
            }
        }
    }
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        return String.format("[%s -> %s]", floor, ceiling);
    }

    public boolean isInRange(long value) {
        return value>=floor && value<=ceiling;
    }
}

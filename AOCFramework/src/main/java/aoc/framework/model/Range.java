package aoc.framework.model;

import java.util.ArrayList;
import java.util.List;

public class Range {

    private static final Range EMPTY_RANGE = new Range(0,0);

    long floor;
    long ceiling;

    public Range(long floor, long ceiling) {
        if(floor > ceiling) {
            throw new IllegalArgumentException("Cannot create Range with smaller floor is greater that the ceiling");
        }
        this.floor = floor;
        this.ceiling = ceiling;
    }

    public long getFloor() {
        return floor;
    }

    public long getCeiling() {
        return ceiling;
    }


    public boolean inRange(Long value) {
        return value>=floor && value<=ceiling;
    }

    public boolean isEmpty() {
        return ceiling == floor;
    }

    public Range getOverlapping(Range other) {
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

    public List<Range> getNonOverlapping(Range other){
        List<Range> result = new ArrayList<>();

        Range overlapping = getOverlapping(other);
        if(!overlapping.isEmpty()) {
            if (floor < overlapping.floor) {
                result.add(new Range(floor, overlapping.floor - 1));
            }
            if (ceiling > overlapping.ceiling) {
                result.add(new Range(overlapping.ceiling + 1, ceiling));
            }
        }
        return result;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        return String.format("[%s -> %s]", floor, ceiling);
    }

}

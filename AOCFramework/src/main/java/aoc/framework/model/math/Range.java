package aoc.framework.model.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record Range(long floor, long ceiling) {

    public Range {
        if (floor > ceiling) {
            throw new IllegalArgumentException("Cannot create Range with smaller floor is greater that the ceiling");
        }
    }

    public long getFloor() {
        return floor;
    }

    public long getCeiling() {
        return ceiling;
    }


    public boolean inRange(Long value) {
        return value >= floor && value <= ceiling;
    }

    public boolean isEmpty() {
        return ceiling == floor;
    }

    public Optional<Range> getOverlapping(Range other) {
        if (other.ceiling < this.floor || other.floor > this.ceiling) {
            return Optional.empty();
        }
        if (other.floor >= this.floor) {
            if (other.ceiling <= this.ceiling) {
                return Optional.of(new Range(other.floor, other.ceiling));
            } else {
                return Optional.of(new Range(other.floor, this.ceiling));
            }
        } else {
            if (other.ceiling <= this.ceiling) {
                return Optional.of(new Range(this.floor, other.ceiling));
            } else {
                return Optional.of(new Range(this.floor, this.ceiling));
            }
        }
    }

    public List<Range> getNonOverlapping(Range other) {
        List<Range> result = new ArrayList<>();

        Optional<Range> overlapping = getOverlapping(other);
        if (overlapping.isPresent()) {
            if (floor < overlapping.get().floor) {
                result.add(new Range(floor, overlapping.get().floor - 1));
            }
            //if (other.floor < overlapping.get().floor) {
            //    result.add(new Range(other.floor, overlapping.get().floor - 1));
            //}
            if (ceiling > overlapping.get().ceiling) {
                result.add(new Range(overlapping.get().ceiling + 1, ceiling));
            }
            //if (other.ceiling > overlapping.get().ceiling) {
            //    result.add(new Range(overlapping.get().ceiling + 1, other.ceiling));
            //}
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

    @Override
    public boolean equals(Object other) {
        return other instanceof Range range
                && range.ceiling == ceiling
                && range.floor == floor;
    }


}

package model.math;

import aoc.framework.model.math.Range;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class RangeTest {

    @Test
    void testEqualsWhenEqual() {
        Range first = new Range(1, 10);
        Range second = new Range(1, 10);

        boolean result = first.equals(second);

        assertThat(result).isTrue();
    }

    @Test
    void testEqualsWhenNotEqual() {
        Range first = new Range(1, 10);
        Range second = new Range(5, 10);

        boolean result = first.equals(second);

        assertThat(result).isFalse();
    }

    @Test
    void testGetOverlappingWhenOverlappingLeft() {
        Range first = new Range(1, 10);
        Range second = new Range(5, 15);
        Range expectedResult = new Range(5, 10);

        Optional<Range> result = first.getOverlapping(second);

        assertThat(result).contains(expectedResult);
    }

    @Test
    void testGetOverlappingWhenOverlappingRight() {
        Range first = new Range(1, 10);
        Range second = new Range(5, 15);
        Range expectedResult = new Range(5, 10);

        Optional<Range> result = second.getOverlapping(first);

        assertThat(result).contains(expectedResult);
    }

    @Test
    void testGetOverlappingWhenExactOverlap() {
        Range first = new Range(1, 10);
        Range second = new Range(1, 10);

        Optional<Range> result = first.getOverlapping(second);

        assertThat(result).contains(first);

    }

    @Test
    void testGetOverlappingWhenFirstIsSubRange() {
        Range first = new Range(5, 10);
        Range second = new Range(1, 15);
        Range expectedResult = new Range(5, 10);

        Optional<Range> result = first.getOverlapping(second);

        assertThat(result).contains(expectedResult);

    }

    @Test
    void testGetOverlappingWhenNotOverlapping() {
        Range first = new Range(1, 10);
        Range second = new Range(15, 20);

        Optional<Range> result = first.getOverlapping(second);

        assertThat(result).isEmpty();

    }

    @Test
    void getNonOverlappingWhenOverlapRight() {
        Range first = new Range(1, 10);
        Range second = new Range(5, 15);
        List<Range> expectedResult = List.of(new Range(1, 4));

        List<Range> result = first.getNonOverlapping(second);

        assertThat(result).hasSameElementsAs(expectedResult);
    }

    @Test
    void getNonOverlappingWhenOverlapLeft() {
        Range first = new Range(1, 10);
        Range second = new Range(5, 15);
        List<Range> expectedResult = List.of(new Range(11, 15));

        List<Range> result = second.getNonOverlapping(first);

        assertThat(result).hasSameElementsAs(expectedResult);
    }

    @Test
    void getNonOverlappingExactOverlap() {
        Range first = new Range(1, 10);
        Range second = new Range(1, 10);

        List<Range> result = second.getNonOverlapping(first);

        assertThat(result).isEmpty();
    }

    @Test
    void getNonOverlappingFirstIsSubRange() {
        Range first = new Range(5, 10);
        Range second = new Range(1, 15);
        List<Range> expectedResult = List.of(new Range(1, 4), new Range(11, 15));

        List<Range> result = second.getNonOverlapping(first);

        assertThat(result).hasSameElementsAs(expectedResult);
    }

    @Test
    void getNonOverlappingSecondIsSubRange() {
        Range first = new Range(1, 15);
        Range second = new Range(5, 10);
        List<Range> expectedResult = List.of(new Range(1, 4), new Range(11, 15));

        List<Range> result = second.getNonOverlapping(first);

        assertThat(result).isEmpty();
    }
}

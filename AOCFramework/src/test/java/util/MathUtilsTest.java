package util;

import aoc.framework.util.MathUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class MathUtilsTest {
    
    @Test
    void testRangeWithBothBoundaries() {
        List<Long> expectedResult = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);

        assertThat(MathUtils.range(1L, 10L)).isEqualTo(expectedResult);
    }

    @Test
    void testRangeWithOnlyCeiling() {
        List<Long> expectedResult = List.of(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);

        assertThat(MathUtils.range(10L)).isEqualTo(expectedResult);
    }

    
}

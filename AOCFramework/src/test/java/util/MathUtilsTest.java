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
    void testGetPrimeFactors12() {
        Map<Long, Long> expectedPrimeFactorsMapForTwelve = new HashMap<>();
        expectedPrimeFactorsMapForTwelve.put(2L, 2L);
        expectedPrimeFactorsMapForTwelve.put(3L, 1L);

        assertThat(MathUtils.getPrimeFactors(12L)).containsAllEntriesOf(expectedPrimeFactorsMapForTwelve);
    }

    @Test
    void testGetPrimeFactors18() {
        Map<Long, Long> expectedPrimeFactorsMapForEighteen = new HashMap<>();
        expectedPrimeFactorsMapForEighteen.put(2L, 1L);
        expectedPrimeFactorsMapForEighteen.put(3L, 2L);
        assertThat(MathUtils.getPrimeFactors(18L)).containsAllEntriesOf(expectedPrimeFactorsMapForEighteen);
    }

    @Test
    void testLCM() {
        assertThat(MathUtils.lcm(List.of(12L, 18L))).isEqualTo(36L);
    }

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

    @Test
    void testSetUnion() {
        Set<Long> first = Set.of(1L, 2L, 3L);
        Set<Long> second = Set.of(3L, 4L, 5L);
        Set<Long> result = Set.of(1L, 2L, 3L, 4L, 5L);

        assertThat(MathUtils.union(first, second)).containsExactlyInAnyOrderElementsOf(result);

    }
    
}

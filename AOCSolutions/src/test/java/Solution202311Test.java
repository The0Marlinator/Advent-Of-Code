import aoc.framework.exception.AOCException;
import aoc.framework.solution.AOCSolution;
import aoc.framework.util.StringUtils;
import aoc.solutions.Solution202311;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Solution202311Test {

    @Test
    void testPart1Example1() throws AOCException {
        List<String> input = StringUtils.splitStringAroundNewLine("""
                ...#......
                .......#..
                #.........
                ..........
                ......#...
                .#........
                .........#
                ..........
                .......#..
                #...#.....
                """)
                .asList();

        AOCSolution solution = new Solution202311(true, input);

        assertThat(solution.solvePart1()).isEqualTo("374");
    }

    @Test
    void testPart2Example1Times10() throws AOCException {
        List<String> input = StringUtils.splitStringAroundNewLine("""
                ...#......
                .......#..
                #.........
                ..........
                ......#...
                .#........
                .........#
                ..........
                .......#..
                #...#.....
                """)
                .asList();

        Solution202311 solution = new Solution202311(true, input);

        assertThat(solution.solvePart2(10)).isEqualTo("1030");
    }

    @Test
    void testPart2Example1Times100() throws AOCException {
        List<String> input = StringUtils.splitStringAroundNewLine("""
                ...#......
                .......#..
                #.........
                ..........
                ......#...
                .#........
                .........#
                ..........
                .......#..
                #...#.....
                """)
                .asList();

        Solution202311 solution = new Solution202311(true, input);

        assertThat(solution.solvePart2(100)).isEqualTo("8410");
    }
}

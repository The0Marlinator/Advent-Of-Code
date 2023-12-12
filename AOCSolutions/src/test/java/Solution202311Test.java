import aoc.framework.exception.AOCException;
import aoc.framework.solution.AOCSolution;
import aoc.framework.util.StringUtils;
import aoc.solutions.Solution202311;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Solution202311Test {

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
}

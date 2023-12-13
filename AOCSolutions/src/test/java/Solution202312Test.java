import aoc.framework.exception.AOCException;
import aoc.framework.solution.AOCSolution;
import aoc.framework.util.StringUtils;
import aoc.solutions.Solution202312;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Solution202312Test {

    @Test
    void TestPart1() throws AOCException {
        List<String> input = StringUtils.splitStringAroundNewLine(
                        """
                                ???.### 1,1,3
                                .??..??...?##. 1,1,3
                                ?#?#?#?#?#?#?#? 1,3,1,6
                                ????.#...#... 4,1,1
                                ????.######..#####. 1,6,5
                                ?###???????? 3,2,1
                                """)
                .asList();

        AOCSolution solution = new Solution202312(true, input);
        assertThat(solution.solvePart1()).contains("21");
    }

}

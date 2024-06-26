import aoc.framework.exception.AOCException;
import aoc.framework.solution.AOCSolution;
import aoc.framework.util.StringUtils;
import aoc.solutions.Solution202310;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Solution202310Test {

    @Test
    void testExample1() throws AOCException {
        List<String> input = StringUtils.splitStringAroundNewLine("""
                        .....
                        .S-7.
                        .|.|.
                        .L-J.
                        .....""")
                .asList();

        AOCSolution solution = new Solution202310(false, input);

        assertThat(solution.solvePart1()).isEqualTo("4");
    }

    @Test
    void testExample1MoreComplicated() throws AOCException {
        List<String> input = StringUtils.splitStringAroundNewLine("""
                        ..F7.
                        .FJ|.
                        SJ.L7
                        |F--J
                        LJ...""")
                .asList();


        AOCSolution solution = new Solution202310(false, input);

        assertThat(solution.solvePart1()).isEqualTo("8");
    }

    @Test
    void testPart2SimpleExample() throws AOCException {
        List<String> input = StringUtils.splitStringAroundNewLine("""
                        ...........
                        .S-------7.
                        .|F-----7|.
                        .||.....||.
                        .||.....||.
                        .|L-7.F-J|.
                        .|..|.|..|.
                        .L--J.L--J.
                        ...........""")
                .asList();


        AOCSolution solution = new Solution202310(false, input);

        assertThat(solution.solvePart2()).contains("4");
    }


    @Test
    void testPart2ComplicatedExample() throws AOCException {
        List<String> input = StringUtils.splitStringAroundNewLine("""
                        .F----7F7F7F7F-7....
                        .|F--7||||||||FJ....
                        .||.FJ||||||||L7....
                        FJL7L7LJLJ||LJ.L-7..
                        L--J.L7...LJS7F-7L7.
                        ....F-J..F7FJ|L7L7L7
                        ....L7.F7||L7|.L7L7|
                        .....|FJLJ|FJ|F7|.LJ
                        ....FJL-7.||.||||...
                        ....L---J.LJ.LJLJ...""")
                .asList();

        AOCSolution solution = new Solution202310(false, input);

        assertThat(solution.solvePart2()).contains("8");
    }

    @Test
    void testPart2ComplicatedExample2() throws AOCException {
        List<String> input = StringUtils.splitStringAroundNewLine("""
                        FF7FSF7F7F7F7F7F---7
                        L|LJ||||||||||||F--J
                        FL-7LJLJ||||||LJL-77
                        F--JF--7||LJLJ7F7FJ-
                        L---JF-JLJ.||-FJLJJ7
                        |F|F-JF---7F7-L7L|7|
                        |FFJF7L7F-JF7|JL---7
                        7-L-JL7||F7|L7F-7F7|
                        L.L7LFJ|||||FJL7||LJ
                        L7JLJL-JLJLJL--JLJ.L""")
                .asList();

        AOCSolution solution = new Solution202310(false, input);

        assertThat(solution.solvePart2()).contains("10");
    }
}

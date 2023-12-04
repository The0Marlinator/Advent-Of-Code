package aoc;

import aoc.days.Day01;
import aoc.days.Day02;
import aoc.days.Day03;
import aoc.days.Day04;

public class AdventOfCode2022Solutions {
    public static void main(String[] args) {
        try {
            AdventOfCode2022Solutions.solveAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private static void solveAll() throws Exception {
        System.out.println("Advent of Code 2022 solutions:");
        System.out.println(" > Solution 1 to day 01: " + Day01.solvePart1());
        System.out.println(" > Solution 2 to day 01: " + Day01.solvePart2());
        System.out.println(" > Solution 1 to day 02: " + Day02.solvePart1());
        System.out.println(" > Solution 2 to day 02: " + Day02.solvePart2());
        System.out.println(" > Solution 1 to day 03: " + Day03.solvePart1());
        System.out.println(" > Solution 2 to day 03: " + Day03.solvePart2());
        System.out.println(" > Solution 1 to day 04: " + Day04.solvePart1());
        System.out.println(" > Solution 2 to day 04: " + Day04.solvePart2());
    }
}

package aoc;

import aoc.days.Day01;
import aoc.days.Day02;
import aoc.days.Day03;
import aoc.days.Day04;
import aoc.factory.DaySolutionFactory;
import aoc.framework.Day;

import java.util.ArrayList;
import java.util.List;

public class AdventOfCode2022Solutions {

    public static void main(String[] args) {
        try {
            AdventOfCode2022Solutions.solveAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private static void solveAll() {
        System.out.println("Advent of Code 2022 solutions:");

        DaySolutionFactory.createDaySolutions()
                .forEach(Day::runAndPrintOutput);

    }
}

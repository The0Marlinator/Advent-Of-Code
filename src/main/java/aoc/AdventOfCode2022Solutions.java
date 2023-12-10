package aoc;

import aoc.factory.DaySolutionFactory;
import aoc.framework.exception.AOCException;
import aoc.framework.solution.AOCSolution;
import aoc.framework.solution.Solution;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class AdventOfCode2022Solutions {

    public static void main(String[] args) {
        try {
            AdventOfCode2022Solutions.solveAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private static void solveAll() throws AOCException {
        for (int year : getAllYears()) {
            System.out.printf("Solutions for %s Advent Of Code %n", year);
            List<? extends AOCSolution> days = getAllSolutionsForYear(year);
            for (int i = 0; i < days.size(); i++) {
                System.out.printf("\t >Day %s %n", days.get(i).getClass().getAnnotation(Solution.class).day());
                long startTime = System.currentTimeMillis();
                String solutionPart1 = days.get(i).solvePart1();
                long endTime = System.currentTimeMillis();

                System.out.printf("\t\t> Part 1: %s Runtime: %sms \n", solutionPart1, endTime - startTime);

                startTime = System.currentTimeMillis();
                String solutionPart2 = days.get(i).solvePart2();
                endTime = System.currentTimeMillis();

                System.out.printf("\t\t> Part 2: %s Runtime: %sms \n", solutionPart2, endTime - startTime);
            }
        }
    }

    private static List<Integer> getAllYears() {
        return DaySolutionFactory.createDaySolutions().stream()
                .filter(day -> Objects.nonNull(day.getClass().getAnnotation(Solution.class)))
                .map(day -> day.getClass().getAnnotation(Solution.class).year())
                .distinct()
                .sorted()
                .toList();
    }

    private static List<? extends AOCSolution> getAllSolutionsForYear(int year) {
        return DaySolutionFactory.createDaySolutions().stream()
                .filter(day -> day.getClass().getAnnotation(Solution.class) != null)
                .filter(day -> day.getClass().getAnnotation(Solution.class).year() == year)
                .sorted(Comparator.comparingInt((AOCSolution o) -> o.getClass().getAnnotation(Solution.class).day()))
                .toList();
    }

}

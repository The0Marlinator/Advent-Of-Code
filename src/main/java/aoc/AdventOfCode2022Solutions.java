package aoc;

import aoc.factory.DaySolutionFactory;
import aoc.framework.exception.AOCException;
import aoc.framework.solution.AOCSolution;
import aoc.framework.solution.Solution;

import java.lang.reflect.InvocationTargetException;
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

    /**
     * Runs all advent of code solutions
     *
     * @throws AOCException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private static void solveAll() throws AOCException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (int year : getAllYears()) {
            System.out.printf("Solutions for %s Advent Of Code %n", year);
            List<Class<? extends AOCSolution>> days = getAllSolutionsForYear(year);
            for (Class<? extends AOCSolution> day : days) {
                System.out.printf("\t >Day %s %n", day.getAnnotation(Solution.class).day());

                AOCSolution currentSolution = day.getConstructor(boolean.class).newInstance(false);
                long startTime = System.currentTimeMillis();
                String solutionPart1 = currentSolution.solvePart1();
                long endTime = System.currentTimeMillis();

                System.out.printf("\t\t> Part 1: %s Runtime: %sms \n", solutionPart1, endTime - startTime);

                startTime = System.currentTimeMillis();
                String solutionPart2 = currentSolution.solvePart2();
                endTime = System.currentTimeMillis();

                System.out.printf("\t\t> Part 2: %s Runtime: %sms \n", solutionPart2, endTime - startTime);
            }
        }
    }

    private static List<Integer> getAllYears() {
        return DaySolutionFactory.createDaySolutions().stream()
                .filter(day -> Objects.nonNull(day.getAnnotation(Solution.class)))
                .map(day -> day.getAnnotation(Solution.class).year())
                .distinct()
                .sorted()
                .toList();
    }

    private static List<Class<? extends AOCSolution>> getAllSolutionsForYear(int year) {
        return DaySolutionFactory.createDaySolutions().stream()
                .filter(day -> day.getAnnotation(Solution.class) != null)
                .filter(day -> day.getAnnotation(Solution.class).year() == year)
                .sorted(Comparator.comparingInt((Class<? extends AOCSolution> o) -> o.getAnnotation(Solution.class).day()))
                .toList();
    }

}

package aoc;

import aoc.factory.DaySolutionFactory;
import aoc.framework.exception.AOCException;
import aoc.framework.solution.AOCSolution;
import aoc.framework.solution.Solution;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class AdventOfCode2022Solutions {

    public static void main(String[] args) {
        try {

            if (Arrays.asList(args).contains("BATCH_MODE")) {
                AdventOfCode2022Solutions.solveAll();
            } else {
                AdventOfCode2022Solutions.solveNewest();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private static void solveNewest() throws AOCException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Optional<Integer> highestYear = getAllYears().stream().max(Comparator.naturalOrder())
                .stream()
                .findAny();

        if (highestYear.isEmpty()) {
            System.out.println("No years found in the solution!");
            return;
        }
        Optional<Class<? extends AOCSolution>> currentSolution = getAllSolutionsForYear(highestYear.get()).stream()
                .max(Comparator.comparingInt((Class<? extends AOCSolution> o) -> o.getAnnotation(Solution.class).day()));

        if (currentSolution.isEmpty()) {
            System.out.println("No days found in the newest year!");
            return;
        }

        System.out.printf("Running Solution: Year: %s, Day %s %n",
                currentSolution.get().getAnnotation(Solution.class).year(),
                currentSolution.get().getAnnotation(Solution.class).day()
        );
        runSolution(currentSolution.get());
    }

    /**
     * Runs all advent of code solutions*
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
               runSolution(day);
            }
        }
    }

    private static void runSolution(Class<? extends AOCSolution> currentSolution) throws AOCException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        AOCSolution currentSolutionInstance = currentSolution.getConstructor(boolean.class).newInstance(false);
        long startTime = System.currentTimeMillis();
        String solutionPart1 = currentSolutionInstance.solvePart1();
        long endTime = System.currentTimeMillis();

        System.out.printf("\t\t> Part 1: %s Runtime: %sms \n", solutionPart1, endTime - startTime);

        startTime = System.currentTimeMillis();
        String solutionPart2 = currentSolutionInstance.solvePart2();
        endTime = System.currentTimeMillis();

        System.out.printf("\t\t> Part 2: %s Runtime: %sms \n", solutionPart2, endTime - startTime);
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

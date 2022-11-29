package aoc.days;

import aoc.util.AocUtil;

public class Day01 {

    private static final String DAY_INPUT_FILE = "./day01/input.txt";

    private static int[] sonar;

    private static void init() throws Exception {
        sonar = AocUtil.readFileToIntArray(DAY_INPUT_FILE);
    }

    public static String solvePart1() throws Exception {
        init();
        int counter = 0;

        for (int i = 0; i < sonar.length - 1; i++) {
            if (sonar[i + 1] - sonar[i] > 0) {
                counter++;
            }
        }
        return Integer.toString(counter);
    }

    public static String solvePart2() throws Exception {
        init();
        int counter = 0;

        for (int i = 0; i < sonar.length - 3; i++) {
            if (sonar[i + 3] - sonar[i] > 0) {
                counter++;
            }
        }
        return Integer.toString(counter);
    }
}
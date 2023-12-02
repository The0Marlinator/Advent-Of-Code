package aoc.days;

import aoc.util.AocUtil;

import java.util.HashMap;
import java.util.List;

public class Day02 {

    private static final String DAY_INPUT_FILE = "day02/input.txt";
    private static final HashMap<Color, Integer> CUBE_COUNTS = new HashMap<>() {{
        put(Color.RED, 12);
        put(Color.GREEN, 13);
        put(Color.BLUE, 14);
    }};
    private static boolean printOutput = false;
    private static List<String> sonar;

    private static void init(boolean printOutputParam) throws Exception {
        printOutput = printOutputParam;
        sonar = AocUtil.readFileToStrings(DAY_INPUT_FILE);
    }

    private static HashMap<Color, Integer> getCubeColorCountsFromGame(String game) {
        HashMap<Color, Integer> colorCountHashmap = new HashMap<>();

        String[] cubes = game.split("Game \\d.*: |, |; ");

        for (String s : cubes) {
            if (s.isEmpty()) {
                continue;
            }
            String[] split = s.split(" ");

            Color parsedColor = Color.valueOf(split[1].toUpperCase());

            Integer count = Integer.parseInt(split[0]);

            if (colorCountHashmap.containsKey(parsedColor)) {
                if (colorCountHashmap.get(parsedColor).compareTo(count) < 0)
                    colorCountHashmap.put(parsedColor, count);
            } else {
                colorCountHashmap.put(parsedColor, Integer.parseInt(split[0]));
            }

        }

        if (printOutput) {
            System.out.printf("Input: %s, Result: %s\n", game, colorCountHashmap);
        }

        return colorCountHashmap;
    }


    private static Integer getIdFromGame(String game) {
        String id = game.split("Game |: ")[1];
        return Integer.parseInt(id);
    }


    private static Integer getIdIfPossibleOr0(String game) {
        String[] cubes = game.split("Game \\d.*: |, |; ");
        Integer id = getIdFromGame(game);

        for (String cube : cubes) {

            if (cube.isEmpty()) {
                continue;
            }

            String[] split = cube.split(" ");
            Color parsedColor = Color.valueOf(split[1].toUpperCase());

            Integer maxCountForColor = CUBE_COUNTS.get(parsedColor);
            Integer countedColor = Integer.parseInt(split[0]);

            if (!(maxCountForColor.compareTo(countedColor) >= 0)) {
                return 0;
            }
        }

        return id;
    }

    public static Integer solvePart1() throws Exception {
        return solvePart1(false);
    }

    public static Integer solvePart1(boolean printOutput) throws Exception {
        init(printOutput);
        return sonar.stream()
                .map(Day02::getIdIfPossibleOr0)
                .reduce(Integer::sum)
                .orElseThrow(() -> new Exception("No Result Obtained"));
    }

    private static Integer multiplyCubesTogether(HashMap<Color, Integer> counts) {
        return counts.values().stream()
                .reduce(Math::multiplyExact)
                .orElse(0);
    }

    public static Integer solvePart2() throws Exception {
        return solvePart2(false);
    }

    public static Integer solvePart2(boolean printOutput) throws Exception {
        init(printOutput);
        return sonar.stream()
                .map(Day02::getCubeColorCountsFromGame)
                .map(Day02::multiplyCubesTogether)
                .reduce(Integer::sum)
                .orElseThrow(() -> new Exception("Unable to parse File"));
    }


    private enum Color {RED, GREEN, BLUE}


}

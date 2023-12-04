package aoc.days;

import aoc.framework.Day;
import aoc.framework.DaySolution;

import java.util.HashMap;

@DaySolution(day = 2)
public class Day02 extends Day {

    private static final String DAY_INPUT_FILE = "day02/input.txt";
    private static final HashMap<Color, Integer> CUBE_COUNTS = new HashMap<>() {{
        put(Color.RED, 12);
        put(Color.GREEN, 13);
        put(Color.BLUE, 14);
    }};

    private enum Color {RED, GREEN, BLUE}

    public Day02(boolean printOutput) throws RuntimeException {
        super(printOutput, DAY_INPUT_FILE, 2);
    }

    @Override
    protected String solvePart1() {
        return parsedInput.stream()
                .map(this::getIdIfPossibleOr0)
                .reduce(Integer::sum)
                .orElseThrow(() -> new RuntimeException("No Result Obtained"))
                .toString();
    }

    @Override
    protected String solvePart2() {
        return parsedInput.stream()
                .map(this::getCubeColorCountsFromGame)
                .map(this::multiplyCubesTogether)
                .reduce(Integer::sum)
                .orElseThrow(() -> new RuntimeException("Unable to parse File"))
                .toString();
    }

    private HashMap<Color, Integer> getCubeColorCountsFromGame(String game) {
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

        printToOutput(String.format("Input: %s, Result: %s %n", game, colorCountHashmap));

        return colorCountHashmap;
    }


    private Integer getIdFromGame(String game) {
        String id = game.split("Game |: ")[1];
        return Integer.parseInt(id);
    }


    private Integer getIdIfPossibleOr0(String game) {
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

    private Integer multiplyCubesTogether(HashMap<Color, Integer> counts) {
        return counts.values().stream()
                .reduce(Math::multiplyExact)
                .orElse(0);
    }
}

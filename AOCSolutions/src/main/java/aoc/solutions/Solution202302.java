package aoc.solutions;

import aoc.framework.solution.AOCSolution;
import aoc.framework.solution.Solution;

import java.util.EnumMap;
import java.util.Map;

@Solution(year = 2023, day = 2)
public class Solution202302 extends AOCSolution {

    private final Map<Color, Integer> cubeCounts;

    public Solution202302(boolean printOutput) {
        super(printOutput);

        cubeCounts = new EnumMap<>(Color.class);
        cubeCounts.put(Color.RED, 12);
        cubeCounts.put(Color.GREEN, 13);
        cubeCounts.put(Color.BLUE, 14);
    }

    @Override
    public String solvePart1() {
        return parsedInput.stream()
                .map(this::getIdIfPossibleOr0)
                .reduce(Integer::sum)
                .orElseThrow(() -> new RuntimeException("No Result Obtained"))
                .toString();
    }

    @Override
    public String solvePart2() {
        return parsedInput.stream()
                .map(this::getCubeColorCountsFromGame)
                .map(this::multiplyCubesTogether)
                .reduce(Integer::sum)
                .orElseThrow(() -> new RuntimeException("Unable to parse File"))
                .toString();
    }

    private Map<Color, Integer> getCubeColorCountsFromGame(String game) {
        Map<Color, Integer> colorCountHashmap = new EnumMap<>(Color.class);

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

        printToOutput(() -> String.format("Input: %s, Result: %s %n", game, colorCountHashmap));

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

            Integer maxCountForColor = cubeCounts.get(parsedColor);
            Integer countedColor = Integer.parseInt(split[0]);

            if (maxCountForColor.compareTo(countedColor) < 0) {
                return 0;
            }
        }

        return id;
    }

    private Integer multiplyCubesTogether(Map<Color, Integer> counts) {
        return counts.values().stream()
                .reduce(Math::multiplyExact)
                .orElse(0);
    }

    private enum Color {RED, GREEN, BLUE}
}

package aoc.days;

import aoc.framework.Day;
import aoc.framework.DaySolution;

import java.util.*;
import java.util.stream.Collectors;

@DaySolution(day = 4)
public class Day05 extends Day {

    private static String INPUT_FILE_PATH = "day05/input.txt";
    private static String SEEDS = "seeds";
    private static String SEEDS_TO_SOIL = "seed-to-soil";
    private static String SOIL_TO_FERTILIZER = "soil-to-fertilizer";
    private static String FERTILIZER_TO_WATER = "fertilizer-to-water";
    private static String WATER_TO_LIGHT = "water-to-light";
    private static String LIGHT_TO_TEMPERATURE = "light-to-temperature";
    private static String TEMPERATURE_TO_HUMIDITY = "temperature-to-humidity";
    private static String HUMIDITY_TO_LOCATION = "humidity-to-location";

    private List<Integer> Seeds;

    public Day05(boolean printOutput) throws RuntimeException {
        super(printOutput, INPUT_FILE_PATH, 4);
    }

    @Override
    protected String solvePart1() {
        Map<String, List<List<Long>>> lists = splitStringIntoSections();

        return Arrays.stream(parsedInput.get(0).split(":")[1].trim().split(" "))
                .map(Long::valueOf)
                .map(integer -> lists.get(SEEDS_TO_SOIL).getOrDefault(integer, integer))
                .map(integer -> lists.get(SOIL_TO_FERTILIZER).getOrDefault(integer, integer))
                .map(integer -> lists.get(FERTILIZER_TO_WATER).getOrDefault(integer, integer))
                .map(integer -> lists.get(WATER_TO_LIGHT).getOrDefault(integer, integer))
                .map(integer -> lists.get(LIGHT_TO_TEMPERATURE).getOrDefault(integer, integer))
                .map(integer -> lists.get(TEMPERATURE_TO_HUMIDITY).getOrDefault(integer, integer))
                .map(integer -> lists.get(HUMIDITY_TO_LOCATION).getOrDefault(integer, integer))
                .min(Long::compareTo)
                .orElse(0L)
                .toString();

    }

    private static Long mapFromRange(Long value )

    private Map<String, List<List<Long>>> splitStringIntoSections() {
        List<String> split = new ArrayList<>();

        List<List<String>> newParsed = new ArrayList<>();

        for (String s:parsedInput) {
            if(s.isBlank()) {
                newParsed.add(split);
                split = new ArrayList<>();
            } else {
                split.add(s);
            }
        }
        newParsed.add(split);
        return newParsed.stream()
                .filter(list -> !list.getFirst().split(":")[0].equalsIgnoreCase(SEEDS))
                .collect(Collectors.toMap(
                        list -> list.getFirst().split(":")[0].split(" ")[0],
                        list -> splitIntoRanges(list.subList(1, list.size()))));
    }

    List<List<Long>> splitIntoRanges(List<String> list ){
        return list.stream()
                .map(s -> Arrays.stream(s.split(" "))
                        .map(Long::valueOf)
                        .toList())
                .toList();

    }

    Map<Long, Long> mapFromRangeTOMap(List<String> ranges) {
        Map<Long, Long> result = new HashMap<>();
        for (String range: ranges) {
            List<Long> values = Arrays.stream(range.split(" "))
                    .map(Long::valueOf)
                    .toList();
            for(long i = 0; i<values.get(2); i++){
                result.put(values.get(1)+i, values.get(0)+i);
            }
        }
        return result;
    }
    @Override
    protected String solvePart2() {
        return null;
    }
}

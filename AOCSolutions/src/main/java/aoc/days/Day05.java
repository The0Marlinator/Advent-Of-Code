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

    public Day05(boolean printOutput) throws RuntimeException {
        super(printOutput, INPUT_FILE_PATH, 4);
    }

    @Override
    protected String solvePart1() {
        Map<String, List<List<Long>>> lists = splitStringIntoSections();

        List<Long> seeds = Arrays.stream(parsedInput.get(0).split(":")[1]
                        .trim()
                        .split(" "))
                .map(Long::valueOf)
                .toList();

        return getSmallestSeedFromAlmanack(lists, seeds).toString();

    }

    @Override
    protected String solvePart2() {
        Map<String, List<List<Long>>> lists = splitStringIntoSections();

        List<Long> seeds = Arrays.stream(parsedInput.get(0).split(":")[1]
                        .trim()
                        .split(" "))
                .map(Long::valueOf)
                .toList();

        long smallest = Long.MAX_VALUE;

        for (int i = 0; i + 1 < seeds.size(); i += 2) {
            long floor = seeds.get(i);
            long ceiling = seeds.get(i) + seeds.get(i + 1);
            for(long j = floor; j <= ceiling; j++){
                long value =getSmallestSeedFromAlmanack(lists, Collections.singletonList(j));
                smallest = Math.min(smallest, value);
            }
        }
        return "" + smallest;
    }


    private Long getSmallestSeedFromAlmanack(Map<String, List<List<Long>>> lists, List<Long> seeds) {
        return seeds.stream()
                .map(integer -> mapFromRange(lists.get(SEEDS_TO_SOIL), integer))
                .map(integer -> mapFromRange(lists.get(SOIL_TO_FERTILIZER), integer))
                .map(integer -> mapFromRange(lists.get(FERTILIZER_TO_WATER), integer))
                .map(integer -> mapFromRange(lists.get(WATER_TO_LIGHT), integer))
                .map(integer -> mapFromRange(lists.get(LIGHT_TO_TEMPERATURE), integer))
                .map(integer -> mapFromRange(lists.get(TEMPERATURE_TO_HUMIDITY), integer))
                .map(integer -> mapFromRange(lists.get(HUMIDITY_TO_LOCATION), integer))
                .min(Long::compareTo)
                .orElse(0L);
    }

    private static Long mapFromRange(List<List<Long>> map, Long value) {
        for (List<Long> integer : map) {

            long source = integer.get(1);
            long destination = integer.get(0);
            long rangeLength = integer.get(2);

            if (source <= value && source + rangeLength >= value) {
                return Math.abs(source - value) + destination;
            }
        }
        return value;
    }

    private Map<String, List<List<Long>>> splitStringIntoSections() {
        List<String> split = new ArrayList<>();

        List<List<String>> newParsed = new ArrayList<>();

        for (String s : parsedInput) {
            if (s.isBlank()) {
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

    List<List<Long>> splitIntoRanges(List<String> list) {
        return list.stream()
                .map(s -> Arrays.stream(s.split(" "))
                        .map(Long::valueOf)
                        .toList())
                .toList();

    }

    Map<Long, Long> mapFromRangeTOMap(List<String> ranges) {
        Map<Long, Long> result = new HashMap<>();
        for (String range : ranges) {
            List<Long> values = Arrays.stream(range.split(" "))
                    .map(Long::valueOf)
                    .toList();
            for (long i = 0; i < values.get(2); i++) {
                result.put(values.get(1) + i, values.get(0) + i);
            }
        }
        return result;
    }
}

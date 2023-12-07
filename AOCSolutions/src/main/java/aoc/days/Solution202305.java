package aoc.days;

import aoc.framework.Day;
import aoc.framework.DaySolution;
import aoc.framework.model.Pair;
import aoc.framework.model.Range;

import java.util.*;
import java.util.stream.Collectors;

@DaySolution(year = 2023, day = 5)
public class Solution202305 extends Day {

    private static final String INPUT_FILE_PATH = "202305/input.txt";
    private static final String SEEDS = "seeds";
    private static final String SEEDS_TO_SOIL = "seed-to-soil";
    private static final String SOIL_TO_FERTILIZER = "soil-to-fertilizer";
    private static final String FERTILIZER_TO_WATER = "fertilizer-to-water";
    private static final String WATER_TO_LIGHT = "water-to-light";
    private static final String LIGHT_TO_TEMPERATURE = "light-to-temperature";
    private static final String TEMPERATURE_TO_HUMIDITY = "temperature-to-humidity";
    private static final String HUMIDITY_TO_LOCATION = "humidity-to-location";
    private static final String END = "end";

    private final Map<String, List<Pair<Range, Range>>> rangeMapping;

    public Solution202305(boolean printOutput) throws RuntimeException {
        super(printOutput, INPUT_FILE_PATH);
        rangeMapping = splitStringIntoSections();
    }

    @Override
    public String solvePart1() {
        List<Long> seeds = Arrays.stream(parsedInput.get(0).split(":")[1]
                        .trim()
                        .split(" "))
                .map(Long::valueOf)
                .toList();

        return getLocationFromSeed(seeds).toString();

    }

    @Override
    public String solvePart2() {

        List<Long> seeds = Arrays.stream(parsedInput.get(0).split(":")[1]
                        .trim()
                        .split(" "))
                .map(Long::valueOf)
                .toList();

        long smallest = Long.MAX_VALUE;

        for (int i = 0; i + 1 < seeds.size(); i += 2) {
            Range seedRange = new Range(seeds.get(i), seeds.get(i) + seeds.get(i + 1));

            printToOutput("Starting seedRange: " + seedRange);
            smallest = Math.min(smallest, getSmallestLocationFromRange(seedRange, SEEDS_TO_SOIL));
            printToOutput("\n");
        }
        return "" + smallest;
    }

    private long getSmallestLocationFromRange(Range range, String mapIndex) {
        if (END.equals(mapIndex)) {
            printToOutput("Step: " + mapIndex + " input:" + range + " value:" + range.getFloor());
            return range.getFloor();
        }

        List<Pair<Range, Range>> mapList = rangeMapping.get(mapIndex);
        List<Range> unmapped = List.of(range);

        long smallest = Long.MAX_VALUE;
        for (Pair<Range, Range> map : mapList) {
            List<Range> newUnmapped = new LinkedList<>();
            for (Range unmappedRange : unmapped) {
                Range overLappingRange = map.first.overlapping(unmappedRange);
                if (overLappingRange.isEmpty()) {
                    newUnmapped.add(new Range(unmappedRange.getFloor(), unmappedRange.getCeiling()));
                    continue;
                } else {
                    if (unmappedRange.getFloor() < overLappingRange.getFloor()) {
                        newUnmapped.add(new Range(unmappedRange.getFloor(), overLappingRange.getFloor() - 1));
                    }
                    if (unmappedRange.getCeiling() > overLappingRange.getCeiling()) {
                        newUnmapped.add(new Range(overLappingRange.getCeiling() + 1, unmappedRange.getCeiling()));
                    }
                }
                printToOutput("Step: " + mapIndex + " input:" + unmappedRange + " overlapping:" + overLappingRange + " source map:" + map.first + " destination map:" + map.second + " mapped:" + mapRangeToRangePair(overLappingRange, map) + " unmapped: " + newUnmapped);
                smallest = Math.min(smallest, getSmallestLocationFromRange(mapRangeToRangePair(overLappingRange, map), getNextMapIndex(mapIndex)));
            }
            unmapped = newUnmapped;
        }
        printToOutput("Step:" + mapIndex + " Handling unmapped values that are left: " + unmapped);
        long value = unmapped.stream()
                .map(ran -> getSmallestLocationFromRange(ran, getNextMapIndex(mapIndex)))
                .min(Long::compare)
                .orElse(Long.MAX_VALUE);


        return Math.min(smallest, value);
    }

    private Range mapRangeToRangePair(Range r, Pair<Range, Range> rp) {
        long floorOffset = Math.abs(r.getFloor() - rp.first.getFloor());

        return new Range(rp.second.getFloor() + floorOffset, rp.second.getFloor() + floorOffset + (r.getCeiling() - r.getFloor()));
    }

    private String getNextMapIndex(String mapIndex) {
        return switch (mapIndex) {
            case SEEDS_TO_SOIL -> SOIL_TO_FERTILIZER;
            case SOIL_TO_FERTILIZER -> FERTILIZER_TO_WATER;
            case FERTILIZER_TO_WATER -> WATER_TO_LIGHT;
            case WATER_TO_LIGHT -> LIGHT_TO_TEMPERATURE;
            case LIGHT_TO_TEMPERATURE -> TEMPERATURE_TO_HUMIDITY;
            case TEMPERATURE_TO_HUMIDITY -> HUMIDITY_TO_LOCATION;
            default -> END;
        };
    }

    private Long mapFromRange(String mapIndex, Long value) {
        for (Pair<Range, Range> mapping : rangeMapping.get(mapIndex)) {
            if (mapping.first.inRange(value)) {
                long sourceFloor = mapping.first.getFloor();
                long destinationFloor = mapping.second.getFloor();

                return Math.abs(sourceFloor - value) + destinationFloor;
            }
        }
        return value;
    }

    private Long mapFromRangeFlipped(String mapIndex, Long value) {
        for (Pair<Range, Range> mapping : rangeMapping.get(mapIndex)) {
            if (mapping.flip().first.inRange(value)) {
                long sourceFloor = mapping.first.getFloor();
                long destinationFloor = mapping.second.getFloor();

                return Math.abs(sourceFloor - value) + destinationFloor;
            }
        }
        return value;
    }

    private Long getLocationFromSeed(List<Long> seeds) {
        return seeds.stream()
                .map(seed -> mapFromRange(SEEDS_TO_SOIL, seed))
                .map(seed -> mapFromRange(SOIL_TO_FERTILIZER, seed))
                .map(seed -> mapFromRange(FERTILIZER_TO_WATER, seed))
                .map(seed -> mapFromRange(WATER_TO_LIGHT, seed))
                .map(seed -> mapFromRange(LIGHT_TO_TEMPERATURE, seed))
                .map(seed -> mapFromRange(TEMPERATURE_TO_HUMIDITY, seed))
                .map(seed -> mapFromRange(HUMIDITY_TO_LOCATION, seed))
                .min(Long::compareTo)
                .orElse(0L);
    }

    private Map<String, List<Pair<Range, Range>>> splitStringIntoSections() {
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

    private List<Pair<Range, Range>> splitIntoRanges(List<String> list) {
        return list.stream()
                .map(s -> Arrays.stream(s.split(" "))
                        .map(Long::parseLong)
                        .toList()
                )
                .map(this::splitArrayIntoRange)
                .toList();
    }

    private Pair<Range, Range> splitArrayIntoRange(List<Long> rangeData) {
        return new Pair<>(new Range(rangeData.get(1), rangeData.get(1) + rangeData.get(2)), new Range(rangeData.get(0), rangeData.get(0) + rangeData.get(2)));
    }

}

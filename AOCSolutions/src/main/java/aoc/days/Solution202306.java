package aoc.days;

import aoc.framework.Day;
import aoc.framework.DaySolution;
import aoc.utils.MathUtils;
import aoc.utils.Pair;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@DaySolution(year = 2023, day = 6)
public class Solution202306 extends Day {

    private static final String INPUT_FILE = "202306/input.txt";

    public Solution202306(boolean printOutput) throws RuntimeException {
        super(printOutput, INPUT_FILE);
    }

    @Override
    public String solvePart1() {
        List<Long> times = Arrays.stream(parsedInput.get(0).split(":")[1].trim().split(" "))
                .filter(Predicate.not(String::isBlank))
                .map(Long::valueOf)
                .toList();

        List<Long> records = Arrays.stream(parsedInput.get(1).split(":")[1].trim().split(" "))
                .filter(Predicate.not(String::isBlank))
                .map(Long::valueOf)
                .toList();

        long cumulative = 1L;

        for (int i = 0; i < times.size(); i++) {
            final int finalI = i;
            long durationstoeatRecord = MathUtils.range(times.get(i)).stream()
                    .map(duration -> getDistanceTraveledIfHeldForDuration(duration, times.get(finalI)))
                    .filter(distance -> distance > records.get(finalI))
                    .count();

            cumulative *= durationstoeatRecord;
        }
        return Long.toString(cumulative);
    }

    @Override
    public String solvePart2() {
        Long time = Long.valueOf(Arrays.stream(parsedInput.get(0).split(":")[1].trim().split(" "))
                .filter(Predicate.not(String::isBlank))
                .collect(Collectors.joining("")));

        Long record = Long.valueOf(Arrays.stream(parsedInput.get(1).split(":")[1].trim().split(" "))
                .filter(Predicate.not(String::isBlank))
                .collect(Collectors.joining("")));

        return Long.toString(MathUtils.range(time).stream()
                .map(duration -> getDistanceTraveledIfHeldForDuration(duration, time))
                .filter(distance -> distance > record)
                .count());

    }

    private long getDistanceTraveledIfHeldForDuration(long duration, long time) {
        //duration == speed
        long timeLeft = time - duration;

        return duration * timeLeft;

    }

}

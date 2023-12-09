package aoc.solutions;

import aoc.framework.solution.AOCSolution;
import aoc.framework.solution.Solution;
import aoc.framework.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Solution(year = 2023, day = 9)
public class Solution202309 extends AOCSolution {


    public Solution202309(boolean printOutput) {
        super(printOutput);
    }

    @Override
    public String solvePart1() {
        return parsedInput.stream()
                .map(StringUtils::splitStringAroundSpaces)
                .map(StringUtils.StringWrapper::withoutBlank)
                .map(StringUtils.StringWrapper::withoutEmpty)
                .map(StringUtils.StringWrapper::asLongs)
                .map(this::buildHistorySequence)
                .map(List::reversed)
                .map(this::getNextFromHistory)
                .reduce(Long::sum)
                .orElseThrow()
                .toString();
    }

    @Override
    public String solvePart2() {
        return parsedInput.stream()
                .map(StringUtils::splitStringAroundSpaces)
                .map(StringUtils.StringWrapper::withoutBlank)
                .map(StringUtils.StringWrapper::withoutEmpty)
                .map(StringUtils.StringWrapper::asLongs)
                .map(List::reversed)
                .map(this::buildHistorySequence)
                .map(List::reversed)
                .map(this::getNextFromHistory)
                .reduce(Long::sum)
                .orElseThrow()
                .toString();
    }


    private List<List<Long>> buildHistorySequence(List<Long> sequence) {
        printToOutput("buildHistorySequence: " + sequence);
        List<Long> nonZeroElements = sequence.stream()
                .filter(l -> l != 0L)
                .toList();

        if (nonZeroElements.isEmpty()) {
            return List.of(List.of(0L));
        }
        List<Long> result = new ArrayList<>();

        for (int i = 0; i < sequence.size() - 1; i++) {
            result.add(sequence.get(i + 1) - sequence.get(i));
        }

        List<List<Long>> lists = new LinkedList<>(buildHistorySequence(result));
        lists.addFirst(sequence);

        return lists;
    }

    private Long getNextFromHistory(List<List<Long>> sequencesOldestFirst) {
        if (sequencesOldestFirst.isEmpty()) {
            return 0L;
        }

        long histValue = getNextFromHistory(sequencesOldestFirst.subList(0, sequencesOldestFirst.size() - 1));

        long callcaulatedValue = sequencesOldestFirst.getLast().getLast() + histValue;

        printToOutput("getNextFromHistory: " + histValue + " : Value from sequence: " + sequencesOldestFirst.getLast().getLast() + " : addition: " + callcaulatedValue);
        return callcaulatedValue;
    }
}

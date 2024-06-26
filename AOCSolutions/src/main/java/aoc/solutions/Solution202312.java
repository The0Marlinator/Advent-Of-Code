package aoc.solutions;

import aoc.framework.exception.AOCException;
import aoc.framework.model.Pair;
import aoc.framework.solution.AOCSolution;
import aoc.framework.solution.Solution;
import aoc.framework.util.CollectionUitls;
import aoc.framework.util.StringUtils;

import java.util.*;
import java.util.stream.Stream;

@Solution(year = 2023, day = 12)
public class Solution202312 extends AOCSolution {

    private final List<Pair<List<SpringType>, List<Long>>> linesAndGroupCounts;


    public Solution202312(boolean printOutput, List<String> input) {
        super(printOutput, input);
        linesAndGroupCounts = parsedInput.stream()
                .map(StringUtils::splitStringAroundSpaces)
                .map(StringUtils.StringWrapper::asList)
                .map(strings -> new Pair<>(strings.getFirst(), strings.getLast()))
                .map(mapping -> new Pair<>(mapping.first(), StringUtils.splitStringAroundCommae(mapping.second())))
                .map(mapping -> new Pair<>(mapping.first(), mapping.second().asLongs()))
                .map(mapping -> new Pair<>(StringUtils.splitIntoCharacters(mapping.first()), mapping.second()))
                .map(mapping -> new Pair<>(mapping.first().asStringStream(), mapping.second()))
                .map(mapping -> new Pair<>(mapping.first().string().map(SpringType::of).toList(), mapping.second()))
                .toList();
    }

    public Solution202312(boolean printOutput) {
        super(printOutput);
        linesAndGroupCounts = parsedInput.stream()
                .map(StringUtils::splitStringAroundSpaces)
                .map(StringUtils.StringWrapper::asList)
                .map(strings -> new Pair<>(strings.getFirst(), strings.getLast()))
                .map(mapping -> new Pair<>(mapping.first(), StringUtils.splitStringAroundCommae(mapping.second())))
                .map(mapping -> new Pair<>(mapping.first(), mapping.second().asLongs()))
                .map(mapping -> new Pair<>(StringUtils.splitIntoCharacters(mapping.first()), mapping.second()))
                .map(mapping -> new Pair<>(mapping.first().asStringStream(), mapping.second()))
                .map(mapping -> new Pair<>(mapping.first().string().map(SpringType::of).toList(), mapping.second()))
                .toList();
    }

    @Override
    public String solvePart1() throws AOCException {
        return "" + linesAndGroupCounts.stream()
                .map(this::getPossibleGroupFormations)
                .map(Set::stream)
                .map(Stream::count)
                //.map(Stream::toList)
                //.map(s-> s + "\n")
                //.toList();
                .reduce(Long::sum)
                .orElseThrow(() -> new AOCException("Unable to look for formations"));
    }

    @Override
    public String solvePart2() throws AOCException {
        return null;
    }

    private Set<List<SpringType>> getPossibleGroupFormations(Pair<List<SpringType>, List<Long>> currentRowInfo) {
        List<Integer> groups = currentRowInfo.second().stream().map(Long::intValue).toList();
        Queue<List<SpringType>> futureCombinationsToCheck = new ArrayDeque<>();
        Set<List<SpringType>> foundCombinations = new HashSet<>();
        futureCombinationsToCheck.add(currentRowInfo.first());

        while (!futureCombinationsToCheck.isEmpty()) {
            List<SpringType> current = futureCombinationsToCheck.poll();
            List<Integer> unknownIndexes = CollectionUitls.findIndexOfAll(current, SpringType.UNKNOWN);
            for (int unknowIndex : unknownIndexes) {
                List<SpringType> newMap = CollectionUitls.replaceIndexInListAsNewList(current, unknowIndex, SpringType.BROKEN);
                CombinationrType formationValid = isFormationValid(newMap, groups);
                if (CombinationrType.POSSIBLE.equals(formationValid)) {
                    futureCombinationsToCheck.add(newMap);
                } else if (CombinationrType.DONE.equals(formationValid)) {
                    foundCombinations.add(newMap);
                }
            }
        }
        return foundCombinations;

    }

    private CombinationrType isFormationValid(List<SpringType> combinationToCheck, List<Integer> groupsInput) {
        List<List<Integer>> found = new ArrayList<>();

        List<Integer> currentGroup = new ArrayList<>();
        for (int i = 0; i < combinationToCheck.size(); i++) {
            SpringType current = combinationToCheck.get(i);
            if (current.equals(SpringType.BROKEN)) {
                currentGroup.add(i);
            } else if (current.equals(SpringType.SPRING) || current.equals(SpringType.UNKNOWN)) {
                if (!currentGroup.isEmpty()) {
                    found.add(currentGroup);
                    currentGroup = new ArrayList<>();
                }
            }
        }
        if (!currentGroup.isEmpty()) {
            found.add(currentGroup);
        }

        int currentGroupIndex = 0;
        for (List<Integer> integers : found) {
            if (currentGroupIndex < groupsInput.size() && groupsInput.get(currentGroupIndex).equals(integers.size())) {
                currentGroupIndex += 1;
            } else if (currentGroupIndex < groupsInput.size() && !groupsInput.get(currentGroupIndex).equals(integers.size())) {
                return CombinationrType.POSSIBLE;
            } else if (currentGroupIndex == groupsInput.size()) {
                return CombinationrType.IMPOSSIBLE;
            }
        }

        if (currentGroupIndex == groupsInput.size()) {
            return CombinationrType.DONE;
        }
        return CombinationrType.POSSIBLE;

    }

    enum CombinationrType {
        POSSIBLE, IMPOSSIBLE, DONE
    }

    enum SpringType {
        SPRING, BROKEN, UNKNOWN, INVALID;

        @Override
        public String toString() {
            return switch (this) {
                case BROKEN -> "#";
                case SPRING -> ".";
                case UNKNOWN -> "?";
                case INVALID -> "I";
            };
        }

        public static SpringType of(String c) {
            return switch (c) {
                case "." -> SPRING;
                case "#" -> BROKEN;
                case "?" -> UNKNOWN;
                default -> INVALID;
            };
        }
    }
}

package aoc.solutions;

import aoc.framework.exception.AOCException;
import aoc.framework.model.Pair;
import aoc.framework.model.mapping.Coordinate;
import aoc.framework.model.mapping.CoordinateAddressable;
import aoc.framework.model.mapping.CoordinateMap;
import aoc.framework.solution.AOCSolution;
import aoc.framework.solution.Solution;
import aoc.framework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Solution(year = 2023, day = 11)
public class Solution202311 extends AOCSolution {

    private final CoordinateAddressable<GalaxyTypes> galaxyMap;

    public Solution202311(boolean printOutput, List<String> input) {
        super(printOutput, input);

        galaxyMap = new CoordinateMap<>(parsedInput.stream()
                .map(StringUtils::splitIntoCharacters)
                .map(StringUtils.CharacterWrapper::characters)
                .map(c -> c.map(GalaxyTypes::of))
                .map(Stream::toList)
                .toList())
                .transpose();
    }

    public Solution202311(boolean printOutput) {
        super(printOutput);

        galaxyMap = new CoordinateMap<>(parsedInput.stream()
                .map(StringUtils::splitIntoCharacters)
                .map(StringUtils.CharacterWrapper::characters)
                .map(c -> c.map(GalaxyTypes::of))
                .map(Stream::toList)
                .toList())
                .transpose();
    }

    @Override
    public String solvePart1() throws AOCException {

        CoordinateAddressable<GalaxyTypes> newMap = new CoordinateMap<>(galaxyMap);
        List<Integer> emptyColumns = galaxyMap.findEmptyColumns(GalaxyTypes::isEmpty);
        List<Integer> emptyRoews = galaxyMap.findEmptyRows(GalaxyTypes::isEmpty);

        for(int i = 0; i<emptyColumns.size(); i++) {
            newMap.insertColumn(emptyColumns.get(i)+i, GalaxyTypes.INVALID);
        }

        for(int i = 0; i<emptyRoews.size(); i++) {
            newMap.insertRow(emptyRoews.get(i)+i, GalaxyTypes.INVALID);
        }

        List<Coordinate> spaceLocations = newMap.findAll(GalaxyTypes.GALAXY);

        Set<Pair<Coordinate, Coordinate>> galaxyPairs = new HashSet<>();
        for (Coordinate first : spaceLocations) {
            for (Coordinate second : spaceLocations) {
                if (!galaxyPairs.contains(new Pair<>(second, first)) && !first.equals(second)) {
                    galaxyPairs.add(new Pair<>(first, second));
                }
            }
        }

        return ""+galaxyPairs.stream()
                .map(coordinateCoordinatePair -> coordinateCoordinatePair.first().manhattanDistance(coordinateCoordinatePair.second()))
                .reduce(Integer::sum)
                .orElseThrow(() -> new AOCException("Unable to Find Coordinate Pairs"));
    }

    @Override
    public String solvePart2() throws AOCException {
        return null;
    }


    private enum GalaxyTypes {
        GALAXY, SPACE, INVALID;

        public static GalaxyTypes of(char value) {
            return switch (value) {
                case '#' -> GALAXY;
                case '.' -> SPACE;
                default -> INVALID;
            };

        }

        @Override
        public String toString() {
            return switch (this) {
                case GALAXY -> "#";
                case SPACE -> ".";
                case INVALID -> "/";
            };
        }

        public boolean isEmpty() {
            return SPACE.equals(this);
        }
    }
}

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
import java.util.LinkedList;
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
        List<Integer> emptyRows = galaxyMap.findEmptyRows(GalaxyTypes::isEmpty);

        for (int i = 0; i < emptyColumns.size(); i++) {
            newMap.insertColumn(emptyColumns.get(i) + i, GalaxyTypes.SPACE);
        }

        for (int i = 0; i < emptyRows.size(); i++) {
            newMap.insertRow(emptyRows.get(i) + i, GalaxyTypes.SPACE);
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

        return "" + galaxyPairs.stream()
                .map(coordinateCoordinatePair -> coordinateCoordinatePair.first().manhattanDistance(coordinateCoordinatePair.second()))
                .reduce(Long::sum)
                .orElseThrow(() -> new AOCException("Unable to Find Coordinate Pairs"));
    }

    @Override
    public String solvePart2() throws AOCException {
        return solvePart2(1000000);
    }

    public String solvePart2(int numberToReplaceColumnWith) throws AOCException {

        List<Integer> emptyColumns = galaxyMap.findEmptyColumns(GalaxyTypes::isEmpty);
        List<Integer> emptyRows = galaxyMap.findEmptyRows(GalaxyTypes::isEmpty);
        List<Coordinate> allGalaxies = galaxyMap.findAll(GalaxyTypes.GALAXY);

        List<Coordinate> oldGalaxyLocations = new LinkedList<>(allGalaxies);

        //We have to reduce numberToReplaceColumnWith with one in the following code because
        //we are replacing a column with numberToReplaceColumnWith ones. This means adding numberToReplaceColumnWith-1
        //to the total number
        for (Integer emptyRow : emptyRows) {
            List<Coordinate> newGalaxyLocations = new LinkedList<>();
            for (int j = 0; j < oldGalaxyLocations.size(); j++) {
                Coordinate current = oldGalaxyLocations.get(j);
                if (allGalaxies.get(j).y() > emptyRow) {
                    newGalaxyLocations.add(new Coordinate(current.x(), current.y() + numberToReplaceColumnWith - 1));
                } else {
                    newGalaxyLocations.add(current);
                }
            }
            oldGalaxyLocations = newGalaxyLocations;
        }

        for (Integer emptyColumn : emptyColumns) {
            List<Coordinate> newGalaxyLocations = new LinkedList<>();
            for (int j = 0; j < oldGalaxyLocations.size(); j++) {
                Coordinate current = oldGalaxyLocations.get(j);
                if (allGalaxies.get(j).x() > emptyColumn) {
                    newGalaxyLocations.add(new Coordinate(current.x() + numberToReplaceColumnWith -1 , current.y()));
                } else {
                    newGalaxyLocations.add(current);
                }
            }
            oldGalaxyLocations = newGalaxyLocations;
        }


        Set<Pair<Coordinate, Coordinate>> galaxyPairs = new HashSet<>();
        for (Coordinate first : oldGalaxyLocations) {
            for (Coordinate second : oldGalaxyLocations) {
                if (!galaxyPairs.contains(new Pair<>(second, first)) && !first.equals(second)) {
                    galaxyPairs.add(new Pair<>(first, second));
                }
            }
        }

        return "" + galaxyPairs.stream()
                .map(coordinateCoordinatePair -> coordinateCoordinatePair.first().manhattanDistance(coordinateCoordinatePair.second()))
                .reduce(Long::sum)
                .orElseThrow(() -> new AOCException("Unable to Find Coordinate Pairs"));
    }


    private enum GalaxyTypes {
        GALAXY, SPACE, INVALID, FOLD;

        public static GalaxyTypes of(char value) {
            return switch (value) {
                case '#' -> GALAXY;
                case '.' -> SPACE;
                case 'F' -> FOLD;
                default -> INVALID;
            };

        }

        @Override
        public String toString() {
            return switch (this) {
                case GALAXY -> "#";
                case SPACE -> ".";
                case FOLD -> "F";
                case INVALID -> "/";
            };
        }

        public boolean isEmpty() {
            return SPACE.equals(this);
        }
    }
}

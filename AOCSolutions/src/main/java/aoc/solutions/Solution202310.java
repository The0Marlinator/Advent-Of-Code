package aoc.solutions;

import aoc.framework.exception.AOCException;
import aoc.framework.model.Pair;
import aoc.framework.model.math.Coordinate;
import aoc.framework.model.math.Matrix;
import aoc.framework.solution.AOCSolution;
import aoc.framework.solution.Solution;
import aoc.framework.util.CollectionUitls;
import aoc.framework.util.StringUtils;

import java.util.*;
import java.util.stream.Stream;

@Solution(year = 2023, day = 10)
public class Solution202310 extends AOCSolution {

    private final Matrix<PipeType> map;

    public Solution202310(boolean printOutput) {
        super(printOutput);
        List<List<PipeType>> mapData = parsedInput.stream()
                .map(StringUtils::splitIntoCharacters)
                .map(StringUtils.CharacterWrapper::characters)
                .map(c -> c.map(PipeType::of))
                .map(Stream::toList)
                .toList();

        PipeType[][] processedData = new PipeType[mapData.getFirst().size()][mapData.size()];
        for (int x = 0; x < processedData[0].length; x++) {
            for (int y = 0; y < processedData.length; y++) {
                processedData[x][y] = mapData.get(y).get(x);
            }
        }

        map = new Matrix<>(processedData);
    }

    public Solution202310(boolean printOutput, List<String> input) {
        super(printOutput, input);

        List<List<PipeType>> mapData = parsedInput.stream()
                .map(StringUtils::splitIntoCharacters)
                .map(StringUtils.CharacterWrapper::characters)
                .map(c -> c.map(PipeType::of))
                .map(Stream::toList)
                .toList();

        PipeType[][] processedData = new PipeType[mapData.getFirst().size()][mapData.size()];
        for (int x = 0; x < processedData.length; x++) {
            for (int y = 0; y < processedData[0].length; y++) {
                processedData[x][y] = mapData.get(y).get(x);
            }
        }

        map = new Matrix<>(processedData);
    }

    @Override
    public String solvePart1() throws AOCException {

        List<Coordinate> wayTostart = map.findAny(PipeType.STARTING_POSITION)
                .map(l -> findLoop(l, l))
                .orElseThrow(() -> new AOCException("Unable to Find Starting Position in Input"))
                .orElseThrow(() -> new AOCException("Unable to find Loop"))
                .history()
                .reversed();

        return "" + (wayTostart.size() + 1) / 2;
    }

    @Override
    public String solvePart2() throws AOCException {

        List<Coordinate> wayTostart = map.findAny(PipeType.STARTING_POSITION)
                .map(l -> findLoop(l, l))
                .orElseThrow(() -> new AOCException("Unable to Find Starting Position in Input"))
                .orElseThrow(() -> new AOCException("Unable to find Loop"))
                .history();

        return String.format("Solution 1: %s , Solution 2 (reversed path): %s", fidInnerPoints(wayTostart, false).size(), fidInnerPoints(wayTostart, true).size());
    }

    private List<Coordinate> fidInnerPoints(List<Coordinate> path, boolean reversed) {
        List<Coordinate> finalPath = reversed ? path.reversed() : path;

        PipeType[][] d = new PipeType[map.xLength()][map.yLength()];

        for (int i = 0; i < map.xLength(); i++) {
            for (int j = 0; j < map.yLength(); j++) {
                d[i][j] = map.getData()[i][j];
            }
        }

        Matrix<PipeType> newMap = new Matrix<>(d);
        for (int i = 0; i < path.size() - 1; i++) {
            newMap.setValue(path.get(i), PipeType.PATH);
            Pair<Coordinate, Coordinate> check = getCoordinateToLeftAndStraightAhead(finalPath, i);

            if (map.isLegalCoordinate(check.first())) {
                newMap.floodFill(check.first(), path::contains, PipeType.INNER);
            }
            if (map.isLegalCoordinate(check.second())) {
                newMap.floodFill(check.second(), path::contains, PipeType.INNER);
            }
        }
        return newMap.findAll(PipeType.INNER);
    }

    private Pair<Coordinate, Coordinate> getCoordinateToLeftAndStraightAhead(List<Coordinate> wayTostart, int i) {
        Coordinate current = wayTostart.get(i);
        Coordinate next = wayTostart.get(i + 1);
        Coordinate left;
        Coordinate straight;
        if (next.x() < current.x()) {
            left = new Coordinate(current.x(), current.y() - 1);
            straight = new Coordinate(next.x(), next.y() - 1);
        } else if (next.x() > current.x()) {
            left = new Coordinate(current.x(), current.y() + 1);
            straight = new Coordinate(next.x(), next.y() + 1);
        } else if (next.y() < current.y()) {
            left = new Coordinate(current.x() + 1, current.y());
            straight = new Coordinate(next.x() + 1, next.y());
        } else {
            left = new Coordinate(current.x() - 1, current.y());
            straight = new Coordinate(next.x() - 1, next.y());
        }

        return new Pair<>(left, straight);
    }

    private Optional<StackFrame> findLoop(Coordinate startingPosition, Coordinate source) {

        Queue<StackFrame> travelStack = new ArrayDeque<>();

        travelStack.add(new StackFrame(startingPosition, source, Collections.singletonList(startingPosition)));

        while (!travelStack.isEmpty()) {
            StackFrame current = travelStack.poll();
            Coordinate start = current.startingNode();
            Coordinate sourceC = current.Source();
            for (int xDt = -1; xDt <= 1; xDt++) {
                for (int yDt = -1; yDt <= 1; yDt++) {
                    Coordinate next = new Coordinate(start.x() + xDt, start.y() + yDt);
                    if (map.isLegalCoordinate(next) && !sourceC.equals(next) && (canGoTo(start, next) && canGoFrom(start, next))) {
                        if (map.get(next).equals(PipeType.STARTING_POSITION)) {
                            return Optional.of(current);
                        } else if (!current.history.contains(next)) {
                            travelStack.add(new StackFrame(next, start, CollectionUitls.appendFirstToList(next, current.history())));
                            printToOutput(() -> String.format("AddingToStack: %s -> %s, tile source: %s, tile destination: %s", start, next, map.get(start), map.get(next)));
                        } else if (map.get(next) != PipeType.GROUND) {
                            printToOutput(() -> String.format("NOT: AddingToStack: %s -> %s, tile source: %s, tile destination: %s because it was in the history", start, next, map.get(start), map.get(next)));
                        }
                    } else {
                        if (map.isLegalCoordinate(next) && !sourceC.equals(next) && map.get(next) != PipeType.GROUND) {
                            printToOutput(() -> String.format("NOT: AddingToStack: %s -> %s, tile source: %s, tile destination: %s because we can't go there", start, next, map.get(start), map.get(next)));
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }

    private boolean canGoFrom(Coordinate source, Coordinate destination) {
        PipeType destinationPipeType = map.get(source);

        if (source.isDiagonalBottomLeft(destination) || source.isDiagonalTopLeft(destination) || source.isDiagonalTopRight(destination) || source.isDiagonalBottomRight(destination)) {
            return false;
        }

        return switch (destinationPipeType) {
            case VERTICAL_BEND -> destination.isAbove(source) || destination.isBelow(source);
            case HORIZONTAL_PIPE -> destination.isRight(source) || destination.isLeft(source);
            case L_BEND -> destination.isRight(source) || destination.isAbove(source);
            case J_BEND -> destination.isLeft(source) || destination.isAbove(source);
            case SEVEN_BEND -> destination.isLeft(source) || destination.isBelow(source);
            case F_BEND -> destination.isRight(source) || destination.isBelow(source);
            case STARTING_POSITION -> true;
            default -> false;
        };
    }

    private boolean canGoTo(Coordinate source, Coordinate destination) {
        PipeType destinationPipeType = map.get(destination);

        if (source.isDiagonalBottomLeft(destination) || source.isDiagonalTopLeft(destination) || source.isDiagonalTopRight(destination) || source.isDiagonalBottomRight(destination)) {
            return false;
        }

        return switch (destinationPipeType) {
            case VERTICAL_BEND -> source.isAbove(destination) || source.isBelow(destination);
            case HORIZONTAL_PIPE -> source.isRight(destination) || source.isLeft(destination);
            case L_BEND -> source.isRight(destination) || source.isAbove(destination);
            case J_BEND -> source.isLeft(destination) || source.isAbove(destination);
            case SEVEN_BEND -> source.isLeft(destination) || source.isBelow(destination);
            case F_BEND -> source.isRight(destination) || source.isBelow(destination);
            case STARTING_POSITION -> true;
            default -> false;
        };
    }


    private enum PipeType {
        VERTICAL_BEND, HORIZONTAL_PIPE, L_BEND, J_BEND, SEVEN_BEND, F_BEND, GROUND, STARTING_POSITION, INVALID_CHARACTER, INNER, OUTER, PATH;


        public static PipeType of(char s) {
            return switch (s) {
                case '|' -> PipeType.VERTICAL_BEND;
                case '-' -> PipeType.HORIZONTAL_PIPE;
                case 'L' -> PipeType.L_BEND;
                case 'J' -> PipeType.J_BEND;
                case '7' -> PipeType.SEVEN_BEND;
                case 'F' -> PipeType.F_BEND;
                case '.' -> PipeType.GROUND;
                case 'S' -> PipeType.STARTING_POSITION;
                default -> PipeType.INVALID_CHARACTER;
            };
        }

        @Override
        public String toString() {
            return switch (this.ordinal()) {
                case 0 -> "|";
                case 1 -> "-";
                case 2 -> "L";
                case 3 -> "J";
                case 4 -> "7";
                case 5 -> "F";
                case 6 -> ".";
                case 7 -> "S";
                case 8 -> "/";
                case 9 -> "I";
                case 10 -> "O";
                case 11 -> "P";
                default -> " ";
            };
        }
    }


    private record StackFrame(Coordinate startingNode, Coordinate Source, List<Coordinate> history) {
    }

}

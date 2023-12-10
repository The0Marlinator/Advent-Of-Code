package aoc.solutions;

import aoc.framework.exception.AOCException;
import aoc.framework.model.math.Coordinate;
import aoc.framework.model.math.Matrix;
import aoc.framework.solution.AOCSolution;
import aoc.framework.solution.Solution;
import aoc.framework.util.CollectionUitls;
import aoc.framework.util.MathUtils;
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
                .map(c -> c.map(this::classifyLocation))
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
                .map(c -> c.map(this::classifyLocation))
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

        List<Coordinate> wayTostart = map.findLocationOfValue(PipeType.STARTING_POSITION)
                .map(l -> findLoop(l, l))
                .orElseThrow(() -> new AOCException("Unable to Find Starting Position in Input"))
                .orElseThrow(() -> new AOCException("Unable to find Loop"))
                .history();

        return "" + (wayTostart.size() + 1) / 2;
    }

    @Override
    public String solvePart2() throws AOCException {

        List<Coordinate> wayTostart = map.findLocationOfValue(PipeType.STARTING_POSITION)
                .map(l -> findLoop(l, l))
                .orElseThrow(() -> new AOCException("Unable to Find Starting Position in Input"))
                .orElseThrow(() -> new AOCException("Unable to find Loop"))
                .history();

        Map<Integer, List<Coordinate>> grouped = groupByYCoordinate(wayTostart);
        long placesBetweenPipes = 0;

        for (int i : grouped.keySet()) {
            if (grouped.get(i) != null && grouped.get(i).size() != map.xLength()) {
                boolean isReadingSpace = false;
                Coordinate readStart = null;
                for (int j = 0; j < grouped.get(i).size(); j++) {
                    if (isReadingSpace) {
                        isReadingSpace = false;
                        placesBetweenPipes += countGroundBetweenPointXCoordinates(readStart, grouped.get(i).get(j));
                    } else if (j + 1 != grouped.get(i).size()) {
                        readStart = grouped.get(i).get(j);
                        isReadingSpace = true;
                    }
                }
                if (isReadingSpace) {
                    throw new AOCException("Has finished reading line but the last pipe was not closed. This was unexepected");
                }
            }

        }

        return "" + placesBetweenPipes;

    }

    private long countGroundBetweenPointXCoordinates(Coordinate first, Coordinate second) {
        return MathUtils.range(first.x(), second.x()).stream()
                .map(l -> new Coordinate(l.intValue(), first.y()))
                .map(map::get)
                .filter(pipeType -> pipeType.equals(PipeType.GROUND))
                .count();

    }

    private Map<Integer, List<Coordinate>> groupByYCoordinate(List<Coordinate> path) {
        Map<Integer, List<Coordinate>> grouped = new HashMap<>();


        for (Coordinate c : path) {
            List<Coordinate> found = grouped.get(c.y());

            if (found == null) {
                List<Coordinate> newList = new ArrayList<>();
                newList.add(c);
                grouped.put(c.y(), newList);
            } else {
                grouped.get(c.y()).add(c);
                grouped.get(c.y()).sort(Comparator.comparing(Coordinate::x));
            }
        }
        return grouped;
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

    private PipeType classifyLocation(char s) {
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

    private enum PipeType {
        VERTICAL_BEND, HORIZONTAL_PIPE, L_BEND, J_BEND, SEVEN_BEND, F_BEND, GROUND, STARTING_POSITION, INVALID_CHARACTER;
    }


    private record StackFrame(Coordinate startingNode, Coordinate Source, List<Coordinate> history) {
    }

}

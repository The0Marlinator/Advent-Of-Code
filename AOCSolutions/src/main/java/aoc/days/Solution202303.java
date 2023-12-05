package aoc.days;

import aoc.framework.Day;
import aoc.framework.DaySolution;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@DaySolution(year = 2023, day = 3)
public class Solution202303 extends Day {

    private static final String DAY_INPUT_FILE = "202303/input.txt";

    public Solution202303(boolean printOutput) throws RuntimeException {
        super(printOutput, DAY_INPUT_FILE);
    }

    @Override
    public String solvePart1() {

        List<List<List<Integer>>> rows = findIntegers(this::isNumeric);

        int totalSum = 0;

        for (int row_index = 0; row_index < rows.size(); ++row_index) {

            List<List<Integer>> row = rows.get(row_index);

            int finalRowIndex = row_index;
            List<Integer> numbers = getIndexesBorderingSymbol(row, row_index)
                    .stream()
                    .map((List<Integer> number) -> buildNumberFromIntegerList(number, finalRowIndex))
                    .toList();

            Integer sum = numbers.stream()
                    .reduce(Integer::sum)
                    .orElse(0);

            totalSum += sum;
        }

        return Integer.toString(totalSum);
    }

    @Override
    public String solvePart2() {

        List<List<List<Integer>>> stars = findIntegers((Character c) -> c == '*');

        printToOutput(String.format("Found numbers: %s %n", borderingPlacesHaveNumbers(stars).stream()
                .filter(integers -> integers.size() == 2).toList()));

        return borderingPlacesHaveNumbers(stars).stream()
                .filter(integers -> integers.size() == 2)
                .map(integers -> integers.stream().map(BigInteger::valueOf).reduce(BigInteger::multiply).orElse(BigInteger.ZERO))
                .reduce(BigInteger::add)
                .orElse(BigInteger.ZERO)
                .toString();
    }

    private List<List<List<Integer>>> findIntegers(Predicate<Character> condition) {
        List<List<List<Integer>>> integerIndexes = new ArrayList<>();

        boolean isParsingNumber = false;
        for (String s : parsedInput) {
            List<Integer> idList = new ArrayList<>();
            ArrayList<List<Integer>> row = new ArrayList<>();
            for (int i = 0; i < s.length(); i++) {
                if (condition.test(s.charAt(i)) && isParsingNumber) {
                    idList.add(i);
                } else if (condition.test(s.charAt(i)) && !isParsingNumber) {
                    idList.add(i);
                    isParsingNumber = true;
                } else if (!condition.test(s.charAt(i)) && isParsingNumber) {
                    isParsingNumber = false;
                    row.add(idList);

                    idList = new ArrayList<>();
                }
            }
            if (isParsingNumber) {
                row.add(idList);
            }

            printToOutput(String.format("input: %s : row: %s %n", s, row));

            integerIndexes.add(row);
        }
        return integerIndexes;
    }

    private boolean isNumeric(char value) {
        return switch (value) {
            case '0', '2', '4', '9', '8', '7', '6', '5', '3', '1' -> true;
            default -> false;
        };
    }

    private int parseChar(char c) {
        return switch (c) {
            case '1' -> 1;
            case '2' -> 2;
            case '3' -> 3;
            case '4' -> 4;
            case '5' -> 5;
            case '6' -> 6;
            case '7' -> 7;
            case '8' -> 8;
            case '9' -> 9;
            default -> 0;
        };
    }

    private boolean isIndexOnGrid(Integer xIndex, Integer yIndex) {
        return yIndex >= 0 && yIndex < parsedInput.size() && xIndex >= 0 && xIndex < parsedInput.get(yIndex).length();
    }

    private List<List<Integer>> getIndexesBorderingSymbol(List<List<Integer>> indexes, Integer yIndex) {
        List<List<Integer>> indexesFiltered = new ArrayList<>();
        for (List<Integer> index : indexes) {
            boolean found = false;
            for (int xIndexIndex = 0; xIndexIndex < index.size(); xIndexIndex++) {
                if (borderingPLacesHaveSymbol(index.get(xIndexIndex), yIndex) && !found) {
                    indexesFiltered.add(index);
                    found = true;
                }
            }
        }
        return indexesFiltered;
    }

    private Integer buildNumberFromIntegerList(List<Integer> integers, Integer yIndex) {
        int result = 0;
        for (Integer i : integers) {
            result = (result * 10) + parseChar(parsedInput.get(yIndex).charAt(i));
        }
        return result;
    }

    private boolean borderingPLacesHaveSymbol(int xIndex, int yIndex) {
        return hasSymbol(xIndex - 1, yIndex)
                || hasSymbol(xIndex, yIndex - 1)
                || hasSymbol(xIndex - 1, yIndex - 1)

                || hasSymbol(xIndex + 1, yIndex)
                || hasSymbol(xIndex, yIndex + 1)
                || hasSymbol(xIndex + 1, yIndex + 1)

                || hasSymbol(xIndex - 1, yIndex + 1)
                || hasSymbol(xIndex + 1, yIndex - 1);
    }

    private boolean hasSymbol(int xIndex, int yIndex) {
        return isIndexOnGrid(xIndex, yIndex) && !isNumeric(parsedInput.get(yIndex).charAt(xIndex)) && !(parsedInput.get(yIndex).charAt(xIndex) == '.');
    }

    private List<List<Integer>> borderingPlacesHaveNumbers(List<List<List<Integer>>> starLocations) {

        List<List<List<Integer>>> numberLocations = findIntegers(this::isNumeric);

        List<List<Integer>> numbersAdjacentToStar = new ArrayList<>();

        for (int star_y = 0; star_y < starLocations.size(); ++star_y) {
            for (int star_x = 0; star_x < starLocations.get(star_y).size(); ++star_x) {

                List<Integer> adjacentNumbers = new ArrayList<>();
                int starXActual = starLocations.get(star_y).get(star_x).get(0);

                if (star_y - 1 >= 0) {
                    for (List<Integer> numberIndeciesOnRow : numberLocations.get(star_y - 1)) {
                        if (!adjacentNumbers.contains(buildNumberFromIntegerList(numberIndeciesOnRow, star_y - 1)) && (numberIndeciesOnRow.contains(starXActual - 1) || numberIndeciesOnRow.contains(starXActual) || numberIndeciesOnRow.contains(starXActual + 1))) {
                            adjacentNumbers.add(buildNumberFromIntegerList(numberIndeciesOnRow, star_y - 1));
                        }
                    }
                }

                for (List<Integer> numberIndiciesOnRow : numberLocations.get(star_y)) {
                    if (!adjacentNumbers.contains(buildNumberFromIntegerList(numberIndiciesOnRow, star_y)) && (numberIndiciesOnRow.contains(starXActual - 1) || numberIndiciesOnRow.contains(starXActual) || numberIndiciesOnRow.contains(starXActual + 1))) {
                        adjacentNumbers.add(buildNumberFromIntegerList(numberIndiciesOnRow, star_y));
                    }
                }

                if (star_y + 1 < parsedInput.size()) {
                    for (List<Integer> numberIndeciesOnRow : numberLocations.get(star_y + 1)) {
                        if (!adjacentNumbers.contains(buildNumberFromIntegerList(numberIndeciesOnRow, star_y + 1)) && (numberIndeciesOnRow.contains(starXActual - 1) || numberIndeciesOnRow.contains(starXActual) || numberIndeciesOnRow.contains(starXActual + 1))) {
                            adjacentNumbers.add(buildNumberFromIntegerList(numberIndeciesOnRow, star_y + 1));
                        }
                    }
                }
                numbersAdjacentToStar.add(adjacentNumbers);
            }
        }
        return numbersAdjacentToStar;
    }
}

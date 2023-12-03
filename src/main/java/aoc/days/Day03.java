package aoc.days;

import aoc.util.AocUtil;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day03 {

    private static final String DAY_INPUT_FILE = "day03/input.txt";
    private static boolean printOutput = false;
    private static List<String> sonar;

    private static void init(boolean printOutputParam) throws Exception {
        printOutput = printOutputParam;
        sonar = AocUtil.readFileToStrings(DAY_INPUT_FILE);
    }

    private static List<List<List<Integer>>> findIntegers(Predicate<Character> condition) {
        List<List<List<Integer>>> integerIndexes = new ArrayList<>();

        boolean isParsingNumber = false;
        for (int rowIndex = 0; rowIndex < sonar.size(); rowIndex++) {
            String s = sonar.get(rowIndex);
            List<Integer> IdList = new ArrayList<>();
            ArrayList<List<Integer>> row = new ArrayList<>();
            for (int i = 0; i < s.length(); i++) {
                if (condition.test(s.charAt(i)) && isParsingNumber) {
                    IdList.add(i);
                } else if (condition.test(s.charAt(i)) && !isParsingNumber) {
                    IdList.add(i);
                    isParsingNumber = true;
                } else if (!condition.test(s.charAt(i)) && isParsingNumber) {
                    isParsingNumber = false;
                    row.add(IdList);

                    IdList = new ArrayList<>();
                }
            }
            if (isParsingNumber) {
                row.add(IdList);
            }

            if (printOutput) {
                System.out.printf("input: %s : row: %s \n", s, row);
            }

            integerIndexes.add(row);
        }
        return integerIndexes;
    }

    private static boolean isNumeric(char value) {
        return switch (value) {
            case '0' -> true;
            case '1' -> true;
            case '2' -> true;
            case '3' -> true;
            case '4' -> true;
            case '5' -> true;
            case '6' -> true;
            case '7' -> true;
            case '8' -> true;
            case '9' -> true;
            default -> false;
        };
    }

    private static int parseChar(char c) {
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

    private static boolean isIndexOnGrid(Integer xIndex, Integer yIndex) {
        return yIndex >= 0 && yIndex < sonar.size() && xIndex >= 0 && xIndex < sonar.get(yIndex).length();
    }

    private static List<List<Integer>> GetIndexesBorderingSymbol(List<List<Integer>> indexes, Integer yIndex) {
        List<List<Integer>> indexesFiltered = new ArrayList<>();
        for (int currentNumber = 0; currentNumber < indexes.size(); currentNumber++) {
            boolean found = false;
            for (int xIndexIndex = 0; xIndexIndex < indexes.get(currentNumber).size(); xIndexIndex++) {
                if (borderingPLacesHaveSymbol(indexes.get(currentNumber).get(xIndexIndex), yIndex) && !found) {
                    indexesFiltered.add(indexes.get(currentNumber));
                    found = true;
                }
            }
        }
        return indexesFiltered;
    }

    static Integer buildNumberFromIntegerList(List<Integer> integers, Integer yIndex) {
        Integer result = 0;
        for (Integer i : integers) {
            result = (result * 10) + parseChar(sonar.get(yIndex).charAt(i));
        }
        return result;
    }

    private static boolean borderingPLacesHaveSymbol(int xIndex, int yIndex) {
        return hasSymbol(xIndex - 1, yIndex)
                || hasSymbol(xIndex, yIndex - 1)
                || hasSymbol(xIndex - 1, yIndex - 1)

                || hasSymbol(xIndex + 1, yIndex)
                || hasSymbol(xIndex, yIndex + 1)
                || hasSymbol(xIndex + 1, yIndex + 1)

                || hasSymbol(xIndex - 1, yIndex + 1)
                || hasSymbol(xIndex + 1, yIndex - 1);
    }

    private static boolean hasSymbol(int xIndex, int yIndex) {
        return isIndexOnGrid(xIndex, yIndex) && !isNumeric(sonar.get(yIndex).charAt(xIndex)) && !(sonar.get(yIndex).charAt(xIndex) == '.');
    }

    private static List<List<Integer>> borderingPlacesHaveNumbers(List<List<List<Integer>>> starLocations) {

        List<List<List<Integer>>> numberLocations = findIntegers(Day03::isNumeric);

        List<List<Integer>> numbersDajacentToStar = new ArrayList<>();

        for (int star_y = 0; star_y < starLocations.size(); ++star_y) {
            for (int star_x = 0; star_x < starLocations.get(star_y).size(); ++star_x) {

                List<Integer> adjacentNumbers = new ArrayList<>();
                int star_x_actual = starLocations.get(star_y).get(star_x).get(0);

                if (star_y - 1 >= 0) {
                    for (List<Integer> numberIndeciesOnRow : numberLocations.get(star_y - 1)) {
                        if (!adjacentNumbers.contains(buildNumberFromIntegerList(numberIndeciesOnRow, star_y - 1)) && (numberIndeciesOnRow.contains(star_x_actual - 1) || numberIndeciesOnRow.contains(star_x_actual) || numberIndeciesOnRow.contains(star_x_actual + 1))) {
                            adjacentNumbers.add(buildNumberFromIntegerList(numberIndeciesOnRow, star_y - 1));
                        }
                    }
                }

                for (List<Integer> numberIndeciesOnRow : numberLocations.get(star_y)) {
                    if (!adjacentNumbers.contains(buildNumberFromIntegerList(numberIndeciesOnRow, star_y)) && (numberIndeciesOnRow.contains(star_x_actual - 1) || numberIndeciesOnRow.contains(star_x_actual) || numberIndeciesOnRow.contains(star_x_actual + 1))) {
                        adjacentNumbers.add(buildNumberFromIntegerList(numberIndeciesOnRow, star_y));
                    }
                }

                if (star_y + 1 < sonar.size()) {
                    for (List<Integer> numberIndeciesOnRow : numberLocations.get(star_y + 1)) {
                        if (!adjacentNumbers.contains(buildNumberFromIntegerList(numberIndeciesOnRow, star_y + 1)) && (numberIndeciesOnRow.contains(star_x_actual - 1) || numberIndeciesOnRow.contains(star_x_actual) || numberIndeciesOnRow.contains(star_x_actual + 1))) {
                            adjacentNumbers.add(buildNumberFromIntegerList(numberIndeciesOnRow, star_y + 1));
                        }
                    }
                }
                numbersDajacentToStar.add(adjacentNumbers);
            }
        }
        return numbersDajacentToStar;
    }

    public static Integer solvePart1() throws Exception {
        return solvePart1(false);
    }

    public static Integer solvePart1(boolean printOutput) throws Exception {
        init(printOutput);
        List<List<List<Integer>>> rows = findIntegers(Day03::isNumeric);

        Integer totalSum = 0;

        for (int row_index = 0; row_index < rows.size(); ++row_index) {

            List<List<Integer>> row = rows.get(row_index);

            int finalRow_index = row_index;
            List<Integer> numbers = GetIndexesBorderingSymbol(row, row_index)
                    .stream()
                    .map((List<Integer> number) -> Day03.buildNumberFromIntegerList(number, finalRow_index))
                    .toList();

            Integer sum = numbers.stream()
                    .reduce(Integer::sum)
                    .orElse(0);

            totalSum += sum;
        }

        return totalSum;
    }

    public static BigInteger solvePart2() throws Exception {
        return solvePart2(false);
    }

    public static BigInteger solvePart2(boolean printOutput) throws Exception {
        init(printOutput);

        List<List<List<Integer>>> stars = findIntegers((Character c) -> c == '*');

        if (printOutput) {
            System.out.printf("Found numbers: %s \n", borderingPlacesHaveNumbers(stars).stream()
                    .filter(integers -> integers.size() == 2).collect(Collectors.toList()));
        }
        return borderingPlacesHaveNumbers(stars).stream()
                .filter(integers -> integers.size() == 2)
                .map(integers -> integers.stream().map(BigInteger::valueOf).reduce(BigInteger::multiply).orElse(BigInteger.ZERO))
                .reduce(BigInteger::add)
                .orElse(BigInteger.ZERO);
    }
}

package aoc.days;

import aoc.util.AocUtil;

import java.util.ArrayList;
import java.util.List;

public class Day03 {

    private static final String DAY_INPUT_FILE = "day03/input.txt";
    private static boolean printOutput = false;
    private static List<String> sonar;

    private static void init(boolean printOutputParam) throws Exception {
        printOutput = printOutputParam;
        sonar = AocUtil.readFileToStrings(DAY_INPUT_FILE);
    }

    private static Integer findIntegers() {
        List<Integer> integerIndexes = new ArrayList<>();

        Integer totalSum = 0;

        boolean isParsingNumber = false;
        for (int rowIndex = 0; rowIndex < sonar.size(); rowIndex++) {
            String s = sonar.get(rowIndex);
            List<Integer> IdList = new ArrayList<>();
            ArrayList<List<Integer>> row = new ArrayList<>();
            for (int i = 0; i < s.length(); i++) {
                if (isNumeric(s.charAt(i)) && isParsingNumber) {
                    IdList.add(i);
                } else if (isNumeric(s.charAt(i)) && !isParsingNumber) {
                    IdList.add(i);
                    isParsingNumber = true;
                } else if (!isNumeric(s.charAt(i)) && isParsingNumber) {
                    isParsingNumber = false;
                    row.add(IdList);

                    IdList = new ArrayList<>();
                }
            }

            int finalRowIndex = rowIndex;
            List<Integer> numbers = GetIndexesBorderingSymbol(row, rowIndex)
                    .stream()
                    .map((List<Integer> number) -> Day03.buildNumberFromIntegerList(number, finalRowIndex))
                    .toList();

            Integer sum = numbers.stream()
                    .reduce(Integer::sum)
                    .orElse(0);

            totalSum += sum;

            if (printOutput) {
                System.out.printf("input: %s : Integers: %s : Row Sum: %s : Total: %s\n", s, numbers, sum, totalSum);
            }

            integerIndexes.addAll(numbers);
        }
        return totalSum;
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

    public static Integer solvePart1() throws Exception {
        return solvePart1(false);
    }

    public static Integer solvePart1(boolean printOutput) throws Exception {
        init(printOutput);
        return findIntegers();

    }

    public static Integer solvePart2() throws Exception {
        return solvePart2(false);
    }

    public static Integer solvePart2(boolean printOutput) throws Exception {
        init(printOutput);
        return 0;
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
}

package aoc.days;

import aoc.util.AocUtil;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day01 {

    private static final String DAY_INPUT_FILE = "day01/input.txt";

    private static List<String> sonar;

    private static boolean printOutput = false;

    private static void init(boolean printOutputParam) throws Exception {
        printOutput = printOutputParam;
        sonar = AocUtil.readFileToStrings(DAY_INPUT_FILE);
    }

    private static Integer getDigitFromString(final String s) {
        Pattern pattern = Pattern.compile("[1-9]", Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(s);
        if (!matcher.find()) {
            return 0;
        }
        char first = s.charAt(matcher.start());
        char last = first;
        while (matcher.find()) {
            last = s.charAt(matcher.end() - 1);
        }
        Integer result = Integer.parseInt(String.format("%s%s", first, last));

        if (printOutput) {
            System.out.printf("Input: %s, First: %s, Last: %s, Result: %s\n", s, first, last, result);
        }
        return result;
    }

    private static Integer getDigitsAndNUmbersFromString(final String s) {
        Pattern pattern = Pattern.compile("(?=(one|two|three|four|five|six|seven|eight|nine)).|([1-9])", Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(s);
        if (!matcher.find()) {
            return 0;
        }
        Integer first = buildIntegerFromStringAndIndexes(matcher, 1, 2);
        Integer last = first;
        while (matcher.find()) {
            last = buildIntegerFromStringAndIndexes(matcher, 1, 2);
        }

        Integer result = Integer.parseInt(String.format("%s%s", first, last));

        if (printOutput) {
            System.out.printf("Input: %s, First: %s, Last: %s, Result: %s\n", s, first, last, result);
        }
        return result;
    }


    private static Integer buildIntegerFromStringAndIndexes(Matcher matcher, int stringGroup, int intGroup) {
        if (matcher.group(intGroup) != null) {
            return Integer.parseInt(String.format("%s", matcher.group(intGroup)));
        }

        return parseIntegerFromWord(matcher.group(stringGroup));

    }

    private static Integer parseIntegerFromWord(String s) {
        return switch (s.toLowerCase()) {
            case "one" -> 1;
            case "two" -> 2;
            case "three" -> 3;
            case "four" -> 4;
            case "five" -> 5;
            case "six" -> 6;
            case "seven" -> 7;
            case "eight" -> 8;
            case "nine" -> 9;
            default -> 0;
        };
    }

    public static Integer solvePart1() throws Exception {
        return solvePart1(false);
    }

    public static Integer solvePart1(boolean printOutput) throws Exception {
        init(printOutput);
        Optional<Integer> sum = sonar.stream()
                .map(Day01::getDigitFromString)
                .reduce(Integer::sum);

        return sum.orElseThrow(() -> new Exception("Unable to get Lines to determine sum"));
    }

    public static Integer solvePart2() throws Exception {
        return solvePart2(false);
    }

    public static Integer solvePart2(boolean printOutput) throws Exception {
        init(printOutput);
        Optional<Integer> sum = sonar.stream()
                .map(Day01::getDigitsAndNUmbersFromString)
                .reduce(Integer::sum);

        return sum.orElseThrow(() -> new Exception("Unable to get Lines to determine sum"));
    }
}
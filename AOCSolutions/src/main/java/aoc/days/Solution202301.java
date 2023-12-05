package aoc.days;

import aoc.framework.Day;
import aoc.framework.DaySolution;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DaySolution(year = 2023, day = 1)
public class Solution202301 extends Day {

    private static final String DAY_INPUT_FILE = "202301/input.txt";

    public Solution202301(boolean printOutput) throws RuntimeException {
        super(printOutput, DAY_INPUT_FILE);
    }

    private Integer getDigitFromString(final String s) {
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

        printToOutput(String.format("Input: %s, First: %s, Last: %s, Result: %s\n", s, first, last, result));
        return result;
    }

    private Integer getDigitsAndNUmbersFromString(final String s) {
        Pattern pattern = Pattern.compile("(?=(one|two|three|four|five|six|seven|eight|nine)).|([1-9])", Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(s);
        if (!matcher.find()) {
            return 0;
        }
        Integer first = buildIntegerFromStringAndIndexes(matcher);
        Integer last = first;
        while (matcher.find()) {
            last = buildIntegerFromStringAndIndexes(matcher);
        }

        Integer result = Integer.parseInt(String.format("%s%s", first, last));

        printToOutput(String.format("Input: %s, First: %s, Last: %s, Result: %s\n", s, first, last, result));
        return result;
    }


    private Integer buildIntegerFromStringAndIndexes(Matcher matcher) {
        if (matcher.group(2) != null) {
            return Integer.parseInt(String.format("%s", matcher.group(2)));
        }

        return parseIntegerFromWord(matcher.group(1));

    }

    private Integer parseIntegerFromWord(String s) {
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

    @Override
    public String solvePart1() {
        Optional<Integer> sum = parsedInput.stream()
                .map(this::getDigitFromString)
                .reduce(Integer::sum);

        return sum.orElseThrow(() -> new RuntimeException("Unable to get Lines to determine sum")).toString();
    }

    @Override
    public String solvePart2() {

        Optional<Integer> sum = parsedInput.stream()
                .map(this::getDigitsAndNUmbersFromString)
                .reduce(Integer::sum);

        return sum.orElseThrow(() -> new RuntimeException("Unable to get Lines to determine sum")).toString();
    }
}
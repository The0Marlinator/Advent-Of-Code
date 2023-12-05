package aoc.days;

import aoc.framework.Day;
import aoc.framework.DaySolution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@DaySolution(year = 2023, day = 4)
public class Solution202304 extends Day {
    private static final String DAY_INPUT_FILE = "202304/input.txt";

    private Map<String, Integer> part2IntermittentResults = new HashMap<>();

    public Solution202304(boolean printOutput) throws RuntimeException {
        super(printOutput, DAY_INPUT_FILE);
    }

    @Override
    public String solvePart1() {

        return parsedInput.stream()
                .map(this::getNumberMatching)
                .filter(number -> number > 0)
                .map(number -> 1L << (number - 1))
                .reduce(Long::sum)
                .orElseThrow(() -> new RuntimeException("Unable to read Input"))
                .toString();
    }

    @Override
    public String solvePart2() {
        part2IntermittentResults = new HashMap<>();

        return parsedInput.stream()
                .map(this::scoreCardUsingCopies)
                .reduce(Integer::sum)
                .orElseThrow(() -> new RuntimeException("Unable to handle Input"))
                .toString();
    }

    private long getNumberMatching(String s) {
        String noCard = s.substring(s.indexOf(':') + 1);
        List<String> values = List.of(noCard.split("\\|")[1].split(" "));
        List<String> answers = List.of(noCard.split("\\|")[0].split(" "));

        long count = values.stream()
                .filter(Predicate.not(String::isEmpty))
                .filter(answers::contains)
                .count();

        printToOutput(String.format("Input: %s : Count: %s %n", s, count));

        return count;
    }

    private Integer scoreCardUsingCopies(String s) {

        if (part2IntermittentResults.containsKey(s)) {
            return part2IntermittentResults.get(s);
        }
        long matches = getNumberMatching(s);

        int cardNumber = Integer.parseInt(s.substring(s.indexOf("Card") + 4, s.indexOf(":")).trim());

        Integer count = 1;


        for (int i = 1; i <= matches; ++i) {
            int nextCard = cardNumber + i;
            if (nextCard - 1 >= 0 && nextCard - 1 < parsedInput.size()) {
                count += scoreCardUsingCopies(parsedInput.get(nextCard - 1));
            }
        }

        part2IntermittentResults.put(s, count);

        return count;

    }
}
package aoc.days;

import aoc.util.AocUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Day04 {
    private static final String DAY_INPUT_FILE = "day04/input.txt";
    private static boolean printOutput = false;
    private static List<String> sonar;

    private static Map<String, Integer> result_part2 = new HashMap<>();

    private static void init(boolean printOutputParam) throws Exception {
        printOutput = printOutputParam;
        sonar = AocUtil.readFileToStrings(DAY_INPUT_FILE);
    }

    private static long getNumberMacthing(String s) {
        String noCard = s.substring(s.indexOf(':') + 1);
        List<String> values = List.of(noCard.split("\\|")[1].split(" "));
        List<String> answers = List.of(noCard.split("\\|")[0].split(" "));

        Long count = values.stream()
                .filter(Predicate.not(String::isEmpty))
                .filter(answers::contains)
                .count();

        if (printOutput) {
            System.out.printf("Input: %s : Count: %s %n", s, count);
        }

        return count;
    }

    private static Integer scoreCardUsingCopies(String s){

        if(result_part2.containsKey(s)) {
            return result_part2.get(s);
        }
        Long matches = getNumberMacthing(s);

        Integer cardnumber = Integer.parseInt(s.substring(s.indexOf("Card")+4, s.indexOf(":")).trim());

        Integer count = 1;


        for(int i = 1; i <=matches; ++i) {
            int nextCard = cardnumber+i;
            if(nextCard-1 >=0 && nextCard-1< sonar.size()) {
                count += scoreCardUsingCopies(sonar.get(nextCard-1));
            }
        }

        result_part2.put(s, count);

        return count;

    }

    public static Long solvePart1() throws Exception {
        return solvePart1(false);
    }

    public static Long solvePart1(boolean printOutput) throws Exception {
        init(printOutput);

        return sonar.stream()
                .map(Day04::getNumberMacthing)
                .filter(number -> number > 0 )
                .map(number -> 1L << (number-1))
                .reduce(Long::sum)
                .orElseThrow(() -> new Exception("Unable to read Input"));
    }

    public static Integer solvePart2() throws Exception {
        return solvePart2(false);
    }

    public static Integer solvePart2(boolean printOutput) throws Exception {
        init(printOutput);

        result_part2 = new HashMap<>();

        return sonar.stream()
                .map(Day04::scoreCardUsingCopies)
                .reduce(Integer::sum)
                .orElseThrow(() -> new Exception("Unable to handle Input"));
    }
}
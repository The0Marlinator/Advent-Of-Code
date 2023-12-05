package aoc.framework;
import aoc.framework.util.AocUtil;

import java.util.List;

public abstract class Day {

    private final boolean printOutput;
    protected List<String> parsedInput;
    protected List<String> splitInput;
    private final int dayOfSolution;

    protected Day(boolean printOutput, String filePath, int dayOfSolution) throws RuntimeException {
        this.printOutput = printOutput;
        this.dayOfSolution = dayOfSolution;

        try {
            parsedInput = AocUtil.readFileToStrings(filePath);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    protected void printToOutput(String output) {
        if(printOutput) {
            System.out.println(output);
        }
    }

    protected abstract String solvePart1();

    protected abstract String solvePart2();

    public void runAndPrintOutput() {
        System.out.printf(" > Solution %s to day %s: %s %n", 1, dayOfSolution, solvePart1());
        System.out.printf(" > Solution %s to day %s: %s %n", 2, dayOfSolution, solvePart2());
    }
}

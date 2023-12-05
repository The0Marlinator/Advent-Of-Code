package aoc.framework;

import aoc.framework.util.AocUtil;

import java.util.List;

public abstract class Day {

    private final boolean printOutput;
    protected List<String> parsedInput;

    protected Day(boolean printOutput, String filePath) throws RuntimeException {
        this.printOutput = printOutput;

        try {
            parsedInput = AocUtil.readFileToStrings(filePath);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    protected void printToOutput(String output) {
        if (printOutput) {
            System.out.println(output);
        }
    }

    public abstract String solvePart1();

    public abstract String solvePart2();

}

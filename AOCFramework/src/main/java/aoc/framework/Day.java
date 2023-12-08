package aoc.framework;

import aoc.framework.util.AocUtil;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Day {

    private final boolean printOutput;
    protected final List<String> parsedInput;

    protected Day(boolean printOutput, String filePath) throws RuntimeException {
        this.printOutput = printOutput;

        try {
            parsedInput = AocUtil.readFileToStrings(filePath);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Deprecated as it has been surpassed by Day::printToOutput(Supplier<String> stringSupplier)
     * This uses a functional interface and should therefore not be evaluated until needed.
     */
    @Deprecated
    protected void printToOutput(String output) {
        if (printOutput) {
            System.out.println(output);
        }
    }

    protected void printToOutput(Supplier<String> stringSupplier) {
        if (printOutput && (stringSupplier.get()!=null)) {
                System.out.println(stringSupplier.get());

        }
    }

    public abstract String solvePart1();

    public abstract String solvePart2();

}

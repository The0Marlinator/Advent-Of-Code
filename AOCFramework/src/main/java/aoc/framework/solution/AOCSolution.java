package aoc.framework.solution;

import aoc.framework.util.AocUtil;
import aoc.framework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

public abstract class AOCSolution {

    protected final List<String> parsedInput;
    private final boolean printOutput;

    protected AOCSolution(boolean printOutput) {
        this.printOutput = printOutput;
        Solution annotation = this.getClass().getAnnotation(Solution.class);

        if (annotation == null) {
            throw new RuntimeException("Unable to instantiate a solution as the Solution annotation could not be found. Unbale to get Day and Year of the solution and cannot retrieve inout data because of that");
        }

        try {
            String input = AocUtil.getInputFileFromRemote(annotation.day(), annotation.year());
            parsedInput = StringUtils.splitStringAroundNewLine(input).asStrings();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    protected void printToOutput(String output) {
        if (printOutput) {
            System.out.println(output);
        }
    }

    protected void printToOutput(Supplier<String> stringSupplier) {
        if (printOutput && (stringSupplier.get() != null)) {
            System.out.println(stringSupplier.get());

        }
    }

    public abstract String solvePart1();

    public abstract String solvePart2();

}

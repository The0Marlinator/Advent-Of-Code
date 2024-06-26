package aoc.framework.solution;

import aoc.framework.exception.AOCException;
import aoc.framework.util.AocUtil;
import aoc.framework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

public abstract class AOCSolution {

    protected final List<String> parsedInput;
    private final boolean printOutput;

    protected AOCSolution(boolean printOutput, List<String> input) {
        this.printOutput = printOutput;
        this.parsedInput = input;
    }

    protected AOCSolution(boolean printOutput) throws ExceptionInInitializerError {
        this.printOutput = printOutput;
        Solution annotation = this.getClass().getAnnotation(Solution.class);

        if (annotation == null) {
            throw new ExceptionInInitializerError("Unable to instantiate a solution as the Solution annotation could not be found. Unbale to get Day and Year of the solution and cannot retrieve inout data because of that");
        }

        try {
            String input = AocUtil.getInputFileFromRemote(annotation.day(), annotation.year());
            parsedInput = StringUtils.splitStringAroundNewLine(input).asList();
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
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

    public abstract String solvePart1() throws AOCException;

    public abstract String solvePart2() throws AOCException;

}

package aoc.framework.exception;

public class AOCException extends Exception {

    public AOCException(String message, Exception e) {
        super(message, e);
    }

    public AOCException(String message) {
        super(message);
    }
}

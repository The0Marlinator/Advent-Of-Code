package aoc.framework.util;

import aoc.framework.model.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class StringUtils {

    private StringUtils() {
        // Empty as this is a static class
    }

    public static StringWrapper splitStringAroundNewLine(String input) {
        return new StringWrapper(Arrays.stream(input.split("\n")));
    }

    public static CharacterWrapper splitIntoCharacters(String input) {
        return new CharacterWrapper(Arrays.stream(input.split("")).map(s -> s.charAt(0)));
    }

    public record StringWrapper(Stream<String> string) {
        public List<String> asList() {
            return string.toList();
        }

    }

    public record CharacterWrapper(Stream<Character> characters) {

    }

}

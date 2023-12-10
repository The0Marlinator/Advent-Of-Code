package aoc.framework.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class StringUtils {

    private StringUtils() {
        // Empty as this is a static class
    }

    public static StringWrapper splitStringAroundSpaces(String input) {
        return new StringWrapper(Arrays.stream(input.split(" ")));
    }

    public static StringWrapper splitStringAroundNewLine(String input) {
        return new StringWrapper(Arrays.stream(input.split("\n")));
    }

    public static CharacterWrapper splitIntoCharacters(String input) {
        return new CharacterWrapper(Arrays.stream(input.split("")).map(s -> s.charAt(0)));
    }

    public record StringWrapper(Stream<String> string) {

        public StringWrapper withoutEmpty() {
            return new StringWrapper(string.filter(Predicate.not(String::isEmpty)));
        }

        public StringWrapper withoutBlank() {
            return new StringWrapper(string.filter(Predicate.not(String::isBlank)));
        }

        public List<Long> asLongs() {
            return string.map(Long::parseLong).toList();
        }

        public List<String> asStrings() {
            return string.toList();
        }
    }

    public record CharacterWrapper(Stream<Character> characters) {

    }

}

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


    public static class StringWrapper {

        Stream<String> string;

        private StringWrapper(Stream<String> input) {
            string = input;
        }

        public StringWrapper withoutEmpty() {
            string = string.filter(Predicate.not(String::isEmpty));
            return this;
        }

        public StringWrapper withoutBlank() {
            string = string.filter(Predicate.not(String::isBlank));
            return this;
        }

        public List<Long> asLongs() {
            return string
                    .map(Long::parseLong)
                    .toList();
        }

        public List<String> asStrings() {
            return string.toList();
        }
    }

}

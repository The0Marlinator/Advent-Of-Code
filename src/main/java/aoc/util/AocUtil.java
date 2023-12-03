package aoc.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public final class AocUtil {
    private AocUtil() {
        // EMPTY
    }

    public static List<String> readFileToStrings(final String filename) throws Exception {

        try (InputStream is = AocUtil.class.getClassLoader().getResourceAsStream(filename);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            return reader.lines().collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException("Error reading resource file", e);
        }
    }
}

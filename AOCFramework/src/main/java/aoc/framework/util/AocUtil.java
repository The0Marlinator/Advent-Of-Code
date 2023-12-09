package aoc.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public final class AocUtil {

    private static String adventOfCodeLink = "https://adventofcode.com/%s/day/%s/input";

    private AocUtil() {
        // EMPTY
    }

    public static String getInputFileFromRemote(int day, int year) throws IOException {
        String token = System.getenv("AOC_TOKEN");

        URL url = new URL(String.format(adventOfCodeLink, year, day));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Cookie", "session=" + token);
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int status = connection.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine + "\n");
        }
        in.close();
        connection.disconnect();

        return content.toString();
    }

    public static List<String> readFileToStrings(final String filename) {

        try (InputStream is = AocUtil.class.getClassLoader().getResourceAsStream(filename)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

                return reader.lines().toList();

            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading resource file", e);
        }
    }
}

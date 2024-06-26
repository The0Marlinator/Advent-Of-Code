package aoc.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public final class AocUtil {

    private static final String ADVENT_OF_CODE_PROBLEM_URL = "https://adventofcode.com/%s/day/%s/input";

    private static final String ADVENT_OF_CODE_ANSWER_PATH = "https://adventofcode.com/%s/day/%s/answer";

    private AocUtil() {
        // EMPTY
    }

    /**
     * Gets the personal puzzle input from Advent of Code.
     * NOTE: you need to set the Environmental Variable "AOC_TOKEN" for this to be able to read your session token
     * @param day
     * @param year
     * @return The Input for the puzzle
     * @throws IOException
     */
    public static String getInputFileFromRemote(int day, int year) throws IOException {
        String token = System.getenv("AOC_TOKEN");

        URL url = new URL(String.format(ADVENT_OF_CODE_PROBLEM_URL, year, day));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Cookie", "session=" + token);
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

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

    /**
     * The idea is to submit a solution automatically here but i have not gotten this
     * working yet. So i am not doing this yet.
     * @param day
     * @param year
     * @param part
     * @return
     * @throws IOException
     */
    public static boolean submitAnswer(int day, int year, int part) throws IOException {
        String token = System.getenv("AOC_TOKEN");

        URL url = URI.create(String.format(ADVENT_OF_CODE_ANSWER_PATH, year, day)).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Cookie", "session=" + token);
        connection.setRequestProperty("answer", "1");
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

        //return content.toString();

        return false;
    }
}

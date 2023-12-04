package aoc.factory;

import aoc.days.Day01;
import aoc.days.Day02;
import aoc.days.Day03;
import aoc.days.Day04;
import aoc.framework.Day;
import java.util.ArrayList;
import java.util.List;

public class DaySolutionFactory {
  public static List<? extends Day> createDaySolutions() {
    List<Day> result = new ArrayList<>();
    result.add(new Day01(false));
    result.add(new Day02(false));
    result.add(new Day03(false));
    result.add(new Day04(false));
    return result;
  }
}

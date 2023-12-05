package aoc.factory;

import aoc.days.Solution202301;
import aoc.days.Solution202302;
import aoc.days.Solution202303;
import aoc.days.Solution202304;
import aoc.days.Solution202305;
import aoc.framework.Day;
import java.util.ArrayList;
import java.util.List;

public class DaySolutionFactory {
  public static List<? extends Day> createDaySolutions() {
    List<Day> result = new ArrayList<>();
    result.add(new Solution202301(false));
    result.add(new Solution202302(false));
    result.add(new Solution202303(false));
    result.add(new Solution202304(false));
    result.add(new Solution202305(false));
    return result;
  }
}

package aoc.framework.solution;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A simple annotation so that we know what day and year each solution is from
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Solution {

    int year();

    int day();
}

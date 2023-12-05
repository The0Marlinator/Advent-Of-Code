# My solutions to the Advent Of COde

Note the actual Solutions are in the AOC Solutions Module (They are annotatedn with the Year and day)

The rest is "Framework Code" and a runner which uses the annotations to dtermine which soltion is for which day.

## Adding a new Solution
To add a new solution, create a new Solution class in the AOCSOlutions Module that Extends they Day abstract class and add the DaySolution Annotation as show below.

```java 
@DaySolution(year = 2023, day = 1)
public class Solution202301 extends Day {
```

The annotation processor in the respective module should pick it up when the project is building and it should directly be available.

## Actions
There is an action in this repository which will cause github to run and build this project on a push or merge.


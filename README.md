# Solutions to Advent of Code

Note the actual Solutions are in the AOC Solutions Module (They are annotatedn with the Year and day)

The rest is "Framework Code" and a runner which uses the annotations to dtermine which soltion is for which day.

## Adding a new Solution
To add a new solution, create a new Solution class in the AOCSOlutions Module that Extends they Day abstract class and add the DaySolution Annotation as show below.

It should also take a boolean if it can print to ouptut. This is just pushed to the super (Day) class which provides the method PrintToOutput(String) which provides easy printing of strings.

```java 
@Solution(year = 2023, day = 9)
public class Solution202309 extends AOCSolution {


    public Solution202309(boolean printOutput) {
        super(printOutput);
    }

    @Override
    public String solvePart1() { return null; }

    @Override
    public String solvePart2() { return null;}

```

The annotation processor in the respective module should pick it up when the project is building and it should directly be available.

## Running
To run this an environmental variable (AOC_TOKEN) needs to be provided which contains the cookie session token to be used in order to connect to advent of code to pull the input for the day.
This will use the day and year values configured in the annotation for the class.

This is to remove the need on input files whihc will need to be packaged and refered to. The Solution constructor simply quesries the data for us when running.


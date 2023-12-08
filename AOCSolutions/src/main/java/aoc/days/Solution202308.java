package aoc.days;

import aoc.framework.Day;
import aoc.framework.DaySolution;
import aoc.framework.model.Node;
import aoc.framework.model.Pair;

import java.util.*;
import java.util.function.Predicate;

@DaySolution(year = 2023, day = 8)
public class Solution202308 extends Day {

    private static final String INPUT_FILE = "202308/input.txt";

    private final List<Integer> instructions;

    private final Map<String, Node> allNodes;

    public Solution202308(boolean printToOutput) {
        super(true, INPUT_FILE);
        allNodes = buildNodeList();
        instructions = Arrays.stream(parsedInput.getFirst().split(""))
                .map(s -> s.equals("L") ? 0 : 1)
                .toList();
    }

    @Override
    public String solvePart1() {
        Node currentNode = allNodes.get("AAA");
        Node targetNode = allNodes.get("ZZZ");
        int stepCount = 0;
        if (currentNode == null || targetNode == null) {
            return "Unable to complete Solution Because the source or target Node could not be found";
        }
        int intrsuctionIndex = 0;
        while (!currentNode.equals(targetNode)) {
            currentNode = currentNode.getChildAtPosition(instructions.get(intrsuctionIndex));
            stepCount += 1;
            intrsuctionIndex = (intrsuctionIndex + 1) % instructions.size();
            if (currentNode.equals(targetNode)) {
                return "" + stepCount;
            }
        }
        return null;
    }

    @Override
    public String solvePart2() {
        List<Node> startNodes = getNodesEnding('A');
        List<Node> currentNodes = getNodesEnding('A');
        List<Node> endNodes = getNodesEnding('Z');

        Map<Node, Long> firstCycleLength = new HashMap<>();
       List<Pair<Node, Long>> cycleLengths = new ArrayList<>();
        int intrsuctionIndex = 0;
        long stepCount = 0;

        while (cycleLengths.size() != startNodes.size()) {
            List<Node> newCurrents = new ArrayList<>();
            stepCount += 1;
            for (int i = 0; i < currentNodes.size(); i++) {
                Node childAtPosition = currentNodes.get(i).getChildAtPosition(instructions.get(intrsuctionIndex));

                if(endNodes.contains(childAtPosition)) {
                    if(firstCycleLength.get(startNodes.get(i)) != null) {
                        int finalI = i;
                        long cycleLength = stepCount-firstCycleLength.get(startNodes.get(i));
                        printToOutput(() -> String.format("The traversal starting with %s has arrived at at an endNode after %s cycleLength.", startNodes.get(finalI), cycleLength));
                        cycleLengths.add(new Pair<>(startNodes.get(i), cycleLength));
                    } else {
                        long finalStepCount = stepCount;
                        int finalI1 = i;
                        printToOutput(() -> String.format("The traversal starting with %s has arrived at at an endNode after %s steps.", startNodes.get(finalI1), finalStepCount));
                        firstCycleLength.put(startNodes.get(i), stepCount);
                    }
                }
                newCurrents.add(childAtPosition);
            }
            intrsuctionIndex = (intrsuctionIndex + 1) % instructions.size();
            currentNodes = newCurrents;
        }
        return "" + smallestCommonGround(cycleLengths, firstCycleLength);
    }


    private Long smallestCommonGround(List<Pair<Node, Long>> counts, Map<Node, Long> starts) {

        List<Long> sums = starts.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .toList();

        List<Long> cycleLenghts = counts.stream()
                .sorted(Comparator.comparing(Pair::getFirst))
                .map(Pair::getSecond)
                .toList();

        while (new HashSet<>(sums).size() != 1) {
            List<Long> newSums = new LinkedList<>();
            for (int i = 0; i<sums.size(); i++){
                newSums.add(sums.get(i)+cycleLenghts.get(i));
            }
            List<Long> finalSums = sums;
            printToOutput(finalSums::toString);
            sums = newSums;
        }
        return sums.getFirst();
    }

    private List<Node> getNodesEnding(char c) {
        return allNodes.keySet().stream()
                .filter(s -> s.charAt(s.length() - 1) == c)
                .map(allNodes::get)
                .toList();
    }

    private Map<String, Node> buildNodeList() {
        HashMap<String, Node> allNodes = new HashMap<>();
        for (String line : parsedInput.subList(2, parsedInput.size())) {
            String[] split = line.split("=");

            String currentNode = split[0].trim();

            List<Node> children = Arrays.stream(split[1].split("[(,)]"))
                    .map(String::trim)
                    .filter(Predicate.not(String::isEmpty))
                    .map(s -> getOrPutNodeInMap(s, allNodes))
                    .toList();

            if(!allNodes.containsKey(currentNode)) {
                allNodes.put(currentNode, new Node(currentNode, children));
            } else {
                allNodes.get(currentNode).addChildren(children);
            }
        }
        return allNodes;
    }

    private Node getOrPutNodeInMap(String s, HashMap<String, Node> n) {
        if(n.containsKey(s)) {
            return n.get(s);
        }
        Node newNode = new Node(s);
        n.put(s, newNode);
        return newNode;
    }
}

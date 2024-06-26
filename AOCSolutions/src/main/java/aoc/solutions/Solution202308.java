package aoc.solutions;

import aoc.framework.model.graph.Node;
import aoc.framework.solution.AOCSolution;
import aoc.framework.solution.Solution;
import aoc.framework.util.MathUtils;

import java.util.*;
import java.util.function.Predicate;

@Solution(year = 2023, day = 8)
public class Solution202308 extends AOCSolution {

    private final List<Integer> instructions;

    private final Map<String, Node> allNodes;

    public Solution202308(boolean printToOutput) {
        super(printToOutput);
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
        int instructionIndex = 0;
        while (!currentNode.equals(targetNode)) {
            currentNode = currentNode.getChildAtPosition(instructions.get(instructionIndex));
            stepCount += 1;
            instructionIndex = (instructionIndex + 1) % instructions.size();
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
        int instructionIndex = 0;
        long stepCount = 0;

        while (!currentNodes.isEmpty()) {
            List<Node> newCurrents = new ArrayList<>();
            stepCount += 1;
            for (int i = 0; i < currentNodes.size(); i++) {
                Node childAtPosition = currentNodes.get(i).getChildAtPosition(instructions.get(instructionIndex));

                if (endNodes.contains(childAtPosition)) {
                    printToOutput(String.format("The traversal starting with %s has arrived at at an endNode after %s steps.", startNodes.get(i), stepCount));
                    firstCycleLength.put(startNodes.get(i), stepCount);
                    continue;
                }
                newCurrents.add(childAtPosition);
            }
            instructionIndex = (instructionIndex + 1) % instructions.size();
            currentNodes = newCurrents;
        }
        return "" + MathUtils.lcm(firstCycleLength.values().stream().toList());
    }

    private List<Node> getNodesEnding(char c) {
        return allNodes.keySet().stream()
                .filter(s -> s.charAt(s.length() - 1) == c)
                .map(allNodes::get)
                .toList();
    }

    private Map<String, Node> buildNodeList() {
        HashMap<String, Node> collectedNodes = new HashMap<>();
        for (String line : parsedInput.subList(2, parsedInput.size())) {
            String[] split = line.split("=");

            String currentNode = split[0].trim();

            List<Node> children = Arrays.stream(split[1].split("[(,)]"))
                    .map(String::trim)
                    .filter(Predicate.not(String::isEmpty))
                    .map(s -> getOrPutNodeInMap(s, collectedNodes))
                    .toList();

            if (!collectedNodes.containsKey(currentNode)) {
                collectedNodes.put(currentNode, new Node(currentNode, children));
            } else {
                collectedNodes.get(currentNode).addChildren(children);
            }
        }
        return collectedNodes;
    }

    private Node getOrPutNodeInMap(String s, HashMap<String, Node> n) {
        if (n.containsKey(s)) {
            return n.get(s);
        }
        Node newNode = new Node(s);
        n.put(s, newNode);
        return newNode;
    }
}

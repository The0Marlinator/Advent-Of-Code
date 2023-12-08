package aoc.days;

import aoc.framework.Day;
import aoc.framework.DaySolution;
import aoc.framework.model.Node;

import java.util.*;
import java.util.function.Predicate;

@DaySolution(year = 2023, day = 8)
public class Solution202308 extends Day {

    private static final String INPUT_FILE = "202308/input.txt";

    private final List<Integer> instructions;

    private Map<String, Node> allNodes;

    public Solution202308(boolean printToOutput) {
        super(printToOutput, INPUT_FILE);
        allNodes = buildNodeList();
        instructions = Arrays.stream(parsedInput.getFirst().split(""))
                .map( s -> s.equals("L")? 0: 1)
                .toList();
    }

    @Override
    public String solvePart1() {
        Node currentNode = allNodes.get("AAA");
        Node targetNode = allNodes.get("ZZZ");
        int stepCount = 0;
        if(currentNode == null || targetNode == null) {
            return "Unable to complete Solution Because the source or target Node could not be found";
        }
        int intrsuctionIndex = 0;
        while (!currentNode.equals(targetNode)) {
            currentNode = currentNode.traverseToSpecificChild(instructions.get(intrsuctionIndex));
            stepCount+=1;
            intrsuctionIndex = (intrsuctionIndex+1)%instructions.size();
            if(currentNode.equals(targetNode)) {
                return ""+stepCount;
            }
        }
        return null;
    }

    @Override
    public String solvePart2() {
       List<Node> currentNodes = getNodesEnding('A');
       List<Node> endNodes = getNodesEnding('Z');

        int intrsuctionIndex = 0;
        long stepCount = 0;

       while (!endNodes.containsAll(currentNodes)) {
           List<Node> newCurrents = new ArrayList<>();
           stepCount+=1;
           for (Node current: currentNodes) {
               newCurrents.add(current.traverseToSpecificChild(instructions.get(intrsuctionIndex)));
           }
           currentNodes= newCurrents;
           intrsuctionIndex = (intrsuctionIndex+1)%instructions.size();
       }
       return ""+stepCount;
    }

    private List<Node> getNodesEnding(char c) {
        return allNodes.keySet().stream()
                .filter(s -> s.charAt(s.length()-1) == c)
                .map(s -> allNodes.get(s))
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

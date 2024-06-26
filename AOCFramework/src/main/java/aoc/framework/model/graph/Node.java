package aoc.framework.model.graph;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node> {

    private List<Node> children;

    private String label;

    public Node(String label) {
        children = new ArrayList<>();
        this.label = label;
    }

    public Node(String label, List<Node> children) {
        this.children = new ArrayList<>();
        this.children.addAll(children);
        this.label = label;
    }

    public void addChildren(List<Node> children) {
        this.children.addAll(children);
    }

    public Node getChildAtPosition(int child) {
        return children.get(child);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Node)) {
            return false;
        }

        return ((Node) other).label.equals(label);
    }

    @Override
    public String toString() {
        return "(Node:" + label + ")";
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }

    @Override
    public int compareTo(Node o) {
        return label.compareTo(o.label);
    }
}

package aoc.framework.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {

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

    public Node traverseToSpecificChild(int child) {
        return children.get(child);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Node)) {
            return false;
        }

        return ((Node) other).label.equals(label);
    }
}

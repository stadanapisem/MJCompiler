package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.concepts.Struct;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Tree {

    private Node root;

    public Tree() {
        root = null;
    }

    public void insert(Struct name) {

        if (root == null) {
            root = new Node(name, null);
        } else {
            root.children.add(new Node(name, root));
        }
    }

    public void insert(Struct base, Struct derived) {
        Node baseNode = search(base);
        baseNode.children.add(new Node(derived, baseNode));
    }

    public Node search(Struct name) {
        LinkedList<Node> Q = new LinkedList<>();
        Q.add(root);
        while (!Q.isEmpty()) {
            Node iter = Q.pollFirst();
            for (int i = 0; i < iter.children.size(); i++) {
                if (iter.children.get(i).data.equals(name)) {
                    return iter.children.get(i);
                }

                Q.add(iter.children.get(i));
            }
        }

        return null;
    }

    public boolean search(Struct base, Struct derived) {
        Node derivedNode = search(derived);
        while (derivedNode != null) {
            if (derivedNode.data.equals(base)) {
                return true;
            }

            derivedNode = derivedNode.parent;
        }

        return false;
    }

    private static class Node {
        Struct data;
        List<Node> children = null;
        Node parent = null;

        public Node(Struct data, Node parent) {
            this.data = data;
            children = new ArrayList<>();
            this.parent = parent;
        }
    }
}

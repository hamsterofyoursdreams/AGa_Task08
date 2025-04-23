package org.AGa.rsService;

import java.util.*;

public class Trie {
    private static class Node {
        Map<Character, Node> children = new HashMap<>();
        boolean isWord = false;
    }

    private final Node root = new Node();

    public void insert(String word) {
        Node node = root;
        for (char ch : word.toCharArray()) {
            node = node.children.computeIfAbsent(ch, c -> new Node());
        }
        node.isWord = true;
    }

    public List<String> search(String prefix) {
        List<String> results = new ArrayList<>();
        Node node = root;
        for (char ch : prefix.toCharArray()) {
            node = node.children.get(ch);
            if (node == null) return results;
        }
        collect(node, new StringBuilder(prefix), results);
        return results;
    }

    private void collect(Node node, StringBuilder prefix, List<String> results) {
        if (node.isWord) {
            results.add(prefix.toString());
        }
        for (Map.Entry<Character, Node> entry : node.children.entrySet()) {
            prefix.append(entry.getKey());
            collect(entry.getValue(), prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }
}


package src.Solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public abstract class WordLadderSolver {

    protected HashSet<String> dict = new HashSet<>();
    protected double timeExec = 0;
    protected int countVisited = 0;

    public WordLadderSolver(HashSet<String> dict) {
        this.dict = dict;
    }

    public double getTimeExec() {
        return this.timeExec;
    }

    public int getCountVisited() {
        return this.countVisited;
    }

    public List<String> expandWord(String word) {
        List<String> newWords = new ArrayList<>();
        char[] chars = word.toLowerCase().toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char oldChar = chars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c != oldChar) {
                    chars[i] = c;
                    String newWord = new String(chars);
                    if (dict.contains(newWord)) {
                        newWords.add(newWord);
                    }
                }
            }
            chars[i] = oldChar;
        }

        return newWords;
    }

    public List<String> buildPath(Node node) {
        LinkedList<String> path = new LinkedList<>();
        while (node != null) {
            path.addFirst(node.word);
            node = node.parent;
        }

        return path;
    }

    public int hammingDistance(String s1, String s2) {
        if (s1.length() != s2.length()) {
            throw new IllegalArgumentException("Strings must be of equal length");
        }

        int distance = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }

    public abstract List<String> calc(String start, String end) throws Exception;

    public List<String> solve(String start, String end) throws Exception {
        this.timeExec = 0;
        this.countVisited = 0;
        double startTime = System.nanoTime();
        try {
            List<String> result = this.calc(start, end);
            double endTime = System.nanoTime();

            this.timeExec = (endTime - startTime) / 1_000_000;

            return result;
        } catch (Exception e) {
            double endTime = System.nanoTime();

            this.timeExec = (endTime - startTime) / 1_000_000;
            throw e;
        }

    }

    public class Node implements Comparable<Node> {
        String word;
        Node parent;
        Integer cost;

        public Node(String word, Node parent, Integer cost) {
            this.word = word;
            this.parent = parent;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node other) {
            return this.cost - other.cost;
        }
    }

}
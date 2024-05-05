package src.Solver;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class AStarSolver extends WordLadderSolver {
    public AStarSolver(HashSet<String> dict) {
        super(dict);
    }

    @Override
    public List<String> calc(String start, String end) throws Exception {

        if (!dict.contains(start)) {
            throw new Exception("Start word doesn't exist in dictionary");
        }

        if (!dict.contains(end)) {
            throw new Exception("End word doesn't exist in dictionary");

        }

        if (start.length() != end.length()) {
            throw new Exception("Length of the start and end word must be the same");
        }

        HashMap<String, Integer> visitedCost = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>();

        queue.add(new Node(start, null, 0 + hammingDistance(start, end)));
        visitedCost.put(start, 0);

        while (!queue.isEmpty()) {
            Node currNode = queue.poll();

            if (currNode.word.equals(end)) {

                return buildPath(currNode);
            }

            for (String newWord : expandWord(currNode.word)) {
                int newGCost = visitedCost.get(currNode.word) + 1;
                if (!visitedCost.containsKey(newWord) || visitedCost.get(newWord) > newGCost) {
                    if (!visitedCost.containsKey(newWord)) {
                        this.countVisited++;
                    }
                    int newHCost = hammingDistance(newWord, end);
                    int newCost = newGCost + newHCost;
                    queue.add(new Node(newWord, currNode, newCost));
                    visitedCost.put(newWord, newGCost);
                }
            }

        }

        return Collections.emptyList();

    }
}

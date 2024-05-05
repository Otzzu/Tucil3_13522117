package src.Solver;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class UCSSolver extends WordLadderSolver{
    public UCSSolver(HashSet<String> dict) {
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

        queue.add(new Node(start, null, 0));
        visitedCost.put(start, 0);

        while (!queue.isEmpty()) {
            Node currNode = queue.poll();

            if (currNode.word.equals(end)) {
                LinkedList<String> path = new LinkedList<>();
                while (currNode != null) {
                    path.addFirst(currNode.word);
                    currNode = currNode.parent;
                }

                return path;
            }

            for (String newWord : expandWord(currNode.word)) {
                Integer newCost = currNode.cost + 1;
                if (!visitedCost.containsKey(newWord) || visitedCost.get(newWord) > newCost) {
                    if (!visitedCost.containsKey(newWord)) {
                        this.countVisited++;
                    }
                    queue.add(new Node(newWord, currNode, newCost));
                    visitedCost.put(newWord, newCost);
                }
            }

        }

        return Collections.emptyList();

    }
}

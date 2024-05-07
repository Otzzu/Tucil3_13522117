package src.Solver;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class GBFSSolver extends WordLadderSolver {
    public GBFSSolver(HashSet<String> dict) {
        super(dict);
    }

    @Override
    public List<String> calc(String start, String end) throws Exception {

        if (!dict.contains(start)) {
            throw new Exception("Start word doen't exist in dictionary");
        }

        if (!dict.contains(end)) {
            throw new Exception("End word doen't exist in dictionary");

        }

        if (start.length() != end.length()) {
            throw new Exception("Length of the start and end word must be the same");
        }

        HashSet<String> visitedCost = new HashSet<>();
        PriorityQueue<Node> queue = new PriorityQueue<>();

        queue.add(new Node(start, null, hammingDistance(start, end)));
        visitedCost.add(start);

        while (!queue.isEmpty()) {
            Node currNode = queue.poll();

            // queue.clear();

            if (currNode.word.equals(end)) {

                return buildPath(currNode);
            }

            for (String newWord : expandWord(currNode.word)) {
                if (!visitedCost.contains(newWord)) {
                    this.countVisited++;
                    // count++;
                    Integer newCost = hammingDistance(newWord, end);
                    queue.add(new Node(newWord, currNode, newCost));
                    visitedCost.add(newWord);
                }
            }

        }

        return Collections.emptyList();

    }
}

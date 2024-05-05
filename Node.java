public class Node implements Comparable<Node> {
    String word;
    int cost;

    public Node(String word, int cost) {
        this.word = word;
        this.cost = cost;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.cost, other.cost);
    }
}
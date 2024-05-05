import java.io.*;
import java.util.*;

public class WordLadderSolver {
    private Set<String> wordList;
    private Map<String, List<String>> graph;

    public WordLadderSolver(Set<String> wordList) {
        this.wordList = wordList;
        buildGraph();
    }

    private void buildGraph() {
        graph = new HashMap<>();
        for (String word : wordList) {
            graph.put(word, new ArrayList<>());
            char[] chars = word.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char originalChar = chars[i];
                for (char c = 'A'; c <= 'Z'; c++) {
                    if (c != originalChar) {
                        chars[i] = c;
                        String neighbor = new String(chars);
                        if (wordList.contains(neighbor)) {
                            graph.get(word).add(neighbor);
                        }
                    }
                }
                chars[i] = originalChar;
            }
        }
    }

    public List<String> ucs(String startWord, String endWord) {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();
        Map<String, Integer> pathCost = new HashMap<>();
        Map<String, String> parent = new HashMap<>();

        queue.offer(new Node(startWord, 0));
        pathCost.put(startWord, 0);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            String currentWord = currentNode.word;
            int currentCost = currentNode.cost;

            if (currentWord.equals(endWord)) {
                return reconstructPath(startWord, endWord, parent);
            }

            visited.add(currentWord);

            for (String neighbor : graph.get(currentWord)) {
                int newCost = currentCost + 1; // Assuming each edge has a cost of 1
                if (!visited.contains(neighbor) && (!pathCost.containsKey(neighbor) || newCost < pathCost.get(neighbor))) {
                    pathCost.put(neighbor, newCost);
                    parent.put(neighbor, currentWord);
                    queue.offer(new Node(neighbor, newCost));
                }
            }
        }

        return null; // No path found
    }

    // A* Search
    public List<String> aStar(String startWord, String endWord) {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();
        Map<String, Integer> pathCost = new HashMap<>();
        Map<String, String> parent = new HashMap<>();

        queue.offer(new Node(startWord, 0 + heuristic(startWord, endWord))); // f = g + h
        pathCost.put(startWord, 0);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            String currentWord = currentNode.word;
            int currentCost = currentNode.cost;

            if (currentWord.equals(endWord)) {
                return reconstructPath(startWord, endWord, parent);
            }

            visited.add(currentWord);

            for (String neighbor : graph.get(currentWord)) {
                int newCost = currentCost + 1; // Assuming each edge has a cost of 1
                if (!visited.contains(neighbor) && (!pathCost.containsKey(neighbor) || newCost < pathCost.get(neighbor))) {
                    pathCost.put(neighbor, newCost);
                    parent.put(neighbor, currentWord);
                    queue.offer(new Node(neighbor, newCost + heuristic(neighbor, endWord))); // f = g + h
                }
            }
        }

        return null; // No path found
    }

    // Greedy Best First Search
    public List<String> greedyBestFirstSearch(String startWord, String endWord) {
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n -> heuristic(n.word, endWord)));
        Set<String> visited = new HashSet<>();
        Map<String, String> parent = new HashMap<>();

        queue.offer(new Node(startWord, 0));

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            String currentWord = currentNode.word;

            if (currentWord.equals(endWord)) {
                return reconstructPath(startWord, endWord, parent);
            }

            visited.add(currentWord);

            for (String neighbor : graph.get(currentWord)) {
                if (!visited.contains(neighbor)) {
                    parent.put(neighbor, currentWord);
                    queue.offer(new Node(neighbor, heuristic(neighbor, endWord)));
                }
            }
        }

        return null; // No path found
    }

    private int heuristic(String word, String endWord) {
        // Heuristic function (e.g., Hamming distance, Levenshtein distance, etc.)
        // Here, we use Hamming distance as a simple heuristic
        int distance = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != endWord.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }

    private List<String> reconstructPath(String startWord, String endWord, Map<String, String> parent) {
        List<String> path = new ArrayList<>();
        String currentWord = endWord;
        while (!currentWord.equals(startWord)) {
            path.add(0, currentWord);
            currentWord = parent.get(currentWord);
        }
        path.add(0, startWord);
        return path;
    }

    public static void main(String[] args) {
        Set<String> wordList = new HashSet<>();
        loadWordsFromFile("dictionary.txt", wordList);

        WordLadderSolver solver = new WordLadderSolver(wordList);

        Scanner scanner = new Scanner(System.in);
        try{
            System.out.print("Enter start word: ");
            String startWord = scanner.nextLine().trim().toUpperCase();

            System.out.print("Enter end word: ");
            String endWord = scanner.nextLine().trim().toUpperCase();

            // Call the appropriate search function here and print the word ladder
            if(!wordList.contains(startWord) || !wordList.contains(endWord)){
                System.out.println("I'm sorry, but the word that you choose is not in the creator's dictionary");
                return;
            }
            else{
                if(startWord.length() != endWord.length()){
                    System.out.println("I'm sorry, but the word didn't have the same length");
                    return;
                }
                else{
                    //Insert UCS, A*, and GFS
                    System.out.println("UCS:");
                    long startTimeUCS = System.nanoTime();
                    List<String> pathUCS = solver.ucs(startWord, endWord);
                    long endTimeUCS = System.nanoTime();

                    if (pathUCS != null) {
                        System.out.println("Shortest path: " + pathUCS);
                    } else {
                        System.out.println("No path found.");
                    }
                    System.out.println("Time taken (UCS): " + (endTimeUCS - startTimeUCS) / 1_000);

                    System.out.println("Greedy Best First Search:");
                    long startTimeGFS = System.nanoTime();
                    List<String> pathGFS = solver.greedyBestFirstSearch(startWord, endWord);
                    long endTimeGFS = System.nanoTime();
                    
                    if (pathGFS != null) {
                        System.out.println("Shortest path: " + pathGFS);
                    } else {
                        System.out.println("No path found.");
                    }
                    System.out.println("Time taken (UCS): " + (endTimeGFS - startTimeGFS) / 1_000);

                    System.out.println("A*:");
                    long startTimeAstar = System.nanoTime();
                    List<String> pathAstar = solver.aStar(startWord, endWord);
                    long endTimeAstar = System.nanoTime();
                    
                    if (pathAstar != null) {
                        System.out.println("Shortest path: " + pathAstar);
                    } else {
                        System.out.println("No path found.");
                    }
                    System.out.println("Time taken (UCS): " + (endTimeAstar - startTimeAstar) / 1_000);
                }
            }
        }
        finally{
            scanner.close();
        }
    }

    private static void loadWordsFromFile(String filename, Set<String> wordList) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Replace periods and underscores with no space and convert to uppercase
                String word = line.trim().toUpperCase().replaceAll("[._]", "");
                wordList.add(word);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}

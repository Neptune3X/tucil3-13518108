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

    // Uniform Cost Search (UCS)
    public List<String> ucs(String startWord, String endWord) {
        // Implement UCS algorithm here
        return null;
    }

    // A* Search
    public List<String> aStar(String startWord, String endWord) {
        // Implement A* algorithm here
        return null;
    }

    // Greedy Best First Search
    public List<String> greedyBestFirstSearch(String startWord, String endWord) {
        // Implement Greedy Best First Search algorithm here
        return null;
    }

    public static void main(String[] args) {
        Set<String> wordList = new HashSet<>();
        loadWordsFromFile("dictionary.txt", wordList);

        WordLadderSolver solver = new WordLadderSolver(wordList);

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter start word: ");
        String startWord = scanner.nextLine().trim().toUpperCase();

        System.out.print("Enter end word: ");
        String endWord = scanner.nextLine().trim().toUpperCase();

        // Call the appropriate search function here and print the word ladder
    }

    private static void loadWordsFromFile(String filename, Set<String> wordList) {
        try (Scanner scanner = new Scanner(new FileReader(filename))) {
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine().trim().toUpperCase();
                wordList.add(word);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}

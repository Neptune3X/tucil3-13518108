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
        
        return null;
    }

    // A* Search
    public List<String> aStar(String startWord, String endWord) {
        
        return null;
    }

    // Greedy Best First Search
    public List<String> greedyBestFirstSearch(String startWord, String endWord) {
        
        return null;
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
                return;
            }
            else{
                if(startWord.length() != endWord.length()){
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

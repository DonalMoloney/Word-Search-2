package finalProject.wordSearch2;

import java.util.*;

/**
 * A class representing a node in the Trie data structure.
 */
class TrieNode {
    Map<Character, TrieNode> children; // A map of child nodes keyed by their characters
    boolean isEndOfWord; // A boolean flag indicating whether the node represents the end of a word

    /**
     * Constructor for the TrieNode class.
     */
    public TrieNode() {
        children = new HashMap<>();
        isEndOfWord = false;
    }
}

/**
 * A class representing a Trie data structure.
 */
class Trie {
    TrieNode root; // The root node of the Trie

    /**
     * Constructor for the Trie class.
     */
    public Trie() {
        root = new TrieNode();
    }

    /**
     * Inserts a word into the Trie.
     * 
     * @param word the word to be inserted
     */
    public void insert(String word) {
        TrieNode node = root;
        // Traverse the Trie using the characters in the word
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            // If the current node doesn't have a child node with the current character, create one
            if (!node.children.containsKey(c)) {
                node.children.put(c, new TrieNode());
            }
            // Move down the Trie to the child node with the current character
            node = node.children.get(c);
        }
        // Mark the final node as the end of a word
        node.isEndOfWord = true;
    }

    /**
     * Searches for a word in the Trie.
     * 
     * @param word the word to be searched for
     * @return true if the word is found, false otherwise
     */
    public boolean search(String word) {
        TrieNode node = root;
        // Traverse the Trie using the characters in the word
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            // If the current node doesn't have a child node with the current character, the word isn't in the Trie
            if (!node.children.containsKey(c)) {
                return false;
            }
            // Move down the Trie to the child node with the current character
            node = node.children.get(c);
        }
        // If the final node is marked as the end of a word, the word is in the Trie
        return node.isEndOfWord;
    }

    /**
     * Checks if a prefix is a prefix of any word in the Trie.
     * 
     * @param prefix the prefix to be checked
     * @return true if the prefix is a prefix of any word in the Trie, false otherwise
     */
    public boolean startsWith(String prefix) {
        TrieNode node = root;
        // Traverse the Trie using the characters in the prefix
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            // If the current node doesn't have a child node with the current character, the prefix isn't a prefix of any word in the Trie
            if (!node.children.containsKey(c)) {
                return false;
            }
            // Move down the Trie to the child node with the current character
            node = node.children.get(c);
        }
        // The prefix is a prefix of some word in the Trie if it reaches a node marked as the end of a word or if it has child nodes
        return node.isEndOfWord || !node.children.isEmpty();
    }
}

/**
 * A class representing a Word Search 2 puzzle.
 */
public class WordSearch2 {
	private int ROWS;
	private int COLS;
	private String[] WORDS;
	private char[][] BOARD;
	private Trie TRIEOBJ;

	/**
	 * Constructor for the WordSearch2 class.
	 * 
	 * @param board the 2D character array representing the board
	 * @param words the array of words to be found in the board
	 * @throws IllegalArgumentException if the board or words array is invalid or contains invalid words
	 */
	public WordSearch2(char[][] board, String[] words) throws IllegalArgumentException {
	    // Check if the board or words array is invalid
	    if (board == null || board.length == 0 || board[0].length == 0 || words == null || words.length == 0) {
	        throw new IllegalArgumentException("Invalid board or words array");
	    }

	    // Check if the words array contains duplicates
	    Set<String> uniqueWords = new HashSet<>(Arrays.asList(words));
	    if (uniqueWords.size() != words.length) {
	        throw new IllegalArgumentException("The words searched for must all be unique");
	    }
	    
	    // Check if the board has equal row lengths
	    int rowLength = board[0].length;
	    for (int i = 1; i < board.length; i++) {
	        if (board[i].length != rowLength) {
	            throw new IllegalArgumentException("Board needs to have m x n dimension. Ensure all rows are of equal length.");
	        }
	    }

	    // Initialize class variables
	    ROWS = board.length;
	    COLS = board[0].length;
	    BOARD = board;
	    WORDS = words;

	    // Check if the words array contains invalid words
	    for (String word : WORDS) {
	        if (word == null || word.isEmpty()) {
	            throw new IllegalArgumentException("Invalid word, is null or empty");
	        }
	        if (!word.matches("^[a-z]+$")) {
	            throw new IllegalArgumentException("Word must only contain lowercase letters");
	        }
	        if (word.matches(".*\\d.*")) {
	            throw new IllegalArgumentException("Words may not contain digits");
	        }
	        if (word.matches(".*[A-Z].*")) {
	            throw new IllegalArgumentException("Words may not contain uppercase letters");
	        }
	    }
	    
	    // Create a new Trie data structure and fill it with the words in the dictionary
	    TRIEOBJ = new Trie();
	    fillTrie(words);
	}

	/**
	 * Fills the Trie data structure with the words in the dictionary.
	 * 
	 * @param words the array of words to be inserted into the Trie
	 */
	private void fillTrie(String[] words) {
	    // Insert each word in the dictionary into the Trie data structure
	    for (String word : words) {
	        TRIEOBJ.insert(word);
	    }
	}

	/**
	 * Finds all the words in the character board using backtracking with a Trie data structure.
	 * 
	 * @return a List of words found in the board
	 */
	public List<String> findWordsBackTrackingWithTrie() {
	    List<String> result = new ArrayList<>();

	    // Create a 2D boolean array to keep track of visited cells
	    boolean[][] visited = new boolean[ROWS][COLS];
	    
	    // For each cell in the board, perform backtracking with Trie starting from that cell
	    for (int i = 0; i < ROWS; i++) {
	        for (int j = 0; j < COLS; j++) {
	            backtrackWithTrie(BOARD, visited, i, j, "", TRIEOBJ, result);
	        }
	    }

	    // Return the List of words found in the board
	    return result;
	}

	/**
	 * A recursive helper function that performs backtracking with a Trie to find words in a character board.
	 * 
	 * @param board the 2D character array representing the board
	 * @param visited the 2D boolean array representing the visited cells
	 * @param i the row index of the current cell
	 * @param j the column index of the current cell
	 * @param word the string formed by the current path
	 * @param trie the Trie data structure representing the dictionary
	 * @param result the List of words found so far
	 */
	private void backtrackWithTrie(char[][] board, boolean[][] visited, int i, int j, String word, Trie trie, List<String> result) {
	    // If the current cell is out of bounds or has already been visited, return
	    if (i < 0 || i >= ROWS || j < 0 || j >= COLS || visited[i][j]) {
	        return;
	    }
	    // Append the current cell's letter to the current path
	    word += board[i][j];
	    // If the current path is not a prefix of any word in the Trie, return
	    if (!trie.startsWith(word)) {
	        return;
	    }
	    // If the current path is a word in the Trie, add it to the result list
	    if (trie.search(word)) {
	        result.add(word);
	    }
	    // Mark the current cell as visited
	    visited[i][j] = true;
	    // Recursively call backtrackWithTrie for each neighbor cell with the updated path
	    backtrackWithTrie(board, visited, i + 1, j, word, trie, result);
	    backtrackWithTrie(board, visited, i - 1, j, word, trie, result);
	    backtrackWithTrie(board, visited, i, j + 1, word, trie, result);
	    backtrackWithTrie(board, visited, i, j - 1, word, trie, result);
	    // Mark the current cell as unvisited (backtrack)
	    visited[i][j] = false;
	}
	
	/**
	 * Finds all the words in the character board using depth-first search with a Trie data structure.
	 * 
	 * @return a List of words found in the board
	 */
	public List<String> findWordsDFSWithTrie() {
	    List<String> result = new ArrayList<>();

	    // Create a 2D boolean array to keep track of visited cells
	    boolean[][] visited = new boolean[ROWS][COLS];
	    
	    // For each cell in the board, perform DFS with Trie starting from that cell
	    for (int i = 0; i < ROWS; i++) {
	        for (int j = 0; j < COLS; j++) {
	            dfsWithTrie(BOARD, visited, i, j, "", TRIEOBJ, result);
	        }
	    }

	    // Return the List of words found in the board
	    return result;
	}

	/**
	 * A recursive helper function that performs depth-first search with a Trie to find words in a character board.
	 * 
	 * @param board the 2D character array representing the board
	 * @param visited the 2D boolean array representing the visited cells
	 * @param i the row index of the current cell
	 * @param j the column index of the current cell
	 * @param word the string formed by the current path
	 * @param trie the Trie data structure representing the dictionary
	 * @param result the List of words found so far
	 */
	private void dfsWithTrie(char[][] board, boolean[][] visited, int i, int j, String word, Trie trie, List<String> result) {
	    // If the current cell is out of bounds or has already been visited, return
	    if (i < 0 || i >= ROWS || j < 0 || j >= COLS || visited[i][j]) {
	        return;
	    }
	    // Append the current cell's letter to the current path
	    word += board[i][j];
	    // If the current path is not a prefix of any word in the Trie, return
	    if (!trie.startsWith(word)) {
	        return;
	    }
	    // If the current path is a word in the Trie, add it to the result list
	    if (trie.search(word)) {
	        result.add(word);
	    }
	    // Mark the current cell as visited
	    visited[i][j] = true;
	    // Recursively call dfsWithTrie for each neighbor cell with the updated path
	    dfsWithTrie(board, visited, i + 1, j, word, trie, result);
	    dfsWithTrie(board, visited, i - 1, j, word, trie, result);
	    dfsWithTrie(board, visited, i, j + 1, word, trie, result);
	    dfsWithTrie(board, visited, i, j - 1, word, trie, result);
	    // Mark the current cell as unvisited (backtrack)
	    visited[i][j] = false;
	}
	
	/**
	 * Finds all the words in the character board using depth-first search with a Set.
	 * 
	 * @return a List of words found in the board
	 */
	public List<String> findWordsDFSWithSet() {
	    List<String> result = new ArrayList<>();
	    
	    // Create a 2D boolean array to keep track of visited cells
	    boolean[][] visited = new boolean[ROWS][COLS];
	    // Create a Set of words to be found, initialized with the words in the dictionary
	    Set<String> set = new HashSet<>(Arrays.asList(WORDS));
	    
	    // For each cell in the board, perform DFS with Set starting from that cell
	    for (int i = 0; i < ROWS; i++) {
	        for (int j = 0; j < COLS; j++) {
	            dfsWithSet(BOARD, visited, i, j, "", set, result);
	        }
	    }
	    
	    // Return the List of words found in the board
	    return result;
	}

	/**
	 * A recursive helper function that performs depth-first search with a Set to
	 * find words in a character board.
	 * 
	 * @param board   the 2D character array representing the board
	 * @param visited the 2D boolean array representing the visited cells
	 * @param i       the row index of the current cell
	 * @param j       the column index of the current cell
	 * @param word    the string formed by the current path
	 * @param set     the Set of words to be found
	 * @param result  the List of words found so far
	 */
	private void dfsWithSet(char[][] board, boolean[][] visited, int i, int j, String word, Set<String> set,
			List<String> result) {
		// If the current word is in the Set, add it to the result list and remove it
		// from the Set
		if (set.contains(word)) {
			result.add(word);
			set.remove(word);
		}
		// If the current cell is out of bounds or has already been visited, return
		if (i < 0 || i >= ROWS || j < 0 || j >= COLS || visited[i][j]) {
			return;
		}
		// Mark the current cell as visited
		visited[i][j] = true;
		// Recursively call dfsWithSet for each neighbor cell with the updated path
		dfsWithSet(board, visited, i + 1, j, word + board[i][j], set, result);
		dfsWithSet(board, visited, i - 1, j, word + board[i][j], set, result);
		dfsWithSet(board, visited, i, j + 1, word + board[i][j], set, result);
		dfsWithSet(board, visited, i, j - 1, word + board[i][j], set, result);
		// Mark the current cell as unvisited (backtrack)
		visited[i][j] = false;
	}

}

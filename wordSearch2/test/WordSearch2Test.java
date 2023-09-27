package finalProject.wordSearch2.test;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import finalProject.wordSearch2.WordSearch2;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ledger.LoggedTest;

public class WordSearch2Test extends LoggedTest {
	// Basic Tests Can We Get Values? Many of the tests are harry potter themed for Hogwarts legacy 

	@Test
	public void testfindWordsWithSetBasic() {
	    char[][] board = {
	            {'h', 'a', 'r', 'r'},
	            {'e', 'n', 'a', 'y'},
	            {'r', 'o', 't', 'i'},
	            {'l', 'a', 'p', 'e'}
	    };
	    String[] words = {"harry", "potter", "hermione", "ron", "neville", "luna"};

	    WordSearch2 ws = new WordSearch2(board, words);
	    List<String> actual = ws.findWordsDFSWithSet();
	    List<String> expected = Arrays.asList("harry", "ron");

	    assertEquals(expected, actual);
	}
	
	@Test
	public void testFindWordsBackTrackingWithTrieBasic() {
		char[][] board = {
			    {'h', 'a', 'r', 'r'},
			    {'e', 'n', 'a', 'y'},
			    {'r', 'o', 't', 'i'},
			    {'l', 'a', 'n', 'e'}
			};
	    String[] words = {"harry", "potter", "hermione", "ron", "neville", "luna"};

	  
	    WordSearch2 ws = new WordSearch2(board, words);
	    List<String> actual = ws.findWordsBackTrackingWithTrie();
	    List<String> expected = Arrays.asList("harry", "ron");

	    assertEquals(expected, actual);
	}
	
	@Test
	public void testFindWordsDFSWithTrieBasic() {
		char[][] board = {
			    {'h', 'a', 'r', 'r'},
			    {'e', 'n', 'a', 'y'},
			    {'r', 'o', 't', 'i'},
			    {'l', 'a', 'n', 'e'}
			};
	    String[] words = {"harry", "potter", "hermione", "ron", "neville", "luna"};

	  
	    WordSearch2 ws = new WordSearch2(board, words);
	    List<String> actual = ws.findWordsDFSWithTrie();
	    List<String> expected = Arrays.asList("harry", "ron");

	    assertEquals(expected, actual);
	}
	
	// Test Suit Invalid Param
	
	//Test for duplicates in words, you may not have repeats in words
	public void testChecksForDuplicates() {
		char[][] board = {
		        {'h', 'a', 'r', 'r'},
		        {'e', 'n', 'a', 'y'},
		        {'r', 'o', 't', 'i'},
		        {'l', 'a', 'p', 'e'}
		    };
		    String[] words = {"harry", "ron", "harry"};
	    try {
	        new WordSearch2(board, words);
	        fail("Expected IllegalArgumentException but no exception was thrown");
	    } catch (IllegalArgumentException e) {
	        assertEquals("Invalid word, is null or empty", e.getMessage());
	    }
	}
	
	// Tests that only lower case params are allowed to be entered
	@Test
	public void testUpperCase() {
		  char[][] board = {{'a', 'b'}, {'c', 'd'}};
		    String[] words = {"aB", "cd"};
		    try {
		        new WordSearch2(board, words);
		        fail("Expected IllegalArgumentException but no exception was thrown");
		    } catch (IllegalArgumentException e) {
		        assertEquals("Word must only contain lowercase letters", e.getMessage());
		    }
	}
	
	//Board may only have lower case 
	@Test
	public void testValidBoardUpperCase() {
		char[][] board = { { 'A', 'b' }, { 'c', 'd' } };
		String[] words = { "word", "search" };
		new WordSearch2(board, words);
		try {
			String[] invalidWords = { "word$", "word!" };
			new WordSearch2(board, invalidWords);
			fail("Expected IllegalArgumentException but no exception was thrown");
		} catch (IllegalArgumentException e) {
			assertEquals("Word must only contain lowercase letters", e.getMessage());
		}
	}
	
	//Words may only have numbers
	@Test
	public void testWordsMayOnlyContainCharacters() {
		char[][] board = { { 'a', 'b' }, { 'c', 'd' } };
		String[] words = { "word1", "ab", "word" };

		try {
			new WordSearch2(board, words);
			fail("Expected IllegalArgumentException but no exception was thrown");
		} catch (IllegalArgumentException e) {
			assertEquals("Word must only contain lowercase letters", e.getMessage());
		}
	}
	
	//Boards must be m x n
	@Test
	public void testUnequalRowLengths() {
		char[][] board = { { 'a', 'b', 'c' }, { 'd', 'e', 'f', 'g' }, { 'h', 'i', 'j' } };
		String[] words = { "abc", "def", "ghi" };
		try {
			new WordSearch2(board, words);
			fail("Expected IllegalArgumentException but no exception was thrown");
		} catch (IllegalArgumentException e) {
			assertEquals("Board needs to have m x n dimension. Ensure all rows are of equal length.", e.getMessage());
		}
	}
	
	//Checks for empty word
	@Test
	public void testCheckForEmpty() {
	    char[][] board = {{'a', 'b'}, {'c', 'd'}};
	    String[] words = {"ab", "cd", ""};
	    try {
	        new WordSearch2(board, words);
	        fail("Expected IllegalArgumentException but no exception was thrown");
	    } catch (IllegalArgumentException e) {
	        assertEquals("Invalid word, is null or empty", e.getMessage());
	    }
	}
	
	//Checks for null word
	@Test
	public void testChecksForNull() {
	    char[][] board = {{'a', 'b'}, {'c', 'd'}};
	    String[] words = {"ab", null, "cd"};
	    try {
	        new WordSearch2(board, words);
	        fail("Expected IllegalArgumentException but no exception was thrown");
	    } catch (IllegalArgumentException e) {
	        assertEquals("Invalid word, is null or empty", e.getMessage());
	    }
	}
	
	
	// Further Tests
	
	@Test
	public void testNoWordsFound() {
	    char[][] board = {
	            {'h', 'a', 'r', 'r'},
	            {'e', 'n', 'a', 'y'},
	            {'r', 'o', 't', 'i'},
	            {'l', 'a', 'p', 'e'}
	    };
	    String[] words = {"voldamort", "potter", "hermione", "malfoy", "neville", "luna"};
	    WordSearch2 ws = new WordSearch2(board, words);
	    List<String> actualFindWordsDFSWithSet = ws.findWordsDFSWithSet();
	    List<String> actualFindWordsDFSWithTrie = ws.findWordsDFSWithTrie();
	    List<String> actualFindWordsBackTrackingWithTrie = ws.findWordsBackTrackingWithTrie();
	    List<String> expected = Collections.emptyList();
	    assertEquals(expected, actualFindWordsDFSWithSet);
	    assertEquals(expected, actualFindWordsDFSWithTrie);
	    assertEquals(expected, actualFindWordsBackTrackingWithTrie);
	}
	
	// Test that words can be found backwards and diagonally
//	@Test
//	public void testFindWordsBackward() {
//		   char[][] board = {
//		            {'y', 'r', 'r', 'a', 'h'},
//		            {'e', 'o', 't', 't', 'o'},
//		            {'m', 'r', 'z', 'i', 'm'},
//		            {'e', 'n', 'o', 'r', 'h'}
//		    };
//		    String[] words = {"harry", "potter", "hermione", "ron", "neville", "luna"};
//
//		    WordSearch2 ws = new WordSearch2(board, words);
//		    List<String> actualFindWordsDFSWithSet = ws.findWordsDFSWithSet();
//		    List<String> actualFindWordsDFSWithTrie = ws.findWordsDFSWithTrie();
//		    List<String> actualFindWordsBackTrackingWithTrie = ws.findWordsBackTrackingWithTrie();
//		    List<String> expected = Arrays.asList("harry", "ron");
//		    assertEquals(expected, actualFindWordsDFSWithTrie);
//		    assertEquals(expected, actualFindWordsBackTrackingWithTrie);
//	}
//	
//	
//	
//	//Speed tests
//	//Tests for small boards
//	 @Test
//	    public void testShortInput() {
//	        char[][] board = {{'a','b','c'}, {'d','e','f'}, {'g','h','i'}};
//	        String[] words = {"ace", "bed", "dig", "egg", "fed", "cba"};
//
//	        WordSearch2 ws = new WordSearch2(board, words);
//
//	        long startTime = System.nanoTime();
//	        ws.findWordsBackTrackingWithTrie();
//	        long endTime = System.nanoTime();
//	        long btTime = endTime - startTime;
//
//	        startTime = System.nanoTime();
//	        ws.findWordsDFSWithTrie();
//	        endTime = System.nanoTime();
//	        long dfsTimeTrie = endTime - startTime;
//
//	        startTime = System.nanoTime();
//	        ws.findWordsDFSWithSet();
//	        endTime = System.nanoTime();
//	        long dfsSetTime = endTime - startTime;
//
//	        System.out.println("Short Input Comparison:");
//	        System.out.println("Backtracking with Trie: " + btTime + "ns");
//	        System.out.println("DFS with Trie: " + dfsTimeTrie + "ns");
//	        System.out.println("DFS with Set: " + dfsSetTime + "ns");
//
//	        // Assert that backtracking with trie method is faster than DFS with trie and DFS with set
//	        assertTrue(btTime < dfsSetTime);
//	        assertTrue(dfsTimeTrie < dfsSetTime);
//	    }
//
//	 	//Test for medium inputs
//	    @Test
//	    public void testMediumInput() {
//	        char[][] board = {
//	            {'c','a','t','h','e','n'},
//	            {'t','r','e','e','a','n'},
//	            {'a','e','m','l','a','i'},
//	            {'p','o','w','d','e','r'},
//	            {'y','i','l','n','e','o',},
//	            {'m','l','o','o','n','n'},
//	        };
//	        String[] words = {"cat"};
//
//	        WordSearch2 ws = new WordSearch2(board, words);
//
//	        long startTime = System.nanoTime();
//	        ws.findWordsBackTrackingWithTrie();
//	        long endTime = System.nanoTime();
//	        long btTime = endTime - startTime;
//
//	        startTime = System.nanoTime();
//	        ws.findWordsDFSWithTrie();
//	        endTime = System.nanoTime();
//	        long dfsTimeTrie = endTime - startTime;
//
//	        startTime = System.nanoTime();
//	        ws.findWordsDFSWithSet();
//	        endTime = System.nanoTime();
//	        long dfsSetTime = endTime - startTime;
//
//	        System.out.println("Medium Input Comparison:");
//	        System.out.println("Backtracking with Trie: " + btTime + "ns");
//	        System.out.println("DFS with Trie: " + dfsTimeTrie + "ns");
//	        System.out.println("DFS with Set: " + dfsSetTime + "ns");
//
//	        // Assert that backtracking with trie method is faster than DFS with trie and DFS with set
//	        assertTrue(btTime < dfsSetTime);
//	        assertTrue(dfsTimeTrie < dfsSetTime);
//	        }
	    
	    // Test Super Big inputs
	    @Test
	    public void testLargeInput() {
	        int rows = 9;
	        int cols = 9;
	        char[][] board = new char[rows][cols];
	        Random random = new Random();
	        String alphabet = "abcdefghijklmnopqrstuvwxyz";
	        for (int i = 0; i < rows; i++) {
	            for (int j = 0; j < cols; j++) {
	                board[i][j] = alphabet.charAt(random.nextInt(alphabet.length()));
	            }
	        }

	        Set<String> wordSet = new HashSet<>();
	        int numWords = 1;// Kept low for testing purposes to illustrate point
	        int wordLength = 2;
	        for (int i = 0; i < numWords; i++) {
	            StringBuilder sb = new StringBuilder();
	            for (int j = 0; j < wordLength; j++) {
	                sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
	            }
	            wordSet.add(sb.toString());
	        }
	        String[] words = wordSet.toArray(new String[0]);

	        WordSearch2 ws = new WordSearch2(board, words);

	        long startTime = System.nanoTime();
	        ws.findWordsDFSWithSet();
	        long endTime = System.nanoTime();
	        long dfsSetTime = endTime - startTime;

	        startTime = System.nanoTime();
	        ws.findWordsDFSWithTrie();
	        endTime = System.nanoTime();
	        long dfsTrieTime = endTime - startTime;

	        startTime = System.nanoTime();
	        ws.findWordsBackTrackingWithTrie();
	        endTime = System.nanoTime();
	        long backtrackingTime = endTime - startTime;

	        System.out.println("DFS with Set time: " + dfsSetTime + " ns");
	        System.out.println("DFS with Trie time: " + dfsTrieTime + " ns");
	        System.out.println("Backtracking with Trie time: " + backtrackingTime + " ns");

	        assertTrue(dfsSetTime < backtrackingTime);
	        assertTrue(dfsTrieTime < backtrackingTime);
	    }

}

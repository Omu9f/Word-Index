package F28DA_CW1;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Moses Varghese
 *
 * ListWordMapTest will test various cases that can
 * be encountered when working with ListWordMap
 * and ensures everything works as expected.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * */
public class ListWordMapTest {

	// Add your own tests
	ListWordMap map;
	
	@Before
	public void setup() {
		map = new ListWordMap();
	}
	
	/** Test 1: add an entry and number of entries is 1 */
	@Test // TEST PASSES
	public void test1() {
		String word = "abc";
		String file = "f1";
		int line = 2;
		WordPosition pos = new WordPosition(file, line, word);
		map.addPos(word, pos);
		assertTrue(map.numberOfEntries() == 1);
	}
	
	/** Test 2: add and find an entry */
	@Test // TEST PASSES
	public void test2() {
		String word = "abc";
		String file = "f1";
		int line = 2;
		WordPosition pos = new WordPosition(file, line, word);
		map.addPos(word, pos);
		Iterator<IPosition> possOut;
		try {
			possOut = map.positions(word);
			IPosition posOut = possOut.next();
			assertTrue(posOut.getFileName().equals(file) && posOut.getLine() == line);
		} catch (WordException e) {
			fail();
		}
	}
	
	/** Test 3: look for an inexistent key */
	@Test // TEST PASSES
	public void test3() {
		String word = "abc";
		String file = "f1";
		int line = 2;
		WordPosition pos = new WordPosition(file, line, word);
		map.addPos(word, pos);
		try {
			map.positions(word);
		} catch (WordException e) {
			assertTrue(true);
		}
	}
	
	/** Test 4: try to delete a nonexistent entry. Should throw an exception. */
	@Test // TEST PASSES
	public void test4() {
		String word = "abc";
		String file = "f1";
		int line = 2;
		WordPosition pos = new WordPosition(file, line, word);
		map.addPos(word, pos);
		try {
			map.removeWord("other");
			fail();
		} catch (WordException e) {
			assertTrue(true);
		}
	}
	
	/** Test 5: delete an actual entry. Should not throw an exception. */
	@Test // TEST PASSES
	public void test5() {
		try {
			String word1 = "abc";
			String word2 = "bcd";
			String file = "f1";
			int line = 2;
			WordPosition pos1 = new WordPosition(file, line, word1);
			WordPosition pos2 = new WordPosition(file, line, word2);
			map.addPos(word1, pos1);
			map.addPos(word2, pos2);
			map.removePos(word2, pos2);			
			assertEquals(map.numberOfEntries(), 1);
			Iterator<IPosition> possOut = map.positions(word1);
			assertTrue(possOut.hasNext());
			assertEquals(possOut.next(), pos1);
		} catch (WordException e) {
			fail();
		}
	}
	
	/** Test 6: insert 200 different values into the Map and check that all these values are in the Map */
	@Test // TEST PASSES
	public void test6() {
		String word;
		int line;
		String file;
		WordPosition pos;
		for (int k = 0; k < 200; k++) {
			word = "w" + k;
			line = k + 1;
			file = "f" + k;
			pos = new WordPosition(file, line, word);
			map.addPos(word, pos);
		}
		assertEquals(map.numberOfEntries(), 200);
		for (int k = 0; k < 200; ++k) {
			word = "w" + k;
			try {
				Iterator<IPosition> poss = map.positions(word);
				assertTrue(poss.hasNext());
			} catch (WordException e) {
				fail();
			}

		}
	}
	
	/** Test 7: Delete a lot of entries from the  Map */
	@Test // TEST PASSES
	public void test7() {
		try	{
			String word;
			int line;
			String file;
			WordPosition pos;
			for (int k = 0; k < 200; ++k) {
				word = "w" + k;
				line = k + 1;
				file = "f" + k;
				pos = new WordPosition(file, line, word);
				map.addPos(word, pos);
			}
			assertEquals(map.numberOfEntries(), 200);
			for ( int k = 0; k < 200; ++k ) {
				word = "w" + k;
				line = k + 1;
				file = "f" + k;
				pos = new WordPosition(file, line, word);
				map.removePos(word, pos);
			}
			assertEquals(map.numberOfEntries(), 0);
			for (int k = 0; k < 200; ++k) {
				word = "w" + k;
				try {
					map.positions(word);
					fail();
				} catch (WordException e) {
				}
			}
		} catch (WordException e) {
			fail();
		}
	}

	/** Test 8: Get iterator over all keys */
	@Test // TEST PASSES
	public void test8() {
		String word;
		int line;
		String file;
		WordPosition pos;
		try {
			for (int k = 0; k < 100; k++) {
				word = "w" + k;
				line = k + 1;
				file = "f" + k;
				pos = new WordPosition(file, line, word);
				map.addPos(word, pos);
			}

			for (int k = 10; k < 30; k++) {
				word = "w" + k;
				line = k + 1;
				file = "f" + k;
				pos = new WordPosition(file, line, word);
				map.removePos(word, pos);
			}
		} catch(WordException e) {
			fail();
		}
		Iterator<String> it = map.words();
		int count = 0;

		while (it.hasNext()) {
			count++;
			it.next();
		}
		assertEquals(map.numberOfEntries(), 80);
		assertEquals(count, 80);
	}
	
	/** Test 9: other */
	@Test // TEST PASSES
	public void test9() {
		try {
			String word1 = "abc";
			String word2 = "def";
			String word3 = "def";
			String file = "f1";
			int line = 1;
			WordPosition pos1 = new WordPosition(file, line, word1);
			WordPosition pos2 = new WordPosition(file, line, word2);
			WordPosition pos3 = new WordPosition(file, line, word3);
			
			map.addPos(word1, pos1);
			map.addPos(word2, pos2);
			map.addPos(word3, pos3);
			
			assertEquals(map.numberOfEntries(), 2);
			map.removePos(word2, pos2);		
			assertEquals(map.numberOfEntries(), 2);
			map.removePos(word2, pos3);	
			assertEquals(map.numberOfEntries(), 1);
			map.removePos(word1, pos1);	
			assertEquals(map.numberOfEntries(), 0);
		} catch (WordException e) {
			fail();
		}
	}
	/** Test 10: insert and delete several thousand different values into the Map */
	// @Test
	public void test10() {
		int N = 100000;
		long start, end;
		start = System.currentTimeMillis();
		String word;
		int line;
		String file;
		WordPosition pos;	
		try	{
			for (int k = 0; k < N; ++k) {
				word = "w" + k;
				line = k + 1;
				file = "f" + k;
				pos = new WordPosition(file, line, word);
				map.addPos(word, pos);}
			assertEquals(map.numberOfEntries(), N);
			System.out.println("inserted");
			for (int k = 0; k < N; ++k) {
				word = "w" + k;
				line = k + 1;
				file = "f" + k;
				pos = new WordPosition(file, line, word);
				map.removePos(word, pos);}
			System.out.println("removed");
		} catch (WordException e) {fail();  }
		end = System.currentTimeMillis();
		System.out.println("Time: " + (end-start));
	}
	// ...

	@Test // TEST PASSES
	public void signatureTest() {
        try {
            IWordMap map = new ListWordMap();
            String word1 = "test1";
            String word2 = "test2";
            IPosition pos1 = new WordPosition("test.txt", 4, word1);
            IPosition pos2 = new WordPosition("test.txt", 5, word2);      
            map.addPos(word1, pos1);
            map.addPos(word2, pos2);
            map.words();
            map.positions(word1);
            map.numberOfEntries();
            map.removePos(word1, pos1);
            map.removeWord(word2);
        } catch (Exception e) {
            fail("Signature of solution does not conform");
        }
	}

}

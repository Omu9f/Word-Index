package F28DA_CW1;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Moses Varghese
 * * * * * * * * * * * * * * * * * */
public class HashWordMapTest {

	// Add your own tests, for example to test the method hashCode from HashWordMap
	HashWordMap h;
	float maxLF = 0.5f;
	
	@Before
	public void setup() {
		h = new HashWordMap(maxLF);
	}
	
	/** Test 1: add an entry and number of entries is 1 */
	@Test
	public void test1() {
		String word = "abc";
		String file = "f1";
		int line = 2;
		WordPosition pos = new WordPosition(file, line, word);
		h.addPos(word, pos);
		assertTrue(h.numberOfEntries() == 1);
	}

	/** Test 2: add and find an entry */
	@Test
	public void test2() {
		String word = "abc";
		String file = "f1";
		int line = 2;
		WordPosition pos = new WordPosition(file, line, word);
		h.addPos(word, pos);
		Iterator<IPosition> possOut;
		try {
			possOut = h.positions(word);
			IPosition posOut = possOut.next();
			assertEquals(posOut.getFileName().equals(file), posOut.getLine() == line);
		} catch (WordException e) {
			fail();
		}
	}

	/** Test 3: look for an inexistent key */
	@Test
	public void test3() {
		String word = "abc";
		String file = "f1";
		int line = 2;
		WordPosition pos = new WordPosition(file, line, word);
		h.addPos(word, pos);
		try {
			h.positions(word);
		} catch (WordException e) {
			assertTrue(true);
		}
	}

	/** Test 4: try to delete a nonexistent entry. Should throw an exception. */
	@Test
	public void test4() {
		String word = "abc";
		String file = "f1";
		int line = 2;
		WordPosition pos = new WordPosition(file, line, word);
		h.addPos(word, pos);
		try {
			h.removeWord("other");
			fail();
		} catch (WordException e) {
			assertTrue(true);
		}
	}

	/** Test 5: delete an actual entry. Should not throw an exception. */
	@Test
	public void test5() {
		try {
			String word1 = "abc";
			String word2 = "bcd";
			String file = "f1";
			int line = 2;
			WordPosition pos1 = new WordPosition(file, line, word1);
			WordPosition pos2 = new WordPosition(file, line, word2);
			h.addPos(word1, pos1);
			h.addPos(word2, pos2);
//			assertEquals(h.numberOfEntries(), 2);
			h.removePos(word2, pos2);
//			h.removeWord(word2);
			assertEquals(h.numberOfEntries(), 1);
			Iterator<IPosition> possOut = h.positions(word1);
			assertTrue(possOut.hasNext());
			assertEquals(possOut.next(), pos1);
		} catch (WordException e) {
			fail();
		}
	}

	/** Test 6: insert 200 different values into the Map and check that all these values are in the Map */
	@Test
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
			h.addPos(word, pos);
		}
		assertEquals(h.numberOfEntries(), 200);
		for (int k = 0; k < 200; ++k) {
			word = "w" + k;
			try {
				Iterator<IPosition> poss = h.positions(word);
				assertTrue(poss.hasNext());
			} catch (WordException e) {
				fail();
			}
		}
	} 

	/** Test 7: Delete a lot of entries from the  Map */
	@Test
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
				h.addPos(word, pos);
			}
			assertEquals(h.numberOfEntries(), 200);
			for (int k = 0; k < 200; ++k) {
				word = "w" + k;
				line = k + 1;
				file = "f" + k;
				pos = new WordPosition(file, line, word);
				h.removePos(word, pos);
			}
			assertEquals(h.numberOfEntries(), 0);
			for (int k = 0; k < 200; ++k) {
				word = "w" + k;
				try {
					h.positions(word);
					fail();
				} catch (WordException e) {
				}
			}
		} catch (WordException e) {
			fail();
		}
	}
	
	/** Test 8: Get iterator over all keys */
	@Test
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
				h.addPos(word, pos);
			}
			for (int k = 10; k < 30; k++) {
				word = "w" + k;
				line = k + 1;
				file = "f" + k;
				pos = new WordPosition(file, line, word);
				h.removePos(word, pos);
			}
		} catch(WordException e) {
			fail();
		}
		Iterator<String> it = h.words();
		int count = 0;
		while (it.hasNext()) {
			count++;
			it.next();
		}
		assertEquals(h.numberOfEntries(), 80);
		assertEquals(count, 80);
	}
	
	/** Test 9: add 2 same word entries and number of entries is 1 */
	@Test // TEST PASSES
	public void test9() {
		String word1 = "word";
		String word2 = "word";
		String file = "f1";
		WordPosition pos1 = new WordPosition(file, 1, word1);
		h.addPos(word1, pos1);
		WordPosition pos2 = new WordPosition(file, 2, word2);
		h.addPos(word1, pos2);
		try {
			Iterator<IPosition> it = h.positions(word2);
			System.out.println(it.next().getLine());
			System.out.println(it.next().getLine());
		} catch (WordException e) {
			e.printStackTrace();
		}
		assertEquals(h.numberOfEntries(), 1);
	}
	
	/** Test 10: insert and delete several thousand different values into the Map */
	// @Test
	public void test10() {
		int N = 1000000;
		HashWordMap h = new HashWordMap();
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
				h.addPos(word, pos);}
			assertEquals(h.numberOfEntries(), N);
			System.out.println("inserted");
			end = System.currentTimeMillis();
//			System.out.println("Time: " + (end-start));
			for (int k = 0; k < N; ++k) {
				word = "w" + k;
				line = k + 1;
				file = "f" + k;
				pos = new WordPosition(file, line, word);
				h.removePos(word, pos);}
			System.out.println("removed");
		} catch (WordException e) {fail();  }
		end = System.currentTimeMillis();
		System.out.println("Time: " + (end-start) + "ms");
	}
	// ...
	
	@Test
	public void signatureTest() {
        try {
            IWordMap map = new HashWordMap(0.5f);
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

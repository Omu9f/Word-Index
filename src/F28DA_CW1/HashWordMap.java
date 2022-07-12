package F28DA_CW1;

import java.util.LinkedList;
import java.util.Iterator;
/**
 * @author Moses Varghese
 * HashTable API that uses open addressing with double hashing
 * Initial table size: 13. Size increases to the next prime that
 * is at least twice as large as the previous size when the load
 * factor goes passed its max set limit (maxLoadFactor).
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
public class HashWordMap implements IWordMap, IHashMonitor {
	/**Data fields for HashWordMap. */
	private float maxLoadFactor;
	private short numOfProbes = 0, numOfOperations = 0;
	private int numOfEntries = 0, capacity = 13;
	private Entry HT[] = new Entry[capacity];
	/**Required constructor. Default load factor set to 0.5f. */
	public HashWordMap() {maxLoadFactor = 0.5f;}
	/**Required constructor. Set a custom load factor. */
	public HashWordMap(float maxLF) {maxLoadFactor = maxLF;}
	/**Required method. Add a new word position to the map. */
	public void addPos(String word, IPosition pos) {
		int h = hashCode(word), j = 1;
		numOfOperations++;
		// look for a valid position to add the entry
		while (HT[h] != null) {
			// if the word is already in the map, only add the position
			if (HT[h].getWord().equals(word)) {
				HT[h].getPos().add(pos);
				return;}
			// reset DEFUNCT if the index had a deleted entry
			// add the word entry to the HashTable
			if (HT[h].getDEFUNCT()) {
				HT[h].setDEFUNCT(false);
				HT[h] = new Entry(word, pos);
				numOfEntries++;}
			h = Math.abs((hashCode(word) + j*hash2(word)) % capacity);
			j++; // else get a new hash code and search the map again
			numOfProbes++;
		}
		// if this is the index is empty, add the word entry
		if (HT[h] == null) {
			HT[h] = new Entry(word, pos);
			numOfEntries++;}
		checkLoadFactor(); // check if HashTable needs resizing
	}
	/**Required method. Remove a word entry from the map. */
	public void removeWord(String word) throws WordException {
		int h = hashCode(word), j = 1;
		numOfOperations++;
		// throw a WordException if the word is not in the map
		if (HT[h] == null) throw new WordException();
		// look for the word in the map
		while (HT[h] != null) {
			// if this is the word to be deleted
			// set this index to have a deleted entry
			if(!HT[h].getDEFUNCT() && HT[h].getWord().equals(word)) {
				HT[h].setDEFUNCT(true);
				numOfEntries--;}
			h = Math.abs((hashCode(word) + j*hash2(word)) % capacity);
			j++; // else get a new hash code and search the map again
			numOfProbes++;}
	}
	/**Required method. Remove a word's position from the map. */
	public void removePos(String word, IPosition pos) throws WordException {
		int h = hashCode(word), j = 1;
		numOfOperations++;
		// throw a WordException if the word is not in the map
		if (HT[h] == null) throw new WordException();
		// look for the word in the map
		while (HT[h] != null) {
			// if this contains the word we are looking for, remove the position
			if(!HT[h].getDEFUNCT() && HT[h].getWord().equals(word)) {
				// if there is only one position left, delete the entry
				if (HT[h].getPos().size() == 1) {
					HT[h].setDEFUNCT(true);
					numOfEntries--;}
				else HT[h].getPos().remove(pos);}
			h = Math.abs((hashCode(word) + j*hash2(word)) % capacity);
			j++; // else get a new hash code and search the map again
			numOfProbes++;}
	}
	/**Required method. Return an iterator of all the words in the map. */
	public Iterator<String> words() {
		LinkedList<String> words = new LinkedList<>();
		for (Entry e : HT) if (e != null && !e.getDEFUNCT()) words.add(e.getWord());
		return words.iterator();
	}
	/**Required method. Returns an iterator of a word's positions.
	 * Throw and exception if the word is not present in the map. */
	public Iterator<IPosition> positions(String word) throws WordException {
		int h = hashCode(word), j = 1;
		// look for the word in the map
		while (HT[h] != null) {
			// return an iterator of all the word's positions
			// if this element is the word we are looking for
			if (!HT[h].getDEFUNCT() && HT[h].getWord().equals(word)) 
				return HT[h].getPos().iterator();
			// otherwise keep searching
			h = Math.abs((hashCode(word) + j*hash2(word)) % capacity);
			j++; // else get a new hash code and search the map again
			numOfProbes++;}
		throw new WordException(); // if the word is not in the map
	}
	/**Required method. Return the number of word entries in the map. */
	public int numberOfEntries() {return numOfEntries;}
	/**Required method. Return the load factor that was set for the map. */
	public float getMaxLoadFactor() {return maxLoadFactor;}
	/**Required method. Return the current load factor. */
	public float getLoadFactor() {return (float) numOfEntries / capacity;}
	/**Required method. Returns the number of probes per operation. */
	public float averNumProbes() {return (float) numOfProbes / numOfOperations;}
	/**Required method. Primary hash function. */
	public int hashCode(String s) {
		if (capacity > 9999) return hashFunc(s, 109, 0) % capacity;
		return hashFunc(s, 31, 0) % capacity;
	}
	/**Helper method. Secondary hash function. */
	private int hash2(String s) {
		if (hashFunc(s, 29, 0) % capacity == 0) return 3;
		return hashFunc(s, 29, 0) % capacity;
	}
	/**Helper method. Uses polynomial accumulation. */
	private int hashFunc(String s, int prime, int a) {
		for (int p = 0; p < s.length(); p++)
			a += s.charAt(p)*(long) Math.pow(prime, s.length()-p-1);
		return (int) (Math.abs(a) % java.lang.Integer.MAX_VALUE);
	}
	/**Helper method. Resize the table once the load factor exceeds the max load factor. */
	private void checkLoadFactor() {if (getLoadFactor() > maxLoadFactor) rehash();}
	/**Helper method. Resize the table */
	private void rehash() {
		capacity = nextPrime(capacity, 2);
		Entry[] copy = HT.clone();
		HT = new Entry[capacity];
		// perform rehashing
		for (Entry e : copy) {
			// apply the hash function if the element is valid
			if (e != null && !e.getDEFUNCT()) {
				int h = hashCode(e.getWord()), j = 1;
				// look for a valid position to add the entry
				while (HT[h] != null) {
					// add the word entry to the HashTable if DEFUNCT is true
					if (HT[h].getDEFUNCT()) {
						HT[h].setDEFUNCT(false);
						HT[h] = e;}
					h = Math.abs((hashCode(e.getWord()) + j*hash2(e.getWord())) % capacity);
					j++; // else get a new hash code and search the map again
					numOfProbes++;}
				// if the index is empty, add the word entry
				if (HT[h] == null) HT[h] = e;
			}
		}
	}
	/**Helper method. Used to resize the capacity to 
	 * the next prime number at least n times as big. */
	private int nextPrime(int capacity, int n) {
		int next = capacity*n;
		while (!primeCheck(next)) next++;
		return next;
	}
	/**Helper method to check if a number is prime. */
	private boolean primeCheck(int n) {
		if (n==0 || n%2==0) return false; if (n==2) return true;
		for (int i=3; i<=Math.sqrt(n); i+=2) if (n%i==0) return false;
		return true;
	}
}
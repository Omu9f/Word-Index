package F28DA_CW1;

import java.util.Iterator;
import java.util.LinkedList;
/**
 * @author Moses Varghese
 * LinkedList Map API using the Entry class. Each entry consists of a word and a 
 * LinkedList to hold all the line numbers (positions of that word in the text file)
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
public class ListWordMap implements IWordMap {
	/**Data field for ListWordMap. */
	private LinkedList<Entry> LM; // list map
	/**Constructor to initialize the list map. */
	public ListWordMap() {LM = new LinkedList<Entry>();}
	/**Required method. Add a new word position to the map. */
	public void addPos(String word, IPosition pos) {
		// add the position to the entry if the word is already in the map
		if (LM.contains(new Entry(word))) LM.get(LM.indexOf(new Entry(word))).add(pos);
		else LM.add(new Entry(word, pos)); // otherwise add the word and the position
	}
	/**Required method. Remove a word entry from the map. */
	public void removeWord(String word) throws WordException {
		// remove the entry of the word if the word is already in the map
		if (LM.contains(new Entry(word))) LM.remove(new Entry(word));
		else throw new WordException(); // otherwise the word is not in the map
	}
	/**Required method. Remove a word's position from the map. */
	public void removePos(String word, IPosition pos) throws WordException {
		// remove the position of the word if the word is already in the map
		if (LM.contains(new Entry(word))) {
			int i = LM.indexOf(new Entry(word)); Entry e = LM.get(i);
			// delete the word entry if there are no more positions of this word
			if (e.getPos().size() == 1) LM.remove(e);
			else LM.get(i).remove(pos);} // else delete only the position
		else throw new WordException(); // otherwise word is not in the map
	}
	/**Required method. Return an iterator over all the words in the map. */
	public Iterator<String> words() {
		LinkedList<String> words = new LinkedList<>();
		for (Entry e : LM) words.add(e.getWord());
		return words.iterator();
	}
	/**Required method. Returns an iterator of a word's positions.
	 * Throw and exception if the word is not present in the map. */
	public Iterator<IPosition> positions(String word) throws WordException {
		// if the word is in the list, get the word's index to get the WordEntry element
		// finally, get all the positions of this word using the WordEntry element
		if (LM.contains(new Entry(word))) return LM.get(LM.indexOf(new Entry(word))).getPos().iterator(); 
		else throw new WordException(); // otherwise the word is not in the map
	}
	/**Required method. Return the number of word entries in the map. */
	public int numberOfEntries() {return LM.size();}
}
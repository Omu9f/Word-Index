package F28DA_CW1;

import java.util.LinkedList;

/**
 * @author Moses Varghese
 * MapEntry class holds an entry of a word and 
 * its positions in the map. Since a word can have
 * multiple occurrences, a LinkedList data structure
 * is used to store all the word's positions
 * * * * * * * * * * * * * * * * * * * * * * * * * * */
public class Entry {
	private String wordEntry;
	private LinkedList<IPosition> posList;
	
	/**constructor for a word entry with word and position */
	public Entry(String word, IPosition pos) {
		wordEntry = word;
		posList = new LinkedList<IPosition>();
		posList.add(pos);
	}
	/**constructor for a word entry with just the word */
	public Entry(String word) {
		wordEntry = word;
		posList = new LinkedList<IPosition>();
	}
	
	/**getter for the words */
	public String getWord() {
		return wordEntry;
	}
	/**getter for the positions */
	public LinkedList<IPosition> getPos() {
		return posList;
	}
	
	/**add and remove position methods */
	public void add(IPosition pos) {
		posList.add(pos);
	}
	public void remove(IPosition pos) {
		posList.remove(pos);
	}
	
	/**Method to check of two word entries are equal */
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o instanceof Entry) {
			Entry e = (Entry) o;
			return wordEntry.equals(e.wordEntry);
		} else return false;
	}
	
	/**getter and setter for DEFUNCT 
	 * to be used by HashWordMap */
	private boolean DEFUNCT = false;
	public boolean getDEFUNCT() {
		return DEFUNCT;
	}
	
	public void setDEFUNCT(boolean b) {
		DEFUNCT = b;
	}
 }
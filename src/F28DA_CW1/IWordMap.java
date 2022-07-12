package F28DA_CW1;

import java.util.Iterator;

/** Interface your map has to implement */
public interface IWordMap {

	/**
	 * Adds a new position to an entry of the map. Creates the
	 * entry if the word is not already present in the map. */
	public void addPos(String word, IPosition pos);

	/**
	 * Removes from the map the entry for word.
	 * 
	 * @throws WordException
	 *                       if no entry with word in the map.
	 */
	public void removeWord(String word) throws WordException;

	/**
	 * Removes from the map position for word.
	 * 
	 * @throws WordException
	 *                       if no entry with word in the map or if word is not
	 *                       associated
	 *                       with given position.
	 */
	public void removePos(String word, IPosition pos) throws WordException;

	/**
	 * Returns an iterator over all words of the map. */
	public Iterator<String> words();

	/**
	 * Returns an iterator over the positions of a given word.
	 * 
	 * @throws WordException
	 *                       if no entry with word in the map.
	 */
	public Iterator<IPosition> positions(String word) throws WordException;

	/**
	 * Returns the number of entries stored in the map. */
	public int numberOfEntries();

}

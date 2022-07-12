package F28DA_CW1;

/**In-file word-position */
public class WordPosition implements IPosition {
	private String fileName;
	private int lineNumber;
	private String word;

	/**Creates a new word-position object give
	 * a file name, a line number and a word. */
	WordPosition(String fileName, int lineNumber, String word) {
		this.fileName = fileName;
		this.lineNumber = lineNumber;
		this.word = word;
	}

	/**Returns the file name of the word-position. */
	public String getFileName() {
		return fileName;
	}

	/**Returns the line number of the word-position. */
	public int getLine() {
		return lineNumber;
	}

	/**Returns the word of the word-position. */
	public String getWord() {
		return word;
	}

	/**Defines the default way a position is printed to a string. */
	public String toString() {
		return String.format("%s:%d:%s", this.fileName, this.lineNumber, this.word);
	}

	/**Defines word position equality. Two positions are equal if the word the
	 * refer, the file name and the line number are all the same. */
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o instanceof WordPosition) {
			WordPosition pos = (WordPosition) o;
			boolean sameFileName = this.getFileName().equals(pos.getFileName());
			boolean sameLineNumber = this.getLine() == pos.getLine();
			boolean sameWord = this.getWord().equals(pos.getWord());
			return sameFileName && sameLineNumber && sameWord;
		} else return false;
	}
}

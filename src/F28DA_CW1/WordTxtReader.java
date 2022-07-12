package F28DA_CW1;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * This class is for reading words form an input file.
 * <ol>
 * <li>Create an object of the class WordTxtReader by providing a file name: <br>
 * {@code WordTxtReader readWords = new WordTxtReader("filename.txt");}</li>
 * <li>To read a word, first check that there is a next word in the file: <br>
 * {@code if (readWords.hasNextWord()) }</li>
 * <li>Finally, to read a word, use: <br>
 * {@code WordPosition nextWord = readWords.nextWord(); }</li>
 * </ol>
 */
public class WordTxtReader {
	private BufferedInputStream inputFileBuffer;
	private WordPosition nextWord;
	private boolean endOfFile;
	private int currentLine;
	private boolean prevCharR;
	private String fileName;

	@SuppressWarnings("unused")
	private WordTxtReader() {
	}

	/**
	 * Creates a new {@code WordTxtReader} object to read words from a given file
	 * (the file name is given as argument).
	 * 
	 * @param inputFileName
	 *            The name of the file to read words from
	 */
	public WordTxtReader(String inputFileName) throws IOException {
		inputFileBuffer = new BufferedInputStream(new FileInputStream(inputFileName));
		this.fileName = inputFileName;
		endOfFile = false;
		currentLine = 1;
		nextWord = readWord();
		prevCharR = false;
	}

	/**
	 * Creates a new {@code WordTxtReader} object to read words from a given file.
	 * 
	 * @param inputFile
	 *            File to read words from
	 */
	public WordTxtReader(File inputFile) throws IOException {
		inputFileBuffer = new BufferedInputStream(new FileInputStream(inputFile));
		this.fileName = inputFile.getName();
		endOfFile = false;
		currentLine = 1;
		nextWord = readWord();
		prevCharR = false;
	}

	/**Performs the reading of the next word in the 
	 * file and updates the current line number. */
	private WordPosition readWord() throws IOException {
		int nextByte;
		char nextChar;
		StringBuffer buffer = new StringBuffer();
		int wordLine = currentLine;

		while (true) {
			nextByte = inputFileBuffer.read();
			if (nextByte == -1) {
				endOfFile = true;
				if (buffer.length() == 0) {
					// Reaching end of file with no new word, returning null
					inputFileBuffer.close();
					return null;
				} else {
					// Reaching end of file, returning last word
					return (new WordPosition(fileName, wordLine, buffer.toString()));
				}
			}
			nextChar = Character.toLowerCase((char) nextByte);
			if ('\r' == nextChar) {
				prevCharR = true;
				if (buffer.length() != 0) {
					wordLine = currentLine;
				}
				currentLine++;
			} else if ('\n' == nextChar && prevCharR) {
				prevCharR = false;
			} else if ('\n' == nextChar && !prevCharR) {
				if (buffer.length() != 0) {
					wordLine = currentLine;
				}
				currentLine++;
			} else {
				prevCharR = false;
			}
			if (Character.isWhitespace(nextChar)) {
				if (buffer.length() == 0) {
					// Skipping whitespace character before word, updating line number if necessary
				} else {
					// Reaching end of word, returning word
					return (new WordPosition(fileName, wordLine, buffer.toString()));
				}
			} else {
				if ((nextChar >= 'a' && nextChar <= 'z') || (nextChar >= '0' && nextChar <= '9')) {
					// New character is a-z or 0-9 to add to current word
					buffer.append(nextChar);
				} else {
					// Skipping other characters
				}
			}
		}
	}

	/**Returns true if the input file has another word. */
	public boolean hasNextWord() {
//		if (nextWord != null) return true;
//		else return false;
		return (nextWord != null);
	}

	/**Returns the next word of the input file. */
	public WordPosition nextWord() throws java.io.IOException {
		WordPosition toReturn = nextWord;
		if (!endOfFile) nextWord = readWord();
		else nextWord = null;
		return toReturn;
	}

}

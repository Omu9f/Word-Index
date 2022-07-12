package F28DA_CW1;

import java.io.IOException; import java.io.File; import java.io.FileFilter; import java.io.FilenameFilter;
import java.util.Arrays; import java.util.ArrayList; import java.util.Iterator;

/** Main class for the Word Index program */
public class WordIndex {
	static final File textFilesFolder = new File("TextFiles"); // modify the folder to read text files from
	static final FileFilter commandFileFilter = (File file) -> file.getParent() == null;
	static final FilenameFilter txtFilenameFilter = (File dir, String filename) -> filename.endsWith(".txt");
	
	private static int numOfFiles = 0, numOfEntries = 0, entries = 0;
	private static long marco, miguel, polo; // start, mid, and end times
	private static IWordMap wordPossMap; // map can be declared as LinkedList or HashTable

	public static void main(String[] args) {
		args = new String[1];
		args[0] = "commands.txt";
		if (args.length != 1) {
			System.err.println("Usage: WordIndex commands.txt");
			System.exit(1);}
		try {
			File commandFile = new File(args[0]);
			if (commandFile.getParent() != null) {
				System.err.println("Use a command file in current directory");
				System.exit(1);}
			// creating a command reader from a file
			WordTxtReader commandReader = new WordTxtReader(commandFile);
			// initialize the map
//			wordPossMap = new ListWordMap();
			wordPossMap = new HashWordMap(); // default max load factor is 50%
			// reading the content of the command file
			while (commandReader.hasNextWord()) {
				// getting the next command
				String command = commandReader.nextWord().getWord();
				runCommands(command, commandReader);}
		}
		catch (IOException e) { // catch any exceptions caused by input errors
			System.err.println("Check your file name");
			System.exit(1);}  
	}
	
	// switch case to hold the command code functionality
	private static void runCommands(String command, WordTxtReader commandReader) throws IOException {
		switch (command) {
		case "addall":
			marco = System.currentTimeMillis(); // ------------start time-------------
			assert(textFilesFolder.isDirectory());
			entries = 0;
			File[] listOfFiles = textFilesFolder.listFiles(txtFilenameFilter);
			Arrays.sort(listOfFiles);
			for (File textFile : listOfFiles) {
				miguel = System.currentTimeMillis(); // start time for each file
				WordTxtReader wordReader = new WordTxtReader(textFile);
				while (wordReader.hasNextWord()) {
					WordPosition wordPos = wordReader.nextWord();
					// adding word to the map and increment the entries
					wordPossMap.addPos(wordPos.getWord(), wordPos);
					entries++; numOfEntries++;}
				polo = System.currentTimeMillis(); // --------------end time--------------
				System.out.println("Time taken for " + textFile + ": " + (polo-miguel) + "ms\n");
			} // output message
			numOfFiles = listOfFiles.length;
			System.out.printf("%d entries have been indexed "
			+ "from %d files\n\n", entries, numOfFiles);
			polo = System.currentTimeMillis(); // --------------end time--------------
			System.out.println("Time taken: " + (polo-marco) + "ms\n");
			break;

		case "add":
			marco = System.currentTimeMillis(); // ------------start time-------------
			File textFile = new File(textFilesFolder, commandReader.nextWord().getWord()+".txt");
			WordTxtReader wordReader = new WordTxtReader(textFile);
			entries = 0;
			while (wordReader.hasNextWord()) {
				WordPosition wordPos = wordReader.nextWord();
				// adding word to the map and increment the entries
				wordPossMap.addPos(wordPos.getWord(), wordPos);
				entries++; numOfEntries++;} // increment number of words in the file
			numOfFiles++; // increment total number of files added
			// output message
			System.out.printf("%d entries have been "
			+ "indexed from file %s\n\n", entries, textFile);
			polo = System.currentTimeMillis(); // --------------end time--------------
			System.out.println("Time taken: " + (polo-marco) + "ms\n");
			break;

		case "search":
			marco = System.currentTimeMillis(); // ------------start time-------------
            int nb = Integer.parseInt(commandReader.nextWord().getWord()), i = 0;
            String word = commandReader.nextWord().getWord();
            ArrayList<ArrayList<String>> arr = new ArrayList<>();
            ArrayList<String> lineNumbs, wordStats = lineNumbs = new ArrayList<>();
            String prevFile = null; entries = 0;
            try {
                Iterator<IPosition> poss = wordPossMap.positions(word);
                while (poss.hasNext()) { // look through all the positions
                    IPosition pos = poss.next();
                    String currFile = pos.getFileName();
                    if (currFile != prevFile) {
                        if (prevFile != null) {
                            wordStats.add(Integer.toString(entries)); // add total occurrences
                            // add file name         // add all line positions of word
                            wordStats.add(prevFile); wordStats.addAll(lineNumbs); 
                            arr.add(wordStats);} // add the information to the main array
                        // reset
                        entries = 0; prevFile = currFile;
                        wordStats = new ArrayList<String>();
                        lineNumbs = new ArrayList<String>();}
                    String line = Integer.toString(pos.getLine());
                    // add the line if valid
                    if (lineNumbs.indexOf(line)==-1) lineNumbs.add(line);
                    i++; entries++; // increment the number of occurrences
                }
                wordStats.add(Integer.toString(entries));
                wordStats.add(prevFile); wordStats.addAll(lineNumbs);
                arr.add(wordStats);
                // use bubble sort on the main array to sort the line numbers
                for (int p=0; p<arr.size(); p++) {
                    for (int j=p+1; j<arr.size(); j++) { 
                    	// index 0 holds the total number of occurrences
                        int iv = Integer.valueOf(arr.get(p).get(0));
                        int jv = Integer.valueOf(arr.get(j).get(0));
                        ArrayList<String> tmp = null;  
                        // compare the occurrences and sort from biggest to smallest
                        if (iv < jv) {
                            tmp = arr.get(p);  
                            arr.set(p, arr.get(j));  
                            arr.set(j, tmp);}  }  }
                // output the message
                System.out.printf("The word \"%s\" occurs %d " +
                "times in %d files: \n", word, i, arr.size());
                for (int p=0; p<nb; p++) {
                    String occur = arr.get(p).get(0), file = arr.get(p).get(1);
                    System.out.printf(" %s times in %s: \n", occur, file);
                    System.out.print("   (lines ");
                    for (int j=2; j<arr.get(p).size(); j++) {
                    	// add an ending bracket ) to the last line number
                        if (j == arr.get(p).size()-1)
                             System.out.print(arr.get(p).get(j) + ")\n");
                        else System.out.print(arr.get(p).get(j) + ", ");}  
                } System.out.println(); // line break
                polo = System.currentTimeMillis(); // --------------end time--------------
    			System.out.println("Time taken: " + (polo-marco) + "ms\n");
            } catch (WordException e) {System.err.println("not found");    }
            break;
            
		case "remove":
			marco = System.currentTimeMillis(); // ------------start time-------------
			File textFileToRemove = new File(textFilesFolder, 
					commandReader.nextWord().getWord()+".txt");
			wordReader = new WordTxtReader(textFileToRemove);
			entries = 0;
			while (wordReader.hasNextWord()) {
				WordPosition wordPos = wordReader.nextWord();
				try { // try to remove the word positions
					wordPossMap.removePos(wordPos.getWord(), wordPos);
					entries++; numOfEntries--;
				} catch(WordException e) {}}
			numOfFiles--;
			// output message
			System.out.printf("%d entries have been removed "
			+ "from file %s\n\n", entries, textFileToRemove);
			polo = System.currentTimeMillis(); // --------------end time--------------
			System.out.println("Time taken: " + (polo-marco) + "ms\n");
			break;

		case "overview":
			marco = System.currentTimeMillis(); // ------------start time-------------
			System.out.printf("Overview: \n  "
					+ "Number of words: %d\n  "
					+ "Number of positions: %d\n  "
					+ "Number of files: %d\n\n", 
					wordPossMap.numberOfEntries(), numOfEntries, numOfFiles);
			polo = System.currentTimeMillis(); // --------------end time--------------
			System.out.println("Time taken: " + (polo-marco) + "ms\n");
			break;

		default: break;  }
	}
}
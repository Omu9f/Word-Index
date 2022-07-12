package F28DA_CW1;

public class HashWordMapProvidedExp {

	public static void main(String[] args) {
		runDifferentLoadFactors();
	}

	/**
	 * Runs the hash table at different load factors and print out the average probe
	 * numbers versus the running time.
	 * The average probe number should go up as the max load factor goes up.
	 */
	private static void runDifferentLoadFactors() {
		System.out.println("Runs the hash table at different load factors and print out the average probe numbers versus the running time.");
		System.out.println("The average probe number should go up as the max load factor goes up.");

		float maxLF = 0.5f;
		HashWordMap h = new HashWordMap(maxLF);
		long startTime, finishTime;
		double time;
		String word;
		int line;
		String file;
		WordPosition pos;

		while (maxLF < 0.99) {
			startTime = System.currentTimeMillis();
			h = new HashWordMap(maxLF);
			try {
				for (int k = 0; k < 10000; ++k) {
					word = "w" + k;
					line = k + 1;
					file = "f" + k;
					pos = new WordPosition(file, line, word);
					h.addPos(word, pos);
				}
				for (int k = 0; k < 10000; ++k) {
					word = "w" + k;
					line = k + 1;
					file = "f" + k;
					pos = new WordPosition(file, line, word);
					h.removePos(word, pos);
				}
				finishTime = System.currentTimeMillis();
				time = finishTime - startTime;
				System.out.println(
						String.format("For load factor %9f, average num. of  probes is %9f time in milseconds is %9f",
								maxLF, h.averNumProbes(), time));
				maxLF = maxLF + (float) 0.05;
			} catch (WordException e) {
				System.out.println("Failure");
			}
		}
	}

}

package F28DA_CW1;

/** Interface your hash table has to implement */
public interface IHashMonitor {

	/** Returns the maximum authorized load factor */
	public float getMaxLoadFactor();

	/** Returns the current load factor */
	public float getLoadFactor();

	/** Returns the average number of probes */
	public float averNumProbes();

	/** Returns the hash code as an integer of a given string */
	public int hashCode(String s);

}

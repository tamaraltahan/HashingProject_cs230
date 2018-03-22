import java.util.ArrayList;

/*
 * Map implementation using hash table with linear probing.
 *
 * @author Eric Zamore
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */

public class DoubleHashMap<K, V> extends AbstractHashMap<K, V> {
    //my data
    int maxProbes = -1;
    int totalProbes;
    int probeAttempts;
    private MapEntry<K, V>[] table;        // a fixed array of entries (all initially null)
    private MapEntry<K, V> DEFUNCT = new MapEntry<>(null, null);   //sentinel

    /**
     * Creates a hash table with capacity 17 and prime factor 109345121.
     */
    public DoubleHashMap() {
        super();
    }

    /**
     * Creates a hash table with given capacity and prime factor 109345121.
     */
    public DoubleHashMap(int cap) {
        super(cap);
    }
    //

    // provide same constructors as base class

    /**
     * Creates a hash table with the given capacity and prime factor.
     */
    public DoubleHashMap(int cap, int p) {
        super(cap, p);
    }


    private int hashTwo(int hash, int i) {

    }

    private static int primeUnder(int n) {
        while (!prime(n)) {
            n--;
        }
        return n;
    }

    private static boolean prime(int m) {
        int n = m;
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }


    public int getMaxProbes() {
        return maxProbes;
    }

    public int getAverageProbes() {
        return totalProbes / probeAttempts;
    }

    /**
     * Creates an empty table having length equal to current capacity.
     */
    @Override
    @SuppressWarnings({"unchecked"})
    protected void createTable() {
        table = (MapEntry<K, V>[]) new MapEntry[capacity];   // safe cast
    }

    /**
     * Returns true if location is either empty or the "defunct" sentinel.
     */
    private boolean isAvailable(int j) {
        return (table[j] == null || table[j] == DEFUNCT);
    }

    /**
     * Searches for an entry with key equal to k (which is known to have
     * hash value h), returning the index at which it was found, or
     * returning -(a+1) where a is the index of the first empty or
     * available slot that can be used to store a new such entry.
     *
     * @param h the precalculated hash value of the given key
     * @param k the key
     * @return index of found entry or if not found, value -(a+1) where a is index of first available slot
     */
    private int findSlot(int h, K k) {
        probeAttempts++;
        totalProbes = 0;
        int avail = -1;                               // no slot available (thus far)
        int j = h;                                    // index while scanning table
        do {
            if (isAvailable(j)) {                       // may be either empty or defunct
                totalProbes++; //increment probes
                if (avail == -1) avail = j;               // this is the first available slot!
                if (table[j] == null) {
                    break;
                }             // if empty, search fails immediately
            } else if (table[j].getKey().equals(k))
                return j;                                 // successful match
            j = (j + 1) % capacity;                       // keep looking (cyclically)
        } while (j != h);                             // stop if we return to the start
        if (maxProbes > totalProbes) maxProbes = totalProbes; //set max probes
        return -(avail + 1);                          // search has failed
    }

    /**
     * Returns value associated with key k in bucket with hash value h.
     * If no such entry exists, returns null.
     *
     * @param h the hash value of the relevant bucket
     * @param k the key of interest
     * @return associate value (or null, if no such entry)
     */
    @Override
    protected V bucketGet(int h, K k) {
        int j = findSlot(h, k);
        if (j < 0) return null;                   // no match found
        return table[j].getValue();
    }

    /**
     * Associates key k with value v in bucket with hash value h, returning
     * the previously associated value, if any.
     *
     * @param h the hash value of the relevant bucket
     * @param k the key of interest
     * @param v the value to be associated
     * @return previous value associated with k (or null, if no such entry)
     */
    @Override
    protected V bucketPut(int h, K k, V v) {
        int j = findSlot(h, k);
        if (j >= 0)                               // this key has an existing entry
            return table[j].setValue(v);
        table[-(j + 1)] = new MapEntry<>(k, v);     // convert to proper index
        n++;
        return null;
    }

    /**
     * Removes entry having key k from bucket with hash value h, returning
     * the previously associated value, if found.
     *
     * @param h the hash value of the relevant bucket
     * @param k the key of interest
     * @return previous value associated with k (or null, if no such entry)
     */
    @Override
    protected V bucketRemove(int h, K k) {
        int j = findSlot(h, k);
        if (j < 0) return null;                   // nothing to remove
        V answer = table[j].getValue();
        table[j] = DEFUNCT;                       // mark this slot as deactivated
        n--;
        return answer;
    }

    /**
     * Returns an iterable collection of all key-value entries of the map.
     *
     * @return iterable collection of the map's entries
     */
    @Override
    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K, V>> buffer = new ArrayList<>();
        for (int h = 0; h < capacity; h++)
            if (!isAvailable(h)) buffer.add(table[h]);
        return buffer;
    }

    public int getTableSize() {
        return table.length;
    }

}

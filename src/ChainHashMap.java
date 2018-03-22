import java.util.ArrayList;

/*
 * Map implementation using hash table with separate chaining.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */

public class ChainHashMap<K, V> extends AbstractHashMap<K, V> {
    // a fixed capacity array of UnsortedTableMap that serve as buckets
    private UnsortedTableMap<K, V>[] table;   // initialized within createTable

    //cluster info
    private int maxClusterSize = 0;
    private int averageClusterSize = 0;
    private int numberOfClusters = 0;
    //getters
    public int getMaxClusterSize(){return maxClusterSize;}
    public int getAverageClusterSize(){return averageClusterSize;}
    public int getNumberOfClusters(){return numberOfClusters;}
    //

    //probe info
    private int averageProbes = 0;
    private int maxProbes = 0;
    //getters
    public int getAverageProbes(){return averageProbes;}
    public int getMaxProbes(){return maxProbes;}
    //




    // provide same constructors as base class

    /**
     * Creates a hash table with capacity 11 and prime factor 109345121.
     */
    public ChainHashMap() {
        super();
    }

    /**
     * Creates a hash table with given capacity and prime factor 109345121.
     */
    public ChainHashMap(int cap) {
        super(cap);
    }

    /**
     * Creates a hash table with the given capacity and prime factor.
     */
    public ChainHashMap(int cap, int p) {
        super(cap, p);
    }

    /**
     * Creates an empty table having length equal to current capacity.
     */
    @Override
    @SuppressWarnings({"unchecked"})
    protected void createTable() {
        table = (UnsortedTableMap<K, V>[]) new UnsortedTableMap[capacity];
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
        UnsortedTableMap<K, V> bucket = table[h];
        if (bucket == null) return null;
        return bucket.get(k);
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
        UnsortedTableMap<K, V> bucket = table[h];
        if (bucket == null)
            bucket = table[h] = new UnsortedTableMap<>();
        int oldSize = bucket.size();
        V answer = bucket.put(k, v);
        n += (bucket.size() - oldSize);   // size may have increased
        return answer;
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
        UnsortedTableMap<K, V> bucket = table[h];
        if (bucket == null) return null;
        int oldSize = bucket.size();
        V answer = bucket.remove(k);
        n -= (oldSize - bucket.size());   // size may have decreased
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
            if (table[h] != null)
                for (Entry<K, V> entry : table[h].entrySet())
                    buffer.add(entry);
        return buffer;
    }

    public void parseClusters() {;
        int numOfBuckets = 0;
        int totalBucketSize = 0;
        int clusterSize = 0;
        for (int i = 0; i < table.length; i++) {
            UnsortedTableMap<K, V> bucket = table[i];
            if (bucket != null) {
                numOfBuckets++;
                totalBucketSize += bucket.size();
                if(bucket.size() > maxProbes) maxProbes = bucket.size();
                if (bucket.size() > 0)
                    numberOfClusters++;
                    clusterSize = bucket.size();
                if (clusterSize > maxClusterSize) maxClusterSize = clusterSize;
            }
        }
        averageClusterSize = clusterSize/numberOfClusters;
        averageProbes = totalBucketSize / numOfBuckets;
    }
}

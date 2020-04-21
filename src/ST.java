import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class ST<Key extends Comparable<Key>, Value> implements Iterable<Key> {

    private TreeMap<Key, Value> st;

    /**
     * Initializes an empty symbol table.
     */
    public ST() {
        st = new TreeMap<>();
    }

    /**
     * Returns the value associated with the given key in the symbol table.
     *
     * @param key The key
     * @return the value associated with the given key if the key is in the symbol table;
     *         {@code null} if the key is not in the symbol table.
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        if (key == null)
            throw new IllegalArgumentException("Calls get() with null key");

        return st.get(key);
    }

    /**
     * Inserts the specified key-value pair into symbol table, overwriting the old value
     * with the new value if the key already exists.
     * Deletes the specified key (and its associated value) from the symbol table.
     * if the specified value is {@code null}.
     *
     * @param key The key
     * @param val The value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null)
            throw new IllegalArgumentException("Calls put() with null key");
        if (val == null)
            st.remove(key);
        else
            st.put(key, val);
    }

    /**
     * Removes the specified key and its associated value from the symbol table.
     *
     * @param key The key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(Key key) {
        if (key == null)
            throw new IllegalArgumentException("Calls delete() with null key");
        st.remove(key);
    }

    /**
     * Removes the specified key and its associated value from the symbol table.
     *
     * @param key The key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void remove(Key key) {
        if (key == null)
            throw new IllegalArgumentException("Calls remove() with null key");
        st.remove(key);
    }

    /**
     * Returns true if this symbol table contain the given key.
     *
     * @param key The key
     * @return {@code true} if this symbol table contains {@code key} and
     *         {@code false} otherwise.
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(Key key) {
        if (key == null)
            throw new IllegalArgumentException("Calls contains() with null key");
        return st.containsKey(key);
    }

    /**
     * Returns the number of key-value pairs in the symbol table.
     *
     * @return the number of key-value pairs in the symbol table.
     */
    public int size() {
        return st.size();
    }

    /**
     * Returns true if the symbol table is empty.
     *
     * @return {@code true} if this symbol table is empty and {@code false} otherwise.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns all keys in the symbol table.
     *
     * @return all keys in the symbol table.
     */
    public Iterable<Key> keys() {
        return st.keySet();
    }

    /**
     * Returns all of the keys in the symbol table.
     *
     * @return an iterator to all of the keys in the symbol table.
     * @deprecated Replaced by {@link #keys()}.
     */
    @Deprecated
    public Iterator<Key> iterator() {
        return st.keySet().iterator();
    }

    /**
     * Returns the smallest key in the symbol table.
     *
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key min() {
        if (isEmpty())
            throw new NoSuchElementException("Calls min() with empty symbol table");
        return st.firstKey();
    }

    /**
     * Returns the largest key in the symbol table.
     *
     * @return the largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key max() {
        if (isEmpty())
            throw new NoSuchElementException("Calls max() with empty symbol table");
        return st.lastKey();
    }

    /**
     * Returns the smallest key in the symbol table greater than or equal to {@code key}.
     *
     * @param  key the key
     * @return the smallest key in the symbol table greater than or equal to {@code key}
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key ceiling(Key key) {
        if (key == null)
            throw new IllegalArgumentException("Argument to ceiling() is null");
        Key k = st.ceilingKey(key);
        if (k == null)
            throw new NoSuchElementException("Argument to ceiling() is too large");
        return k;
    }

    /**
     * Returns the largest key in the symbol table less than or equal to {@code key}.
     *
     * @param  key the key
     * @return the largest key in the symbol table less than or equal to {@code key}
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key floor(Key key) {
        if (key == null)
            throw new IllegalArgumentException("Argument to floor() is null");
        Key k = st.floorKey(key);
        if (k == null)
            throw new NoSuchElementException("Argument to floor() is too small");
        return k;
    }
}

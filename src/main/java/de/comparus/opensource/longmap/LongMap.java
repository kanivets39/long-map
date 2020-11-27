package de.comparus.opensource.longmap;

/**
 * Long map.
 *
 * @param <V> the type parameter of value
 */
public interface LongMap<V> {
    /**
     * Put v.
     *
     * @param key   the key
     * @param value the value
     * @return the value
     */
    V put(long key, V value);

    /**
     * Get v.
     *
     * @param key the key
     * @return the value
     */
    V get(long key);

    /**
     * Remove v.
     *
     * @param key the key
     * @return the value
     */
    V remove(long key);

    /**
     * Is map empty boolean.
     *
     * @return the boolean
     */
    boolean isEmpty();

    /**
     * Is map contains key boolean.
     *
     * @param key the key
     * @return the boolean
     */
    boolean containsKey(long key);

    /**
     * Is map contains value boolean.
     *
     * @param value the value
     * @return the boolean
     */
    boolean containsValue(V value);

    /**
     * Keys long[ ].
     *
     * @return the long[ ]
     */
    long[] keys();

    /**
     * Values v[ ].
     *
     * @return the v[ ]
     */
    V[] values();

    /**
     * Give a size of a long map.
     *
     * @return the long
     */
    long size();

    /**
     * Clear a long map.
     */
    void clear();
}

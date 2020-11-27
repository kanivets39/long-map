package de.comparus.opensource.longmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Long map.
 *
 * @param <V> the type parameter of value
 */
public class LongMapImpl<V> implements LongMap<V> {

    /**
     * The interface Entry.
     *
     * @param <K> the type parameter of key
     * @param <V> the type parameter of value
     */
    interface Entry<K, V> {
        /**
         * Gets key.
         *
         * @return the key
         */
        K getKey();

        /**
         * Gets value.
         *
         * @return the value
         */
        V getValue();
    }

    /**
     * The type Entry.
     *
     * @param <K> the type parameter of key
     * @param <V> the type parameter of value
     */
    class EntryImpl<K, V> implements Entry<K, V> {

        /**
         * The Key.
         */
        K key;
        /**
         * The Value.
         */
        V value;
        /**
         * The Next entry.
         */
        EntryImpl<K, V> next;

        /**
         * Instantiates a new Entry.
         *
         * @param key   the key
         * @param value the value
         * @param next  the next
         */
        public EntryImpl(K key, V value, EntryImpl<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "EntryImpl{" +
                    "k=" + key +
                    ", v=" + value +
                    '}';
        }

    }

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private EntryImpl<Long, V>[] table;
    private int capacity;
    private double loadFactor;
    private int size = 0;

    /**
     * Instantiates a new K map.
     *
     * @param capacity   the capacity
     * @param loadFactor the load factor
     */
    @SuppressWarnings("unchecked")
    public LongMapImpl(int capacity, double loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        table = new EntryImpl[capacity];
    }

    /**
     * Instantiates a new K map.
     */
    public LongMapImpl() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    private int getKey(Long key) {
        int mask = capacity;
        int index = key.hashCode() % mask;
        return index >= 0 ? index : -index;
    }

    @Override
    public V put(long key, V value) {
        int index = getKey(key);
        EntryImpl<Long, V> entryImpl = table[index];
        if (size >= capacity * loadFactor)
            expandTable();
        if (entryImpl == null) {
            table[index] = new EntryImpl<>(key, value, null);
            size++;
        } else
            table[index] = new EntryImpl<>(key, value, entryImpl);

        return table[index].getValue();
    }

    @SuppressWarnings("unchecked")
    private void expandTable() {
        EntryImpl<Long, V>[] newTable = new EntryImpl[2 * capacity];
        rehashTable(newTable);
    }

    private void rehashTable(EntryImpl<Long, V>[] newTable) {
        List<EntryImpl<Long, V>> list;
        size = 0;
        capacity = 2 * capacity;
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                continue;
            }
            list = Arrays.asList(table);
            if (!list.isEmpty()) {
                table = newTable;
                for (EntryImpl<Long, V> entryImpl : list) {
                    if (entryImpl != null) {
                        entryImpl.next = null;
                        put(entryImpl.getKey(), entryImpl.getValue());
                    }
                }
            }
        }
    }

    @Override
    public V get(long key) {
        int index = getKey(key);
        if (table[index] == null)
            return null;

        return findValueByEqualKey(key, table[index]);
    }

    /**
     * Find value by equal key.
     *
     * @param key       the key
     * @param entryImpl the entry
     * @return the v
     */
    public V findValueByEqualKey(Long key, EntryImpl<Long, V> entryImpl) {
        if (key.equals(entryImpl.getKey()))
            return entryImpl.getValue();
        else if (entryImpl.next != null)
            return findValueByEqualKey(key, entryImpl.next);

        return entryImpl.getValue();
    }

    @Override
    public V remove(long key) {
        V old = get(key);
        int index = getKey(key);
        table[index] = null;

        return old;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(long key) {
        EntryImpl<Long, V>[] entries;
        if ((entries = table) != null && size > 0) {
            for (EntryImpl<Long, V> longVEntry : entries) {
                for (EntryImpl<Long, V> entry = longVEntry; entry != null; entry = entry.next) {
                    if (entry.key == key)

                        return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean containsValue(V value) {
        EntryImpl<Long, V>[] entries;
        V tempValue;
        if ((entries = table) != null && size > 0) {
            for (EntryImpl<Long, V> longVEntry : entries) {
                for (EntryImpl<Long, V> entry = longVEntry; entry != null; entry = entry.next) {
                    if ((tempValue = entry.value) == value || (value != null && value.equals(tempValue)))

                        return true;
                }
            }
        }

        return false;
    }

    @Override
    public long[] keys() {
        EntryImpl<Long, V>[] tab;
        List<Long> keys = new ArrayList<>();
        if ((tab = table) != null && size > 0) {
            for (EntryImpl<Long, V> longVEntry : tab) {
                for (EntryImpl<Long, V> e = longVEntry; e != null; e = e.next) {
                    keys.add(e.key);
                }
            }
        }

        return keys.stream().mapToLong(l -> l).toArray();
    }

    @Override
    @SuppressWarnings("unchecked")
    public V[] values() {
        EntryImpl<Long, V>[] entries;
        ArrayList<V> values = new ArrayList<>();
        if ((entries = table) != null && size > 0) {
            for (EntryImpl<Long, V> entry : entries) {
                for (EntryImpl<Long, V> entryToAdd = entry; entryToAdd != null; entryToAdd = entryToAdd.next) {
                    values.add(entryToAdd.value);
                }
            }
        }

        return (V[]) values.toArray();
    }

    @Override
    public long size() {
        return size;
    }


    @Override
    public void clear() {
        Arrays.fill(this.table, null);
        size = 0;
    }

    @Override
    public String toString() {
        return "LongMapImpl{" +
                "table=" + Arrays.toString(table) +
                ", size=" + size +
                '}';
    }

}
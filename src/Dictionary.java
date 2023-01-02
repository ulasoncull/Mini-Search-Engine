import java.util.Iterator;
import java.util.Arrays;;


public class Dictionary<K extends Comparable<? super K>, V> implements DictionaryInterface<K, V> {
	Entry<K, V>[] dictionary;
	private int numOfEntries;
	private final static int DEFAULT_CAPACITY = 100;

	public Dictionary() {
		this(DEFAULT_CAPACITY);
	}

	public Dictionary(int initialCapacity) {
		@SuppressWarnings("unchecked")
		Entry<K, V>[] tempDictionary = (Entry<K, V>[]) new Entry[initialCapacity];
		dictionary = tempDictionary;
		numOfEntries = 0;
	}

	public int locateIndex(K key) {
		int index = 0;
		while ((index < numOfEntries) && key.compareTo(dictionary[index].getKey()) > 0)
			index++;
		return index;
	}

	private void makeRoom(int newPosition) {
		if ((newPosition >= 0) && (newPosition < numOfEntries)) {
			int lastIndex = numOfEntries - 1;
			for (int index = lastIndex; index >= newPosition; index--)
				dictionary[index + 1] = dictionary[index];
		}
	}

	private void ensureCapacity() {
		if (numOfEntries == dictionary.length)
			dictionary = Arrays.copyOf(dictionary, 2 * dictionary.length);
	}

	public V add(K key, V value) {
		V result = null;
		int keyIndex = locateIndex(key);
		if (keyIndex < numOfEntries && key.equals(dictionary[keyIndex].getKey())) {
			// key found; return and replace old value
			result = dictionary[keyIndex].getValue();
			dictionary[keyIndex].setValue(value);
		} else {
			ensureCapacity();
			makeRoom(keyIndex);
			dictionary[keyIndex] = new Entry<K, V>(key, value);
			numOfEntries++;
		} // end if
		return result;
	}

	/**
	 * Removes a specific entry from this dictionary.
	 * 
	 * @param key an object search key of the entry to be removed
	 * @return either the value that was associated with the search key or null if
	 *         no such object exists
	 */
	public V remove(K key) {
		V result = null;
		int keyIndex = locateIndex(key);
		int newIndex = keyIndex + 1;
		int lastIndex = numOfEntries - 1;
		if (keyIndex < numOfEntries && contains(key)) {
			result = dictionary[keyIndex].getValue();
			for (int index = newIndex; index <= lastIndex; index++)
				dictionary[index - 1] = dictionary[index];
			dictionary[numOfEntries - 1] = null;
			numOfEntries--;
		}
		return result;
	}

	/**
	 * Retrieves from this dictionary the value associated with a given search key.
	 * 
	 * @param key an object search key of the entry to be retrieved
	 * @return either the value that is associated with the search key or null if no
	 *         such object exists
	 */
	public V getValue(K key) {
		V result = null;
		if (contains(key)) {
			int keyIndex = locateIndex(key);
			result = dictionary[keyIndex].getValue();
		}
		return result;
	}
	

	/**
	 * Sees whether a specific entry is in this dictionary.
	 * 
	 * @param key an object search key of the desired entry
	 * @return true if key is associated with an entry in the dictionary
	 */
	public boolean contains(K key) {
		V result = null;
		int keyIndex = locateIndex(key);
		if ((keyIndex < numOfEntries) && key.equals(dictionary[keyIndex].getKey()))
			return true;
		return false;
	}

	/**
	 * Sees whether this dictionary is empty.
	 * 
	 * @return true if the dictionary is empty
	 */
	public boolean isEmpty() {
		return numOfEntries == 0;
	}

	/**
	 * Gets the size of this dictionary.
	 * 
	 * @return the number of entries (key-value pairs) currently in the dictionary
	 */
	public int getSize() {
		return numOfEntries;
	}

	/**
	 * Creates an iterator that traverses all search keys in this dictionary.
	 * 
	 * @return an iterator that provides sequential access to the search keys in the
	 *         dictionary
	 */
	public Iterator<K> getKeyIterator() {
		return new KeyIterator();
	}

	/**
	 * Creates an iterator that traverses all values in this dictionary.
	 * 
	 * @return an iterator that provides sequential access to the values in this
	 *         dictionary
	 */
	public Iterator<V> getValueIterator() {
		return new ValueIterator();
	}

	/** Removes all entries from this dictionary. */
	public void clear() {
		for(int i = 0; i < numOfEntries; i++) {
			dictionary[i] = null;
		}
		numOfEntries = 0;
	}

	
	private class Entry<S, T> {
		private S key;
		private T value;

		public Entry(S key, T value) {
			this.key = key;
			this.value = value;
		}

		public S getKey() {
			return key;
		}

		public T getValue() {
			return value;
		}

		public void setValue(T value) {
			this.value = value;
		}

	}

	private class KeyIterator implements Iterator<K> {
		private Iterator<Entry<K, V>> traverser;
		// end default constructor
		private KeyIterator() {
			Entry<K, V>[] tempDictionary = (Entry<K, V>[]) new Entry[numOfEntries];
			for(int i = 0; i < numOfEntries; i++) {
				tempDictionary[i] = dictionary[i];
			}
			traverser = Arrays.asList(tempDictionary).iterator();
		}
		public boolean hasNext() {			
			return traverser.hasNext();
		} // end hasNext

		public K next() {
			Entry<K, V> nextEntry = traverser.next();
			return (K) nextEntry.getKey();
		} // end next

		public void remove() {
			throw new UnsupportedOperationException();
		} // end remove
	}

	private class ValueIterator implements Iterator<V> {
		private Iterator<Entry<K, V>> traverser;
		// end default constructor
		private ValueIterator() {
			Entry<K, V>[] tempDictionary = (Entry<K, V>[]) new Entry[numOfEntries];
			for(int i = 0; i < numOfEntries; i++) {
				tempDictionary[i] = dictionary[i];
			}
			traverser = Arrays.asList(tempDictionary).iterator();
		}
		public boolean hasNext() {
			return traverser.hasNext();
		} // end hasNext

		public V next() {
			Entry<K, V> nextEntry = traverser.next();
			return (V) nextEntry.getValue();
		} // end next

		public void remove() {
			throw new UnsupportedOperationException();
		} // end remove
	}
	

}

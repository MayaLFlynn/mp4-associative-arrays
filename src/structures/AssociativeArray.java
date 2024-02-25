package structures;

import static java.lang.reflect.Array.newInstance;
//import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardUpLeftHandler;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @author Maya Flynn
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V> pairs[];

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({ "unchecked" })
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>()).getClass(),
        DEFAULT_CAPACITY);
    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   */
  public AssociativeArray<K, V> clone() {
    AssociativeArray<K,V> toReturn = new AssociativeArray<K,V>();
    toReturn.size = this.size;
    for (int i = 0; i < size; i++) {
      toReturn.pairs[i] = this.pairs[i];
    } // for
    return toReturn; // STUB
  } // clone()

  /**
   * Convert the array to a string.
   */
  public String toString() {
    String arrayPairs = this.pairs[0].toString();
    for (int i = 1; i < size; i++) {
      arrayPairs = arrayPairs + ", " + this.pairs[i].toString();
    }
    return "[" + arrayPairs + "]";
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to
   * get(key) will return value.
   */
  public void set(K key, V value) throws NullKeyException {
    if (value == null) {
      throw new NullKeyException();
    } // if null key given
    try {
      int i = find(key);
      this.pairs[i].value = value;
    } 
    catch (KeyNotFoundException e) {
      this.pairs[size] = new KVPair<>(key, value);
      size++;
    }
  } // set(K,V)

  /**
   * Get the value associated with key.
   *
   * @throws KeyNotFoundException
   *                              when the key is null or does not 
   *                              appear in the associative array.
   */
  public V get(K key) throws KeyNotFoundException {
    int i = find(key);
    return this.pairs[i].value;
  } // get(K)

  /**
   * Determine if key appears in the associative array. Should
   * return false for the null key.
   */
  public boolean hasKey(K key) {
    try {
      find(key);
      return true;
    }
    catch (KeyNotFoundException f) {
      return false;
    }
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls
   * to get(key) will throw an exception. If the key does not appear
   * in the associative array, does nothing.
   */
  public void remove(K key) {
    try {
      int i = find(key); 
      this.pairs[i] = this.pairs[size - 1];
      this.pairs[size -1] = new KVPair<>(); 
      size--;
    } // try
    catch(KeyNotFoundException e) {}
  } // remove(K)

  /**
   * Determine how many key/value pairs are in the associative array.
   */
  public int size() {
    return this.size;
  } // size()

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  void expand() {
    this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key.
   * If no such entry is found, throws an exception.
   */
  int find(K key) throws KeyNotFoundException {
    for (int i = 0; i < size; i++) {
      if (this.pairs[i].key == key) {
        return i;
      }
    }
    throw new KeyNotFoundException(); 
  } // find(K)

} // class AssociativeArray

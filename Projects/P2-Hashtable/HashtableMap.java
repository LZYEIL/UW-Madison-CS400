import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * A generic hash table implementation that stores key-value pairs using chaining
 * (via linked lists) to handle collisions. Keys and values are generic types.
 *
 * @param <KeyType>   the type of keys maintained by this map
 * @param <ValueType> the type of mapped values
 */
public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {
  
  
  protected LinkedList<Pair>[] table = null;
  
  
  /**
   * A class representing a key-value pair stored in the hash table.
   */
  protected class Pair {

    public KeyType key;
    public ValueType value;

    
    /**
     * Constructs a Pair with the specified key and value.
     *
     * @param key   the key of the pair
     * @param value the value associated with the key
     */
    public Pair(KeyType key, ValueType value) {
        this.key = key;
        this.value = value;
    }
  }
  
  
  
  /**
   * Constructs a new HashtableMap with the specified initial capacity.
   *
   * @param capacity the initial capacity of the hash table
   */
  @SuppressWarnings("unchecked")
  public HashtableMap(int capacity) {
    // Initialize the array using raw type and cast to desired type
    table = (LinkedList<Pair>[]) new LinkedList[capacity];
  }
  
  

  /**
   * Default constructor with a capacity of 64.
   */
  public HashtableMap() {
    this(64);
  }
  
  
  
  
  /**
   * Adds a new key,value pair/mapping to this collection.It is ok that the value is null.
   * @param key the key of the key,value pair
   * @param value the value that key maps to
   * @throws IllegalArgumentException if key already maps to a value
   * @throws NullPointerException if key is null
   */
  @Override
  public void put(KeyType key, ValueType value) throws IllegalArgumentException {
    
    // Key is null:
    if (key == null) {
      throw new NullPointerException("Key is null!");
    }
    
    // Key already maps to a value:
    if (this.containsKey(key)) {
      throw new IllegalArgumentException("Key already maps to a value!");
    }
    
    double load_factor = (double) (this.getSize() + 1) / this.getCapacity();   // Check the current LF

    
    // Resize if needed
    if (load_factor >= 0.8) {
      @SuppressWarnings("unchecked")
      LinkedList<Pair>[] newTable = (LinkedList<Pair>[]) new LinkedList[2 * this.getCapacity()];

      //Rehashing and copying the existing pairs to the updated table:
      for (int i = 0; i < this.getCapacity(); i++) {
        if (this.table[i] != null) {
          for (Pair currPair : this.table[i]) {
            int newIndex = Math.abs(currPair.key.hashCode()) % newTable.length;
            if (newTable[newIndex] == null) {
              newTable[newIndex] = new LinkedList<>();
            }
            newTable[newIndex].add(currPair);
          }
        }
      }

      this.table = newTable; // update the table reference to the resized one
    }

    // Insert the new pair
    int index = Math.abs(key.hashCode()) % this.getCapacity();
    if (this.table[index] == null) {
      this.table[index] = new LinkedList<>();
    }
    this.table[index].add(new Pair(key, value));
  }
  
  
  
  
  /**
   * Checks whether a key maps to a value in this collection.
   * @param key the key to check
   * @return true if the key maps to a value, and false is the
   *         key doesn't map to a value
   */
  @Override
  public boolean containsKey(KeyType key) {
    int index = Math.abs(key.hashCode()) % this.getCapacity();   // Key's index at the array
    
    // The field is not null:
    if (this.table[index] != null) {
      // Iterate through the LinkedList to check:
      for (Pair p: this.table[index]) {
        if (p.key.equals(key)) {
          return true;
        }
      }
    }
    return false;  // The field is null/no existence in the Linkedlist
  }
  
  
  

  /**
   * Retrieves the specific value that a key maps to.
   * @param key the key to look up
   * @return the value that key maps to
   * @throws NoSuchElementException when key is not stored in this
   *         collection
   */
  @Override
  public ValueType get(KeyType key) throws NoSuchElementException {
    int index = Math.abs(key.hashCode()) % this.getCapacity();  //Key's index at the array

    // Check if the field is null:
    if (this.table[index] != null) {
      for (Pair p : this.table[index]) {
        if (p.key.equals(key)) {
          return p.value;   // We find the corresponding value anf return it here
        }
      }
    }
    
    // Same as calling containsKey():
    throw new NoSuchElementException("Key is not stored inside!");
  }

  
  
  
  /**
   * Remove the mapping for a key from this collection.
   * @param key the key whose mapping to remove
   * @return the value that the removed key mapped to
   * @throws NoSuchElementException when key is not stored in this
   *         collection
   */
  @Override
  public ValueType remove(KeyType key) throws NoSuchElementException {
    int index = Math.abs(key.hashCode()) % this.getCapacity();  //Key's index at the array
    
    // Check if key exists:
    if (this.containsKey(key)) {
      ValueType result = this.get(key);  // Get the value first before we lose the information

      // Iterate through the Linkedlist (Safely assume it is non-null):
      for (Pair p: this.table[index]) {
        if (p.key.equals(key)) {
          this.table[index].remove(p);  // Remove the pair object from the Linkedlist
          return result;
        }
      }
    }
    
    // No key found inside
    throw new NoSuchElementException("Key is not stored inside!");
  }
  
  
  
  /**
   * Removes all key,value pairs from this collection.
   */
  @Override
  public void clear() {
    // Set all fields inside the table to null (Discard all key,value information)
    for (int i = 0; i < this.getCapacity(); i++) {
      this.table[i] = null;
    }
  }

  
  
  
  /**
   * Retrieves the number of keys stored in this collection.
   * @return the number of keys stored in this collection
   */
  @Override
  public int getSize() {
    int count  = 0;
    
    // Traverse through the table to get the number of keys stored:
    for (int i = 0; i < this.getCapacity(); i++) {
      if (this.table[i] != null) {
        count += this.table[i].size();  //Each of the field is a Linkedlist (may have multiple pairs)
      }
    }
    return count;
  }

  
  
  
  /**
   * Retrieves this collection's capacity.
   * @return the size of te underlying array for this collection
   */
  @Override
  public int getCapacity() {
    return this.table.length;
  }
  
  
  
  
  /**
   * Test for the put() method
   */
  @Test
  public void test1() {
    //A Hashtablemap instance with the key/velue being Integer type
    HashtableMap<Integer, Integer> hm = new HashtableMap<Integer, Integer>(5);
    
    // Test for the initial configurations:
    Assertions.assertEquals(hm.getCapacity(), 5);
    Assertions.assertEquals(0, hm.getSize());
    
    //First insertion:
    hm.put(1, 10); // Key=1, Value=10
    Assertions.assertEquals(1, hm.getSize());
    Assertions.assertTrue(hm.containsKey(1));
    Assertions.assertEquals(10, hm.get(1)); // Check for Integer 10
    Assertions.assertEquals(5, hm.getCapacity());
    
    //Second insertion:
    hm.put(2, null); // Key=2, Value=null
    Assertions.assertEquals(2, hm.getSize());
    Assertions.assertTrue(hm.containsKey(2));
    Assertions.assertNull(hm.get(2));
    Assertions.assertEquals(5, hm.getCapacity());
    
    
    //Check for null key:
    try {
      hm.put(null, 50);
      Assertions.assertTrue(false);
    }
    catch (NullPointerException e) {
      Assertions.assertTrue(true);
      Assertions.assertEquals(2, hm.getSize()); // Verify state didn't change
    }
    
    
    
    // Check for duplicate insertion of key:
    try {
      hm.put(1, 100);
      Assertions.assertTrue(false);
    }
    catch (IllegalArgumentException e) {
      Assertions.assertTrue(true);
      Assertions.assertEquals(2, hm.getSize()); // Verify state didn't change
      Assertions.assertEquals(10, hm.get(1)); // Verify original value remains
    }
    
    // Insertion at same index as key = 1
    hm.put(6, 60); // Key=6, Value=60 (Collides with key 1)
    Assertions.assertEquals(3, hm.getSize());
    Assertions.assertTrue(hm.containsKey(6));
    Assertions.assertEquals(60, hm.get(6));
    Assertions.assertTrue(hm.containsKey(1));
    Assertions.assertEquals(10, hm.get(1));
    
    // Check index1 and index6 should be at the same index:
    int index1 = Math.abs(Integer.valueOf(1).hashCode()) % hm.getCapacity();
    int index6 = Math.abs(Integer.valueOf(6).hashCode()) % hm.getCapacity();
    Assertions.assertEquals(index1, index6);
    
    
    // Current state: size=3, capacity=5. Load Factor = 3/5 = 0.6.
    // Adding the 4th element: (3 + 1) / 5 = 4/5 = 0.8. This put should trigger resize.
    // Expected new capacity = 5 * 2 = 10.
    hm.put(3, 30); // Key=3, Value=30 (This is the 4th element, should trigger resize)
    Assertions.assertEquals(4, hm.getSize());
    Assertions.assertEquals(10, hm.getCapacity());
    Assertions.assertTrue(hm.containsKey(3));
    Assertions.assertEquals(30, hm.get(3));
    
    // Verify ALL previously added elements are still present and correct after rehashing 
    Assertions.assertTrue(hm.containsKey(1));
    Assertions.assertEquals(10, hm.get(1));
    Assertions.assertTrue(hm.containsKey(2));
    Assertions.assertNull(hm.get(2));
    Assertions.assertTrue(hm.containsKey(6));
    Assertions.assertEquals(60, hm.get(6));
    
    // Insert 5th element
    hm.put(13, 130); // Key=13, Value=130 (Hashes to index 3, collides with key 3)
    Assertions.assertEquals(5, hm.getSize());
    Assertions.assertEquals(10, hm.getCapacity());
    Assertions.assertTrue(hm.containsKey(13));
    Assertions.assertEquals(130, hm.get(13));
    Assertions.assertEquals(30, hm.get(3));  // Verify collision partner still exists
    
  }
  
  
  
  
  /**
   * Test for the containsKey() method
   */
  @Test
  public void test2() {
    //A Hashtablemap instance with the key/velue being Integer type
    HashtableMap<Integer, Integer> hm = new HashtableMap<Integer, Integer>(5);
    
    
    // --- Test 1: Empty Map ---
    Assertions.assertFalse(hm.containsKey(0));
    Assertions.assertFalse(hm.containsKey(1));
    
    
    // --- Test 2: Basic Existence Checks ---
    hm.put(1, 10);
    hm.put(2, 20);
    Assertions.assertTrue(hm.containsKey(1));
    Assertions.assertTrue(hm.containsKey(2));
    Assertions.assertEquals(2, hm.getSize());
    Assertions.assertEquals(5, hm.getCapacity());
    
    
    // --- Test 3: Collision Scenario ---
    // Keys 1 and 6 collide with capacity 5 (1 % 5 = 1, 6 % 5 = 1)
    hm.put(6, 60); // Add colliding key
    Assertions.assertTrue(hm.containsKey(1));
    Assertions.assertTrue(hm.containsKey(6));
    Assertions.assertEquals(3, hm.getSize());
    Assertions.assertEquals(5, hm.getCapacity());
    
    
    // --- Test 5: After Resize ---
    // Current state: size=3, capacity=5. Keys: {1, 2, 6}
    // Add 4th element (key 3), triggering resize to capacity 10
    hm.put(3, 30);
    Assertions.assertEquals(10, hm.getCapacity());
    Assertions.assertEquals(4, hm.getSize());
    Assertions.assertTrue(hm.containsKey(3));
    
  }
  
  
  
  
  
  
  
  /**
   * Test for the get() method
   */
  @Test
  public void test3() {
    //A Hashtablemap instance with the key/velue being Integer type
    HashtableMap<Integer, Integer> hm = new HashtableMap<Integer, Integer>(5);
    
    // --- Test 1: Get from Empty Map ---
    try {
      hm.get(2);
      Assertions.assertTrue(false);
    }
    catch (NoSuchElementException e) {
      Assertions.assertTrue(true);
      Assertions.assertEquals(5, hm.getCapacity());
      Assertions.assertEquals(0, hm.getSize());
    }
    
    
    // Test for a valid retreival
    hm.put(10, 50);
    hm.put(24,  500);
    hm.put(54, 700);
    Assertions.assertEquals(50, hm.get(10));
    Assertions.assertEquals(500, hm.get(24));
    Assertions.assertEquals(700, hm.get(54));
    Assertions.assertEquals(5, hm.getCapacity());
    Assertions.assertEquals(3, hm.getSize());
    
    // Resizing and rehashing:
    hm.put(14, 43);
    Assertions.assertEquals(43, hm.get(14));
    Assertions.assertEquals(10, hm.getCapacity());
    Assertions.assertEquals(4, hm.getSize());

  }
  
  
  
  
  
  /**
   * Test for the remove() method
   */
  @Test
  public void test4() {
    //A Hashtablemap instance with the key/velue being Integer type
    HashtableMap<Integer, Integer> hm = new HashtableMap<Integer, Integer>(5);
    
    // Remove from Empty Map
    try {
      hm.remove(2);
      Assertions.assertTrue(false);
    }
    catch (NoSuchElementException e) {
      Assertions.assertTrue(true);
      Assertions.assertEquals(5, hm.getCapacity());
      Assertions.assertEquals(0, hm.getSize());
    }
    
    
    // Test for a valid removal
    hm.put(10, 50);
    hm.put(24,  500);
    hm.put(54, 700);
    Assertions.assertEquals(50, hm.remove(10));
    Assertions.assertEquals(500, hm.remove(24));
    Assertions.assertEquals(700, hm.remove(54));
    Assertions.assertEquals(5, hm.getCapacity());
    Assertions.assertEquals(0, hm.getSize());
    
    // Test for removal after resizing/rehashing
    hm.put(10, 50);
    hm.put(24,  500);
    hm.put(54, 700);
    hm.put(16, 50);
    hm.put(28,  500);
    hm.put(34, 700);
    Assertions.assertEquals(50, hm.remove(10));
    Assertions.assertEquals(500, hm.remove(24));
    Assertions.assertEquals(700, hm.remove(54));
    Assertions.assertEquals(10, hm.getCapacity());
    Assertions.assertEquals(3, hm.getSize());
   
  }
  
  
  
  
  
  
  /**
   * Test for the clear()/getCapacity()/getSize() method
   */
  @Test
  public void test5() {
    //A Hashtablemap instance with the key/velue being Integer type
    HashtableMap<Integer, Integer> hm = new HashtableMap<Integer, Integer>(5);
    
    // Test for methods when nothing is there:
    Assertions.assertEquals(5, hm.getCapacity());
    Assertions.assertEquals(0, hm.getSize());
    
    
    // Test methods before resizing/rehashing
    hm.put(10, 50);
    hm.put(24,  500);
    hm.put(54, 700);
    Assertions.assertEquals(5, hm.getCapacity());
    Assertions.assertEquals(3, hm.getSize());
    
    hm.clear();
    Assertions.assertEquals(5, hm.getCapacity());
    Assertions.assertEquals(0, hm.getSize());
    
    
    // Test for methods after resizing/rehashing
    hm.put(10, 50);
    hm.put(24,  500);
    hm.put(54, 700);
    hm.put(16, 50);
    hm.put(28,  500);
    hm.put(34, 700);
    Assertions.assertEquals(10, hm.getCapacity());
    Assertions.assertEquals(6, hm.getSize());
    
    hm.clear();
    Assertions.assertEquals(10, hm.getCapacity());
    Assertions.assertEquals(0, hm.getSize());
    
  }

}

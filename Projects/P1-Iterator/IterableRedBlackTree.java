import java.util.Iterator;
import java.util.Stack;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * This class extends RedBlackTree into a tree that supports iterating over the values it
 * stores in sorted, ascending order.
 */
public class IterableRedBlackTree<T extends Comparable<T>>
                extends RedBlackTree<T> implements IterableSortedCollection<T> {
  
  
  private Comparable<T> max = null; //maximum for the iterator, or null if no maximum is set.
  private Comparable<T> min = null; //minimum for the iterator, or null if no minimum is set.

  
  /**
   * Test for iterators created with a specified start point
   */
  @Test
  public void testIRB1() {
    IterableRedBlackTree<String> tree = new IterableRedBlackTree<>();
    
    //Set a min threshold
    tree.setIteratorMin("k");
    
    //Construct the whole tree:
    tree.insert("a");
    tree.insert("m");
    tree.insert("g");
    tree.insert("z");
    tree.insert("t");
    tree.insert("k");
    
    //Initialize the iterator used for iteration
    RBTIterator<String> iterator = (RBTIterator<String>) tree.iterator();
    
    //Initialize our actual output:
    String expected = "";
    
    //Add all expected nodes together as a whole string
    while (iterator.hasNext()) {
      expected += iterator.next();
    }
    
    String actual = "kmtz";
    
    //Check if the expected is equal to actual string
    Assertions.assertEquals(actual, expected);
  }
  
  
  
  
  /**
   * Test for iterators created with a specified stop point
   */
  @Test
  public void testIRB2() {
    IterableRedBlackTree<String> tree = new IterableRedBlackTree<>();
    
    //Set a min threshold
    tree.setIteratorMax("k");
    
    //Construct the whole tree:
    tree.insert("a");
    tree.insert("m");
    tree.insert("g");
    tree.insert("z");
    tree.insert("t");
    tree.insert("k");
    
    //Initialize the iterator used for iteration
    RBTIterator<String> iterator = (RBTIterator<String>) tree.iterator();
    
    //Initialize our actual output:
    String expected = "";
    
    //Add all expected nodes together as a whole string
    while (iterator.hasNext()) {
      expected += iterator.next();
    }
    
    String actual = "agk";
    
    //Check if the expected is equal to actual string
    Assertions.assertEquals(actual, expected);
  }
  
  
  
  
  /**
   * Test for iterators created with a specified start AND stop point
   */
  @Test
  public void testIRB3() {
    IterableRedBlackTree<String> tree = new IterableRedBlackTree<>();
    
    //Set a min threshold
    tree.setIteratorMin("k");
    tree.setIteratorMax("s");
    
    //Construct the whole tree:
    tree.insert("a");
    tree.insert("m");
    tree.insert("g");
    tree.insert("z");
    tree.insert("t");
    tree.insert("k");
    
    //Initialize the iterator used for iteration
    RBTIterator<String> iterator = (RBTIterator<String>) tree.iterator();
    
    //Initialize our actual output:
    String expected = "";
    
    //Add all expected nodes together as a whole string
    while (iterator.hasNext()) {
      expected += iterator.next();
    }
    
    String actual = "km";
    
    //Check if the expected is equal to actual string
    Assertions.assertEquals(actual, expected);
  }
  
  
  
  
  /**
   * Test for iterators created with duplicate values
   */
  @Test
  public void testIRB4() {
    IterableRedBlackTree<String> tree = new IterableRedBlackTree<>();
    
    //Set a min threshold
    tree.setIteratorMin("k");
    tree.setIteratorMax("z");
    
    //Construct the whole tree:
    tree.insert("a");
    tree.insert("m");
    tree.insert("m");
    tree.insert("g");
    tree.insert("z");
    tree.insert("t");
    tree.insert("t");
    tree.insert("k");
    
    //Initialize the iterator used for iteration
    RBTIterator<String> iterator = (RBTIterator<String>) tree.iterator();
    
    //Initialize our actual output:
    String expected = "";
    
    //Add all expected nodes together as a whole string
    while (iterator.hasNext()) {
      expected += iterator.next();
    }
    
    String actual = "kmmttz";
    
    //Check if the expected is equal to actual string
    Assertions.assertEquals(actual, expected);
  }
  
  
  
  
  /**
   * Test for iterators created with Integer types
   */
  @Test
  public void testIRB5() {
    IterableRedBlackTree<Integer> tree = new IterableRedBlackTree<>();
    
    //Set a min threshold
    tree.setIteratorMin(5);
    tree.setIteratorMax(25);
    
    //Construct the whole tree:
    tree.insert(1);
    tree.insert(4);
    tree.insert(7);
    tree.insert(15);
    tree.insert(15);
    tree.insert(21);
    tree.insert(25);
    tree.insert(30);
    
    //Initialize the iterator used for iteration
    RBTIterator<Integer> iterator = (RBTIterator<Integer>) tree.iterator();
    
    //Initialize our actual output:
    String expected = "";
    
    //Add all expected nodes together as a whole string
    while (iterator.hasNext()) {
      expected += iterator.next().toString();
    }
    
    String actual = "715152125";
    
    //Check if the expected is equal to actual string
    Assertions.assertEquals(actual, expected);
  }
  
  
  
  
  
  
  
  
  
  
  
    /**
     * Allows setting the start (minimum) value of the iterator. When this method is called,
     * every iterator created after it will use the minimum set by this method until this method
     * is called again to set a new minimum value.
     * @param min the minimum for iterators created for this tree, or null for no minimum
     */
    public void setIteratorMin(Comparable<T> min) {
      this.min = min;
    }
    
    

    /**
     * Allows setting the stop (maximum) value of the iterator. When this method is called,
     * every iterator created after it will use the maximum set by this method until this method
     * is called again to set a new maximum value.
     * @param min the maximum for iterators created for this tree, or null for no maximum
     */
    public void setIteratorMax(Comparable<T> max) {
      this.max = max;
    }
    
    

    /**
     * Returns an iterator over the values stored in this tree. The iterator uses the
     * start (minimum) value set by a previous call to setIteratorMin, and the stop (maximum)
     * value set by a previous call to setIteratorMax. If setIteratorMin has not been called
     * before, or if it was called with a null argument, the iterator uses no minimum value
     * and starts with the lowest value that exists in the tree. If setIteratorMax has not been
     * called before, or if it was called with a null argument, the iterator uses no maximum
     * value and finishes with the highest value that exists in the tree.
     */
    public Iterator<T> iterator() { 
      return new RBTIterator<>(this.root, this.min, this.max); 
    }
    
    
    

    /**
     * Nested class for Iterator objects created for this tree and returned by the iterator method.
     * This iterator follows an in-order traversal of the tree and returns the values in sorted,
     * ascending order.
     */
    protected static class RBTIterator<R> implements Iterator<R> {

         // stores the start point (minimum) for the iterator
         Comparable<R> min = null;
         // stores the stop point (maximum) for the iterator
         Comparable<R> max = null;
         // stores the stack that keeps track of the inorder traversal
         Stack<BinaryTreeNode<R>> stack = null;

         
         
        /**
         * Constructor for a new iterator if the tree with root as its root node, and
         * min as the start (minimum) value (or null if no start value) and max as the
         * stop (maximum) value (or null if no stop value) of the new iterator.
         * @param root root node of the tree to traverse
         * @param min the minimum value that the iterator will return
         * @param max the maximum value that the iterator will return 
         */
        public RBTIterator(BinaryTreeNode<R> root, Comparable<R> min, Comparable<R> max) {
          this.min = min;
          this.max = max;
          this.stack = new Stack<>();
          buildStackHelper(root);
        }

        
         
        
        /**
         * Helper method for initializing and updating the stack. This method both
         * - finds the next data value stored in the tree (or subtree) that is 
         * between start(minimum) and stop(maximum) point (including start and stop points themselves), and
         * - builds up the stack of ancestor nodes that contain values between start(minimum) and stop(maximum) values 
         * (including start and stop values themselves) so that those nodes can be visited in the future.
         * @param node the root node of the subtree to process
         */
        private void buildStackHelper(BinaryTreeNode<R> node) {
          
          //This is the base case:
          if (node == null) {
            return;
          }
          
          if (min != null && this.min.compareTo(node.getData()) > 0) {
            this.buildStackHelper(node.childRight());  //Go right
          } 
          else {
            this.stack.push(node);
            this.buildStackHelper(node.childLeft());  //Go left
          }
        }
        
        
        
        

        /**
         * Returns true if the iterator has another value to return, and false otherwise.
         */
        public boolean hasNext() { 
          
          if (this.stack.isEmpty()) {
            return false;
          }
          
          // If there's a maximum set, check if the top node's value exceeds it
          if (this.max != null) {
            BinaryTreeNode<R> topNode = this.stack.peek();
            // If the top node's value exceeds the maximum, there are no more valid nodes
            if (this.max.compareTo(topNode.getData()) < 0) {
              return false;
            }
          }
          
          // The stack is not empty and the top node's value is within the range
          return true;
        }

        
        
        
        /**
         * Returns the next value of the iterator.
         * @throws NoSuchElementException if the iterator has no more values to return
         */
        public R next() {
          
          // Check if there are more elements
          if (!hasNext()) {
            throw new NoSuchElementException("No more elements in the iterator");
          }
          
          // Get the next node from the stack
          BinaryTreeNode<R> current = this.stack.pop();
          R result = current.getData();
          
          // Process the right subtree to maintain the in-order traversal
          if (current.childRight() != null) {
            this.buildStackHelper(current.childRight());
          }
          return result;
        }

    }

}

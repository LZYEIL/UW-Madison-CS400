
/**
 * This is the Binary Search Tree class
 * 
 * @param <T> Represents a generic type
 */
public class BinarySearchTree<T extends Comparable<T>> implements SortedCollection<T> {
  
  //The only field in this class
  //Root is used for the access of the whole tree
  protected BinaryTreeNode<T> root;
  

  /**
   * Inserts a new data value into the sorted collection.
   * @param data the new value being inserted
   * @throws NullPointerException if data argument is null, we do not allow
   * null values to be stored within a SortedCollection
   */
  @Override
  public void insert(T data) throws NullPointerException {
    
    if (data == null) {
      throw new NullPointerException("Data is null, not allowed!");
    }
    
    BinaryTreeNode<T> theNode = new BinaryTreeNode<T>(data);
    if (this.root == null) {
      this.root = theNode;
    }
    else {
      insertHelper(theNode, root);
    }

    
  }
  
  
  
  
  /**
   * Performs the naive binary search tree insert algorithm to recursively
   * insert the provided newNode (which has already been initialized with a
   * data value) into the provided tree/subtree.  When the provided subtree
   * is null, this method does nothing. 
   * @param newNode The provided node to be inserted
   * @param subtree The provided tree for a node to insert to
   * 
   */
  protected void insertHelper(BinaryTreeNode<T> newNode, BinaryTreeNode<T> subtree) {

    int compareValue = newNode.getData().compareTo(subtree.getData());
    
    //Left branch logic
    if (compareValue <= 0) {
      if (subtree.childLeft() != null) {
        insertHelper(newNode, subtree.childLeft());
      }
      else {
        subtree.setChildLeft(newNode);
        newNode.setParent(subtree);
      }
    }
    //Right branch logic
    else {
      if (subtree.childRight() != null) {
        insertHelper(newNode, subtree.childRight());
      } 
      else {
        subtree.setChildRight(newNode);
        newNode.setParent(subtree);
      }
    }
  }

  
  
  /**
   * Check whether data is stored in the tree.
   * @param data the value to check for in the collection
   * @return true if the collection contains data one or more times, 
   * and false otherwise
   */
  @Override
  public boolean contains(Comparable<T> data) {
    if (this.isEmpty()) {
      return false;
    }

    return this.containsHelper(data, root);
  }
  
  
  /**
   * This is the helper method of the contains method
   * @param data  The data to be searched
   * @param subtree The tree provided for the data to be checked 
   * @return  True if the data is present in the tree, false otherwise
   */
  private boolean containsHelper(Comparable<T> data, BinaryTreeNode<T> subtree) {
    
    int compareValue = data.compareTo(subtree.getData());
    
    if (compareValue == 0) {
      return true;
    }
    
    //Left branch logic
    if (compareValue < 0) {
      if (subtree.childLeft() != null) {
        return containsHelper(data, subtree.childLeft());
      }
      else {
        return false;
      }
    }
    //Right branch logic
    else {
      if (subtree.childRight() != null) {
        return containsHelper(data, subtree.childRight());
      } 
      else {
        return false;
      }
    }
  }

  
  /**
   * Counts the number of values in the collection, with each duplicate value
   * being counted separately within the value returned.
   * @return the number of values in the collection, including duplicates
   */
  @Override
  public int size() {
    return sizeHelper(this.root);
  }
  
  
  /**
   * The helper method of size method, deals with the actual logic 
   * @param node The provided entry point of a whole tree
   * @return  The size of the whole tree
   */
  private int sizeHelper(BinaryTreeNode<T> node) {
    if (node == null) {
      return 0;
    }
    return 1 + sizeHelper(node.childLeft()) + sizeHelper(node.childRight());
  }

  
  /**
   * Checks if the collection is empty.
   * @return true if the collection contains 0 values, false otherwise
   */
  @Override
  public boolean isEmpty() {
    return this.root == null;
  }

  
  /**
   * Removes all values and duplicates from the collection.
   */
  @Override
  public void clear() {
    this.root = null;
  }
  
  
  
  ////////////////////////////Tests/////////////////////////////////////////////////////
  
  /**
   * Test for insert method
   * 
   * @return true if all pass, false otherwise
   */
  public boolean test1() {
    
    //Insert values in ascending order
    BinarySearchTree<Integer> tree1 = new BinarySearchTree<>();
    tree1.insert(1);
    tree1.insert(2);
    tree1.insert(3);
    tree1.insert(4);
    tree1.insert(5);

    String expectedLevelOrder1 = "[ 1, 2, 3, 4, 5 ]";
    String expectedInOrder1 = "[ 1, 2, 3, 4, 5 ]";
    
    if (!tree1.root.toLevelOrderString().equals(expectedLevelOrder1) || 
        !tree1.root.toInOrderString().equals(expectedInOrder1)) {
        return false;
    }

    //Insert values in descending order
    BinarySearchTree<Integer> tree2 = new BinarySearchTree<>();
    tree2.insert(5);
    tree2.insert(4);
    tree2.insert(3);
    tree2.insert(2);
    tree2.insert(1);

    String expectedLevelOrder2 = "[ 5, 4, 3, 2, 1 ]";
    String expectedInOrder2 = "[ 1, 2, 3, 4, 5 ]";
    if (!tree2.root.toLevelOrderString().equals(expectedLevelOrder2) || 
        !tree2.root.toInOrderString().equals(expectedInOrder2)) {
        return false;
    }

    //Insert values in random order
    BinarySearchTree<Integer> tree3 = new BinarySearchTree<>();
    tree3.insert(3);
    tree3.insert(1);
    tree3.insert(5);
    tree3.insert(2);
    tree3.insert(4);
    String expectedLevelOrder3 = "[ 3, 1, 5, 2, 4 ]";
    String expectedInOrder3 = "[ 1, 2, 3, 4, 5 ]";
    if (!tree3.root.toLevelOrderString().equals(expectedLevelOrder3) || 
        !tree3.root.toInOrderString().equals(expectedInOrder3)) {
        return false;
    }

    return true;
  }
  
  
  
  /**
   * Test for contains method
   * 
   * @return true if all pass, false otherwise
   */
  public boolean test2() {
    //Case 1:
    BinarySearchTree<Integer> tree1 = new BinarySearchTree<>();
    
    tree1.insert(4);
    tree1.insert(7);
    tree1.insert(9);
    tree1.insert(11);
    tree1.insert(3);
    tree1.insert(20);
    tree1.insert(15);
    tree1.insert(1);
    tree1.insert(6);
    
    if (!tree1.contains(4) || !tree1.contains(7) || !tree1.contains(9) || !tree1.contains(11) ||
        !tree1.contains(3) || !tree1.contains(20) || !tree1.contains(15) || !tree1.contains(1)
        || !tree1.contains(6)) {
      return false;
    }
    
    //Case 2:
    BinarySearchTree<String> tree2 = new BinarySearchTree<>();
    
    tree2.insert("7");
    tree2.insert("7");
    tree2.insert("6");
    tree2.insert("9");
    tree2.insert("8");
    tree2.insert("11");
    tree2.insert("13");
    tree2.insert("1");
    tree2.insert("2");
    
    if (!tree2.contains("7") || !tree2.contains("6") || !tree2.contains("9") || !tree2.contains("8") 
        || !tree2.contains("11") || !tree2.contains("13") || !tree2.contains("1") || 
        !tree2.contains("2")) {
      return false;
    }
    
    return true;
  }
  
  
  /**
   * Test for size method
   * 
   * @return true if all pass, false otherwise
   */
  public boolean test3() {
    //Case 1:
    BinarySearchTree<Integer> tree1 = new BinarySearchTree<>();
    
    tree1.insert(7);
    tree1.insert(7);
    tree1.insert(6);
    tree1.insert(9);
    tree1.insert(8);
    tree1.insert(11);
    tree1.insert(13);
    tree1.insert(1);
    tree1.insert(2);
    
    if (tree1.size() != 9) {
      return false;
    }
    
    //Case 2:
    BinarySearchTree<Integer> tree2 = new BinarySearchTree<>();
    
    tree2.insert(7);
    tree2.insert(11);
    tree2.insert(13);
    tree2.insert(1);
    tree2.insert(2);
    
    if (tree2.size() != 5) {
      return false;
    }
    
    //Case 3:
    BinarySearchTree<String> tree3 = new BinarySearchTree<>();
    
    tree3.insert("hwuid");
    tree3.insert("ehfuibf");
    tree3.insert("efbifb");
    tree3.insert("whuhd");
    tree3.insert("wudhwd");
    
    if (tree3.size() != 5) {
      return false;
    }

    return true;
  }
  
  
  /**
   * Test for isEmpty method
   * 
   * @return true if all pass, false otherwise
   */
  public boolean test4() {
    //Case 1:
    BinarySearchTree<Integer> tree1 = new BinarySearchTree<>();
    
    tree1.insert(4);
    tree1.insert(7);
    tree1.insert(1);
    tree1.insert(5);
    tree1.insert(8);
    tree1.insert(22);
    tree1.insert(29);
    tree1.insert(0);
    tree1.insert(5);
    
    if (tree1.isEmpty() == true) {
      return false;
    }
    
    //Case 2:
    BinarySearchTree<String> tree2 = new BinarySearchTree<>();
    
    if (tree2.isEmpty() != true) {
      return false;
    }
    
    return true;
  }
  
  
  /**
   * Test for clear method
   * 
   * @return true if all pass, false otherwise
   */
  public boolean test5() {
    //Case 1: Already empty
    BinarySearchTree<String> tree1 = new BinarySearchTree<>();
    
    tree1.clear();
    if (!tree1.isEmpty() || tree1.size() != 0) {
      return false;
    }
    
    //Case 2: Not empty yet
    BinarySearchTree<String> tree2 = new BinarySearchTree<>();
    
    tree2.insert("hudbi");
    tree2.insert("uewe");
    tree2.insert("hue32ei");
    tree2.insert("heh3uehoi");
    tree2.insert("whey3h");
    
    tree2.clear();
    if (!tree2.isEmpty() || tree2.size() != 0) {
      return false;
    }
    
    return true;
  }
  
  
  
  //This is a static main method used solely for testing purposes
  public static void main(String[] args) {
    BinarySearchTree<Integer> bst = new BinarySearchTree<>();
    System.out.println("Test 1: " + (bst.test1() ? "Passed" : "Failed"));
    System.out.println("Test 2: " + (bst.test2() ? "Passed" : "Failed"));
    System.out.println("Test 3: " + (bst.test3() ? "Passed" : "Failed"));
    System.out.println("Test 4: " + (bst.test4() ? "Passed" : "Failed"));
    System.out.println("Test 5: " + (bst.test5() ? "Passed" : "Failed"));
  }

  
}

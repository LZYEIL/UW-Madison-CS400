
/**
 * This is the public instantiable class 
 * of the roatation action of a BST
 */
public class BSTRotation<T extends Comparable<T>> extends BinarySearchTree<T> {
  
  //Here I will just proceed with the default constructor
  
  
  /**
   * Performs the rotation operation on the provided nodes within this tree.
   * When the provided child is a left child of the provided parent, this
   * method will perform a right rotation. When the provided child is a right
   * child of the provided parent, this method will perform a left rotation.
   * When the provided nodes are not related in one of these ways, this
   * method will either throw a NullPointerException: when either reference is
   * null, or otherwise will throw an IllegalArgumentException.
   *
   * @param child is the node being rotated from child to parent position 
   * @param parent is the node being rotated from parent to child position
   * @throws NullPointerException when either passed argument is null
   * @throws IllegalArgumentException when the provided child and parent
   *     nodes are not initially (pre-rotation) related that way
   */
  protected void rotate(BinaryTreeNode<T> child, BinaryTreeNode<T> parent)
      throws NullPointerException, IllegalArgumentException {
    
    //Check if both inputs are null:
    if (child == null || parent == null) {
      throw new NullPointerException("Passed argument is null!");
    }
    
    //Check for the Parent-child relationship:
    if (child.parent() != parent) {
      throw new IllegalArgumentException("Incorrect relationship!");
    }
    
    //The right child logic branch (rotate to left)
    if (child.isRightChild()) {
      BinaryTreeNode<T> prevParentP = parent.parent();  //Save parent's parent
      BinaryTreeNode<T> prevChildC = child.childLeft(); //Save child's left child 
      
      child.setChildLeft(parent);
      parent.setParent(child); //Parent child rotation
      
      parent.setChildRight(prevChildC);
      if (prevChildC != null) {
        prevChildC.setParent(parent); //Child's (The parameter one) child's relocation
      }
      
      //Logic of parent's parent linkage: 
      if (prevParentP == null) {
        child.setParent(null);  //Set a Null parent, nothing happens actually
        this.root = child;
      }
      else if (parent == prevParentP.childRight()) {
        child.setParent(prevParentP);
        prevParentP.setChildRight(child);
      }
      else {
        child.setParent(prevParentP);
        prevParentP.setChildLeft(child);
      }
    }
    
    //The left child logic branch (rotate to right)
    else {
      BinaryTreeNode<T> prevParentP = parent.parent();   //Save parent's parent
      BinaryTreeNode<T> prevChildC = child.childRight(); //Save child's right child
      
      child.setChildRight(parent);
      parent.setParent(child); //Parent child rotation
      
      parent.setChildLeft(prevChildC);
      if (prevChildC != null) {
        prevChildC.setParent(parent); //Child's (The parameter one) child's relocation
      }
      
      //Logic of parent's parent linkage: 
      if (prevParentP == null) {
        child.setParent(null);  //Set a Null parent, nothing happens actually
        this.root = child;
      }
      else if (parent == prevParentP.childRight()) {
        child.setParent(prevParentP);
        prevParentP.setChildRight(child);
      }
      else {
        child.setParent(prevParentP);
        prevParentP.setChildLeft(child);
      }

    }
  }
  
  
  
  //////////////////////////////Test/////////////////////////////////////////////
  

  /**
   * Perform rotations with 0 shared children.
   * Includes cases where the root node is involved and where it is not.
   * @return true if all pass, false otherwise
   */
  public boolean test1() {
      BSTRotation<Integer> bstr = new BSTRotation<Integer>();
      
      //Case one: Right rotation where parent is the root node
      BinaryTreeNode<Integer> parent1 = new BinaryTreeNode<Integer>(10);
      BinaryTreeNode<Integer> child1 = new BinaryTreeNode<Integer>(5);
      
      parent1.setChildLeft(child1);
      child1.setParent(parent1);
      
      bstr.rotate(child1, parent1);
      
      if (child1.childRight() != parent1 || parent1.parent() != child1 || child1.parent() != null
          || child1.childLeft() != null || parent1.childRight() != null || 
          parent1.childLeft() != null) {
        return false;
      }
      
      //Case two: Left rotation where parent is the root node
      BinaryTreeNode<Integer> parent2 = new BinaryTreeNode<Integer>(10);
      BinaryTreeNode<Integer> child2 = new BinaryTreeNode<Integer>(15);
      
      parent2.setChildRight(child2);
      child2.setParent(parent2);
      
      bstr.rotate(child2, parent2);
      
      if (child2.childRight() != null || parent2.parent() != child2 || child2.parent() != null
          || child2.childLeft() != parent2 || parent2.childRight() != null || 
          parent2.childLeft() != null) {
        return false;
      }
      
      //Case three: Right rotation where parent is non-root node
      BinaryTreeNode<Integer> parentP1 = new BinaryTreeNode<Integer>(20);
      BinaryTreeNode<Integer> parent3 = new BinaryTreeNode<Integer>(10);
      BinaryTreeNode<Integer> child3 = new BinaryTreeNode<Integer>(5);
      
      parent3.setChildLeft(child3);
      child3.setParent(parent3);
      parent3.setParent(parentP1);
      parentP1.setChildLeft(parent3);
      
      bstr.rotate(child3, parent3);
      
      if (parentP1.childLeft() != child3 || child3.parent() != parentP1 || 
          child3.childRight() != parent3 || parent3.parent() != child3 || 
          child3.childLeft() != null || parent3.childLeft() != null || 
          parent3.childRight() != null) {

        return false;
      }
      
      //Case four: Left rotation where parent is non-root node
      BinaryTreeNode<Integer> parentP2 = new BinaryTreeNode<Integer>(20);
      BinaryTreeNode<Integer> parent4 = new BinaryTreeNode<Integer>(25);
      BinaryTreeNode<Integer> child4 = new BinaryTreeNode<Integer>(30);
      
      parentP2.setChildRight(parent4);
      parent4.setParent(parentP2);
      parent4.setChildRight(child4);
      child4.setParent(parent4);
      
      bstr.rotate(child4, parent4);
      
      if (parentP2.childRight() != child4 || child4.parent() != parentP2 || 
          child4.childLeft() != parent4 || parent4.parent() != child4 || 
          child4.childRight() != null || parent4.childLeft() != null ||
          parent4.childRight() != null) {
        return false;
      }
      
      return true;
  }
  
  
  /**
   * Perform rotations with 1 shared children.
   * Includes cases where the root node is involved and where it is not.
   * @return true if all pass, false otherwise
   */
  public boolean test2() {
    //Case1 : Right rotation where parent is the root node
    BSTRotation<Integer> bstr = new BSTRotation<Integer>();
    
    BinaryTreeNode<Integer> parent1 = new BinaryTreeNode<Integer>(10);
    BinaryTreeNode<Integer> child1 = new BinaryTreeNode<Integer>(5);
    BinaryTreeNode<Integer> parent1Child = new BinaryTreeNode<Integer>(15);
    
    
    parent1.setChildLeft(child1);
    child1.setParent(parent1);
    parent1.setChildRight(parent1Child);
    parent1Child.setParent(parent1);
    
    bstr.rotate(child1, parent1);
    
    if (child1.childRight() != parent1 || parent1.parent() != child1 || 
        parent1.childRight() != parent1Child || parent1Child.parent() != parent1 ||
        child1.childLeft() != null || parent1.childLeft() != null || 
        parent1Child.childLeft() != null || parent1Child.childRight() != null) {
      return false;
    }
    
    //Case 2: Left rotation where parent is the root node
    BinaryTreeNode<Integer> parent2 = new BinaryTreeNode<Integer>(10);
    BinaryTreeNode<Integer> child2 = new BinaryTreeNode<Integer>(15);
    BinaryTreeNode<Integer> parent2Child = new BinaryTreeNode<Integer>(5);
    
    parent2.setChildLeft(parent2Child);
    parent2Child.setParent(parent2);
    parent2.setChildRight(child2);
    child2.setParent(parent2);
    
    bstr.rotate(child2, parent2);
    
    if (child2.childRight() != null || parent2.parent() != child2 || 
        parent2.childRight() != null || parent2Child.parent() != parent2 ||
        child2.childLeft() != parent2 || parent2.childLeft() != parent2Child || 
        parent2Child.childLeft() != null || parent2Child.childRight() != null) {
      return false;
    }
    
    
    //Case3: Right rotation where parent is non-root node
    BinaryTreeNode<Integer> parentP = new BinaryTreeNode<Integer>(50);
    BinaryTreeNode<Integer> parent3 = new BinaryTreeNode<Integer>(40);
    BinaryTreeNode<Integer> child3 = new BinaryTreeNode<Integer>(20);
    BinaryTreeNode<Integer> parent3Child = new BinaryTreeNode<Integer>(45);
    
    parentP.setChildLeft(parent3);
    parent3.setParent(parentP);
    parent3.setChildLeft(child3);
    child3.setParent(parent3);
    parent3.setChildRight(parent3Child);
    parent3Child.setParent(parent3);
    
    bstr.rotate(child3, parent3);
    
    if (parentP.childLeft() != child3 || child3.parent() != parentP ||
        child3.childRight() != parent3 || parent3.parent() != child3 ||
        parent3.childRight() != parent3Child || parent3Child.parent() != parent3 ||
        child3.childLeft() != null || parent3.childLeft() != null ||
        parent3Child.childLeft() != null || parent3Child.childRight() != null) {
      return false;
    }
    
    //Case4: Left rotation where parent is non-root node
    BinaryTreeNode<Integer> parentP2 = new BinaryTreeNode<Integer>(50);
    BinaryTreeNode<Integer> parent4 = new BinaryTreeNode<Integer>(40);
    BinaryTreeNode<Integer> child4 = new BinaryTreeNode<Integer>(45);
    BinaryTreeNode<Integer> parent4Child = new BinaryTreeNode<Integer>(20);
    
    parentP2.setChildLeft(parent4);
    parent4.setParent(parentP2);
    parent4.setChildLeft(parent4Child);
    parent4Child.setParent(parent4);
    parent4.setChildRight(child4);
    child4.setParent(parent4);
    
    bstr.rotate(child4, parent4);
    
    if (parentP2.childLeft() != child4 || child4.parent() != parentP2 ||
        child4.childRight() != null || parent4.parent() != child4 ||
        parent4.childRight() != null || parent4Child.parent() != parent4 ||
        child4.childLeft() != parent4 || parent4.childLeft() != parent4Child ||
        parent4Child.childLeft() != null || parent4Child.childRight() != null) {
      return false;
    }

    return true;
  }
  
  
  /**
   * Perform rotations with 2 shared children.
   * Here only Includes cases where the root node is involved.
   * We have already tested the non-root node logic in the above two
   * methods, there is no need to test here since the number of shared children does not 
   * influence the root/non root cases
   * @return true if all pass, false otherwise
   */
  public boolean test3() {
    //Case1: Left rotation
    BSTRotation<Integer> bstr = new BSTRotation<Integer>();
    
    BinaryTreeNode<Integer> parent1 = new BinaryTreeNode<Integer>(10);
    BinaryTreeNode<Integer> child1 = new BinaryTreeNode<Integer>(15);
    BinaryTreeNode<Integer> child1C1 = new BinaryTreeNode<Integer>(12);
    BinaryTreeNode<Integer> child1C2 = new BinaryTreeNode<Integer>(18);
    
    parent1.setChildRight(child1);
    child1.setParent(parent1);
    child1.setChildLeft(child1C1);
    child1C1.setParent(child1);
    child1.setChildRight(child1C2);
    child1C2.setParent(child1);
    
    bstr.rotate(child1, parent1);
    
    if (child1.childRight() != child1C2 || child1C2.parent() != child1 ||
        child1.childLeft() != parent1 || parent1.parent() != child1 || 
        parent1.childRight() != child1C1 || child1C1.parent() != parent1 ||
        child1C2.childLeft() != null || child1C2.childRight() != null ||
        parent1.childLeft() != null || child1C1.childLeft() != null ||
        child1C1.childRight() != null) {
      return false;
    }
    
    //Case2: Right rotation
    BinaryTreeNode<Integer> parent2 = new BinaryTreeNode<Integer>(10);
    BinaryTreeNode<Integer> child2 = new BinaryTreeNode<Integer>(15);
    BinaryTreeNode<Integer> child2C1 = new BinaryTreeNode<Integer>(12);
    BinaryTreeNode<Integer> child2C2 = new BinaryTreeNode<Integer>(18);
    
    parent2.setChildLeft(child2);
    child2.setParent(parent2);
    child2.setChildLeft(child2C1);
    child2C1.setParent(child2);
    child2.setChildRight(child2C2);
    child2C2.setParent(child2);
    
    bstr.rotate(child2, parent2);
    
    if (child2.childRight() != parent2 || child2C2.parent() != parent2 ||
        child2.childLeft() != child2C1 || parent2.parent() != child2 || 
        parent2.childRight() != null || child2C1.parent() != child2 ||
        child2C2.childLeft() != null || child2C2.childRight() != null ||
        parent2.childLeft() != child2C2 || child2C1.childLeft() != null ||
        child2C1.childRight() != null) {
      return false;
    }
    
    return true;
  }
  
  
  /**
   * Perform rotations with 3 shared children.
   * Here only Includes cases where the root node is involved.
   * We have already tested the non-root node logic in the above two
   * methods, there is no need to test here since the number of shared children does not 
   * influence the root/non root cases
   * @return true if all pass, false otherwise
   */
  public boolean test4() {
    //Case1: Left rotation
    BSTRotation<Integer> bstr = new BSTRotation<Integer>();
    
    BinaryTreeNode<Integer> parent1 = new BinaryTreeNode<Integer>(10);
    BinaryTreeNode<Integer> child1 = new BinaryTreeNode<Integer>(15);
    BinaryTreeNode<Integer> child2 = new BinaryTreeNode<Integer>(8);
    BinaryTreeNode<Integer> child1C1 = new BinaryTreeNode<Integer>(12);
    BinaryTreeNode<Integer> child1C2 = new BinaryTreeNode<Integer>(18);
    
    parent1.setChildLeft(child2);
    child2.setParent(parent1);
    parent1.setChildRight(child1);
    child1.setParent(parent1);
    child1.setChildLeft(child1C1);
    child1C1.setParent(child1);
    child1.setChildRight(child1C2);
    child1C2.setParent(child1);
    
    bstr.rotate(child1, parent1);
    
    if (child1.childRight() != child1C2 || child1C2.parent() != child1 || 
        child1.childLeft() != parent1 || parent1.parent() != child1 || 
        parent1.childLeft() != child2 || child2.parent() != parent1 || 
        parent1.childRight() != child1C1 || child1C1.parent() != parent1 ||
        child1C2.childLeft() != null || child1C2.childRight() != null ||
        child2.childLeft() != null || child2.childRight() != null ||
        child1C1.childLeft() != null || child1C1.childRight() != null) {
      return false;
    }
    
   //Case2: Right rotation
    
    BinaryTreeNode<Integer> parent2 = new BinaryTreeNode<Integer>(10);
    BinaryTreeNode<Integer> child3 = new BinaryTreeNode<Integer>(8);
    BinaryTreeNode<Integer> child4 = new BinaryTreeNode<Integer>(15);
    BinaryTreeNode<Integer> child3C1 = new BinaryTreeNode<Integer>(6);
    BinaryTreeNode<Integer> child3C2 = new BinaryTreeNode<Integer>(9);
    
    parent2.setChildLeft(child3);
    child3.setParent(parent2);
    parent2.setChildRight(child4);
    child4.setParent(parent2);
    child3.setChildLeft(child3C1);
    child3C1.setParent(child3);
    child3.setChildRight(child3C2);
    child3C2.setParent(child3);
    
    bstr.rotate(child3, parent2);
    
    if (child3.childLeft() != child3C1 || child3C1.parent() != child3 ||
        child3.childRight() != parent2 || parent2.parent() != child3 || 
        parent2.childLeft() != child3C2 || child3C2.parent() != parent2 || 
        parent2.childRight() != child4 || child4.parent() != parent2 || 
        child3C1.childLeft() != null || child3C1.childRight() != null ||
        child3C2.childLeft() != null || child3C2.childRight() != null ||
        child4.childLeft() != null || child4.childRight() != null) {
      return false;
    }
    
    return true;
  }
  
  
  
  
  //This is a static main method used solely for testing purposes
  public static void main(String[] args) {
    BSTRotation<Integer> bst = new BSTRotation<>();
    System.out.println("Test 1: " + (bst.test1() ? "Passed" : "Failed"));
    System.out.println("Test 2: " + (bst.test2() ? "Passed" : "Failed"));
    System.out.println("Test 3: " + (bst.test3() ? "Passed" : "Failed"));
    System.out.println("Test 4: " + (bst.test4() ? "Passed" : "Failed"));
  }

}
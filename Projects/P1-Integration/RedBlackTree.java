import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * This is the RedBlack Tree class based on the previous work
 */
public class RedBlackTree <T extends Comparable<T>> extends BSTRotation<T> {
  
  //Default constructor, no need to add one
  
  
  
  
  
  /**
   * Handles the repair process for a Red-Black Tree when a red node is inserted and violates 
   * the Red-Black Tree properties. This method is specifically designed to handle cases where 
   * the aunt is black or null
   *
   * @param newNode   The newly inserted node that may be causing a violation
   * @param isLeftCase A boolean flag indicating whether the repair process is for the 
   * left case (true) or the right case (false). 
   */
  private void repairRedBlackTree(RBTNode<T> newNode, boolean isLeftCase) {
    RBTNode<T> grandfather = newNode.parent().parent();
    RBTNode<T> father = newNode.parent();

    
    // Determine the child to check based on the case
    RBTNode<T> childToCheck = isLeftCase ? father.childLeft() : father.childRight();

    // Check for the same branch with grandfather-father
    if (childToCheck == newNode) {
      this.rotate(father, grandfather);
      father.flipColor();
      grandfather.flipColor();
    }
        
    // Not the same branch with grandfather-father
    else {
      this.rotate(newNode, father);
      this.rotate(newNode, grandfather);
      newNode.flipColor();
      grandfather.flipColor();
    }
  }
  
  
  
  
  
  /**
   * Checks if a new red node in the RedBlackTree causes a red property violation
   * by having a red parent. If this is not the case, the method terminates without
   * making any changes to the tree. If a red property violation is detected, then
   * the method repairs this violation and any additional red property violations
   * that are generated as a result of the applied repair operation.
   * @param newNode a newly inserted red node, or a node turned red by previous repair
   */
  protected void ensureRedProperty(RBTNode<T> newNode) {
    
    //No-need-for-repair case:
    if (!newNode.parent().isRed) {
      return;                         
    }
    
    //The case where the repair process SHOULD be handled:
    //1. The aunt is Black:
    RBTNode<T> grandfather = newNode.parent().parent();
    RBTNode<T> father = newNode.parent();
    
    //Also determine the aunt:
    RBTNode<T> aunt;
    if (grandfather.childLeft() != father) {
      aunt = grandfather.childLeft();
    }
    else {
      aunt = grandfather.childRight();
    }
    
    
    // Father is the left child of the grandfather, aunt is black/null
    if (grandfather.childLeft() == father && (aunt == null || !aunt.isRed())) {
      this.repairRedBlackTree(newNode, true);
    }
    
    // Father is the right child of the grandfather, aunt is black/null
    else if (grandfather.childRight() == father && (aunt == null || !aunt.isRed())) {
      this.repairRedBlackTree(newNode, false);
    }
    
    //2. The aunt is Red:
    else {
      grandfather.flipColor();
      grandfather.childLeft().flipColor();
      grandfather.childRight().flipColor();
      
      //Check if the grandfather is the Root node:
      if (grandfather == this.root && grandfather.isRed) {
        grandfather.flipColor();
      }
      //If it is non-root, recursively check for properties:
      else {
        this.ensureRedProperty(grandfather);
      }
    }
  }
  
  
  
  
  /**
   * Inserts a new data value into the sorted collection.
   * @param data the new value being inserted
   * @throws NullPointerException if data argument is null, we do not allow
   * null values to be stored within a SortedCollection
   */
  @Override
  public void insert(T data) throws NullPointerException {
    
    //Check for null data case
    if (data == null) {
      throw new NullPointerException("Data is null, not allowed!");
    }
    
    RBTNode<T> theNode = new RBTNode<T>(data);
    //If it is the root, then just set it:
    if (this.root == null) {
      this.root = theNode;
      ((RBTNode<T>)this.root).flipColor();
    }
    else {
      insertHelper(theNode, this.root);
      ensureRedProperty(theNode);
    }
  }
  
  
  
  
  ///////////////////Testing////////////////////////////////////////////
  
  /**
   * This is the first tester method
   * 
   * Testing for root case + the aunt is black/null case and 
   * grandfather-father relationship is the same with father-child
   */
  @Test
  public void RBTtest1() {
    RedBlackTree<Integer> tree1 = new RedBlackTree<>();

    //Ensure size/data/color throughout the method
    //Insert 1: This is the root node with black color:
    tree1.insert(1);
    Assertions.assertTrue(tree1.size() == 1);
    Assertions.assertTrue(tree1.root.data == 1);
    Assertions.assertTrue(!((RBTNode<Integer>)(tree1.root)).isRed());

    
    //Insert 9: right child of root with red color:
    tree1.insert(9);
    Assertions.assertTrue(tree1.size() == 2);
    Assertions.assertTrue(tree1.root.childRight().data == 9);
    Assertions.assertTrue(!((RBTNode<Integer>)(tree1.root)).isRed());
    Assertions.assertTrue(((RBTNode<Integer>)tree1.root.childRight()).isRed());
    
    
    //Insert 12: right child of 9 with red color:
    //Check 9 is black at root, 1 at left be red and 12 at right be red
    //Notice here that 1-9 is same as 9-12 (Both right-child relationship)
    tree1.insert(12);
    Assertions.assertTrue(tree1.size() == 3);
    Assertions.assertTrue(tree1.root.data == 9);
    Assertions.assertTrue(!((RBTNode<Integer>)(tree1.root)).isRed());
    Assertions.assertTrue(tree1.root.childRight().data == 12);
    Assertions.assertTrue(((RBTNode<Integer>)tree1.root.childRight()).isRed());
    Assertions.assertTrue(tree1.root.childLeft().data == 1);
    Assertions.assertTrue(((RBTNode<Integer>)tree1.root.childLeft()).isRed());
  }
  
  
  
  
  /**
   * This is the second tester method
   * 
   * Testing for root case + the aunt is black/null case and 
   * grandfather-father relationship is NOT same with father-child
   */
  @Test
  public void RBTtest2() {
    RedBlackTree<Integer> tree2 = new RedBlackTree<>();

    //Ensure size/data/color throughout the method
    //Insert 1: This is the root node with black color:
    tree2.insert(1);
    Assertions.assertTrue(tree2.size() == 1);
    Assertions.assertTrue(tree2.root.data == 1);
    Assertions.assertTrue(!((RBTNode<Integer>)(tree2.root)).isRed());

    
    //Insert 9: right child of root with red color:
    tree2.insert(9);
    Assertions.assertTrue(tree2.size() == 2);
    Assertions.assertTrue(tree2.root.childRight().data == 9);
    Assertions.assertTrue(!((RBTNode<Integer>)(tree2.root)).isRed());
    Assertions.assertTrue(((RBTNode<Integer>)tree2.root.childRight()).isRed());

    //Notice that the above setting is exactly the same with tester1
    
    
    //Insert 7: left child of 9 with red color:
    //Check 7 is black root, 1 is left red and 9 is right red
    tree2.insert(7);
    Assertions.assertTrue(tree2.size() == 3);
    Assertions.assertTrue(tree2.root.data == 7);
    Assertions.assertTrue(!((RBTNode<Integer>)(tree2.root)).isRed());
    Assertions.assertTrue(tree2.root.childRight().data == 9);
    Assertions.assertTrue(((RBTNode<Integer>)tree2.root.childRight()).isRed());
    Assertions.assertTrue(tree2.root.childLeft().data == 1);
    Assertions.assertTrue(((RBTNode<Integer>)tree2.root.childLeft()).isRed());
  }
  
  
  
  
  
  
  
  /**
   * This is the third tester method
   * 
   * Testing for root case + the aunt is red case
   * 
   */
  @Test
  public void RBTtest3() {
    RedBlackTree<Integer> tree3 = new RedBlackTree<>();
    
    //Will use the above test2 as the general setting (but with the 7 first)
    tree3.insert(7);
    tree3.insert(1);
    tree3.insert(9);
    
    
    //Let's add a four below 7
    tree3.insert(4);
    
    //What we want now is that 1,7,9 flip color:
    // 1 and 9 are all black, while 7 remains black because it is the ROOT!
    Assertions.assertTrue(tree3.size() == 4);
    Assertions.assertTrue(tree3.root.data == 7);
    Assertions.assertTrue(!((RBTNode<Integer>)(tree3.root)).isRed());
    Assertions.assertTrue(tree3.root.childRight().data == 9);
    Assertions.assertTrue(!((RBTNode<Integer>)(tree3.root.childRight())).isRed());
    Assertions.assertTrue(tree3.root.childLeft().data == 1);
    Assertions.assertTrue(!((RBTNode<Integer>)tree3.root.childLeft()).isRed());
    Assertions.assertTrue(tree3.root.childLeft().childRight().data == 4);
    Assertions.assertTrue(((RBTNode<Integer>)tree3.root.childLeft().childRight()).isRed());
    
  }
  
  
  
  
  /**
   * This is the fourth tester method
   * 
   * Testing for non-root case + the aunt is red case
   * (It means needs recuring repairing to make it valid)
   * 
   * Also, this is from the Q03 Question3
   */
  @Test
  public void RBTtest4() {
    RedBlackTree<String> tree4 = new RedBlackTree<>();
    
    
    //Manually create this tree and connect to them
    //Tbh, don't let us do this again, this is so frustrating...
    RBTNode<String> M = new RBTNode<>("M");
    M.flipColor();
    
    RBTNode<String> F = new RBTNode<>("F");
    
    RBTNode<String> D = new RBTNode<>("D");
    D.flipColor();
    
    RBTNode<String> H = new RBTNode<>("H");
    H.flipColor();
    
    RBTNode<String> G = new RBTNode<>("G");
    RBTNode<String> I = new RBTNode<>("I");
    
    RBTNode<String> R = new RBTNode<>("R");
    RBTNode<String> T = new RBTNode<>("T");
    T.flipColor();
    
    RBTNode<String> W = new RBTNode<>("W");
    
    
    tree4.root = M;
    M.setChildLeft(F);
    F.setParent(M);
    M.setChildRight(T);
    T.setParent(M);
    F.setChildLeft(D);
    D.setParent(F);
    F.setChildRight(H);
    H.setParent(F);
    H.setChildLeft(G);
    G.setParent(H);
    H.setChildRight(I);
    I.setParent(H);
    T.setChildLeft(R);
    R.setParent(T);
    T.setChildRight(W);
    W.setParent(T);
    
    tree4.insert("L");
    
    //Please refer to the Q03 for picture, I feel so tired to manually use text
    //to show you one here, so please check yourself on Canvas.... 
    
    //Root
    Assertions.assertTrue(tree4.size() == 10);
    Assertions.assertTrue(tree4.root.getData().equals("H"));
    Assertions.assertTrue(!((RBTNode<String>)(tree4.root)).isRed());
    
    //Root left:
    Assertions.assertTrue(tree4.root.childLeft().getData().equals("F"));
    Assertions.assertTrue(((RBTNode<String>)(tree4.root.childLeft())).isRed());
    
    //Root right:
    Assertions.assertTrue(tree4.root.childRight().getData().equals("M"));
    Assertions.assertTrue(((RBTNode<String>)(tree4.root.childRight())).isRed());
    
    //Root left's left:
    Assertions.assertTrue(tree4.root.childLeft().childLeft().getData().equals("D"));
    Assertions.assertTrue(!((RBTNode<String>)(tree4.root.childLeft().childLeft())).isRed());
    
    //Root left's right:
    Assertions.assertTrue(tree4.root.childLeft().childRight().getData().equals("G"));
    Assertions.assertTrue(!((RBTNode<String>)(tree4.root.childLeft().childRight())).isRed());
    
    //Root right's left:
    Assertions.assertTrue(tree4.root.childRight().childLeft().getData().equals("I"));
    Assertions.assertTrue(!((RBTNode<String>)(tree4.root.childRight().childLeft())).isRed());
    
    //Root right's right:
    Assertions.assertTrue(tree4.root.childRight().childRight().getData().equals("T"));
    Assertions.assertTrue(!((RBTNode<String>)(tree4.root.childRight().childRight())).isRed());
    
    //Root right's left's right:
    Assertions.assertTrue(tree4.root.childRight().childLeft().childRight().getData().equals("L"));
    Assertions.assertTrue(((RBTNode<String>)(tree4.root.childRight().childLeft().childRight())).isRed());
    
    //Root right's right's right:
    Assertions.assertTrue(tree4.root.childRight().childRight().childRight().getData().equals("W"));
    Assertions.assertTrue(((RBTNode<String>)(tree4.root.childRight().childRight().childRight())).isRed());
    
    
    //Root right's right's left:
    Assertions.assertTrue(tree4.root.childRight().childRight().childLeft().getData().equals("R"));
    Assertions.assertTrue(((RBTNode<String>)(tree4.root.childRight().childRight().childLeft())).isRed());
  }
  
  

}

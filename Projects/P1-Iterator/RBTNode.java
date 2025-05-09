/**
 * This class represents a node in a RedBlackTree and inherits from BinaryTreeNode.
 */
public class RBTNode<T> extends BinaryTreeNode<T> {

    // store whether this is a red or black node
    protected boolean isRed = true;

    /**
     * Constructor that creates a new node with the value data.
     * Both parent and child references of the new node are initialized to null.
     * @param data the value the new node stores
     */
    public RBTNode(T data) { super(data); }

    /**
     * Overrides the childLeft() method from BinaryTreeNode so that child reference is returned
     * as an RBTNode and does not need to be casted.
     */
    @Override
    public RBTNode<T> childLeft() {
        return (RBTNode<T>)this.left;
    }

    /**
     * Overrides the childRight() method from BinaryTreeNode so that child reference is returned
     * as an RBTNode and does not need to be casted.
     */
    @Override
    public RBTNode<T> childRight() {
        return (RBTNode<T>)this.right;
    }

    /**
     * Overrides the parent() method from BinaryTreeNode so that child reference is returned
     * as an RBTNode and does not need to be casted.
     */
    @Override
    public RBTNode<T> parent() {
        return (RBTNode<T>)this.up;
    }

    /**
     * Returns a boolean that indicates if this is a red or black node.
     * @return true if the node is red, false if it is black
     */
    public boolean isRed() {
        return this.isRed;
    }

    /**
     * Inverts the color of this node, turning it either from red to black, or from
     * black to red.
     */
    public void flipColor() {
        this.isRed = !this.isRed;
    }

    /**
     * Returns a string representation for this node.
     * @return a string representation of the node's value and color
     */
    @Override
    public String toString() {
        return this.data.toString() + ( this.isRed() ? "(r)" : "(b)" );
    }

}

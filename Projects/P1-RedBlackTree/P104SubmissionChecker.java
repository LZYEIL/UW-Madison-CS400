import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
* This class extends the RedBlackTree class to run submission check on it.
*/
public class P104SubmissionChecker extends RedBlackTree<Integer> {

    /**
    * Submission check that checks if the root node of a newly created tree is null.
    * @return true is check passes, false if it fails
    */
    @Test
    public void submissionCheckerSmallTree() {
        RedBlackTree<String> tree = new RedBlackTree<>();
        tree.insert("a");
        tree.insert("b");
        tree.insert("c");
        Assertions.assertTrue(tree.root.toLevelOrderString().equals("[ b(b), a(r), c(r) ]"));
    }

    /**
    * Override for the ensureRedProperty method with required signature.
    * This will cause compilation to fail if the method does not exist.
    */
    @Override
    protected void ensureRedProperty(RBTNode<Integer> newNode){}

}

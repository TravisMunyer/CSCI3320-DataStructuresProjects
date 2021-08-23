import java.util.LinkedList;
import java.util.Queue;

/**
 * Implements an unbalanced binary search tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss.
 * Note: Minor modifications have been made from the implementation provided by Weiss.
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {
    /**
     * Construct the tree.
     */
    public BinarySearchTree() {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
     */
    public void insert(AnyType x) {
        root = insert(x, root);
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    public void remove(AnyType x) {
        root = remove(x, root);
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public AnyType findMin() {
        if (isEmpty())
            return null;
        return findMin(root).element;
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public AnyType findMax() {
        if (isEmpty())
            return null;
        return findMax(root).element;
    }

    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return true if not found.
     */
    public boolean contains(AnyType x) {
        return contains(x, root);
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty() {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree() {
        if (isEmpty())
            System.out.println("Empty tree");
        else
            printTree(root);
    }

    /**
     * Prints the tree contents in descending sorted order (Added by Travis).
     */
    public void printTreeDescending() {
        if (isEmpty())
            System.out.println("Empty tree.");
        else
            printTreeDescending(root);
    }

    /**
     * Print the tree contents in level-order (Added by Travis).
     */
    public void levelOrder(){
        if (isEmpty())
            System.out.println("Empty tree.");
        else
            levelOrder(root);
    }

    /**
     * Counts the number of leaves in the tree (added by Travis).
     * @return The number of leaves in the tree, or -1 if the tree is empty.
     */
    public int numLeaves(){
        if (isEmpty()) {
            return -1;
        }
        else
            return numLeaves(root);
    }

    /**
     * Internal method that prints the values between two nodes (added by Travis).
     * @param k1 Lower Bound (inclusive).
     * @param k2 Upper Bound (inclusive).
     */
    public void printBetween(AnyType k1, AnyType k2){
        if (k1.compareTo(k2) >= 0)
            System.out.println("K1 must be less than k2.");
        else
            printBetween(root, k1, k2);
    }

    /**
     * Counts the number of nodes with exactly one child in the tree (added by Travis).
     * @return The number of nodes with one child in the tree, or -1 if the tree is empty.
     */
    public int numOneChildNodes(){
        if (isEmpty()) {
            return -1;
        }
        else
            return numOneChildNodes(root);
    }

    /**
     * Counts the number of nodes with exactly two children in the tree (added by Travis).
     * @return The number of full nodes in the tree, or -1 if the tree is empty.
     */
    public int numTwoChildrenNodes(){
        if (isEmpty()) {
            return -1;
        }
        else
            return numTwoChildrenNodes(root);
    }

    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> t) {
        if (t == null)
            return new BinaryNode<AnyType>(x, null, null);

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            t.left = insert(x, t.left);
        else if (compareResult > 0)
            t.right = insert(x, t.right);
        else
            ;  // Duplicate; do nothing
        return t;
    }

    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t) {
        if (t == null)
            return t;   // Item not found; do nothing

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            t.left = remove(x, t.left);
        else if (compareResult > 0)
            t.right = remove(x, t.right);
        else if (t.left != null && t.right != null) // Two children
        {
            t.element = findMin(t.right).element;
            t.right = remove(t.element, t.right);
        } else
            t = (t.left != null) ? t.left : t.right;
        return t;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param t the node that roots the subtree.
     * @return node containing the smallest item.
     */
    private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t) {
        if (t == null)
            return null;
        else if (t.left == null)
            return t;
        return findMin(t.left);
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param t the node that roots the subtree.
     * @return node containing the largest item.
     */
    private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t) {
        if (t != null)
            while (t.right != null)
                t = t.right;

        return t;
    }

    /**
     * Recursive method to print all values between k1 and k2 (added by Travis).
     * @param t The BinaryNode to start at.
     * @param k1 Lower bound (inclusive).
     * @param k2 Upper bound (inclusive).
     */
    private void printBetween(BinaryNode<AnyType> t, AnyType k1, AnyType k2){
        if (t == null) return;

        int compareResultK1 = k1.compareTo(t.element);
        int compareResultK2 = k2.compareTo(t.element);

        // K1 and K2 are less than current element, traverse left.
        if (compareResultK1 < 0 && compareResultK2 < 0)
            printBetween(t.left, k1, k2);
        // K1 and K2 are greater than current element, traverse right.
        if (compareResultK1 > 0 && compareResultK2 > 0)
            printBetween(t.right, k1, k2);
        // In between K1 and K2, traverse normally and print nodes.
        else if (compareResultK1 < 0 && compareResultK2 > 0){
            printBetween(t.left, k1, k2);
            System.out.print(t.element + " ");
            printBetween(t.right, k1, k2);
        }
        // Edge case, print the value if we are at K1 and check right subtree.
        else if (compareResultK1 == 0){
            System.out.print(t.element + " ");
            printBetween(t.right, k1, k2);
        }
        // Edge case, ignore the right subtree if we are at K2.
        else if ( compareResultK2 == 0){
            System.out.print(t.element + " ");
            printBetween(t.left, k1, k2);
        }
    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private boolean contains(AnyType x, BinaryNode<AnyType> t) {
        if (t == null)
            return false;

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            return contains(x, t.left);
        else if (compareResult > 0)
            return contains(x, t.right);
        else
            return true;    // Match
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param t the node that roots the subtree.
     */
    private void printTree(BinaryNode<AnyType> t) {
        if (t != null) {
            printTree(t.left);
            System.out.print(t.element + " ");
            printTree(t.right);
        }
    }

    /**
     * Internal method to print a subtree in descending sorted order (Added by Travis).
     * @param t the node that roots the subtree.
     */
    private void printTreeDescending(BinaryNode<AnyType> t){
        if (t!= null){
            printTreeDescending(t.right);
            System.out.print(t.element + " ");
            printTreeDescending(t.left);
        }
    }

    /**
     * Internal method to print a subtree in level-order (Added by Travis).
     * @param t the node that roots the subtree.
     */
    private void levelOrder(BinaryNode<AnyType> t){
        Queue<BinaryNode> queue = new LinkedList<>();
        BinaryNode n;

        queue.add(t);

        while(!queue.isEmpty()){
            n = queue.remove();
            System.out.print(n.element + " ");

            if(n.left != null)
                queue.add(n.left);
            if (n.right != null)
                queue.add(n.right);
        }
    }

    /**
     * Internal method to count the number of leaves in the BinarySearchTree starting at t (Added by Travis).
     * @param t root BinaryNode of tree.
     * @return number of leaves in tree.
     */
    private int numLeaves(BinaryNode<AnyType> t){
        if (t == null) return 0;

        int isLeaf = 0;

        if (t.left == null && t.right == null) isLeaf = 1;

        return isLeaf + numLeaves(t.left) + numLeaves(t.right);
    }

    /**
     * Internal method to count the number of nodes with exactly one child
     * in the BinarySearchTree starting at t (Added by Travis).
     * @param t root BinaryNode of tree.
     * @return int count of the number of nodes with exactly one child in the BinarySearchTree.
     */
    private int numOneChildNodes(BinaryNode<AnyType> t){
        if (t == null) return  0;

        int hasOneChild = 0, emptyNodes = 0;

        if (t.left == null) emptyNodes++;
        if (t.right == null) emptyNodes++;
        if (emptyNodes == 1) hasOneChild = 1;

        return hasOneChild + numOneChildNodes(t.left) + numOneChildNodes(t.right);
    }

    /**
     * Internal method to count the number of nodes with exactly two children
     * in the BinarySearchTree starting at t (Added by Travis).
     * @param t root BinaryNode of tree.
     * @return int count of the number of nodes with exactly two children in the BinarySearchTree.
     */
    private int numTwoChildrenNodes(BinaryNode<AnyType> t){
        if (t == null) return 0;

        int hasTwoChildren = 0;

        if(t.left != null && t.right != null) hasTwoChildren = 1;

        return hasTwoChildren + numTwoChildrenNodes(t.left) + numTwoChildrenNodes(t.right);
    }

    /**
     * Internal method to compute height of a subtree.
     * @param t the node that roots the subtree.
     */
    private int height(BinaryNode<AnyType> t) {
        if (t == null)
            return -1;
        else
            return 1 + Math.max(height(t.left), height(t.right));
    }

    // Basic node stored in unbalanced binary search trees
    private static class BinaryNode<AnyType> {
        // Constructors
        BinaryNode(AnyType theElement) {
            this(theElement, null, null);
        }

        BinaryNode(AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt) {
            element = theElement;
            left = lt;
            right = rt;
        }

        AnyType element;            // The data in the node
        BinaryNode<AnyType> left;   // Left child
        BinaryNode<AnyType> right;  // Right child
    }


    /**
     * The tree root.
     */
    private BinaryNode<AnyType> root;

}

package Tree;

import Array.Element;
import Array.Queue;
import List.LinkedList;
import List.Node;

public class TreeNode {

    protected TreeNode left;
    protected TreeNode right;
    protected int data;

    public TreeNode(int data){
        this.data = data;
        this.left = null;
        this.right = null;
    }

    public TreeNode getLeft(){
        return left;
    }

    public TreeNode getRight(){
        return right;
    }

    public int getData(){
        return data;
    }

    public void setLeft(TreeNode left){
        this.left = left;
    }

    public void setRight(TreeNode right){
        this.right = right;
    }

    public void prettyPrint(int level){
        for (int i = 0; i < level; i++){
            System.out.print("\t");
        }
        System.out.println(data);
        if (left != null){
            left.prettyPrint(level + 1);
        }
        if (right != null){
            right.prettyPrint(level + 1);
        }
    }

    /**
     * Write the recursive method in TreeNode class which accumulates the contents (integer as data field) of all leaf
     * nodes in queue. For queue, you are only allowed to use enqueue function. You should use array  implementation
     * for the queue in this question.
     */
    public void accumulateLeafNodes(Queue queue){
        if (left != null)
            left.accumulateLeafNodes(queue);
        if (right != null)
            right.accumulateLeafNodes(queue);
        if (left == null && right == null)
            queue.enqueue(new Element(data));
    }

    /**
     * Write a recursive method which returns true if there is at least one node which is the average of its children
     * (left and right children must exist).
     */
    public boolean averageOfItsChildren(){
        if (left != null && right != null) {
            if (((double)left.data + right.data) / 2 == data) {
                return true;
            }
        }
        boolean leftSearch = left != null && left.averageOfItsChildren();
        boolean rightSearch = right != null && right.averageOfItsChildren();
        
        return leftSearch || rightSearch;
    }

    /**
     * Write a recursive method in TreeNode class which returns the number of nodes in the tree that satisfy the
     * following property: The node's key is the average of its children (left and right children).
     */
    public int averages(){
        int count = 0;
        if (left != null && right != null) {
            if (((double)left.data + right.data) / 2 == data) {
                count++;
            }
        }
        if (left != null)
            count += left.averages();
        if (right != null)
            count += right.averages();
        return count;
    }

    /**
     * Write the recursive method in TreeNode class, which collects all values in all nodes in the tree in a sorted
     * manner. You are not allowed to use any tree methods.
     */
    public int[] collectNodes(){
        // sorted manner = inorder traversal
        int[] leftArr;
        if (left != null) {
            leftArr = left.collectNodes();
        } else {
            leftArr = new int[0];
        }
        int[] rightArr;
        if (right != null) {
            rightArr = right.collectNodes();
        } else {
            rightArr = new int[0];
        }
        
        int[] arr = new int[leftArr.length + 1 + rightArr.length];
        for (int i = 0; i < leftArr.length; i++) {
            arr[i] = leftArr[i];
        }
        arr[leftArr.length] = data;
        for (int i = 0; i < rightArr.length; i++) {
            arr[leftArr.length + 1 + i] = rightArr[i];
        }
        
        return arr;
    }

    /**
     * Write a \textbf{recursive} method in TreeNode Class given a binary search tree (BST) and an array of integers
     * representing a potential path. This method should return {$true$} if the sequence of values in the path array
     * matches a valid path from the current node down to a node in the BST, and {$false$} otherwise. Hint: you should
     * create an extra array to forward reduced path to the children.
     */
    public boolean hasPath(int[] path){
        if (path[0] != data) return false;
        else if (path.length == 1) return true;
        else {
            int[] newPath = new int[path.length - 1];
            for (int i = 0; i < path.length - 1; i++) {
                newPath[i] = path[i + 1];
            }
            path = newPath;
            boolean leftSearch = left != null && left.hasPath(path);
            boolean rightSearch = right != null && right.hasPath(path);
            return leftSearch || rightSearch;
        }
    }

    /**
     * Write a recursive method , which returns the number of nodes in the binary search tree which have value larger
     * than $X$. Your method should run in ${\cal O}(\log N + K)$ time, where $N$ is total number of nodes and $K$ is
     * the number of nodes which have value larger than $X$ in the tree. Do not use any class or external methods.
     */
    public int higherThanX(int X){
        int count = 0;
        if (data <= X) {
            if (right != null)
                count += right.higherThanX(X);
            else return count;
        } else {
            count = 1;
            if (left != null)
                count += left.higherThanX(X);
            if (right != null)
                count += right.higherThanX(X);
        }
        return count;
    }

    /**
     * Given a binary tree~(not necessarily search tree), implement a method in TreeNode class to check whether an input
     * binary tree is a mirror of itself (symmetric). You may not use any additional data structure or array.
     */
    public boolean isMirror(TreeNode left, TreeNode right){
        if (left == null && right == null) return true;
        if (left == null || right == null) return false;
        if (left.data != right.data) return false;
        
        return isMirror(left.left, right.right) && isMirror(left.right, right.left);
    }

    /**
     * Write a class method in TreeNode class that returns the number of {\bf leftist} nodes in a binary
     * tree. A node is {\bf leftist} if
     * <ul>
     *     <li>Its left child's data is larger than its right child's data or,</li>
     *     <li>It has only left child.</li>
     * </ul>
     */
    public int leftist(){
        int count = 0;
        if (left != null && right != null) {
            if (left.data > right.data) count++;
        }
        if (left != null && right == null) count++;
        if (left != null) count += left.leftist();
        if (right != null) count += right.leftist();
        return count;
    }

    /**
     * Write the recursive method in TreeNode class which collects all values in all nodes in the tree. The size of the
     * array should be as large as it should be. (Hint: First calculate how many items the array should contain, then
     * write the same code to fill in the array. The left and right child function calls should return arrays, and the
     * method should concatenate them)
     */
    public int[] lessThanX(int x){
        if (data < x) {
            // sorted manner = inorder traversal
            int[] leftArr;
            if (left != null) {
                leftArr = left.lessThanX(x);
            } else {
                leftArr = new int[0];
            }
            int[] rightArr;
            if (right != null) {
                rightArr = right.lessThanX(x);
            } else {
                rightArr = new int[0];
            }
            
            int[] arr = new int[leftArr.length + 1 + rightArr.length];
            for (int i = 0; i < leftArr.length; i++) {
                arr[i] = leftArr[i];
            }
            arr[leftArr.length] = data;
            for (int i = 0; i < rightArr.length; i++) {
                arr[leftArr.length + 1 + i] = rightArr[i];
            }
            
            return arr;
        } else {
            if (left != null)
                return left.lessThanX(x);
            else return new int[0];
        }
    }

    /**
     * Write a recursive method in TreeNode class that finds the number of duplicate keys in a binary search tree.
     * Assume that if a key is duplicate, it occurs at most twice. Hint: The duplicate of a key is either the maximum
     * number on its left subtree or the minimum number on its right subtree.
     */
    public int numberOfDuplicates(){
        int duplicate = 0;

        if (left != null) {
            TreeNode temp = left;
            while (temp.right != null) {
                temp = temp.right;
            }
            if (temp.data == data) {
                duplicate++;
            }
        }

        if (right != null) {
            TreeNode temp = right;
            while (temp.left != null) {
                temp = temp.left;
            }
            if (temp.data == data) {
                duplicate++;
            }
        }

        if (left != null)
            duplicate += left.numberOfDuplicates();
        if (right != null)
            duplicate += right.numberOfDuplicates();

        return duplicate;
    }

    /**
     * Write a recursive method in the {\bf TreeNode} class, which returns the keys on the path in the linked list $l$,
     * where the path is defined by the current parent as follows: If the parent is odd, go left; otherwise go right.
     * Assume that the function is called with an empty linked list for the root node.
     */
    public void pathToLinkedList(LinkedList l){
        TreeNode temp = this;
        while (temp != null) {
            l.insertLast(new Node(temp.data));
            if (temp.data % 2 == 0) {
                if (right != null)
                    temp = temp.right;
                else return;
            } else {
                if (left != null)
                    temp = temp.left;
                else return;
            }
        }
    }

    /**
     * Write a method that computes the products of all keys in a binary search tree.
     */
    public int productOfTree(){
        int product = data;
        if (left != null)
            product *= left.productOfTree();
        if (right != null)
            product *= right.productOfTree();
        return product;
    }

    /**
     * Write the recursive method in TreeNode class which returns the sum of the keys between p and q in the tree. Your
     * algorithm should run in ${\cal O}(\log N + K)$, where K is the number of nodes which have value larger than p and
     * less than q in the tree.
     */
    public int sumOfNodesBetween(int p, int q){
        int sum = 0;
        if (data <= p) {
            if (right != null)
                sum += right.sumOfNodesBetween(p, q);
            else return sum;
        } else if (data < q) {
            sum = data;
            if (left != null)
                sum += left.sumOfNodesBetween(p, q);
            if (right != null)
                sum += right.sumOfNodesBetween(p, q);
            else return sum;
        } else {
            if (left != null)
                sum += left.sumOfNodesBetween(p, q);
            else return sum;
        }
        return sum;
    }

    /**
     * Write a recursive method in TreeNode class that computes the sum of all keys that are less than $X$ in a binary
     * search tree. You are not allowed to use any tree methods, just attributes, constructors, getters and setters.
     */
    public int sumOfTree(int X){
        int sum = 0;
        if (data < X)
            sum += data;
        if (left != null)
            sum += left.sumOfTree(X);
        if (right != null)
            sum += right.sumOfTree(X);
        return sum;
    }

    /**
     * Write a recursive method which returns the number of nodes which value is greater than its
     * children's average. You may assume left and right children must exist
     * for all non-leaf nodes.
     */
    public int greaterThanChildren(){
        int count = 0;
        if (left != null && right != null) {
            if (data > ((double)left.data + right.data) / 2)
                count++;
        }
        if (left != null)
            count += left.greaterThanChildren();
        if (right != null)
            count += right.greaterThanChildren();
        return count;
    }

    /**
     *  Write a recursive method in TreeNode class that computes the sum of quadratic powers of
     * all keys in a binary search tree.
     */
    public int quadraticSummation() {
        int sum = data * data;
        if (left != null)
            sum += left.quadraticSummation();
        if (right != null)
            sum += right.quadraticSummation();
        return sum;
    }

}

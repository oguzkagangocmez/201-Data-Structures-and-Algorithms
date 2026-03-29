package Array;

import List.LinkedList;
import List.Node;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

public class DisjointSet {
    private Set[] sets;
    private int count;

    public DisjointSet(int count){
        sets = new Set[count];
        for (int i = 0; i < count; i++){
            sets[i] = new Set(i, i);
        }
        this.count = count;
    }

    public DisjointSet(int[] elements, int count){
        sets = new Set[count];
        for (int i = 0; i < count; i++){
            sets[i] = new Set(elements[i], i);
        }
        this.count = count;
    }

    public void union(int index1, int index2){
        int x = findSetIterative(index1);
        int y = findSetIterative(index2);
        if (sets[x].getDepth() < sets[y].getDepth()){
            sets[x].setParent(y);
        } else {
            sets[y].setParent(x);
            if (sets[x].getDepth() == sets[y].getDepth()){
                sets[x].incrementDepth();
            }
        }
    }

    public int findSetRecursive(int index){
        int parent = sets[index].getParent();
        if (parent != index){
            return findSetRecursive(parent);
        }
        return parent;
    }

    public int findSetIterative(int index){
        int parent = sets[index].getParent();
        while (parent != index){
            index = parent;
            parent = sets[index].getParent();
        }
        return parent;
    }

    public int setCount(){
        int total = 0;
        for (int i = 0; i < count; i++){
            if (findSetRecursive(i) == i){
                total++;
            }
        }
        return total;
    }

    /**
     * Given in the index of a set as {\em current}, write a recursive method that returns the ascendants (including also
     * current) of that set as an array. You are not allowed to use any class method except getParent.
     */
    public int[] ascendants1(int current){
        if (sets[current].getParent() == current) {
            return new int[]{current};
        } else {
            int parent = sets[current].getParent();
            int[] upAsc = ascendants1(parent);
            int[] currAsc = new int[upAsc.length + 1];
            System.arraycopy(upAsc, 0, currAsc, 1, upAsc.length);
            currAsc[0] = current;
            return currAsc;
        }
    }

    /**
     * Given the index of a set, write a non-recursive function that returns the ancestors (itself, parent, grandparent,
     * etc.). The size of the returning array should be as much as needed.
     */
    public int[] ascendants2(int index){
        int[] asc = new int[0];
        while (true) {
            int[] newAsc = new int[asc.length + 1];
            System.arraycopy(asc,0, newAsc, 0, asc.length);
            newAsc[asc.length] = index;
            asc = newAsc;
            if (sets[index].getParent() == index) break;
            index = sets[index].getParent();
        }
        return asc;
    }

    /**
     * You are given a set of equalities such as
     * 0=9
     * 1=2
     * 3=5
     * 5=7
     * 9=4
     * 5=4
     * 6=8
     * where numbers correspond to variables. When the equalities are combined, we get
     * 0=9=4=3=5=7
     * 1=2
     * 6=8
     * 3 equalities. Write the function that finds the number of equalities when combined where $N$ represents the number
     * of variables, left and right represent the left and right parts of the equalities.
     */
    public static int combine(int N, int[] left, int[] right){
        DisjointSet ds = new DisjointSet(N);
        for (int i = 0; i < left.length; i++)
            ds.union(left[i], right[i]);
        return ds.setCount();
    }

    /**
     * Given in the index of a set as {\em current}, write a recursive method that returns the descendants of that set to
     * the array list.
     */
    // IMPOSSIBLE 😭due to constraints -> recursive, no helper method, no parameter change
    public int[] descendants1(int current){
        return null;
    }
    /**
     * Given in the index of a set as {\em current}, write a non-recursive method that returns the descendants of that set to
     * the array list.
     */
    public int[] descendants2(int current){
        int[] des = new int[0];
        for (int i = 0; i < sets.length; i++) {
            int temp = i;
            boolean isDescendant = false;
            while (sets[temp].getParent() != i) {
                temp = sets[temp].getParent();
                if (sets[temp].getParent() == current) {
                    isDescendant = true;
                    break;
                }
                if (sets[temp].getParent() == temp) break;
            }
            if (isDescendant) {
                int[] newDes = new int[des.length + 1];
                System.arraycopy(des, 0, newDes, 0, des.length);
                newDes[des.length] = i;
                des = newDes;
            }
        }
        return des;
    }

    /**
     * Write the method which returns the indexes of all sets in the disjoint set where a set with index $index$ is in
     * that set.
     */
    public int[] getSetWithIndex(int index){
        int parent = findSetIterative(index);
        int[] indexes = new int[0];
        for (int i = 0; i < sets.length; i++) {
            if (findSetIterative(i) == parent) {
                int[] newIndexes = new int[indexes.length + 1];
                for (int j = 0; j < indexes.length; j++) {
                    newIndexes[j] = indexes[j];
                }
                newIndexes[indexes.length] = i;
                indexes = newIndexes;
            }
        }
        return indexes;
    }

    /**
     * Given the index of a set, write a method that returns the indexes of its grandchildren as a linked list. Do not
     * use any class or external methods.
     */
    public LinkedList grandChildren(int index){
        LinkedList list = new LinkedList();
        for (int i = 0; i < sets.length; i++) {
            if (i != index && sets[i].getParent() != index && sets[sets[i].getParent()].getParent() == index)
                list.insertLast(new Node(i));
        }
        return list;
    }

    /**
     * Write a method that returns true when the given disjoint set is valid, that is from every node $n$, when the
     * ascendants are traversed, no circularity is observed (that is you do not encounter the node $n$ again).
     */
    public boolean isValid(){
        boolean isValid = true;
        boolean[] visited = new boolean[sets.length];
        for (int i = 0; i < sets.length; i++) {
            if (visited[i]) continue;
            visited[i] = true;
            int temp = i;
            while (sets[temp].getParent() != i) {
                temp = sets[temp].getParent();
                visited[temp] = true;
                if (temp == i) {
                    isValid = false;
                    break;
                } else if (sets[temp].getParent() == temp) break;
            }
        }
        return isValid;
    }


    /**
     * Write the method in {\bf DisjointSet} class that calculates the number of triplet disjoint sets in a disjoint set
     * structure. A disjoint set is a triplet, if the number of sets in that disjoint set is 3. Do not use any class or
     * external methods.
     */
    public int numberOfTriplets(){
        int triplets = 0;
        boolean[] visited = new boolean[sets.length];
        for (int i = 0; i < sets.length; i++) {
            if (visited[i]) continue;
            visited[i] = true;
            int cardinality = 0;
            for (int j = 0; j < sets.length; j++) {
                if (findSetIterative(i) == findSetIterative(j)) {
                    visited[j] = true;
                    cardinality++;
                }
            }
            if (cardinality == 3) triplets++;
        }
        return triplets;
    }

    /**
     * In an unknown world, there are people (zombie) who bites other people (may or may not be zombie). One is converted
     * to a zombie, if he/she was bitten by some other zombie. Given who bites whom in two arrays, identify the number of
     * survivors, that is the number of people who hasn't been bitten yet. Write the static method where whoBites[i] has
     * bitten whoWasBitten[i] correspondingly. Count represents the number of people in the beginning. Size represents
     * the size of the arrays whoBites and whoWasBitten. You are only allowed to use one external Disjoint Set.
     */
    public static int numberOfSurvivors(int count, int[] whoBites, int[] whoWasBitten){
        DisjointSet ds = new DisjointSet(count);
        int survivor = 0;
        for (int i = 0; i < whoBites.length; i++)
            ds.union(whoBites[i], whoWasBitten[i]);
        for (int i = 0; i < ds.sets.length; i++)
            if (ds.sets[i].getParent() == i && ds.sets[i].getDepth() == 1)
                survivor++;
        return survivor;
    }

    /**
     * Write a function that merges three sets given their indexes. You can use {\em findSet} method, but not the
     * original {\em union} method. Merge the sets such that the resulting merged set will have the minimum depth.
     * Update also the depth if needed.
     */
    public void union2(int index1, int index2, int index3){
        int a = findSetIterative(index1);
        int b = findSetIterative(index2);
        int c = findSetIterative(index3);
        if (sets[a].getDepth() < sets[b].getDepth()) {
            sets[a].setParent(b);
            if (sets[b].getDepth() < sets[c].getDepth()) {
                sets[b].setParent(c);
            } else {
                sets[c].setParent(b);
                if (sets[b].getDepth() == sets[c].getDepth())
                    sets[b].incrementDepth();
            }
            
        } else {
            sets[b].setParent(a);
            if (sets[b].getDepth() == sets[a].getDepth())
                sets[a].incrementDepth();
            
            if (sets[a].getDepth() < sets[c].getDepth()) {
                sets[a].setParent(c);
            } else {
                sets[c].setParent(a);
                if (sets[a].getDepth() == sets[c].getDepth())
                    sets[a].incrementDepth();
            }
        }
    }

    /**
     * Write the method that merges $N$ sets given their indexes in the indexList. You should use findSet and the
     * original unionOfSets method. Merge the sets such that the resulting merged set will have the minimum depth. Use
     * an algorithm that sorts the sets according to their depths.
     */
    public void unionOfSets(int[] indexList){
        for (int i = 0; i < indexList.length - 1; i++) {
            union(indexList[i], indexList[i + 1]);
        }
    }

    /**
     * Given the index of a set $S$, write a method that unmerges (creates disjoint sets of) it. After unmerging, the
     * direct children of $S$ and $S$ itself will be disjoint sets themselves. You don't need to modify the depths. Do
     * not use any class or external methods.
     */
    public void unmerge(int index) {
        for (int i = 0; i < sets.length; i++) {
            if (i != index && sets[i].getParent() == index) {
                sets[i].setParent(i);
                break;
            }
        }
    }

    /**
     * Given the index of a set, write a recursive function that finds the value of that set. The value of a node is 1 +
     * maximum value of its children. Any node with no children has value 1. You are not allowed to use any class method
     * except getParent.
     */
    public int value(int index){
        int max = index + 1;
        
        for (int i = 0; i < sets.length; i++) {
                if (i != index && findSetIterative(i) == index) {
                    int childValue = value(i);
                    if (childValue > max) {
                        max = childValue;
                    }
                }
            }
        
        return max;
    }

    /**
     Write the method in {\bf DisjointSet} class which returns the number of sets that have same height as the set with given
     index. The height of an element is calculated by the number of nodes visited when we traverse the tree starting from that set and continue
     through the parent links until the top. Do not use any class or external methods.
     */
    public int sameHeightSets(int index) {
        int height = 1;
        while (sets[index].getParent() != index) {
            index = sets[index].getParent();
            height++;
        }
        int result = 0;
        
        for (int i = 0; i < sets.length; i++) {
            int tempHeight = 1;
            int temp = i;
            while (sets[temp].getParent() != temp) {
                temp = sets[temp].getParent();
                tempHeight++;
            }
            if (tempHeight == height) result++;
        }
        
        return result;
    }

    /**
     Write the method in {\bf DisjointSet} class which returns count of sets where direct children's~(not including grandchildren) total data value is equal
     to set's parent's data value. Root node's parent is itself and a leaf node does have any children. Do not use any class or external
     methods.
     */
    public int childrenParentEqual() {
        int equal = 0, sum;
        for (int i = 0; i < sets.length; i++) {
            sum = 0;
            for (int j = 0; j < sets.length; j++) {
                if (i != j && sets[j].getParent() == i) sum += j;
            }
            if (i == sum) equal++;
        }
        return equal;
    }
}

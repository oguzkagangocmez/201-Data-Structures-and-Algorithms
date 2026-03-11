package Array;

import java.util.Arrays;

public class Hash {

    private Element[] table;

    private boolean[] deleted;

    private int N;

    public Hash(int N){
        table = new Element[N];
        deleted = new boolean[N];
        this.N = N;
    }

    private int hashFunction(int value){
        return value % N;
    }

    public Element search(int value){
        int address;
        address = hashFunction(value);
        while (table[address] != null){
            if (!deleted[address] && table[address].getData() == value){
                break;
            }
            address = (address + 1) % N;
        }
        return table[address];
    }

    public void insert(int value){
        int address;
        address = hashFunction(value);
        while (table[address] != null && !deleted[address]){
            address = (address + 1) % N;
        }
        if (table[address] != null){
            deleted[address] = false;
        }
        table[address] = new Element(value);
    }

    public void insertArray(int[] data){
        for (int datum : data){
            insert(datum);
        }
    }

    public String toString(){
        String s = "";
        for (int i = 0; i < N; i++){
            if (!deleted[i] && table[i] != null){
                s += table[i].getData() + " ";
            }
        }
        return s.trim();
    }

    /**
     * Write a static method that takes an array of integers as a parameter and checks if the array contains any
     * duplicate elements. Your method should run in O($N$) time, where $N$ is the size of the array. You are allowed
     * to use any methods and external data structures we learned in the class.
     */
    public static boolean anyDuplicate(int[] array){
        int maxValue = Integer.MIN_VALUE;
        for (int v : array) if (v > maxValue) maxValue = v;
        Hash hash = new Hash(maxValue + 1);
        for (int val : array){
            if (hash.search(val) == null)
                hash.insert(val);
            else return true;
        }
        return false;
    }

    /**
     * Write a method that deletes all elements having value {\em X}. Assume also that {\em X} can exist more than once
     * in the hash table. For array implementation ssume that linear probing is used as the collision strategy. Do not
     * use any class or external methods except hashFunction.
     */
    public void deleteAll(int X){
        for (int i = 0; i < N; i++) {
            if (!deleted[i] && table[i] != null) {
                if (table[i].getData() == X)
                    deleted[i] = true;
            }
        }
    }

    /**
     * Write a \textbf{static} method in the Hash class (array implementation) This method should return true if any two
     * distinct pairs in the array have the same sum. Your method should run in {$O(N^2)$} time. You can use
     * \textbf{at most one} external hash. Hint: You must store pairwise sums in the external hash table.
     */
    public static boolean equalPairSums(int[] array){
        Hash hash = new Hash((int) Math.pow(array.length, 4));
        for (int v : array)
            hash.insert(v);
        
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                int sum = array[i] + array[j];
                if (hash.search(sum) != null) return true;
            }
        }
        return false;
    }

    /**
     * Write an hash function that maps the key values in an hash table into an hash value. Assume that the hash value
     * of an hash table can be obtained first by summing up the key values of the elements in the hash table and then
     * hashing the sum. Assume also that linear probing is used as the collision strategy. Do not use any class or
     * external methods except hashFunction.
     */
    public int hashFunctionItSelf(){
        int sum = 0;
        for (int i = 0; i < table.length; i++) {
            if (!deleted[i] && table[i] != null)
                sum += table[i].getData();
        }
        return sum % N;
    }

    /**
     * Write a static method in Hash class that takes an array of integers as a parameter and counts the number of extra
     * elements in the array. {\bf Your method should run in ${\cal O}(N)$ time, where N is the size of the array}.
     * Use hashing.
     */
    public static int numberOfExtras(int[] array){
        Hash hash = new Hash(array.length);
        int unique = 0;
        for (int v : array) {
            if (hash.search(v) == null) {
                hash.insert(v);
                unique++;
            }
        }
        return array.length - unique;
    }

    /**
     * Write the method that finds the number of clusters in hash table. A cluster is a contiguous group of non-null
     * elements in the array.
     */
    public int numberOfClusters() {
        int numOfCluster = 0;
        boolean isInCluster = false;
        
        for (int i = 0; i < table.length; i++) {
            if (!deleted[i]) {
                if (table[i] != null) {
                     if (!isInCluster) {
                         isInCluster = true;
                         numOfCluster++;
                     }
                } else {
                    isInCluster = false;
                }
            } else {
                isInCluster = false;
            }
        }
        
        return numOfCluster;
    }

    /**
     * Write a method that simplifies a hash table by creating a new hash table containing elements from the original hash table, where
     * <ol>
     * <item> For single occurrence of a value, copy that value to the new table </item>
     * <item> For multiple occurrences of that value, copy that value only once to the new table </item>
     * </ol>
     */
    public Hash simplify(){
        Hash simple = new Hash(N);
        for (int i = 0; i < table.length; i++) {
            if (!deleted[i] && table[i] != null && simple.search(table[i].getData()) == null)
                simple.insert(table[i].getData());
        }
        return simple;
    }

    /**
     * Write a static method that takes an array of integers as a parameter (which contains distinct numbers less than
     * 2N, where $N$ is the number of elements in the array) and returns the sorted version of that array. Your method
     * should run in ${\cal O}(N)$ time. Do not use any external data structures or arrays except the resulting array
     * and hash table. Hint: Find the maximum number in the array, the sorted array should contain only numbers less
     * than that.
     */
    public static int[] sortByHashing(int[] array){
        int[] sorted = new int[array.length];
        if (array.length == 0) {
            return sorted;
        }

        int maxValue = Integer.MIN_VALUE;
        for (int value : array) {
            if (value > maxValue) {
                maxValue = value;
            }
        }

        Hash hash = new Hash(maxValue + 1);

        for (int value : array) {
            if (hash.table[value] == null) {
                hash.table[value] = new Element(1);
            } else {
                hash.table[value] = new Element(hash.table[value].getData() + 1);
            }
        }

        int index = 0;
        for (int value = 0; value <= maxValue; value++) {
            if (hash.table[value] != null) {
                int count = hash.table[value].getData();
                for (int i = 0; i < count; i++) {
                    sorted[index++] = value;
                }
            }
        }

        return sorted;
    }

    /**
     * Write a static method that should return true if any two distinct pairs absolute difference in the array
     * have the same value. Your method should run in $O(N^2)$ time. You may
     * assume array has at least 2 items. You can
     * use at most one external hash.
     */
    public static boolean equalPairDiffs(int[] array){
        Hash hash = new Hash((int) Math.pow(array.length, 4));
        
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                int diff = Math.abs(array[i] - array[j]);
                if (hash.search(diff) != null) return true;
                else hash.insert(diff);
            }
        }
        
        return false;
    }

}

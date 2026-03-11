package List;

public class Hash {

    private LinkedList[] table;

    private int N;

    public Hash(int N){
        table = new LinkedList[N];
        for (int i = 0; i < N; i++){
            table[i] = new LinkedList();
        }
        this.N = N;
    }

    public Node search(int value){
        int address;
        address = hashFunction(value);
        return table[address].search(value);
    }

    public void insert(int value){
        int address;
        address = hashFunction(value);
        table[address].insertFirst(new Node(value));
    }

    public void insertArray(int[] data){
        for (int datum : data){
            insert(datum);
        }
    }

    private int hashFunction(int value){
        return value % N;
    }

    public String toString(){
        String s = "";
        for (int i = 0; i < N; i++){
            if (!table[i].isEmpty()){
                s += table[i].toString() + " ";
            }
        }
        return s.trim();
    }

    /**
     * Write a method that deletes all elements having value {\em X}. Assume also that {\em X} can exist more than once
     * in the hash table. Do not use any class or external methods except hashFunction.
     */
    public void deleteAll(int X){
        int address = X % N;
        LinkedList list = table[address];
        while (list.head.data == X) {
            Node temp = list.head;
            Node newHead = temp.next;
            temp.next = null;
            list.head = newHead;
            if (list.head == null) return;
        }
        Node prev = list.head;
        Node runner = prev.next;
        while (runner != null) {
            if (runner.data == X) {
                prev.next = runner.next;
                runner.next = null;
                runner = prev.next;
            } else {
                prev = runner;
                runner = runner.next;
            }
        }
    }

    /**
     * Write a \textbf{static} method in the Hash class (linked list implementation) to find the difference of the
     * elements in two arrays and return a new array containing all elements from  {$list1$} that are not present in
     * {$list2$}. Your method should run in {$O(N)$} time, where {$N$} is the total number of elements in both arrays.
     * The difference array must contain exactly as many elements as needed (no extra or missing elements). You can use
     * \textbf{at most one} external hash.
     */
    public static int[] difference(int[] list1, int[] list2){
        Hash hash2 = new Hash(list1.length * 2);
        for (int v : list2)
            if (hash2.search(v) == null)
                hash2.insert(v);
        
        int[] difference = new int[0];
        for (int v : list1) {
            if (hash2.search(v) == null) {
                int [] newDiff = new int[difference.length + 1];
                for (int i = 0; i < difference.length; i++) {
                    newDiff[i] = difference[i];
                }
                newDiff[difference.length] = v;
                difference = newDiff;
            }
        }
        return difference;
    }

    /**
     * Write the static method to find the intersection of the elements in two arrays and return a new array. Your
     * method should run in ${\cal O}(N)$ time, where $N$ is the number of elements in the arrays. Do not use any
     * external data structures or arrays except the resulting array and hash table. The intersection array should
     * contain only that many items not more not less. Hint: How can you search an element from the first list in the
     * second list in ${\cal O}(1)$?
     */
    public static int[] intersection(int[] list1, int[] list2){
        Hash hash = new Hash(list1.length + list2.length + 1);
        for (int v : list2)
            if (hash.search(v) == null)
                hash.insert(v);
        
        int [] intersect = new int[0];
        
        for (int v : list1) {
            if (hash.search(v) != null) {
                int[] newIntersect = new int[intersect.length + 1];
                for (int i = 0; i < intersect.length; i++)
                    newIntersect[i] = intersect[i];
                newIntersect[intersect.length] = v;
                intersect = newIntersect;
            }
        }
        
        return intersect;
    }

    /**
     * Write the method in Hash class linked list implementation that checks if the hash table is valid or not. An hash
     * table is invalid if it contains the same number twice.  Your method should run in ${\cal O}(N)$ time. Do not use
     * external data structures or hash tables.
     */
    public boolean isValid(){
        for (int i = 0; i < table.length; i++) {
            if (table[i].head == table[i].tail) continue;
            else {
                Node ref = table[i].head;
                while (ref != null) {
                    Node scanner = ref.next;
                    while (scanner != null) {
                        if (scanner.data == ref.data) return false;
                        scanner = scanner.next;
                    }
                    ref = ref.next;
                }
            }
        }
        return true;
    }

    /**
     * Write the method \lstinline{int minTableSize(int[] numbers)} which identifies the minimum hash table size, where
     * there will be no collisions at all. Use an external linked list based hash table for checking collisions.
     */
    public static int minTableSize(int[] numbers){
        int start = numbers.length;
        while (true) {
            Hash hash = new Hash(start);
            boolean collision = false;
            for (int num : numbers) {
                int address = hash.hashFunction(num);
                if (hash.table[address].head != null) {
                    collision = true;
                    break;
                } else
                    hash.insert(num);
            }
            if (!collision) return start;
            start++;
        }
        
    }

    /**
     * Write a method that returns true if the hash table contains one node at maximum per linked list in separate
     * chaining, otherwise it returns false.
     */
    public boolean perfectMap(){
        for (LinkedList list : table) if (list.head != list.tail) return false;
        return true;
    }

    /**
     * Write a method that simplifies a hash table by creating a new hash table containing elements from the original hash table, where
     * <ol>
     * <item> For single occurrence of a value, copy that value to the new table </item>
     * <item> For multiple occurrences of that value, copy that value only once to the new table </item>
     * </ol>
     */
    public Hash simplify(){
        Hash hash = new Hash(table.length);
        
        for (LinkedList list : table) {
            Node temp = list.head;
            while (temp != null) {
                if (hash.search(temp.data) == null) hash.insert(temp.data);
                temp = temp.next;
            }
        }
        
        return hash;
    }

    /**
     * Write the static method in Hash class that takes an array of integers as a parameter and returns true if the sum
     * of four elements in the array is K.  Your method should run in $O(N^2)$ time. You are only allowed to use an
     * external array and an external hash table.
     */
    public static boolean sumOfFourK(int[] array, int K){
        Hash hash = new Hash(array.length * array.length);
        
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                int sum = array[i] + array[j];
                int needed = K - sum;
                if (needed < 0) continue;
                if (hash.search(needed) != null) return true;
                else hash.insert(sum);
            }
        }
        
        return false;
    }

    /**
     * Write the static method in Hash class that takes an array of integers as a parameter and returns true if the sum
     * of two elements in the array is $K$. Your method should run in ${\cal O}(N)$ time. Do not use any external data
     * structures or arrays except the external hash table.
     */
    public static boolean sumOfTwoK(int[] array, int k){
        Hash hash = new Hash(array.length + 1);
        for (int v : array) {
            int needed = k - v;
            if (hash.search(needed) != null) return true;
            else hash.insert(v);
        }
        return false;
    }

    /**
     * Write the static method to find the symmetric difference of the elements in two arrays $(A - B) \cup (B - A)$ and
     * return a new array. The symmetric difference array should contain only that many items not more not
     * less. Your method should run in ${\cal O}(N)$ time, where $N$ is the number of elements in the arrays. Do not use
     * any external data structures or arrays except the resulting array and two external hash tables. Write the method
     * for the array implementation.
     */
    public static int[] symmetricDiff(int[] list1, int[] list2){
        Hash first = new Hash(list1.length);
        Hash second = new Hash(list2.length);
        
        for (int val : list1) if (first.search(val) == null) first.insert(val);
        for (int val : list2) if (second.search(val) == null) second.insert(val);
        
        int size = 0;
        for (int val : list1) if (second.search(val) == null) size++;
        for (int val : list2) if (first.search(val) == null) size++;
        
        int[] symDiff = new int[size];
        int idx = 0;
        
        for (int val : list1) if (second.search(val) == null) symDiff[idx++] = val;
        for (int val : list2) if (first.search(val) == null) symDiff[idx++] = val;
        
        return symDiff;
    }

    /**
     * Write a static method to find the union of the elements in two arrays and return a new array. The union array
     * should contain only that  many items not more not less. Your method should run in O($N$) time, where N is the
     * number of elements in the  arrays. Do not use any external data structures or arrays except the resulting array
     * and an external hash table.
     */
    public static int[] union(int[] list1, int[] list2){
        Hash hash1 = new Hash(list1.length);
        Hash hash2 = new Hash(list2.length);
        
        for (int val : list1) if (hash1.search(val) == null) hash1.insert(val);
        for (int val : list2) if (hash2.search(val) == null) hash2.insert(val);
        
        int size = 0;
        for (int val : list1) if (hash1.search(val) != null) size++;
        for (int val : list2) if (hash1.search(val) == null) size++;
        
        int[] union = new int[size];
        int idx = 0;
        
        for (int val : list1) if (hash1.search(val) != null) union[idx++] = val;
        for (int val : list2) if (hash1.search(val) == null) union[idx++] = val;
        
        return union;
    }

    /**
     * Write a static method that takes an array of integers as a parameter and returns true if the
     * sum of three elements in the array is K. Your method should run in
     * $O(N^2)$ time. Do not use any external data structures or arrays except
     * the external hash table.
     */
    public static boolean sumOfThreeK(int[] array, int K) { // FIXME
        for (int i = 0; i < array.length; i++) {
            Hash hash = new Hash(array.length + 1);

            for (int j = i + 1; j < array.length; j++) {
                int needed = K - array[i] - array[j];
                if (needed < 0) continue;
                if (hash.search(needed) != null) {
                    return true;
                }
                hash.insert(array[j]);
            }
        }
        

        return false;
        
        
    }
}

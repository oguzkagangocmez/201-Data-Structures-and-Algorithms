package List;

public class LinkedList {

    protected Node head;
    protected Node tail;

    public LinkedList() {
        head = null;
        tail = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public Node getHead() {
        return head;
    }

    public void insertArray(int[] data) {
        for (int datum : data) {
            insertLast(new Node(datum));
        }
    }

    public void insertFirst(Node newNode) {
        if (isEmpty()) {
            tail = newNode;
        }
        newNode.setNext(head);
        head = newNode;
    }

    public void insertLast(Node newNode) {
        if (head == null) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
    }

    public Node search(int value) {
        Node tmp = head;
        while (tmp != null) {
            if (value == tmp.getData()) {
                return tmp;
            }
            tmp = tmp.getNext();
        }
        return null;
    }

    public void deleteFirst() {
        head = head.getNext();
        if (isEmpty()) {
            tail = null;
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        Node tmp = head;
        while (tmp != null) {
            result.append(tmp).append(" ");
            tmp = tmp.getNext();
        }
        return result.toString().trim();
    }
    
    //written by me to make easier some error handling for the methods below.
    public int length() {
        int len = 0;
        if (head == null) return len;
        else {
            Node temp = head;
            while (temp != null) {
                len++;
                temp = temp.getNext();
            }
            return len;
        }
    }

    /**
     * Write another constructor method which constructs the original list  from $k$ lists of size $m$ as follows. The
     * first, second, $\ldots$, $k$’th element of the original linked
     * list will be the first element of the first, second, . .
     * ., $k$’th input linked list, the  $k+1 $'st,  $k+2 $'nd, $\ldots$, $2k$’th element of the original linked
     * list will be the second element of the first, second, . .
     * ., $k$’th input linked list. The elements of the
     * original linked list should be created (not copied from
     * the linked lists). Your algorithm should run in ${\cal O}(km)$ time. Allocate and use an external array of size
     * $k$ to store current pointers in each of $k$ lists.
     */
    public LinkedList(LinkedList[] list, int k, int m) {
        this.head = null;
        this.tail = null;
        
        Node[] heads = new Node[k];
        for (int i = 0; i < k; i++) {
            heads[i] = list[i].getHead();
        }
        
        for (int y = 0; y < m; y++) {
            for (int x = 0; x < k; x++) {
                Node newNode = new Node(heads[x].data);
                if (head == null) {
                    head = newNode;
                } else {
                    tail.next = newNode;
                }
                tail = newNode;
                heads[x] = heads[x].getNext();
            }
        }
        
    }

    /**
     * Write the method which add newnode after each node in the singly linked list.
     */
    public void addAfterEachNode(Node newNode) {
        if (head == null) return;
        Node temp = head;
        
        while (temp != null){
            Node node = new Node(newNode.data);
            node.next = temp.next;
            temp.next = node;
            temp = temp.next.next;
        }
    }

    /**
     * Write a method which calculates the counts of each number in a sorted linked list. Linked list should contain a
     * count for every number. You can use insertLast. Algorithm is as follows:
     * <ul>
     *     <li>Traverse the list
     *     <ul>
     *         <li>When the number is the first number, insert possibly leading zeros, initialize count.</li>
     *         <li>When the number is the same as the previous number, increment the count</li>
     *         <li>If the number is the new, initialize the count, insert the count of the previous number and possibly
     *         leading zero counts.</li>
     *     </ul>
     *     </li>
     *     <li>For the last number, insert the count.</li>
     * </ul>
     */
    public LinkedList calculateCounts() {
        if (head == null) return null;
        LinkedList list = new LinkedList();
        Node curr = head;
        
        for (int i = 1; i < curr.data; i++) {
            list.insertLast(new Node(0));
        }
        int counter = 1;
        
        while (curr != null) {
            if (curr.next != null) {
                if (curr.next.data == curr.data) {
                    counter++;
                } else {
                    list.insertLast(new Node(counter));
                    counter = 1;
                    for (int i = 1; i < curr.next.data - curr.data; i++) {
                        list.insertLast(new Node(0));
                    }
                }
            } else {
                list.insertLast(new Node(counter));
            }
            curr = curr.next;
        }
        
        return list;
    }

    /**
     * Write a method which returns true if the singly linked list only contains duplicates, that is, every datum
     * (number) occurs only twice. Important warning, the duplicate elements may not be adjacent. You are not allowed
     * to use any singly linked list methods, just attributes, constructors, getters and setters.
     */
    public boolean containsOnlyDuplicates() {
        for (Node temp = head; temp != null; temp = temp.next) {
            int count = 0;
            for (Node runner = head; runner != null; runner = runner.next) {
                if (runner.data == temp.data) {
                    count++;
                }
            }
            if (count != 2) return false;
        }
        return true;
    }

    /**
     * Write a method which returns true if the single linked list only contains triplicates, that is, every datum
     * (number) occurs only three times. Important warning, the triplicate elements may not be adjacent. Your method
     * should have a time complexity of ${\cal O}(N^2)$. You are not allowed to use any single linked list methods, just
     * attributes, constructors, getters and setters.
     */
    public boolean containsOnlyTriplicates() {
        for (Node temp = head; temp != null; temp = temp.next) {
            int count = 0;
            for (Node runner = head; runner != null; runner = runner.next) {
                if (runner.data == temp.data) {
                    count++;
                }
            }
            if (count != 3) return false;
        }
        return true;
    }

    /**
     * Write a linear time method to delete the nodes indexed between p and q (including p'th and q'th items) from a
     * singly linked list.
     */
    public void deleteBetween(int p, int q) {
        int length = length();
        if (head == null || p < 0 || q > length) return;
        Node prevP = null;
        Node afterQ = head;
        
        for (int i = 0; i <= q; i++) {
            if (i < p)
                prevP = afterQ;
            afterQ = afterQ.next;
        }
        
        if (prevP == null) {
            head = afterQ;
            if (afterQ == null) {
                tail = null;
            }
        } else {
            if (afterQ == null) {
                tail = prevP;
                tail.next = null;
            } else {
                prevP.next = afterQ;
            }
        }
        
    }

    /**
     * Write the method which removes the nodes with even values in the original list. Your method should run in
     * ${\cal O}(N)$ time.
     */
    public void deleteEven() {
        if (head == null) return;
        while (head.data % 2 == 0) {
            head = head.next;
            if (head == null) {
                tail = null;
                return;
            }
        }
        Node prev = head;
        Node curr = head.next;
        while (curr != null) {
            if (curr.data % 2 == 0) {
                prev.next = curr.next;
                curr.next = null;
                curr = prev.next;
            } else {
                prev = curr;
                curr = curr.next;
            }
        }
    }

    /**
     * Write a function to delete $k$'th node from a singly linked list.
     */
    public void deleteKth(int K) {
        int length = length();
        if (K > length || head == null) return;
        else {
            Node prev = null;
            Node curr = head;
            for (int i = 0; i < K; i++) {
                prev = curr;
                curr = curr.next;
            }
            if (prev == null) {
                head = head.next;
            } else if (curr == tail) {
                tail = prev;
                tail.next = null;
            } else {
                prev.next = curr.next;
                curr.next = null;
            }
        }
    }

    /**
     * Write a function that will delete all {\bf prime} nodes that is their data field is prime such as 2, 3, 5, 7, etc.
     */
    public void deletePrimes() {
        if (head == null) return;
        while (isPrime(head.data)) {
            head = head.next;
            if (head == null) {
                tail = null;
                return;
            }
        }
        Node prev = head;
        Node temp = head.next;
        while (temp != null) {
            if (isPrime(temp.data)) {
                prev.next = temp.next;
                temp.next = null;
                temp = prev.next;
                if (temp == null) return;
            } else {
                prev = temp;
                temp = temp.next;
            }
        }
    }
    public static boolean isPrime(int N){
        if (N <= 1) return false;
        for (int i = 2; i < N; i++) {
            if (N % i == 0)
                return false;
        }
        return true;
    }

    /**
     * Write the static method in {\bf LinkedList} class to find the difference of the elements in two sorted linked
     * lists and return a new linked list. The resulting list should contain those elements that are in list1 but not
     * in list2. Do not modify linked lists list1 and list2. Your method should run in ${\cal O}(N)$ time. Nodes in the
     * resulting list should be new. You can not use any linked list methods except getters and setters.
     */
    public static LinkedList difference(LinkedList list1, LinkedList list2) {
        LinkedList diff = new LinkedList();
        Node ptr1 = list1.head;
        Node ptr2 = list2.head;
        
        while (ptr1 != null && ptr2 != null) {
            if (ptr1.data < ptr2.data) {
                Node newNode = new Node(ptr1.data);
                if (diff.head == null) {
                    diff.head = newNode;
                } else {
                    diff.tail.next = newNode;
                }
                diff.tail = newNode;
                ptr1 = ptr1.next;
            } else if (ptr1.data == ptr2.data) {
                ptr1 = ptr1.next;
                ptr2 = ptr2.next;
            } else {
                ptr2 = ptr2.next;
            }
        }
        
        while (ptr1 != null) {
            Node newNode = new Node(ptr1.data);
            if (diff.head == null) {
                diff.head = newNode;
            } else {
                diff.tail.next = newNode;
            }
            diff.tail = newNode;
            ptr1 = ptr1.next;
        }
        
        return diff;
    }

    /**
     * Write the algorithm Sieve of Eratosthenes to extract prime numbers using singly linked list. The algorithm works
     * as follows:
     * <ul>
     *     <li>Put all numbers starting from 2 to N in a linked list.</li>
     *     <li>While the linked list contains numbers
     *     <ul>
     *         <li>Remove the first element $p$ from the linked list. Print it (It is prime).</li>
     *         <li>Remove all elements from the linked list which are divisible by $p$. Do not print them.</li>
     *     </ul>
     *     <li>Return the linked list</li>
     *     </li>
     * </ul>
     */
    public static LinkedList eratosthenes(int N) {
        LinkedList list = new LinkedList();
        for (int i = 2; i <= N; i++) {
            list.insertLast(new Node(i));
        }
        for (Node temp = list.head; temp != null; temp = temp.next) {
            Node prev = temp;
            Node scan = prev.next;
            while (scan != null) {
                if (scan.data % temp.data == 0) {
                    prev.next = scan.next;
                    scan.next = null;
                    scan = prev.next;
                } else {
                    prev = scan;
                    scan = scan.next;
                }
            }
        }
        return list;
    }

    /**
     * Write the method which returns true if the singly linked list odd indexed elements are sorted increasing order
     * and even indexed elements are sorted in decreasing order. The first node has index 1. You are not allowed to use
     * any singly linked list methods. You are allowed to use attributes, constructors, getters and setters. Write the
     * method in the LinkedList class.
     */
    public boolean evenOddSorted() {
        if (head == null || head.next == null) return true;
        Node oddIdx = head;
        Node evenIdx = head.next;
        
        while (oddIdx != null) {
            if (oddIdx.next != null && oddIdx.next.next != null) {
                if (oddIdx.next.next.data < oddIdx.data)
                    return false;
            } else
                break;
            oddIdx = oddIdx.next.next;
        }
        while (evenIdx != null) {
            if (evenIdx.next != null && evenIdx.next.next != null) {
                if (evenIdx.next.next.data > evenIdx.data)
                    return false;
            } else
                break;
            evenIdx = evenIdx.next.next;
        }
        return true;
    }

    /**
     * Write the method which returns the elements at positions list[1], list[2], list[3], etc. list[1] is the first
     * element in the list, list[2] is the second element in the list etc. You are not allowed to use any linked list
     * methods. You are only allowed to use attributes, constructors, getters and setters. Assume that list is sorted.
     * Your algorithm should run in ${\cal O}(N)$ time. Your linked list should contain new nodes, not the same nodes
     * in the original linked list.
     */
    public LinkedList getIndexed(LinkedList list) {
        if (head == null || list.head == null) return null;
        LinkedList result = new LinkedList();
        Node prevIndex = null;
        Node index = list.head;
        int toGo = 0;
        Node curr = head;
        while (index != null) {
            if (curr == head)
                toGo = index.data - 1;
            else toGo = index.data - prevIndex.data;
            
            for (int i = 0; i < toGo ;i++)
                curr = curr.next;
            
            Node newNode = new Node(curr.data);
            if (result.head == null) {
                result.head = newNode;
            } else {
                result.tail.next = newNode;
            }
            result.tail = newNode;
            
            prevIndex = index;
            index = index.next;
        }
        return result;
    }

    /**
     * Write the method to find the intersection of the elements in two sorted linked lists and return a new linked
     * list. Implement the following algorithm:
     * <ul>
     *     <li>At the beginning of the algorithm, let say we have two nodes p1 and p2, showing the head nodes of the
     *     first and second lists respectively.</li>
     *     <li>Compare the contents of the nodes p1 and p2;
     *     <ul>
     *         <li>If p1.data $<$ p2.data, advance p1 pointer to show next node in its list.</li>
     *         <li>If p1.data $>$ p2.data, advance p2 pointer to show next node in its list.</li>
     *         <li>If p1.data $=$ p2.data, put a new node with content of p1 and advance both pointers p1 and p2 in
     *         their respective lists.</li>
     *     </ul>
     *     </li>
     *     <li>Continue with step 2 until one of the p1 or p2 is null.</li>
     * </ul>
     * You are not allowed to use any linked list methods. You are only allowed to use attributes, constructors,
     * getters and setters.
     */
    public static LinkedList intersec(LinkedList list1, LinkedList list2) {
        if (list1.head == null || list2.head == null) return null;
        LinkedList intersec = new LinkedList();
        Node ptr1 = list1.head;
        Node ptr2 = list2.head;
        
        while (ptr1 != null && ptr2 != null) {
            if (ptr1.data == ptr2.data) {
                Node newNode = new Node(ptr1.data);
                if (intersec.head == null) {
                    intersec.head = newNode;
                } else {
                    intersec.tail.next = newNode;
                }
                intersec.tail = newNode;
                ptr1 = ptr1.next;
                ptr2 = ptr2.next;
            } else if (ptr1.data < ptr2.data) {
                ptr1 = ptr1.next;
            } else {
                ptr2 = ptr2.next;
            }
        }
        return intersec;
    }

    /**
     * Write a function that checks whether the original singly list contains at least one increasing sequence of length
     * $k > 1$ {\bf exactly}.
     */
    public boolean isIncreasingOfSizeK(int k) {
        if (head == null || k == 0) return false;
        if (k == 1) return true;
        int counter = 1;
        Node curr = head;
        while (curr != null) {
            if (curr.next != null && curr.data >= curr.next.data) {
                if (counter == k) return true;
                counter = 1;
            } else if (curr.next != null && curr.data < curr.next.data) {
                counter++;
            }
            if (curr.next == null && counter == k) return true;
            curr = curr.next;
        }
        return false;
    }

    /**
     * Write the algorithm in the {\bf LinkedList} class which works as follows:
     * <ul>
     *     <li>Delete every k'th element from the list.</li>
     *     <li>When you get the end of the list, return to the first element, as if the list is circular.</li>
     *     <li>Return the remaining node.</li>
     * </ul>
     * Let say the list is 1 2 3 4 5 6, and k = 2, then 2, 4, 6, 3, 1 will be deleted, 5 remains.
     * -I assumed k does not exceed the length of the list-
     */
    public Node lastOneWins(int k) {
        if (k == 1) return tail;
        tail.next = head;
        Node prev = null;
        Node curr = head;
        while (curr != null) {
            for (int m = 0; m < k - 1; m++) {
                prev = curr;
                curr = curr.next;
            }
            prev.next = curr.next;
            curr.next = null;
            curr = prev.next;
        }
        return prev;
    }

    /**
     * Write the algorithm in the {\bf LinkedList} class which works as follows:
     * <ul>
     *     <li>Creates a temporary linked list primes, which stores the prime numbers until N.</li>
     *     <li>Using primes returns all prime divisors (with repeating) of N.</li>
     * </ul>
     * Let say N = 200, the function will return 2, 2, 2, 5, 5. You are not allowed to use any array in the function.
     */
    public static LinkedList primeDivisors(int N) {
        LinkedList primes = new LinkedList();
        LinkedList prDiv = new LinkedList();
        for (int i = 2; i <= N; i++) {
            if (isPrime(i))
                primes.insertLast(new Node(i));
        }
        Node candidate = primes.head;
        while (N != 1) {
            if (N % candidate.data == 0) {
                N /= candidate.data;
                prDiv.insertLast(new Node(candidate.data));
            } else {
                candidate = candidate.next;
            }
        }
        return prDiv;
    }

    /**
     * In math, any number can be represented as the sum of distinct Fibonacci numbers. For example, given the first 10
     * Fibonacci numbers. The number 100 can be represented as 89 + 8 + 3. In this question, you will identify the
     * Fibonacci representation of a number N, which works as follows.
     * <ul>
     *     <li>Creates a temporary linked list of Fibonacci numbers in reverse until N. So, for the given example, the
     *     list will contains the numbers 89, 55, 34, 21, 13, 8, 5, 3, 2, 1.</li>
     *     <li>Using Fibonacci numbers list, returns the representation as a linked list.</li>
     * </ul>
     */
    public static LinkedList fibonacciWay(int N) {
        LinkedList fibs = new LinkedList();
        for (int i = 0; i < N; i++) {
            int fb = fib(i);
            if (fb <= N) fibs.insertFirst(new Node(fb));
            else break;
        }
        Node candidateFibo = fibs.head;
        LinkedList fibWay = new LinkedList();
        while (N != 0) {
            if (N >= candidateFibo.data) {
                N -= candidateFibo.data;
                fibWay.insertLast(new Node(candidateFibo.data));
            } else {
                candidateFibo = candidateFibo.next;
            }
        }
        return fibWay;
    }
    public static int fib(int N) {
        if (N < 0) return Integer.MIN_VALUE;
        if (N == 0) return 0;
        if (N == 1) return 1;
        return fib(N - 1) + fib(N - 2);
    }

    /**
     * Write the method which removes the nodes that appear in the list2 from the original list. You are not allowed to
     * use any methods from the LinkedList class. You can assume both the original list and list2 do not contain
     * duplicate elements. Do not modify list2. Your method should run in ${\cal O}(N^2)$ time.
     */
    public void remove(LinkedList list2) {
        if (head == null || list2 == null || list2.head == null) return;
        
        Node prev = null;
        Node curr = head;
        
        while (curr != null) {
            Node runner = list2.head;
            while (runner != null) {
                if (curr.data == runner.data) {
                    if (curr == head) {
                        head = head.next;
                        continue;
                    } else if (curr == tail) {
                        tail = prev;
                        tail.next = null; // lack of this line, i spent 20 minutes more.
                    } else {
                        if (prev == null) break;
                        prev.next = curr.next;
                    }
                }
                runner = runner.next;
            }
            prev = curr;
            curr = curr.next;
        }
        
    }

    /**
     * Write a function that computes the GCD (Greatest Common Divisor) of every window of \textit{k} consecutive nodes
     * in the list and returns a new linked list containing these GCD values.
     */
    public LinkedList windowedPairwiseGCD(int k) {
        if (head == null) return null;
        //assuming list has enough length and k >= 2
        int length = length();
        LinkedList list = new LinkedList();
        for (int i = 0; i < length - k + 1; i++) {
            Node start = head;
            for (int j = 0; j < i; j++) {
                start = start.next;
            }
            int div = gcd(start.data, start.next.data);
            start = start.next.next;
            for (int m = 0; m < k - 2; m++) {
                div = gcd(div, start.data);
                start = start.next;
            }
            list.insertLast(new Node(div));
        }
        return list;
    }
    public static int gcd(int a, int b) {
        // just for two positive numbers and for the method above
        while (true) {
            int r = a % b;
            if (r == 0) return b;
            a = b;
            b = r;
        }
    }

    /**
     * Write a function that returns the Zibonacci numbers between A and B (both inclusive) as a singly linked list. The
     * first three Zibonacci numbers are \\$Z_0$ = 0, $Z_1$ = 1, $Z_2$ = 1\\ and the general formula is
     * \\$Z_n = Z_{n-1} + Z_{n-3}$. Assume that A $\geq$ 2 and $B > A$.
     */
    public static LinkedList zibonacci(int A, int B) {
        LinkedList zibs = new LinkedList();
        for (int i = 0; i <= B; i++) {
            int z = zib(i);
            if (A < z && z <= B) {
                zibs.insertLast(new Node (z));
            } else if (z > B) break;
        }
        return zibs;
    }
    public static int zib(int N) {
        if (N == 0) return 0;
        else if (N == 1 || N == 2) return 1;
        else return zib(N - 1) + zib(N - 3);
    }

    /**
     * Write a method which compresses consecutive items with the same
     * value into two consecutive values. If a value appears
     * only once consecutively, you only keep the single
     * value without compressing.
     * <ol>
     *     <li>You are not allowed to use any linked list methods, just attributes, constructors, getters and setters.</li>
     *     <li>You can not use extra data structures including arrays.</li>
     *     <li>Time complexity of the algorithm does not matter</li>
     * </ol>
     *
     */
    public void compressConsecutiveDouble() {
        if (head == null || head.next == null || head.next.next == null) return;
        Node curr = head;
        
        while (curr != null && curr.next != null) {
            if (curr.data != curr.next.data) {
                curr = curr.next;
            } else {
                if (curr.next.next != null) {
                    if (curr.next.next.data != curr.data) {
                        curr = curr.next.next;
                    } else {
                        curr.next = curr.next.next;
                    }
                } else {
                    break;
                }
            }
        }
        
    }

    /**
     *  Write the static method that returns the Pell numbers between A and B (both inclusive) as a singly linked list. The first
     *  two Pell numbers are Z_0 = 0, Z_1 = 1, and the general formula is Z_n = 2Z_{n-1} + Z_{n-2}.
     *  Assume that A >= 2 and B > A.
     */
    public static LinkedList pellNumber(int A, int B) {
        int z0 = 0, z1 = 1;
        LinkedList pells = new LinkedList();
        
        while (z0 <= B) {
            if (z0 >= A) pells.insertLast(new Node(z0));
            int ahead = z0 + 2 * z1;
            z0 = z1;
            z1 = ahead;
        }
        return pells;
    }

}

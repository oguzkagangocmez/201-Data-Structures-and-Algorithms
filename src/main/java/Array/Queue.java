package Array;

import java.util.Arrays;

public class Queue {

    private Element[] array;

    private int first;

    private int last;

    private int N;

    public Queue(int N){
        this.N = N;
        array = new Element[N];
        first = 0;
        last = 0;
    }

    boolean isFull(){
        return (last + 1) % N == first;
    }

    public boolean isEmpty(){
        return first == last;
    }

    public void enqueue(Element element){
        if (!isFull()){
            array[last] = element;
            last = (last + 1) % N;
        }
    }

    public Element dequeue(){
        if (!isEmpty()){
            Element tmp = array[first];
            first = (first + 1) % N;
            return tmp;
        }
        return null;
    }

    public String toString(){
        String s = "";
        for (int i = first; i <last; i = (i + 1) % N){
            s += array[i].getData() + " ";
        }
        return s.trim();
    }

    public void insertArray(int[] data){
        for (int datum : data){
            enqueue(new Element(datum));
        }
    }

    /**
     * Write another constructor method which constructs a new array based queue by adding the elements in the $list$
     * of queues one by one.  So, the first $k$ elements of the original queue will be constructed with the first
     * elements of the $k$ queues in the list; the second $k$ elements of the original queue will be constructed with
     * the second elements of the $k$ queues in the list etc. The elements from queues should be recreated (not copied
     * from the queues). You are not allowed to use enqueue, dequeue, isEmpty functions.
     */
    public Queue(Queue[] list){
        if (list == null) return;
        first = 0;
        last = 0;
        // to hold the # of the elements in a queue. assuming every queue has same size.
        int column = (list[0].last - list[0].first + list[0].N) % list[0].N;
        N = list.length * column + 1;
        array = new Element[N];
        
        for (int j = 0; j < column; j++) {
            for (Queue queue : list) {
                if (!((last + 1) % N == first)) {
                    array[last] = new Element(queue.array[queue.first + j].getData());
                    last = (last + 1) % N;
                }
            }
        }
        
    }

    /**
     * Write a method which copies all the elements of the src queue and inserts to the queue at position index. You are
     * not allowed to use enqueue, dequeue, isEmpty functions. You can assume the destination queue has enough space for
     * insertion. Your method should run in ${\cal O}(N)$ time. Hint: Start by counting the number of positions to shift
     * for opening up space for the elements of src.
     */
    public void copyPaste(Queue src, int index){
        int srcLen = (src.last - src.first + src.N) % src.N;
        int numOfShift = (last - index + N) % N;
        for (int i = 0; i < numOfShift; i++) {
            array[(last - i - 1 + srcLen + N) % N] = array[(last - i - 1 + N) % N];
        }
        for (int i = 0; i < srcLen; i++) {
            array[(first + index + i) % N] = new Element(src.array[(src.first + i) % src.N].getData());
        }
        last = last + srcLen % N;
    }

    /**
     * Write a method which cuts all the elements between indexes p and q from the original queue and inserts at the end
     * to the dest queue. You are not allowed to use enqueue, dequeue, isEmpty functions. You can assume the destination
     * queue has enough space for insertion. Your method should run in ${\cal O}(N)$ time.
     */
    public void cutPaste(Queue dest, int p, int q){
        int numToAdd = (q - p + N + 1) % N;
        for (int i = 0; i < numToAdd; i++) {
            dest.array[dest.last] = array[(first + i + p) % N];
            dest.last = (dest.last + 1) % dest.N;
        }
        int numToCut = (q - p + 1 + N) % N;
        for (int i = 0; i < numToCut; i++) {
            array[(first + p + i) % N] = array[(first + q + i + 1) % N];
        }
        last = (last - numToCut + N) % N;
        for (int i = last; array[i] != null; i = (i + 1) % N) {
            array[i] = null;
        }
    }

    /**
     * Write a method which dequeues data as the $k$'th element from the first. dequeue(1) is equal to the original
     * dequeue, that is, the first element has index 1. You are not allowed to use any queue methods and any external
     * structures (arrays, queues, trees, etc). You are allowed to use attributes, constructors, getters and setters.
     */
    public Element dequeue(int k){ // todo
        int indexToDequeue = (first + k - 1 + N) % N;
        Element dequeued = array[indexToDequeue];
        int numOfShift = (indexToDequeue - first + N) % N;
        for (int i = 0; i < numOfShift; i++) {
            array[(indexToDequeue - i + N) % N] = array[(indexToDequeue - i - 1 + N) % N];
        }
        first = (first + 1) % N;
        return dequeued;
    }

    /**
     * Write a method which removes and returns the second item from the queue. Your methods should run in ${\cal O}$(1)
     * time. Do not use any class or external methods except isEmpty().
     */
    public Element dequeue2nd(){
        Element second = array[(first + 1) % N];
        array[(first + 1) % N] = array[first];
        first = (first + 1) % N;
        return second;
    }

    /**
     * Write a function that creates and returns a new queue by removing even indexed elements from the original queue
     * and inserting into the newly created queue. The first node has index 1. You are not allowed to use any queue or
     * linked list methods, just attributes, constructors, getters and setters.
     */
    public Queue divideQueue() {
        Queue odd = new Queue(N);
        Queue even = new Queue(N);
        int len = (last - first + N) % N;
        int toRemove = ((last - first + N) % N) / 2;
        int deletion = 0;
        for (int i = 1; i <= len; i++) {
            if (i % 2 == 0) {
                if (!((even.last + 1) % even.N == even.first)) {
                    even.array[even.last] = array[(first + i - 1 + N) % N];
                    even.last = (even.last + 1) % even.N;
                }
            } else {
                if (!((odd.last + 1) % odd.N == odd.first)) {
                    odd.array[odd.last] = array[(first + i - 1 + N) % N];
                    odd.last = (odd.last + 1) % odd.N;
                }
            }
        }
        this.array = odd.array;
        this.first = odd.first;
        this.last = odd.last;
        this.N = odd.N;
        
        return even;
    }

    /**
     * Write a function that inserts a new element after the largest element of the queue. Write the function for array
     * implementation. You are not allowed to use any queue methods, just attributes, constructors, getters and setters.
     */
    public void insertAfterLargest (int data){
        Element newElement = new Element(data);
        int largestIndex = 0, largestValue = Integer.MIN_VALUE;
        for (int i = first ; array[i] != null; i = (i + 1) % N) {
            if (array[i].getData() > largestValue) {
                largestValue = array[i].getData();
                largestIndex = i;
            }
        }
        
        int numOfShift = (last - 1 - largestIndex + N) % N;
        for (int i = 0; i < numOfShift; i++) {
            array[(last - i + N) % N] = array[(last - 1 - i + N) % N];
        }
        
        array[(largestIndex + 1) % N] = newElement;
        last = (last + 1) % N;
    }

    /**
     * Write the method which removes only the odd indexed (1, 3, . . .) elements from the queue. The first element has
     * index 1. You are only allowed to use enqueue, dequeue, isEmpty functions. {\bf You should use external queue}.
     * You are not allowed to use any queue attributes such as first, last, array etc.
     */
    public void removeOddIndexed(){
        Queue evenIndexed = new Queue((((last - first + N) % N) / 2) + 1);
        for (int i = 1; !isEmpty(); i++) {
            Element candidate = dequeue();
            if (i % 2 == 0) {
                evenIndexed.enqueue(candidate);
            }
        }
        while (!evenIndexed.isEmpty()) {
            enqueue(evenIndexed.dequeue());
        }
    }

    /**
     * Write a method where {$k$} is a non-negative integer representing the number of positions to rotate the queue by,
     * which is implemented using an array. After the rotation, the first element of the queue should move to the back
     * {$k$} times, and the order of the other elements should shift accordingly. You are not allowed to use the
     * enqueue(), dequeue(), or peek() methods. The solution should rotate the array in {$O(N)$} time, where {$N$} is
     * the number of elements in the queue. Assume that $k \leq N$.
     */
    public void rotateQueue(int k){
        int indexToRotate = (first + k) % N;
        for (int i = 0; i < k; i++) {
            array[(last + i) % N] = array[(first + i) % N];
        }
        last = (last + k) % N;
        first = (first + k) % N;
    }

    /**
     * Write another constructor-like method in Array-based Queue implementation which constructs a new array based queue
     * by adding the elements in the
     * $list$ of queues one by one in zig-zag fashion. So, the first $k$ elements of the original queue will be
     * constructed with the first elements of the $k$ queues in the list; the
     * second $k$ elements of the original queue will be constructed with the
     * last elements of the $k$ queues in the list, third $k$ elements of the original queue will be
     * constructed with the second elements of the $k$ queues in the list; etc. The elements from
     * queues should be recreated (not copied from the queues). You are not
     * allowed to use enqueue, dequeue, isEmpty functions. You should solve
     * the question for array implementation. You may assume the length of
     * each queue is same and even.
     */
    public static Queue QueueZigZag(Queue[] list){
        int row = list.length;
        int column = (list[0].last - list[0].first + list[0].N) % list[0].N;
        Queue result = new Queue(row * column + 1);
        
        for (int i = 0; i < column / 2; i++) {
            for (Queue queue : list) {
                int leftRowIndex = (queue.first + i + queue.N) % queue.N;
                result.array[result.last] = new Element((queue.array[leftRowIndex]).getData());
                result.last = result.last + 1 % result.N;
            }
            
            for (Queue queue : list) {
                int rightRowIndex = (queue.last - 1 - i + queue.N) % queue.N;
                result.array[result.last] = new Element((queue.array[rightRowIndex]).getData());
                result.last = result.last + 1 % result.N;
            }
        }
        return result;
    }

}

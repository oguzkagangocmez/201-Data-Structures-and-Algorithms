package List;

public class Queue {

    protected Node first;
    protected Node last;
    private Node target;
    
    public Queue() {
        first = null;
        last = null;
    }

    public boolean isEmpty(){
        return first == null;
    }

    public void enqueue(Node newNode) {
        if (isEmpty()) {
            first = newNode;
        } else {
            last.setNext(newNode);
        }
        last = newNode;
    }

    public Node dequeue(){
        Node result = first;
        if (!isEmpty()){
            first = first.getNext();
            if (isEmpty()){
                last = null;
            }
        }
        return result;
    }

    public void insertArray(int[] data){
        for (int datum : data){
            enqueue(new Node(datum));
        }
    }

    public String toString(){
        if (isEmpty()){
            return "";
        }
        String s = "" + first.getData();
        Node tmp = first.getNext();
        while (tmp != null){
            s += " " + tmp.getData();
            tmp = tmp.getNext();
        }
        return s.trim();
    }

    /**
     * Write another constructor method which constructs a new list based queue by concatenating all elements in the list
     * of queues in order. The elements from queues should be recreated (not copied from the queues). You are not
     * allowed to use enqueue, dequeue, isEmpty functions.
     */
    public Queue(Queue[] list){
        this.first = null;
        this.last = null;
        
        for (int i = 0; i < list.length; i++) {
            for (Node temp = list[i].first; temp != null; temp = temp.next) {
                Node newNode = new Node(temp.data);
                if (first == null) {
                    first = newNode;
                } else {
                    last.next = newNode;
                }
                last = newNode;
            }
        }
        
    }

    /**
     * Write a method which dequeues data as the $k$'th element from the first. dequeue(1) is equal to the original
     * dequeue, that is, the first element has index 1. You are not allowed to use any queue methods and any external
     * structures (arrays, queues, trees, etc). You are allowed to use attributes, constructors, getters and setters.
     */
    public Node dequeue(int k){
        if (k == 1) {
            Node dequeued = first;
            first = first.next;
            dequeued.next = null;
            if (first == null) last = null;
            return dequeued;
        } else {
            Node previous = null;
            Node target = first;
            for (int i = 1; i < k; i++) {
                previous = target;
                target = target.next;
            }
            previous.next = target.next;
            target.next = null;
            return target;
        }
    }

    /**
     * Write a function that creates and returns a new queue by removing even indexed elements from the original queue
     * and inserting into the newly created queue. The first node has index 1. You are not allowed to use any queue or
     * linked list methods, just attributes, constructors, getters and setters.
     */
    public Queue divideQueue(){
        Queue evenQueue = new Queue();
        Node odd = first;
        Node target = first.next;
        while (target != null) {
            odd.next = target.next;
            target.next = null;
            Node newNode = new Node(target.data);
            if (evenQueue.first == null) {
                evenQueue.first = newNode;
            } else {
                evenQueue.last.setNext(newNode);
            }
            evenQueue.last = newNode;
            if (odd.next == null) break;
            odd = odd.next;
            target = odd.next;
        }
        return evenQueue;
    }

    /**
     * Write a method which constructs an array of list based queues by dividing the original queue into k equal parts.
     * The first, second, $\ldots$, $k$'th element of the original queue will be the first element of the first, second,
     * $\ldots$, $k$'th output queues, etc. The elements of the output queues should be recreated (not copied from the
     * original queue). You are not allowed to use enqueue, dequeue, isEmpty functions.
     */
    public Queue[] divideQueue(int k){
        Queue[] result = new Queue[k];
        for (int i = 0; i < k; i++) {
            Queue queue = new Queue();
            Node temp = first;
            for (int j = 0; j < i; j++) {
                temp = temp.next;
            }
            while (temp != null) {
                Node newNode = new Node(temp.data);
                if (queue.first == null) {
                    queue.first = newNode;
                } else {
                    queue.last.next = newNode;
                }
                queue.last = newNode;
                for (int m = 0; m < k && temp != null; m++) {
                    temp = temp.next;
                }
            }
            result[i] = queue;
        }
        return result;
    }

    /**
     * Write a method where the method returns the minimum number in a queue. Do not use any class or external methods
     * except isEmpty().
     */
    public int minimum(){
        int min =Integer.MAX_VALUE;
        for (Node temp = first; temp != null; temp = temp.next) {
            if (temp.data < min)
                min = temp.data;
        }
        return min;
    }

    /**
     * Write a method that returns the maximum number in a queue.
     */
    public int maximum(){
        int max =Integer.MIN_VALUE;
        for (Node temp = first; temp != null; temp = temp.next) {
            if (temp.data > max)
                max = temp.data;
        }
        return max;
    }

    /**
     * Write a method which removes all elements in the queues in the $list$ from the original queue. You are not
     * allowed to use enqueue, dequeue, isEmpty functions.
     */
    public void removeAll(Queue[] list){
        for (Queue queue : list) {
            for (Node temp = queue.first; temp != null; temp = temp.next) {
                Node previous = null;
                Node runner = first;
                while (runner != null) {
                    if (runner.data == temp.data) {
                        if (previous == null) {
                            first = first.next;
                            runner.next = null;
                            runner = first;
                        } else if (runner == last) {
                            last = previous;
                            last.next = null;
                        } else {
                            previous.next = runner.next;
                            runner.next = null;
                            runner = previous.next;
                        }
                    } else {
                        previous = runner;
                        runner = runner.next;
                    }
                }
            }
        }
    }

    /**
     * Write a method given a queue that is implemented as a linked list. The method should reverse the order of
     * elements in the queue without using the queue’s enqueue(), dequeue(), or peek() methods. You must directly
     * manipulate the underlying linked list of the queue to achieve the reversal.
     */
    public void reverseQueue(){
        if (first == null || first.next == null) return;
        Node previous = null;
        Node curr = first;
        Node next;
        while (curr != null) {
            next = curr.next;
            curr.next = previous;
            previous = curr;
            curr = next;
        }
        last = first;
        first = previous;
    }

    /**
     * Write a method which simulates how the queue goes in an hypothetical country, which we all aware of.
     * {\em indexOfNonbribers} is an increasing order sorted array showing the indexes of the people who have not bribed
     * the officer in the queue. The method will send these people to the end of the queue in their respective order.
     * The first element in the queue has the index 0.
     */
    public void thisMustChange(int[] indexOfNonbribers){
        Node nonBriberFirst = null, nonBriberLast = null;
        int deletion = 0;
        for (int v : indexOfNonbribers) {
            Node prev = null;
            Node target = first;
            for (int i = 0; i < v - deletion; i++) {
                prev = target;
                target = target.next;
            }
            // create nonbriber queue.
            Node newNode = new Node(target.data);
            if (nonBriberFirst == null) {
                nonBriberFirst = newNode;
            } else {
                nonBriberLast.next = newNode;
            }
            nonBriberLast = newNode;
            if (prev == null) {
                first = first.next;
                if (first == null) {
                    last = null;
                }
            } else if (target == last) {
                last = prev;
                last.next = null;
            } else {
                prev.next = target.next;
                target.next = null;
            }
            deletion++;
        }
        
        if (first != null) {
            last.next = nonBriberFirst;
        } else {
            first = nonBriberFirst;
        }
        last = nonBriberLast;
    }

    /**
     * Write a method that returns the second maximum number in a queue. Write the method for the
     * linked list implementation.
     */
    public int secondMaximum(){
        int max = Integer.MIN_VALUE;
        int secondMax = Integer.MIN_VALUE;
        for (Node temp = first; temp != null; temp = temp.next) {
            if (temp.data > max) {
                secondMax = max;
                max = temp.data;
            } else if (temp.data < max && temp.data > secondMax) {
                secondMax = temp.data;
            }
        }
        return secondMax;
    }
}

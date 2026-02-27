package Array;


public class Stack {
    private Element[] array;
    private int top;
    private int N;

    public Stack(int N){
        this.N = N;
        array = new Element[N];
        top = -1;
    }

    public boolean isFull(){
        return top == N - 1;
    }

    public boolean isEmpty(){
        return top == -1;
    }

    public Element peek(){
        return array[top];
    }

    public void push(Element element){
        if (!isFull()){
            top++;
            array[top] = element;
        }
    }

    public Element pop(){
        if (!isEmpty()){
            top--;
            return array[top + 1];
        }
        return null;
    }

    public void insertArray(int[] data){
        for (int datum : data){
            push(new Element(datum));
        }
    }

    public String toString(){
        String s = "";
        for (int i = 0; i < top; i++){
            s += array[i].getData() + " ";
        }
        if (top != -1){
            s += array[top].getData();
        }
        return s.trim();
    }

    /**
     * Write a method which checks if the array s is of the form $1^m2^m$ without counting
     * any characters and using an external array based stack.
     * <ul>
     *     <li>You can assume the array consists of only numbers between 1 and 2 and there is at least one from each of them. </li>
     *     <li>You are only allowed to use pop, push, isEmpty functions. </li>
     *     <li>You should use one single external stack. </li>
     *     <li>You can not modify {\bf s}.</li>
     *     <li>Beware, you should return false for the strings 21, 1212, or 112212.</li>
     * </ul>
     */
    public boolean checkString(int[] s){
        Stack external = new Stack(s.length);
        for (int j : s) {
            if (j == 1) {
                external.push(new Element(1));
            } else if (j == 2) {
                if (external.isEmpty()) return false;
                else if (external.pop().getData() != 1) return false;
            }
        }
        return external.isEmpty();
    }

    /**
     * Write a method that compresses the stack by removing the duplicate items. Assume that the elements in the stack
     * are sorted. You are only allowed to use pop, push, isEmpty functions.  (Hint: Use external stack).
     */
    public void compress(){
        Stack external = new Stack(top + 1);
        external.push(pop());
        while (!isEmpty()) {
            Element candidate = pop();
            if (external.peek().getData() != candidate.getData()) {
                external.push(candidate);
            }
        }
        while (!external.isEmpty()) {
            push(external.pop());
        }
    }

    /**
     * Write a method which copies all the elements of the src stack and inserts to the stack at position index. The
     * bottom element in the stack has index 0 as usual. You are not allowed to use pop, push, isEmpty functions. You
     * can assume the destination stack has enough space for insertion. Your method should run in ${\cal O}(N)$ time.
     */
    public void copyPaste(Stack src, int index){
        int srcLen = src.top + 1;
        for (int i = top; i >= index; i--) {
            array[i + srcLen] = array[i];
        }
        top += srcLen;
        for (int i = index; i < index + srcLen; i++) {
            array[i] = src.array[i - index];
        }
    }

    /**
     * Write a function that inserts a new integer after the largest element of the stack. You are not allowed to use
     * any stack methods, just attributes, constructors, getters and setters.
     */
    public void insertAfterLargest(int newValue){
        int largestValue = Integer.MIN_VALUE;
        int largestIndex = 0;
        for (int i = 0; i <= top; i++) {
            if (array[i].getData() > largestValue) {
                largestValue = array[i].getData();
                largestIndex = i;
            }
        }
        for (int i = top; i > largestIndex; i--) {
            array[i + 1] = array[i];
        }
        array[largestIndex + 1] = new Element(newValue);
        top += 1;
    }

    /**
     * Write a static method using an external stack (only one external stack is allowed) that determines if an integer
     * array is balanced or not. A number $k$ less than 10 is balanced with the number $10 + k$. For example, the array
     * 2, 3, 13, 12, 4, 14 is balanced, whereas 5, 15, 4, 3, 14, 13 not. You are not allowed to use any stack attributes
     * such as N, top, array etc.
     */
    public static boolean isBalanced(int[] a){
        Stack external = new Stack(a.length);
        for (int j : a) {
            if (0 <= j && j < 10) {
                external.push(new Element(j));
            } else if (j > 10) {
                int expected = j - 10;
                if (external.isEmpty() || external.pop().getData() != expected) return false;
            }
        }
        return external.isEmpty();
    }

    /**
     * Write a method which pops the $k$'th element from the top and returns it. pop(1) is equal to the original pop,
     * that is, the first element at the top has index 1. You are not allowed to use any stack methods and any external
     * stacks, just attributes, constructors, getters and setters.
     */
    public Element pop(int k){
        Element popped = array[top - k + 1];
        for (int i = top - k + 1; i < top; i++) {
            array[i] = array[i + 1];
        }
        top -= 1;
        return popped;
    }

    /**
     * Write the method which pushes data as the $k$'th element from the top. push(1, data) is equal to the original
     * push, that is, the first element at the top has index 1. You are not allowed to use any stack methods and any
     * external stacks. You are allowed to use attributes, constructors, getters and setters.
     */
    public void push(int k, int data){
        for (int i = top; i > top - k + 1; i--) {
            array[i + 1] = array[i];
        }
        array[top - k + 2] = new Element(data);
        top += 1;
    }

    /**
     * Write a method which removes only the even indexed (2, 4, . . .) elements from the stack. The first element at
     * the bottom has index 1. You are only allowed to use pop, push, isEmpty functions (Hint: Use external stack). You
     * are not allowed to use any stack attributes such as N, top, array etc.
     */
    public void removeEvenIndexed(){
        Stack external = new Stack(N);
        while (!isEmpty()) {
            external.push(pop());
        }
        int indexFromBottom = 1;
        for (int i = 1; !external.isEmpty(); i++) {
            Element candidate = external.pop();
            if (i % 2 == 1) {
                push(candidate);
            }
        }
    }

    /**
     * Write a method which removes all items between maximum and
     * minimum element, keeping these minimum and
     * maximum elements. Notice that minimum does not always need to
     * show up before maximum, it can be
     * other way around as well.
     * <ol>
     *     <li>You may assume there are at least 3 items in the stack.</li>
     *     <li>You may assume all elements are distinct.</li>
     *     <li>You are not allowed to use any stack methods, just attributes, constructors, getters and setters.</li>
     *     <li>You can not use extra data structures including arrays.</li>
     *     <li>Time complexity of the algorithm does not matter.</li>
     * </ol>
     */
    public void removeBetweenMinMax(){
        int largestVal = Integer.MIN_VALUE;
        int largestIndex = 0;
        int minVal = Integer.MAX_VALUE;
        int minIndex = 0;
        for (int i = 0; i <= top; i++) {
            if (array[i].getData() > largestVal) {
                largestVal = array[i].getData();
                largestIndex = i;
            }
            if (array[i].getData() < minVal) {
                minVal = array[i].getData();
                minIndex = i;
            }
        }
        int shift = Math.abs(largestIndex - minIndex) - 1;

        for (int i = Math.max(largestIndex, minIndex); i <= top; i++) {
            array[i - shift] = array[i];
        }
        top -= shift;
    }

}

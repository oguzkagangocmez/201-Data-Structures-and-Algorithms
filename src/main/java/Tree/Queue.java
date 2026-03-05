package Tree;

public class Queue {

    private TreeNode array[];

    private int first;

    private int last;

    private int N;

    public Queue(int N){
        this.N = N;
        array = new TreeNode[N];
        first = 0;
        last = 0;
    }

    boolean isFull(){
        return (last + 1) % N == first;
    }

    boolean isEmpty(){
        return first == last;
    }

    void enqueue(TreeNode TreeNode){
        if (!isFull()){
            array[last] = TreeNode;
            last = (last + 1) % N;
        }
    }

    TreeNode dequeue(){
        if (!isEmpty()){
            TreeNode tmp = array[first];
            first = (first + 1) % N;
            return tmp;
        }
        return null;
    }

}

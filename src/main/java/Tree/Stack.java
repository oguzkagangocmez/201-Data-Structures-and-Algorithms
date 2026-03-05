package Tree;

public class Stack {
    private TreeNode[] array;
    private int top;
    private int N;

    public Stack(int N){
        this.N = N;
        array = new TreeNode[N];
        top = -1;
    }

    boolean isFull(){
        return top == N - 1;
    }

    boolean isEmpty(){
        return top == -1;
    }

    TreeNode peek(){
        return array[top];
    }

    void push(TreeNode TreeNode){
        if (!isFull()){
            top++;
            array[top] = TreeNode;
        }
    }

    TreeNode pop(){
        if (!isEmpty()){
            top--;
            return array[top + 1];
        }
        return null;
    }
}

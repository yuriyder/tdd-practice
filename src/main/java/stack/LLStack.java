package stack;

public class LLStack {

    private int size;
    public Node head;

    public boolean isEmpty() {
        return size == 0;
    }

    public void push(int value) {
        size++;
        head = new Node(value, head);
    }

    public int pop() {
        size--;
        int out = head.getValue();
        head = head.getNext();
        return out;
    }
}

package algorithms;

public class CustomQueue<T> {
    private CustomLinkedList<T> list;

    public CustomQueue() {
        list = new CustomLinkedList<>();
    }

    public void add(T element) {
        list.addLast(element);
    }

    public T poll() {
        return list.poll();
    }

    public T peek() {
        return list.peek();
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void clear() {
        list.clear();
    }

    public CustomArrayList<T> toList() {
        return list.toArrayList();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}

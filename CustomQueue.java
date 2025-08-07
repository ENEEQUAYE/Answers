public class CustomQueue<T> {
    private Node<T> front;
    private Node<T> rear;
    private int size;
    
    private static class Node<T> {
        T data;
        Node<T> next;
        
        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
    
    public CustomQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }
    
    // Add element to the rear of the queue
    public void offer(T element) {
        Node<T> newNode = new Node<>(element);
        
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }
    
    // Alias for offer to match Java Queue interface
    public void add(T element) {
        offer(element);
    }
    
    // Remove and return element from the front of the queue
    public T poll() {
        if (isEmpty()) {
            return null;
        }
        
        T data = front.data;
        front = front.next;
        
        if (front == null) {
            rear = null;
        }
        
        size--;
        return data;
    }
    
    // Remove and return element from the front (throws exception if empty)
    public T remove() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        return poll();
    }
    
    // Get the front element without removing it
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return front.data;
    }
    
    // Get the front element without removing it (throws exception if empty)
    public T element() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        return peek();
    }
    
    public boolean isEmpty() {
        return front == null;
    }
    
    public int size() {
        return size;
    }
    
    public void clear() {
        front = null;
        rear = null;
        size = 0;
    }
    
    public boolean contains(T element) {
        Node<T> current = front;
        while (current != null) {
            if (element == null ? current.data == null : element.equals(current.data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
    
    // Convert to CustomArrayList
    public CustomArrayList<T> toList() {
        CustomArrayList<T> list = new CustomArrayList<>();
        Node<T> current = front;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<T> current = front;
        boolean first = true;
        while (current != null) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(current.data);
            first = false;
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
}

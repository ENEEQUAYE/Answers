package algorithms;
public class CustomLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;
    
    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;
        
        Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }
    
    public CustomLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    public void add(T element) {
        Node<T> newNode = new Node<>(element);
        
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }
    
    public void addFirst(T element) {
        Node<T> newNode = new Node<>(element);
        
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }
    
    public void addLast(T element) {
        add(element);
    }
    
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        if (index == 0) {
            addFirst(element);
            return;
        }
        
        if (index == size) {
            addLast(element);
            return;
        }
        
        Node<T> newNode = new Node<>(element);
        Node<T> current = getNode(index);
        
        newNode.next = current;
        newNode.prev = current.prev;
        current.prev.next = newNode;
        current.prev = newNode;
        size++;
    }
    
    public T get(int index) {
        return getNode(index).data;
    }
    
    public void set(int index, T element) {
        getNode(index).data = element;
    }
    
    public T remove(int index) {
        Node<T> nodeToRemove = getNode(index);
        return removeNode(nodeToRemove);
    }
    
    public boolean remove(T element) {
        Node<T> current = head;
        while (current != null) {
            if (element == null ? current.data == null : element.equals(current.data)) {
                removeNode(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }
    
    public T removeFirst() {
        if (isEmpty()) {
            throw new RuntimeException("List is empty");
        }
        return removeNode(head);
    }
    
    public T removeLast() {
        if (isEmpty()) {
            throw new RuntimeException("List is empty");
        }
        return removeNode(tail);
    }
    
    public T getFirst() {
        if (isEmpty()) {
            throw new RuntimeException("List is empty");
        }
        return head.data;
    }
    
    public T getLast() {
        if (isEmpty()) {
            throw new RuntimeException("List is empty");
        }
        return tail.data;
    }
    
    public T poll() {
        if (isEmpty()) {
            return null;
        }
        return removeFirst();
    }
    
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return head.data;
    }
    
    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }
    
    private T removeNode(Node<T> node) {
        T data = node.data;
        
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        
        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        
        size--;
        return data;
    }
    
    public int indexOf(T element) {
        Node<T> current = head;
        int index = 0;
        while (current != null) {
            if (element == null ? current.data == null : element.equals(current.data)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }
    
    public boolean contains(T element) {
        return indexOf(element) != -1;
    }
    
    public int size() {
        return size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }
    
    public CustomArrayList<T> toArrayList() {
        CustomArrayList<T> list = new CustomArrayList<>();
        Node<T> current = head;
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
        Node<T> current = head;
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


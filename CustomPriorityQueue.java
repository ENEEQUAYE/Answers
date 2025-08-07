import java.util.Comparator;

public class CustomPriorityQueue<T> {
    private static final int DEFAULT_CAPACITY = 11;
    private Object[] heap;
    private int size;
    private Comparator<? super T> comparator;
    
    public CustomPriorityQueue() {
        this(DEFAULT_CAPACITY, null);
    }
    
    public CustomPriorityQueue(Comparator<? super T> comparator) {
        this(DEFAULT_CAPACITY, comparator);
    }
    
    public CustomPriorityQueue(int initialCapacity) {
        this(initialCapacity, null);
    }
    
    public CustomPriorityQueue(int initialCapacity, Comparator<? super T> comparator) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Initial capacity must be at least 1");
        }
        this.heap = new Object[initialCapacity];
        this.size = 0;
        this.comparator = comparator;
    }
    
    public void offer(T element) {
        if (element == null) {
            throw new NullPointerException("Element cannot be null");
        }
        
        if (size >= heap.length) {
            resize();
        }
        
        heap[size] = element;
        heapifyUp(size);
        size++;
    }
    
    public void add(T element) {
        offer(element);
    }
    
    @SuppressWarnings("unchecked")
    public T poll() {
        if (isEmpty()) {
            return null;
        }
        
        T result = (T) heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        
        if (size > 0) {
            heapifyDown(0);
        }
        
        return result;
    }
    
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return (T) heap[0];
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;
    }
    
    public void clear() {
        for (int i = 0; i < size; i++) {
            heap[i] = null;
        }
        size = 0;
    }
    
    public boolean contains(T element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(heap[i])) {
                return true;
            }
        }
        return false;
    }
    
    public void addAll(CustomArrayList<T> collection) {
        for (int i = 0; i < collection.size(); i++) {
            add(collection.get(i));
        }
    }
    
    @SuppressWarnings("unchecked")
    private int compare(T a, T b) {
        if (comparator != null) {
            return comparator.compare(a, b);
        } else {
            return ((Comparable<? super T>) a).compareTo(b);
        }
    }
    
    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            @SuppressWarnings("unchecked")
            T current = (T) heap[index];
            @SuppressWarnings("unchecked")
            T parent = (T) heap[parentIndex];
            
            if (compare(current, parent) >= 0) {
                break;
            }
            
            // Swap with parent
            swap(index, parentIndex);
            index = parentIndex;
        }
    }
    
    private void heapifyDown(int index) {
        while (true) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int smallest = index;
            
            if (leftChild < size) {
                @SuppressWarnings("unchecked")
                T leftValue = (T) heap[leftChild];
                @SuppressWarnings("unchecked")
                T currentValue = (T) heap[smallest];
                if (compare(leftValue, currentValue) < 0) {
                    smallest = leftChild;
                }
            }
            
            if (rightChild < size) {
                @SuppressWarnings("unchecked")
                T rightValue = (T) heap[rightChild];
                @SuppressWarnings("unchecked")
                T currentValue = (T) heap[smallest];
                if (compare(rightValue, currentValue) < 0) {
                    smallest = rightChild;
                }
            }
            
            if (smallest == index) {
                break;
            }
            
            swap(index, smallest);
            index = smallest;
        }
    }
    
    private void swap(int i, int j) {
        Object temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    
    private void resize() {
        int newCapacity = heap.length * 2;
        Object[] newHeap = new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(heap[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}

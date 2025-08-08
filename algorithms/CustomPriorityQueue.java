package algorithms;

import java.util.Comparator;

public class CustomPriorityQueue<T> {
    private CustomArrayList<T> heap;
    private Comparator<T> comparator;

    public CustomPriorityQueue() {
        this(null);
    }

    public CustomPriorityQueue(Comparator<T> comparator) {
        heap = new CustomArrayList<>();
        this.comparator = comparator;
    }

    public void add(T element) {
        heap.add(element);
        heapifyUp(heap.size() - 1);
    }

    public T poll() {
        if (isEmpty()) {
            return null;
        }
        T result = heap.get(0);
        T last = heap.remove(heap.size() - 1);
        if (!isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }
        return result;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return heap.get(0);
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.size() == 0;
    }

    private int compare(T a, T b) {
        if (comparator != null) {
            return comparator.compare(a, b);
        } else if (a instanceof Comparable) {
            return ((Comparable<T>) a).compareTo(b);
        } else {
            throw new IllegalArgumentException("No comparator provided and type does not implement Comparable");
        }
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (compare(heap.get(index), heap.get(parent)) < 0) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    private void heapifyDown(int index) {
        int size = heap.size();
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;
            if (left < size && compare(heap.get(left), heap.get(smallest)) < 0) {
                smallest = left;
            }
            if (right < size && compare(heap.get(right), heap.get(smallest)) < 0) {
                smallest = right;
            }
            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public void clear() {
        heap.clear();
    }

    @Override
    public String toString() {
        return heap.toString();
    }
}

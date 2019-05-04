public class MinHeap {
    private static final int DEFAULT_CAPACITY = 128;
    private int [] array;
    private int size;

    // If the capactiy is not specified by the user, assign the default
    public MinHeap() {
        this(DEFAULT_CAPACITY);
    }

    public MinHeap(int capacity) {
        if(capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.size = 0;
        this.array = new int[capacity];
    }

    public MinHeap(int [] input, int capacity) {
        if(input == null || input.length == 0) {
            throw new IllegalArgumentException();
        }
        // The input starts from index 1, so capacity can't be less than input's length + 1
        if(capacity < input.length+1) {
            throw new IllegalArgumentException();
        }

        this.size = input.length;
        this.array = new int[capacity];
        for(int i = 0; i < input.length; i++) {
            this.array[i+1] = input[i];
        }
        heapify();
    }

    public void heapify() {
        // Let all non-leaf nodes to do a percolate down:
        for(int index = size / 2; index >= 1; index--) {
            percolateDown(index);
        }
    }

    public Integer peek() {
        if(size < 1) {
            System.out.println("This heap is empty");
            return null;
        }
        return this.array[1];
    }

    public boolean offer(int val) {
        this.size++;
        // Return false when the capacity is reached
        if(this.size == this.array.length) {
            System.out.println("This heap is full");
            return false;
        }
        this.array[this.size] = val;
        percolateUp(this.size);
        return true;
    }

    public Integer poll() {
        // Check if the size >= 1. If so, store the result at index 1
        // Move the last element to the top, adjust size, then do percolateDown() at index 1
        if(size < 1) {
            System.out.println("This heap is empty.");
            return null;
        }
        int res = this.array[1];
        swap(1, this.size);
        this.size--;
        percolateDown(1);
        return res;
    }

    public int size() {
        return this.size;
    }

    // Left child = current index * 2
    // Right child = current index * 2 + 1
    public void percolateDown(int curIndex) {
        // Compare the value at curIndex and left child, and then compare to the right child, record the min index
        // If the curIndex is the min index, just break since the curIndex already has min value.
        // Do swap of the value in curIndex and min index.
        while(curIndex <= this.size) {
            int minIndex = curIndex;
            // Compare left child
            int left = curIndex * 2;
            int right = curIndex * 2 + 1;
            if(left <= this.size && this.array[left] < this.array[minIndex]) {
                minIndex = left;
            }
            if(right <= this.size && this.array[right] < this.array[minIndex]) {
                minIndex = right;
            }
            // Break the loop when curIndex is the min value.
            if(minIndex == curIndex) {
                break;
            }
            swap(minIndex, curIndex);
            curIndex = minIndex;
        }
    }

    // Parent index = current index / 2
    public void percolateUp(int curIndex) {
        // Compare the value at curIndex and at parent index, if curIndex is smaller, swap them
        // Break the loop if value at curIndex is larger than parent
        // Keep doing until reaches index 1
        while(curIndex > 1) {
            int parent = curIndex / 2;
            if(parent >= 1 && this.array[parent] > this.array[curIndex]) {
                swap(parent, curIndex);
            }
            else {
                break;
            }
            curIndex = parent;
        }
    }

    private void swap(int i, int j) {
        int temp = this.array[i];
        this.array[i] = this.array[j];
        this.array[j] = temp;
    }
}

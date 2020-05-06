package DataStructure;

/**
 * The {@code DataStructure.LinkedList} class represents the implementation of
 * a doubly linked-list.
 * It supports the add, addFirst, addLast, remove, removeFirst,
 * removeLast, removeAt, indexOf, along with methods to peek first
 * element and last element.
 * It also has the methods for isEmpty, size and clear.
 * The search method take O(n) in worst case.
 * The addFirst, addLast, removeFirst, removeLast takes O(1).
 *
 * @param <T> the generic type of data for the node
 */
public class LinkedList<T> implements Iterable<T> {

    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    // Internal node class to represent data
    private class Node <T> {
        T data;
        Node<T> prev, next;

        public Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    /**
     * Empty linked list and set size to 0, O(n)
     */
    public void clear() {
        Node<T> trav = head;
        while (trav != null) {
            Node<T> next = trav.next;
            trav.prev = trav.next = null;
            trav.data = null;
            trav = next;
        }

        head = tail = trav = null;
        size = 0;
    }

    /**
     * Return the size of this linked list.
     *
     * @return the size of this linked list
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the linked list is empty.
     *
     * @return {@code true} if linked list is empty; otherwise {@code false}
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Add an element to the tail of the linked list, O(1)
     *
     * @param element to add to linked list
     */
    public void add(T element) {
        addLast(element);
    }

    /**
     * Add an element to the beginning of this linked list, O(1)
     *
     * @param element to be added to beginning of this linked list
     */
    public void addFirst(T element) {
        // If linked list is empty
        if (isEmpty()) {
            head = tail = new Node<>(element, null, null);
        } else {
            head.prev = new Node<>(element, null, head);
            head = head.prev;
        }

        size++;
    }

    /**
     * Add a node to the tail of the linked list, O(1)
     *
     * @param element to be added to the tail of the linked list
     */
    public void addLast(T element) {
        // If list is empty
        if (isEmpty()) {
            head = tail = new Node<>(element, null, null);
        } else {
            tail.next = new Node<>(element, tail, null);
            tail = tail.next;
        }

        size++;
    }

    /**
     * Check the value of the first node if it exists, O(1)
     * if {@code isEmpty} is true,
     * @throws RuntimeException for empty linked list
     * @return data at head of linked list
     */
    public T peekFirst() {
        if (isEmpty())
            throw new RuntimeException("Empty list");
        return head.data;
    }

    /**
     * Check the value of the last node if it exits, O(1)
     * if {@code isEmpty} is true,
     * @throws RuntimeException for empty linked list
     * @return data at tail of linked list
     */
    public T peekLast() {
        if (isEmpty())
            throw new RuntimeException("Empty list");
        return tail.data;
    }

    /**
     * Remove the first value at the head of the linked list, O(1)
     * if {@code isEmpty} return true,
     * @throws RuntimeException for empty linked list
     * @return the first value at the head of the linked list
     */
    public T removeFirst() {
        // If list is empty
        if (isEmpty()) {
            throw new RuntimeException("Empty list");
        }

        // Extract the data at the head and move
        // the head pointer forwards one node
        T data = head.data;
        head = head.next;
        --size;

        // If the list is empty set the tail to null
        if (isEmpty())
            tail = null;
        else
            head.prev = null;

        // Return data that was at the first node that was just removed
        return data;
    }

    /**
     * Remove the last value at the tail of the linked list, O(1)
     * if {@code isEmpty} return true,
     * @throws RuntimeException for empty linked list
     * @return last value at the tail of the linked list
     */
    public T removeLast() {
        // Can't remove data from an empty list
        if (isEmpty())
            throw new RuntimeException("Empty list");

        // Extract the data at the tail and move
        // the tail pointer backwards one node
        T data = tail.data;
        tail = tail.prev;
        --size;

        // If the list is now empty set the head to null
        if (isEmpty()) head = null;

            // Do a memory clean of the node that was just removed
        else tail.next = null;

        // Return the data that was in the last node we just removed
        return data;
    }

    /**
     * Remove an arbitrary node from the linked list, O(1)
     *
     * @param node in linked list
     * @return data at the node
     */
    private T remove(Node<T> node) {
        // If the node to remove is somewhere either at the
        // head or tail to be handled independently
        if (node.prev == null)
            return removeFirst();
        if (node.next == null)
            return removeLast();

        // Make the pointers of adjacent nodes skip over node
        node.next.prev = node.prev;
        node.prev.next = node.next;

        // Temporary store the data to return
        T data = node.data;

        // Memory cleanup
        node.data = null;
        node = node.prev = node.next = null;

        --size;

        // Return the data at the node
        return data;
    }

    /**
     * Remove a node at a particular index, O(n)
     * if index is less than 0 or greater than the list size,
     * @throws IllegalArgumentException for invalid index
     * @param index int of node to be removed
     * @return remove node data
     */
    public T removeAt(int index) {
        // Check if valid index
        if (index < 0 || index >= size)
            throw new IllegalArgumentException();

        int i;
        Node<T> trav;

        // Search from the front of the list
        if (index < size/2) {
            for (i = 0, trav = head; i != index; i++)
                trav = trav.next;
        } else {
            // Search from the back of the list
            for (i = size-1, trav = tail; i != index; i++)
                trav = trav.prev;
        }

        return remove(trav);
    }

    /**
     * Remove a value in the linked list, O(n)
     *
     * @param object to be removed
     * @return true if object was removed; otherwise false
     */
    public boolean remove(Object object) {
        Node<T> trav = head;

        // Support searching for null
        if (object == null) {
            for (trav = head; trav != null; trav = trav.next) {
                if (trav.data == null) {
                    remove(trav);
                    return true;
                }
            }
        } else {
            // Search for non null object
            for (trav = head; trav != null; trav = trav.next) {
                if (object.equals(trav.data)) {
                    remove(trav);
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Find the index of a particular value in the linked list, O(n)
     *
     * @param obj value of object
     * @return index if obj was found; otherwise -1
     */
    public int indexOf(Object obj) {
        int index = 0;
        Node<T> trav = head;

        // Support searching for null
        if (obj == null) {
            for (; trav != null; trav = trav.next, index++) {
                if (trav.data == null) {
                    return index;
                }
            }
            // Search for non null object
        } else
            for (; trav != null; trav = trav.next, index++) {
                if (obj.equals(trav.data)) {
                    return index;
                }
            }

        return -1;
    }

    /**
     * Check if a value is contained within the linked list.
     *
     * @param object value of object
     * @return true if object is in list; otherwise false
     */
    public boolean contains(Object object) {
        return indexOf(object) != -1;
    }

    /**
     * Returns all of the node in this list, as an iterator.
     * It does not implement remove() since it is optional.
     *
     * @return all of the node in this list
     */
    @Override
    public java.util.Iterator<T> iterator() {
        return new java.util.Iterator<T>() {
            private Node<T> trav = head;

            @Override
            public boolean hasNext() {
                return trav != null;
            }

            @Override
            public T next() {
                T data = trav.data;
                trav = trav.next;
                return data;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * Returns a string representation of this list.
     *
     * @return string representation of this list.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        Node<T> trav = head;
        while (trav != null) {
            sb.append(trav.data).append(", ");
            trav = trav.next;
        }
        sb.append(" ]");
        return sb.toString();
    }
}
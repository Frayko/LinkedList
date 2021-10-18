import java.io.Console;
import java.util.Iterator;

public class LoopList <T extends Comparable<T>> implements List<T>, Iterable<T> {
    private Node<T> root;
    private int size = 0;

    public LoopList() {
        root = new Node<>(null, null, null);
    }

    private class Node <T extends Comparable<T>> implements Comparable<T> {
        private T data;
        private Node<T> prev, next;

        Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public int compareTo(T o) {
            return data.compareTo(o);
        }
    }

    @Override
    public void pushBack(T data) {
        if(size == 0) {
            this.root = new Node<>(data, null, null);
            this.root.next = this.root;
            this.root.prev = this.root;
        } else {
            Node<T> buf = this.root.prev;
            this.root.prev = new Node<>(data, buf, buf.next);
            this.root.prev.prev.next = this.root.prev;
        }

        ++size;
    }

    @Override
    public void pushFront(T data) {
        if(size == 0) {
            this.root = new Node<>(data, null, null);
            this.root.next = this.root;
            this.root.prev = this.root;
        } else {
            Node<T> buf = this.root;
            this.root = new Node<>(data, buf.prev, buf);
            buf.prev.next = this.root;
            buf.prev = this.root;
        }

        ++size;
    }

    @Override
    public void insert(T data, int index) {
        if(index > size || index < 0)
            throw new NullPointerException("Выход за границы списка");

        if(index == 0) {
            pushFront(data);
        } else {
            Node<T> buf = this.root;
            for (int i = 0; i != index; ++i, buf = buf.next);
            buf = buf.prev;
            buf.next = new Node<>(data, buf, buf.next);
            buf.next.next.prev = buf.next;

            ++size;
        }
    }

    @Override
    public void remove(int index) {
        if(index >= size || index < 0)
            throw new NullPointerException("Выход за границы списка");

        if(index == 0 && size == 1) {
            this.root = null;
        } else {
            Node<T> buf = this.root;
            for (int i = 0; i != index; ++i, buf = buf.next) ;

            buf.prev.next = buf.next;
            buf.next.prev = buf.prev;

            if(index == 0)
                this.root = buf.next;
        }

        --size;
    }

    private Node<T> getNode(int index) {
        if(index >= size || index < 0)
            throw new NullPointerException("Выход за границы списка");

        Node<T> buf = this.root;
        for (int i = 0; i != index; ++i, buf = buf.next);

        return buf;
    }

    @Override
    public T get(int index) {
        if(index >= size || index < 0)
            throw new NullPointerException("Выход за границы списка");

        Node<T> buf = this.root;
        for (int i = 0; i != index; ++i, buf = buf.next);

        return buf.data;
    }

    @Override
    public void set(T data, int index) {
        if(index >= size || index < 0)
            throw new NullPointerException("Выход за границы списка");

        Node<T> buf = this.root;
        for (int i = 0; i != index; ++i, buf = buf.next);

        buf.data = data;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void sort() {
        quickSort(0, size - 1);
    }

    private void quickSort(int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(begin, end);

            quickSort(begin, partitionIndex - 1);
            quickSort(partitionIndex + 1, end);
        }
    }

    private int partition(int begin, int end) {
        T pivot = get(end);
        int i = begin - 1;

        for (int j = begin; j < end; j++) {
            if (get(j).compareTo(pivot) <= 0) {
                ++i;

                swap(getNode(i), getNode(j));
            }
        }

        swap(getNode(i + 1), getNode(end));

        return i + 1;
    }

    private void swap(Node<T> a, Node<T> b) {
        T buf = a.data;
        a.data = b.data;
        b.data = buf;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            int counter = 0;
            Node<T> buf = root;

            @Override
            public boolean hasNext() {
                return this.counter < size;
            }

            @Override
            public T next() {
                if(counter++ != 0)
                    buf = buf.next;
                return buf.data;
            }
        };
    }

    public Iterator<T> reverseIterator() {
        return new Iterator<>() {
            int counter = 0;
            Node<T> buf = root;

            @Override
            public boolean hasNext() {
                return this.counter < size;
            }

            @Override
            public T next() {
                if(counter++ != 0)
                    buf = buf.prev;
                return buf.data;
            }
        };
    }
}

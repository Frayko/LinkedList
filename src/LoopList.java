import java.io.*;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

public class LoopList <T extends Comparable<T>> implements List<T>, Iterable<T>, Consumer<T>, Serializable {
    private Node<T> root;
    private int size = 0;

    public LoopList() {
        root = new Node<>(null, null, null);
    }

    private class Node <T extends Comparable<T>> implements Comparable<T>, Serializable {
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
    public void insert(T data, int index) throws NullPointerException {
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
    public void remove(int index) throws NullPointerException {
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

    @Override
    public T get(int index) throws NullPointerException {
        if(index >= size || index < 0)
            throw new NullPointerException("Выход за границы списка");

        Node<T> buf = this.root;
        for (int i = 0; i != index; ++i, buf = buf.next);

        return buf.data;
    }

    @Override
    public void set(T data, int index) throws NullPointerException {
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
        quickSort(this.root, this.root.prev);
    }

    private void quickSort(Node<T> l, Node<T> h) {
        if(l != h) {
            Node<T> temp = partition(l, h);
            quickSort(l, temp.prev);
            quickSort(temp, h);
        }
    }

    private Node<T> partition(Node<T> l, Node<T> h) {
        T pivot = h.data;

        Node<T> i = l.prev;

        for(Node<T> j = l; j != h; j = j.next) {
            if(j.compareTo(pivot) <= 0) {
                i = (i == h) ? l : i.next;
                swap(i, j);
            }
        }
        i = (i == h) ? l : i.next;
        swap(i, h);
        return i;
    }

    private void swap(Node<T> a, Node<T> b) {
        T buf = a.data;
        a.data = b.data;
        b.data = buf;
    }

    @Override
    public void accept(T t) {
        System.out.println(t);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        for(T data : this)
            action.accept(data);
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
                buf = buf.prev;
                counter++;
                return buf.data;
            }
        };
    }

    public void save(File file) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file, false));

        Node<T> buf = root;

        oos.writeInt(size);
        for(int i = 0; i < size; ++i, buf = buf.next)
            oos.writeObject(buf);
        oos.close();
    }

    public void load(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));

        this.root = null;
        this.size = 0;

        int counts = ois.readInt();
        for(int i = 0; i < counts; ++i) {
            Node<T> tmp = (Node<T>) ois.readObject();
            pushBack(tmp.data);
        }
    }
}

import java.io.*;
import java.util.Iterator;

public class LoopList<T> implements List<T>, Serializable {
    private Node root;
    private int size;

    public LoopList() {
        root = null;
        size = 0;
    }

    private class Node implements Serializable {
        private T data;
        private Node prev, next;

        Node(T data, Node prev, Node next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    '}';
        }
    }

    @Override
    public void pushBack(T data) {
        if(size == 0) {
            this.root = new Node(data, null, null);
        }
        else {
            Node tmp = this.root;
            for(;tmp.next != null; tmp = tmp.next);
            tmp.next = new Node(data, tmp, null);
        }

        ++size;
    }

    @Override
    public void pushFront(T data) {
        this.root = new Node(data, null, this.root);
        if(this.root.next != null)
            this.root.next.prev = this.root;

        ++size;
    }

    @Override
    public void insert(T data, int index) throws NullPointerException {
        if(index > size || index < 0)
            throw new NullPointerException("Выход за границы списка");

        if(index == 0) {
            pushFront(data);
        }
        else if(index == size) {
            pushBack(data);
        }
        else {
            Node buf = this.root;

            for (int i = 0; i < index - 1; ++i, buf = buf.next);

            buf.next = new Node(data, buf, buf.next);
            if (buf.next.next != null)
                buf.next.next.prev = buf.next;

            ++size;
        }
    }

    @Override
    public void remove(int index) throws NullPointerException {
        if(index >= size || index < 0)
            throw new NullPointerException("Выход за границы списка");

        if(index == 0) {
            if(size == 1) {
                this.root = null;
            }
            else {
                this.root = this.root.next;
                this.root.prev = null;
            }
        }
        else {
            Node buf = this.root;
            for (int i = 0; i < index - 1; ++i, buf = buf.next);

            buf.next = buf.next.next;
            if(buf.next != null)
                buf.next.prev = buf;
        }

        --size;
    }

    private Node getNode(int index) throws NullPointerException {
        if(index >= size || index < 0)
            throw new NullPointerException("Выход за границы списка");

        Node buf = this.root;
        for (int i = 0; i != index; ++i, buf = buf.next);

        return buf;
    }

    @Override
    public T get(int index) throws NullPointerException {
        if(index >= size || index < 0)
            throw new NullPointerException("Выход за границы списка");

        Node buf = this.root;
        for (int i = 0; i != index; ++i, buf = buf.next);

        return buf.data;
    }

    @Override
    public void set(T data, int index) throws NullPointerException {
        if(index >= size || index < 0)
            throw new NullPointerException("Выход за границы списка");

        Node buf = this.root;
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
    public void sort(Comparator comparator) {
        this.root = mergeSort(this.root, comparator);
    }

    public Node split(Node node) {
        Node slow = node;
        Node fast = node.next;

        while (fast != null) {
            fast = fast.next;
            if (fast != null) {
                slow = slow.next;
                fast = fast.next;
            }
        }

        return slow;
    }

    public Node merge(Node x, Node y, Comparator comparator) {
        if (x == null) {
            return y;
        }

        if (y == null) {
            return x;
        }

        if (comparator.compare(x.data, y.data) <= 0) {
            x.next = merge(x.next, y, comparator);
            x.next.prev = x;
            x.prev = null;
            return x;
        }
        else {
            y.next = merge(x, y.next, comparator);
            y.next.prev = y;
            y.prev = null;
            return y;
        }
    }

    public Node mergeSort(Node node, Comparator comparator) {
        if (node == null || node.next == null) {
            return node;
        }

        Node x = node, y;

        Node slow = split(node);
        y = slow.next;
        slow.next = null;

        x = mergeSort(x, comparator);
        y = mergeSort(y, comparator);

        node = merge(x, y, comparator);
        return node;
    }

    public void forEach(Action<T> action) {
        if(size > 0) {
            Node node = this.root;
            do {
                action.toDo(node.data);
                node = node.next;
            } while (node != null);
        } else {
            System.out.println("Нет элементов в массиве");
        }
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int counter = 0;
            Node buf = root;

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
            Node buf = root;

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

        Node buf = root;

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
            Node tmp = (Node) ois.readObject();
            pushBack(tmp.data);
        }
    }
}

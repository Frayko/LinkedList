import java.io.*;
import java.util.Iterator;

public class LoopList<T> implements List<T>, Serializable {
    private Node root;
    private int size = 0;

    public LoopList() {
        root = new Node(null, null, null);
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
            this.root.next = this.root;
            this.root.prev = this.root;
        } else {
            Node buf = this.root.prev;
            this.root.prev = new Node(data, buf, buf.next);
            this.root.prev.prev.next = this.root.prev;
        }

        ++size;
    }

    @Override
    public void pushFront(T data) {
        if(size == 0) {
            this.root = new Node(data, null, null);
            this.root.next = this.root;
            this.root.prev = this.root;
        } else {
            Node buf = this.root;
            this.root = new Node(data, buf.prev, buf);
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
            Node buf = this.root;
            for (int i = 0; i != index; ++i, buf = buf.next);
            buf = buf.prev;
            buf.next = new Node(data, buf, buf.next);
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
            Node buf = this.root;
            for (int i = 0; i != index; ++i, buf = buf.next) ;

            buf.prev.next = buf.next;
            buf.next.prev = buf.prev;

            if(index == 0)
                this.root = buf.next;
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
        quickSort(this.root, this.root.prev, comparator);
    }

    private void quickSort(Node l, Node h, Comparator comparator) {
        if(l != h) {
            Node temp = partition(l, h, comparator);
            quickSort(l, temp.prev, comparator);
            quickSort(temp, h, comparator);
            //quickSort(temp.next, h, comparator);
        }
    }

    private Node partition(Node l, Node h, Comparator comparator) {
        T x = h.data;

        Node i = l.prev;

        for(Node j = l; j != h; j = j.next) {
            if(comparator.compare(j.data, x) <= 0) {
                i = (i == h) ? l : i.next;
                swap(i, j);
            }
        }
        i = (i == h) ? l : i.next;
        swap(i, h);
        return i;
    }

    private void swap(Node x, Node y) {
        if(x == y) {
            return;
        }

        Node x_prev_tmp = x.prev;
        Node x_next_tmp = x.next;
        Node y_prev_tmp = y.prev;
        Node y_next_tmp = y.next;

        if (x.next == y) {
            //works?? no, bugs
            x_prev_tmp.next = y;
            y.prev = x_prev_tmp;
            y_next_tmp.next = x;
            x.next = y_next_tmp;
            x.prev = y;
            y.next = x;
        }
//        else if (y.next == x) {
//            y_prev_tmp.next = x;
//            x.prev = y_prev_tmp;
//            x_next_tmp.next = y;
//            y.next = x_next_tmp;
//            y.prev = x;
//            x.next = y;
//        }
        else {
            y.prev = x_prev_tmp;
            y.next = x_next_tmp;
            x_next_tmp.prev = y;
            x_prev_tmp.next = y;

            x.prev = y_prev_tmp;
            x.next = y_next_tmp;
            y_next_tmp.prev = x;
            y_prev_tmp.next = x;
        }
    }

    public void forEach(Action<T> action) {
        if(size > 0) {
            Node node = this.root;
            do {
                action.toDo(node.data);
                node = node.next;
            } while (node != this.root);
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

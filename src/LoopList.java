public class LoopList <T extends Comparable<T>> implements List<T> {
    private Node<T> root;
    private int size = 0;

    public LoopList() {
        root = new Node<T>(null, null, null);
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
    public boolean pushBack(T data) {
        return false;
    }

    @Override
    public boolean pushFront(T data) {
        return false;
    }

    @Override
    public boolean insert(T data, int index) {
        return false;
    }

    @Override
    public boolean remove(int index) {
        return false;
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public boolean set(T data, int index) {
        return false;
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

    }
}

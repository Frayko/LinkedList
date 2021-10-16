public interface List<T> {
    boolean pushBack(T data);
    boolean pushFront(T data);
    boolean insert(T data, int index);
    boolean remove(int index);
    T get(int index);
    boolean set(T data, int index);
    boolean isEmpty();
    int getSize();
    void sort();
}

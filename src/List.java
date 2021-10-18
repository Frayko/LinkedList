public interface List<T> {
    void pushBack(T data);
    void pushFront(T data);
    void insert(T data, int index);
    void remove(int index);
    T get(int index);
    void set(T data, int index);
    boolean isEmpty();
    int getSize();
    void sort();
}

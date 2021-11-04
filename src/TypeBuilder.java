public interface TypeBuilder {
    String getTypeName();
    Object create();
    Comparator getTypeComparator();
}

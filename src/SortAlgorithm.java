import java.util.Comparator;

public interface SortAlgorithm<T> {
    void sort(T[] array, Comparator<? super T> comparator);
}

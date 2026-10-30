import java.util.Comparator;

public class BubbleSort<T> implements SortAlgorithm<T> {

    @Override
    public void sort(T[] array, Comparator<? super T> comparator) {

        for (int i = 0; i < array.length - 1; i++) {

            for (int j = 0; j < array.length - 1 - i; j++) {
                if (comparator.compare(array[j], array[j + 1]) > 0) {
                    T temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
}
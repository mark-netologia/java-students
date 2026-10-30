import java.util.Comparator;

public class SelectionSort<T> implements SortAlgorithm<T> {

    @Override
    public void sort(T[] array, Comparator<? super T> comparator) {

        for (int i =0; i<array.length-1; i++){
            int minIndex = i;
            for (int j = i+1; j < array.length; j++) {
                if (comparator.compare(array[minIndex], array[j]) > 0){
                    minIndex = j;
                }
            }
            T tmp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = tmp;
        }

    }
}
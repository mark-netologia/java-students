import java.util.Arrays;
import java.util.stream.Stream;

public class MarkArrayList<T> {
    private Object[] elements;
    private int size;

    private static final int DEFAULT_CAPACITY = 4;

    //конструктор
    public MarkArrayList() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public int size() {
        return this.size;
    }

    //добавление элемента с проверкой заполнения
    public void add(T element) {
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        }
        elements[size] = element;
        size++;
    }

    //получение элемента списка
    public T get(int index) {
        if (index >= 0 && index < size) {
            return (T) elements[index];
        } else {
            throw new IndexOutOfBoundsException("Неверный индекс " + index);
        }
    }

    //удаление элемента
    public void remove(int index) {
        if (index >= 0 && index < size) {
            for (int i = index; i < size - 1; i++) {
                elements[i] = elements[i + 1];
            }
            size--;
            elements[size] = null;
        } else {
            throw new IndexOutOfBoundsException("Неверный индекс " + index);
        }
    }

    //реализация Stream
    public Stream<T> stream() {
        return Arrays.stream(elements, 0, size)
                .map(element -> (T) element);
    }

    //реализация isEmpty
    public boolean isEmpty() {
        return size == 0;
    }
}

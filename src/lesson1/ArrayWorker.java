package lesson1;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayWorker<T extends java.io.Serializable> {
    private T[] arr;

    public ArrayWorker(T[] arr) {
        this.arr = arr;
    }

    public void switchRandomTwo(int firstIndex, int secondIndex) {
        if (firstIndex > arr.length || secondIndex > arr.length) {
            System.out.println("Индексы выходят за пределы массива");
            return;
        }

        T buffer = arr[firstIndex];
        arr[firstIndex] = arr[secondIndex];
        arr[secondIndex] = buffer;
    }

    public T[] getArr() {
        return arr;
    }

    public void printArr() {
        System.out.println(Arrays.toString(arr));
    }

    public ArrayList<T> getList() {
        return new ArrayList<>(Arrays.asList(arr));
    }


    public void printList(ArrayList<T> list) {
        System.out.print("[ ");
        for (T item: list) {
            System.out.printf("%s ", item);
        }
        System.out.print("]");
        System.out.println();
    }
}

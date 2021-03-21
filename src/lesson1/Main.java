package lesson1;

import lesson1.fruits.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        doTask();
    }

    private static void doTask() {
        switchArrayItems();
        arrayToList();
        workWithBoxes();
    }

    private static void switchArrayItems() {
        System.out.println("Поменять местами 2 элемента массива");
        ArrayWorker<Integer> arr1 = new ArrayWorker<>(new Integer[]{4,5,23,42,2,54,1});
        System.out.println("Исходный массив:");
        arr1.printArr();
        arr1.switchRandomTwo(2, 4);
        System.out.println("Изменённый массив:");
        arr1.printArr();
    }

    private static void arrayToList() {
        System.out.println("Преобразовать массив в Аrrау лист");
        ArrayWorker<String> arr = new ArrayWorker<>(new String[]{"one", "two", "three", "four", "five"});
        System.out.println("Массив");
        arr.printArr();
        ArrayList<String> list = arr.getList();
        System.out.println("Лист");
        arr.printList(list);
    }

    private static void workWithBoxes() {
        Box<Apple> appleBox1 = new Box<>(new Apple[] {
                new Apple(),
                new Apple(),
                new Apple(1.2F),
                new Apple(0.9F),
        });

        Box<Apple> appleBox2 = new Box<>(new Apple[] {
                new Apple(),
                new Apple(1.1F),
                new Apple(),
                new Apple(),
        });

        Box<Orange> orangeBox1 = new Box<>(new Orange[] {
                new Orange(),
                new Orange(1.8F),
                new Orange(),
                new Orange(2.0F),
        });

        Box<Orange> orangeBox2 = new Box<>(new Orange[] {
                new Orange(),
                new Orange(1.6F),
                new Orange(),
                new Orange(),
        });
        appleBox1.printBox();
        System.out.println("Добавим фрукт");
        appleBox1.addFruit(new Apple(1.2F));
        appleBox1.printBox();

        System.out.printf("Вес коробки яблок: %s%n", appleBox1.getWeight());
        System.out.printf("Вес коробки апельсинов: %s%n", orangeBox1.getWeight());

        if (appleBox1.compare(orangeBox1)) {
            System.out.println("Коробка с яблоками и апельсинами весят одинаково");
        }
        else {
            System.out.println("Коробка с яблоками и апельсинами имеют разный вес");
        }

        System.out.println("Пересыпем фрукты их одной коробки в другую");
        System.out.println("Коробка 1");
        appleBox1.printBox();
        System.out.println("Коробка 2");
        appleBox2.printBox();
        System.out.println("Пересыпаем");
        appleBox1.addFromBox(appleBox2);
        System.out.println("Коробка 1");
        appleBox1.printBox();
        System.out.println("Коробка 2");
        appleBox2.printBox();
    }
}

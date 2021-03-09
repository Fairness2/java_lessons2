package lesson1;

import lesson1.fruits.Fruit;

import java.util.ArrayList;
import java.util.Arrays;

public class Box<T extends Fruit> {
    private final ArrayList<T> fruits;

    public Box() {
        fruits = new ArrayList<>();
    }

    public Box(T[] arr) {
        fruits = new ArrayList<>(Arrays.asList(arr));
    }

    public ArrayList<T> getFruits() {
        return fruits;
    }

    public float getWeight() {
        float weight = 0;
        for (T fruit: fruits) {
            weight += fruit.getWeight();
        }
        return weight;
    }

    public boolean compare (Box<?> anyBox) {
        return this.getWeight() == anyBox.getWeight();
    }

    public void addFruit(T fruit) {
        fruits.add(fruit);
    }

    public void addFromBox(Box<T> anyBox) {
        fruits.addAll(anyBox.getFruits());
        anyBox.clear();
    }

    public void clear() {
        fruits.clear();
    }

    public void printBox() {
        if (fruits.size() == 0) {
            System.out.println("Ящик пуст");
        }
        else {
            System.out.println("Ящик содержит:");
            for (T fruit: fruits) {
                System.out.println(fruit.toString());
            }
        }

    }
}
